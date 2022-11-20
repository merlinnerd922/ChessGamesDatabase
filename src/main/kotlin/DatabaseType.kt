import java.lang.IllegalArgumentException

enum class DatabaseType { MY_SQL;

    fun asConnectionString(): String {
        when (this) {
            MY_SQL -> { return "mysql" }
            else -> { throw IllegalArgumentException("TODO")}
        }
    }

}
