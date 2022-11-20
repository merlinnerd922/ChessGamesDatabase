package chess

import chess.enums.ChessPiece
import lenientMatchToFields
import getTableColumnNames
import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.impl.DSL
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class ChessMoveManager(chessDatabaseManager: ChessDatabaseManager) {

    private val chessGamesSchema by lazy { chessDatabaseManager.chessGameSchema }
    private val chessMovesTableName = "moves"
    internal val movesTable by lazy { DSL.table("${chessGamesSchema}.$chessMovesTableName") }
    private val dslContext by lazy { chessDatabaseManager.dslContext }

    private val chessMoveColumns by lazy { dslContext!!.getTableColumnNames(chessMovesTableName) }

    internal fun insertIntoMovesTable(move: SingleMove) {

        val fieldNames = mutableListOf<String>();
        val fieldValues = mutableListOf<Any?>();

        for (field in SingleMove::class.memberProperties) {
            field.isAccessible = true

            fieldValues.add(field.get(move))
            fieldNames.add(field.name)
        }

        val lenientMatchedFieldNames = fieldNames.map { lenientMatchToFields(chessMoveColumns).invoke(it) }.toTypedArray()
        dslContext!!.insertInto(movesTable, *lenientMatchedFieldNames).values(fieldValues).execute()
    }

}
