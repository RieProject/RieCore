package xyz.rieproject.cores

import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import org.apache.logging.log4j.Logger
import org.litote.kmongo.KMongo
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import org.litote.kmongo.updateOneById
import xyz.rieproject.Config
import xyz.rieproject.utils.CConsole
import java.lang.management.ManagementFactory
import java.util.*

class ConnectionManager {
    private val console: Logger = CConsole(ManagementFactory.getRuntimeMXBean().name, this.javaClass).sfl4jlogger
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

    inline fun <reified T: kotlin.Any> getCollection(): MongoCollection<T> {
        return database.getCollection<T>()
    }

    inline fun <reified T: kotlin.Any> getCollection(collection_name: String): MongoCollection<T> {
        return database.getCollection<T>(collection_name)
    }

    inline fun <reified T: Any> get(_id: String): T? {
        return getCollection<T>().findOne(eq("_id", _id))
    }

    inline fun <reified T: Any> set(_id: String, data: T): T? {
        var oldData = get<T>(_id)
        if (oldData === null) {
            getCollection<T>().insertOne(data)
        } else {
            oldData = data
            getCollection<T>().updateOneById(_id, data)
        }
        return oldData
    }

    companion object {
        lateinit var mongoClient: MongoClient
        lateinit var database: MongoDatabase
    }
}