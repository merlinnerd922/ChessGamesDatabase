package chess

import org.jooq.Record
import org.jooq.Record1
import org.jooq.Result
import org.jooq.impl.DSL

class ChessPlayerManager(chessDatabaseManager: ChessDatabaseManager) {

    private val dslContext by lazy { chessDatabaseManager.dslContext }

    internal fun accumulatePlayerTableFields(
        fieldValue: Any?,
        fieldName: String,
        columnValues: MutableList<Any?>,
        insertQueryColumnNames: MutableList<String>
    ) {

    }

    internal fun getOrCreatePlayerId(playerCodedName: String): Int {

        val playerCodedNameRegex = Regex("(\\w+?)-(\\d+)")
        val matchEntire = playerCodedNameRegex.matchEntire(playerCodedName)!!
        val firstName = matchEntire.groups[1]!!.value
        val toString = matchEntire.groups[2]!!.value
        val uniquenessId = Integer.parseInt(toString)

        val targetPlayer: Result<Record> = dslContext!!.select(DSL.asterisk()).from(DSL.table("chess_games.players"))
            .where(
                DSL.and(
                    DSL.field("first_name").eq(firstName),
                    DSL.field("uniqueness_id").eq(uniquenessId)
                )
            ).fetch()
        return if (targetPlayer.isEmpty()) {
            val values =
                dslContext!!.insertInto(DSL.table("players"), DSL.field("first_name"), DSL.field("uniqueness_id"))
                    .values(firstName, uniquenessId)
            val fetch: Result<Record1<Any>> = values.returningResult(DSL.field("id")).fetch()
            fetch[0]["id"] as Int
        } else {
            targetPlayer[0]["id"] as Int
        }

    }
}
