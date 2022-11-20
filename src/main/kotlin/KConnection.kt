import java.sql.Connection
import java.sql.DriverManager

class KConnection { companion object {

    fun create(
        databaseType: DatabaseType,
        baseUrl: String,
        portNumber: Int,
        database: String? = null,
        username: String,
        password: String
    ): Connection {
        return DriverManager.getConnection("jdbc:${databaseType.asConnectionString()}://$baseUrl:$portNumber${if (database != null) "/$database" else ""}",
            username, password
        )
    }
}

}
