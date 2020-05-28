package xyz.rieproject.cores

import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import org.apache.logging.log4j.Logger
import org.bson.conversions.Bson
import org.litote.kmongo.*
import xyz.rieproject.Config
import xyz.rieproject.models.GuildModel
import xyz.rieproject.utils.CConsole
import java.lang.management.ManagementFactory
import java.util.*

class ConnectionManager {
    val console: Logger = CConsole(ManagementFactory.getRuntimeMXBean().name, this.javaClass).sfl4jlogger
    init {
        val creds = MongoCredential.createScramSha256Credential(Config.MONGO_USER, "rie", Config.MONGO_PASS)
        mongoClient = KMongo.createClient(
                MongoClientSettings.builder()
                    .credential(creds)
                    .applyToClusterSettings {
                        console.info("Applying configuration to Cluster Settings..")
                        it.hosts(Arrays.asList(
                            ServerAddress(Config.MONGO_URI, Config.MONGO_PORT)
                        ))
                    }.build()
                )
        database = mongoClient.getDatabase("rie")
        console.info("Database connected! Verified with ${Config.MONGO_USER} username via \"rie\" database!")
    }

    fun getDatabase(): MongoDatabase {
        return database
    }

    inline fun <reified T: Any> getCollection(): MongoCollection<T> {
        return database.getCollection<T>()
    }

    inline fun <reified T: Any> getCollection(collection_name: String): MongoCollection<T> {
        return database.getCollection<T>(collection_name)
    }

    inline fun <reified T: Any> get(_id: String): T? {
        return getCollection<T>().findOne(eq("_id", _id))
    }

    inline fun <reified T: Any> set(_id: String, data: T): InsertOneResult? {
        var result: InsertOneResult? = null
        if (!exists<T>(_id)) {
            result = getCollection<T>().insertOne(data)
        } else {
            getCollection<T>().updateOneById(_id, data)
        }
        return result
    }

    inline fun <reified T: Any> set(_id: String, key: String, value: String): UpdateResult? {
        var result: UpdateResult? = null
        if (exists<T>(_id)) {
            result = getCollection<T>().updateOne("{_id:'$_id'}", "{\$set:{$key:'$value'}}")
        } else {
            console.error("Use 'set(id, data)' for insert instead of 'set(id, key, value)' you damn moron!")
        }
        return result
    }

    inline fun <reified T: Any> update(filter: Bson, data: Bson): UpdateResult? {
        val col = getCollection<T>()
        return col.updateOne(filter, data)
    }

    inline fun <reified T: Any> delete(_id: String): DeleteResult? {
        return getCollection<T>().deleteOne("{_id:'$_id'}")
    }

    inline fun <reified T: Any> delete(_id: List<String>): MutableList<DeleteResult>? {
        val result = mutableListOf<DeleteResult>()
        _id.forEach {
            val res = delete<T>(it)
            if (res != null) {
                result.add(res)
            }
        }
        return result
    }

    inline fun <reified T: Any> exists(_id: String): Boolean {
        get<T>(_id) ?: return false
        return true
    }

    companion object {
        lateinit var mongoClient: MongoClient
        lateinit var database: MongoDatabase
    }
}