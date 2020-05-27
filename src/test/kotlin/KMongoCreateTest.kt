import org.bson.types.ObjectId
import org.litote.kmongo.getCollection
import xyz.rieproject.cores.ConnectionManager

object KMongoCreateTest {
    data class Test(val _id: ObjectId, val data: String, val more_data: String)

    @JvmStatic
    fun main(args: Array<String>) {
        val con = ConnectionManager()
        val database = con.getDatabase()
        val col = database.getCollection<Test>()

        col.insertOne(Test(ObjectId(), "uwu", "more uwus"))
    }
}