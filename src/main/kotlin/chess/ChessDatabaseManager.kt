package chess

import DatabaseType
import KConnection
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Record1
import org.jooq.Result
import org.jooq.SQLDialect
import org.jooq.Table
import org.jooq.impl.DSL
import org.jooq.impl.DSL.and
import org.jooq.impl.DSL.asterisk
import java.sql.Connection

class ChessDatabaseManager {

    fun insertGame(newChessGame: ChessGame) = chessGameManager.insertGame(newChessGame)

    val chessGameSchema by lazy { "chess_games" }
    private val chessGameManager by lazy { ChessGameManager(this) }
    internal val chessPlayerManager by lazy { ChessPlayerManager(this) }
    internal val chessMoveManager by lazy { ChessMoveManager(this) }

    internal var dslContext: DSLContext? = null
    internal var connection: Connection? = null





    companion object {

        fun init(): ChessDatabaseManager {
            val chessDatabaseManager = ChessDatabaseManager()
            chessDatabaseManager.connection = KConnection.create(
                databaseType = DatabaseType.MY_SQL,
                baseUrl = "localhost",
                portNumber = 3306,
                username = "root",
                password = "",
            )
            chessDatabaseManager.dslContext = DSL.using(chessDatabaseManager.connection, SQLDialect.MYSQL)
            return chessDatabaseManager
        }

    }

}

