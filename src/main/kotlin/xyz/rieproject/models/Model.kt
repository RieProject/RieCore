package xyz.rieproject.models

import com.mongodb.client.FindIterable
import org.bson.Document
import org.bson.conversions.Bson
import xyz.rieproject.cores.ConnectionManager

abstract class Model<T>(val colname: String) {
    val collection = ConnectionManager.mongoClient.getDatabase("rie").getCollection(colname.toLowerCase())
    abstract val data: T

    fun find(): FindIterable<Document> {
        return collection.find()
    }

    fun filter(filter: String): FindIterable<Document> {
        val bson = Document.parse(filter)
        return find().filter(bson)
    }
}