import org.apache.commons.csv.CSVFormat
import java.io.File
import java.lang.reflect.Field
import java.sql.Connection
import java.util.Arrays

internal fun getStandardCsvReader(): CSVFormat? = CSVFormat.Builder.create(CSVFormat.DEFAULT).apply {
    setIgnoreSurroundingSpaces(true)
}.build()

private val DUMMY_OBJECT: Any = object {}

fun readResourceFile(filePath: String): File {
    val resource = DUMMY_OBJECT.javaClass.getResource(filePath);
    return File(DUMMY_OBJECT.javaClass.getResource(filePath)!!.toURI())
}

inline fun <reified T> getFieldList() = T::class.java.declaredFields.toList()
