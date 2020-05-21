package xyz.rieproject.cores

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import xyz.jdev.utils.ExitStatus
import xyz.rieproject.utils.CConsole
import java.lang.management.ManagementFactory
import java.sql.Connection
import java.sql.SQLInvalidAuthorizationSpecException
import java.sql.SQLNonTransientConnectionException
import java.sql.SQLSyntaxErrorException
import kotlin.system.exitProcess
import kotlin.system.measureTimeMillis

class ConnectionManager(val DB_URL: String?, val DB_USER: String?, val DB_PASS: String?) {
    private val console = CConsole(ManagementFactory.getRuntimeMXBean().name, this.javaClass).sfl4jlogger
    lateinit var datasource: HikariDataSource
    lateinit var connection: Connection
    private val config = HikariConfig()

    init {
        initialize()
    }

    private fun connect() {
        console.info("Connecting to the connection... ")
        val milli = measureTimeMillis {
            if (DB_URL?.isEmpty() != false) {
                datasource = HikariDataSource()
                connection = datasource.connection
            } else {
                config.jdbcUrl = DB_URL
                config.username = DB_USER
                config.password = DB_PASS
                datasource = HikariDataSource(config)
                connection = datasource.connection
            }
        }
        console.info("Connection established! (${milli}ms)")
    }
    private fun initialize() {
        try {
            connect()
        } catch (ex: SQLInvalidAuthorizationSpecException) {
            console.error(ex.message)
            exitProcess(ExitStatus.SQL_INVALID_PASSWORD.code)
        } catch (ex: SQLNonTransientConnectionException) {
            console.error(ex.message)
            exitProcess(ExitStatus.SQL_UNKNOWN_HOST.code)
        } catch (ex: SQLSyntaxErrorException) {
            console.error(ex.message)
            exitProcess(ExitStatus.SQL_UNKNOWN_DATABASE.code)
        }
    }

}