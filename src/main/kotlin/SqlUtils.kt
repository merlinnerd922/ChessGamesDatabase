import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.impl.DSL
import java.sql.SQLException

internal fun lenientMatchToFields(columnNames: List<String>): (String) -> org.jooq.Field<Any>? {
    return function(columnNames)
}

private fun function(columnNames: List<String>): (String) -> Field<Any> =
    {
        val field: Field<Any> = if (it in columnNames) {
            DSL.field(it)
        } else if (it.toSnakeCase() in columnNames) {
            DSL.field(it.toSnakeCase())
        } else {
            throw SQLException("The field '$it' does not exist!")
        }
        field
    }

public fun DSLContext.getTableColumnNames(tableName: String) =
    informationSchema(meta().getTables(tableName)[0]).columns.map { it.columnName }


fun mapToSQLFields(
    originalPOJOVariableNames: MutableList<String>,
    existingColumnNames: List<String>
) = originalPOJOVariableNames.map(lenientMatchToFields(existingColumnNames)).toTypedArray()