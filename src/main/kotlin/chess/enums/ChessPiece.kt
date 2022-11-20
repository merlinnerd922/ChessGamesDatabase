package chess.enums

import me.liuwj.ktorm.database.Database
import org.jooq.EnumType
import org.jooq.Generator
import org.jooq.meta.jaxb.ForcedType

enum class ChessPiece : EnumType {

    KING, BISHOP, KNIGHT, ROOK, PAWN, QUEEN;

    override fun getLiteral(): String {
        return this.name;
    }

    override fun getName(): String {
        return "ChessPiece";
    }

    companion object {
        val CHAR_PIECE_MAPPING: Map<Char, ChessPiece> =
            mapOf('K' to KING, 'Q' to QUEEN, 'N' to KNIGHT, 'R' to ROOK, 'B' to BISHOP)
    }
}