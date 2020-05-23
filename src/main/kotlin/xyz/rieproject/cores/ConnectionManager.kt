package xyz.rieproject.cores

import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.apache.logging.log4j.Logger
import xyz.rieproject.Config
import xyz.rieproject.utils.CConsole
import java.lang.management.ManagementFactory
import java.util.*

class ConnectionManager {
    private val console: Logger = CConsole(ManagementFactory.getRuntimeMXBean().name, this.javaClass).sfl4jlogger
    init {
        val creds = MongoCredential.createScramSha256Credential(Config.MONGO_USER, "rie", Config.MONGO_PASS)
        mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                    .credential(creds)
                    .applyToClusterSettings {
                        console.info("Applying configuration to Cluster Settings..")
                        it.hosts(Arrays.asList(
                            ServerAddress(Config.MONGO_URI, Config.MONGO_PORT)
                        ))
                    }.build()
                )
        console.info("Database connected! Verified with ${Config.MONGO_USER} username via \"rie\" database!")
    }

    fun getDatabase(): MongoDatabase {
        database = mongoClient.getDatabase("rie")
        return database
    }

    companion object {
        lateinit var mongoClient: MongoClient
        lateinit var database: MongoDatabase
    }
}