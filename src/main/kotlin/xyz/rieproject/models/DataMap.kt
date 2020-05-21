package xyz.rieproject.models

import com.zaxxer.hikari.HikariDataSource
import xyz.rieproject.utils.CConsole
import java.lang.management.ManagementFactory
import java.lang.reflect.Method
import java.sql.Connection
import java.sql.SQLClientInfoException
import java.sql.SQLDataException
import java.sql.SQLException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import java.util.TimerTask
import kotlin.system.measureTimeMillis

class DataMap(name: String,
              private val dataSource: HikariDataSource,
              private var backup: Boolean = false,
              backupInterval: Int? = null
): HashMap<String, String>() {
    var name: String = name.toLowerCase()
    var ready: Boolean = false
    var backupDataMap: DataMap? = null
    private val backupTimer: Timer = Timer()
    private val refreshTimer: Timer = Timer()
    var backupIntervalInMinutes: Long? = backupInterval?.times(60000)?.toLong()
    var refreshIntervalInMinutes: Long? = 5.times(60000).toLong()
    private val connection: Connection = dataSource.connection
    private val console = CConsole(ManagementFactory.getRuntimeMXBean().name, this.javaClass).sfl4jlogger
    private val tableTemplate = "CREATE TABLE IF NOT EXISTS $name (" +
            "id SERIAL PRIMARY KEY," +
            "key TEXT NOT NULL," +
            "value TEXT," +
            "created TIMESTAMP," +
            "edited TIMESTAMP" +
            ");"

    init {
        try {
            console.info("Reading data from $name...")
            val millis = measureTimeMillis {
                fetchEverything()
            }
            console.info("Reading data complete! ($millis)")
        } catch (e: SQLException) {
            console.error(e.message)
        } catch (e: SQLDataException) {
            console.error(e.message)
        } catch (e: SQLClientInfoException) {
            console.error(e.message)
        }
        if (backup) {
            console.info("Preparing backup table...")
            this.prepareBackup()
            if (backupInterval != null) {
                backupTimer.scheduleAtFixedRate(object : TimerTask() {
                    override fun run() {
                        console.info("Backing up table $name...")
                        val milli = measureTimeMillis {
                            autoBackup()
                        }
                        console.info("Backup complete! ($milli)")
                    }
                }, 0, backupIntervalInMinutes as Long)
            }
        }
        ready = true
        console.info("Database load complete!")

        refreshTimer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                console.info("Altering table $name...")
                clear()
                console.info("Fetching new data from $name database...")
                val milli = measureTimeMillis {
                    fetchEverything()
                }
                console.info("Refresh complete! ($milli)")
            }
        }, 0, refreshIntervalInMinutes as Long)
    }

    override fun get(key: String): String? {
        if (!this.readyCheck()) return "Instance not ready!"
        val loweredKey = key.toLowerCase()
        val value = super.get("$name:$loweredKey")
        console.debug("Fetching table $name, key $loweredKey, value $value...")
        return value
    }
    override fun put(key: String, value: String): String? {
        val statement: String = if (this.keyChecking(key)) {
            console.debug("Updating table $name, key ${key.toLowerCase()}, value $value...")
            "UPDATE $name SET value='$value', edited=now() WHERE key='${key.toLowerCase()}'"
        } else {
            console.debug("Inserting table $name, key ${key.toLowerCase()}, value $value...")
            "INSERT INTO $name (key, value, created, edited) VALUES ('${key.toLowerCase()}', '$value', now(), now());"
        }
        connection.prepareStatement(statement).execute()
        return super.put("$name:${key.toLowerCase()}", value)
    }
    override fun remove(key: String): String? {
        val statement = "DELETE FROM $name WHERE key='$key'"
        console.debug("Deleting table $name, key ${key.toLowerCase()}, value ${super.get("$name:${key.toLowerCase()}")}...")
        connection.prepareStatement(statement).executeUpdate()
        return super.remove("$name:${key.toLowerCase()}")
    }

    fun keyChecking(key: String): Boolean {
        var found: Boolean = false
        console.debug("Checking key, table $name, key $key, value ${super.get("$name:${key.toLowerCase()}")}...")
        val expArr: List<String> = fetchKey()
        console.debug("Generated experimental ARR is ${expArr.joinToString(", ")}")
        for (value in expArr) {
            if (value == key) {
                found = true
                break
            }
        }
        console.debug("Key existence is $found!")
        return found
    }

    protected fun autoBackup() {
        val statement = "SELECT * FROM $name;"
        val prepareQuery = connection.prepareStatement(statement)
        val rows = prepareQuery.executeQuery()
        while(rows.next()) {
            this.backupDataMap?.set(rows.getString("key"), rows.getString("value"))
        }
    }
    protected fun stopBackup() {
        console.info("Stopping backup interval...")
        backupTimer.cancel()
        backup = false
        console.info("Backup interval stopped.")
    }
    protected fun isBackingUp(): Boolean {
        return backup
    }

    /**
     * Private Methods!
     */
    private fun fetchEverything() {
        val earlyQuery = connection.prepareStatement(tableTemplate)
        try {
            earlyQuery.execute()
        } catch (e: SQLException) {
            console.error(e.message)
        }
        val statement = "SELECT * FROM $name;"
        val prepareQuery = connection.prepareStatement(statement)
        val rows = prepareQuery.executeQuery()
        while(rows.next()) {
            val key = rows.getString("key").toLowerCase()
            val value = rows.getString("value")
            console.debug("Appending $key with value $value to the DataMap $name...")
            super.put("$name:$key", value)
        }
    }
    private fun prepareBackup() {
        backupDataMap = DataMap(
            "internal::backup_$name",
            dataSource
        )
        this.autoBackup()
    }
    private fun readyCheck(): Boolean {
        return ready
    }

    /**
     * Protected methods!
     */
    protected fun fetchKey(): List<String> {
        val statement = "SELECT key FROM $name"
        val query = connection.prepareStatement(statement)
        val rows = query.executeQuery()
        return rows.use {
            generateSequence {
                if (rows.next()) rows.getString("key") else null
            }.toList()
        }
    }
    protected fun array(): ArrayList<String> {
        return ArrayList(this.values)
    }
    protected fun keyArray(): ArrayList<String> {
        return ArrayList(this.keys)
    }
    fun map(func: Method): ArrayList<Any> {
        val arr = ArrayList<Any>(this.size)
        var i = 0
        for (value in this.values) {
            for (key in this.keys) {
                arr[i++] = func(value, key, this)
            }
        }
        return arr
    }
    protected fun cloneInstance(): DataMap {
        return DataMap(
            "cloned_$name",
            dataSource
        )
    }
    protected fun concat(vararg datamaps: DataMap): DataMap {
        val newColl = this.cloneInstance()
        for (coll in datamaps) {
            for (value in coll.values) {
                for (key in coll.keys) {
                    newColl[key] = value
                }
            }
        }
        return newColl
    }
}
