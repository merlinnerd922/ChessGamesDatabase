package chess

import getFieldList
import getTableColumnNames
import mapToSQLFields
import org.jooq.Configuration
import org.jooq.Field
import org.jooq.Record
import org.jooq.Table
import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType


class ChessGameManager(cdm: ChessDatabaseManager) {



    private val moveManager by lazy {cdm.chessMoveManager}
    private val playerManager by lazy { cdm.chessPlayerManager }
    private val connection by lazy { cdm.connection }
    private val dslContext by lazy { cdm.dslContext }

    private val chessGamesSchema = "chess_games"

    private val gamesTable: Table<Record> by lazy { DSL.table("$chessGamesSchema.games") }

    fun insertGame(newChessGame: ChessGame) {

        val insertQueryColumnNames = mutableListOf<String>()
        val columnValues = mutableListOf<Any?>()

        for (field in getFieldList<ChessGame>()) {
            field.isAccessible = true
            val fieldName = field.name
            val fieldValue = field.get(newChessGame)
            if (fieldValue is ArrayList<*>) {
                continue;
            } else if (fieldName in listOf("whitePlayerName", "blackPlayerName")) {
                columnValues.add(playerManager.getOrCreatePlayerId(fieldValue as String))
                insertQueryColumnNames.add(if (fieldName == "whitePlayerName") "white_player_id" else "black_player_id")
            } else {
                println("$fieldName: $fieldValue")
                insertQueryColumnNames.add(fieldName)
                columnValues.add(fieldValue)
            }
        }

        val map: Array<Field<Any>?> =
            mapToSQLFields(
                originalPOJOVariableNames = insertQueryColumnNames,
                existingColumnNames = dslContext!!.getTableColumnNames("games")
            )
        var newId: Int? = insertGameEntry(map, columnValues)

        newChessGame.id = newId
        insertGameMoves(newChessGame)
    }


    private fun insertGameEntry(
        map: Array<Field<Any>?>,
        columnValues: MutableList<Any?>
    ): Int? {
        var newId: Int? = null
        dslContext!!.transaction { trx: Configuration ->
            trx.dsl().insertInto(this.gamesTable, *map).values(*columnValues.toTypedArray()).execute()

            newId = DSL.using(connection).select(DSL.field("last_insert_id()", SQLDataType.INTEGER)).
                fetchOne()!!.get(0) as Int
        }
        return newId
    }

    private fun insertGameMoves(newChessGame: ChessGame) {
        for (move in newChessGame.moves!!) {
            moveManager.insertIntoMovesTable(move.whiteMove!!.also { it.gameId = newChessGame.id })
            moveManager.insertIntoMovesTable(move.blackMove!!.also { it.gameId = newChessGame.id })
        }
    }

}
