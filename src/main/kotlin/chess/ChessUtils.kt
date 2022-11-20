package chess

import chess.enums.ChessFile
import java.time.LocalDate

enum class CastleSide {
    QUEENSIDE, KINGSIDE
}

enum class MoveType {
    SINGLE_PIECE_MOVEMENT, NORMAL_CAPTURE, CASTLE, EN_PASSANT
}

enum class ChessRank {
    ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT
}

class IllegalChessMoveException(message: String) : Throwable(message)

class ChessSquare(var squareFile: ChessFile, var squareRank: ChessRank) {}

data class ChessGame(

    var date: LocalDate? = null,

    var blackPlayerName: String? = null,

    var whitePlayerName: String? = null,

    var result: ChessMatchResult? = null
) {

    var id: Int? = null
    var moves: List<PairedMoves>? = null
}

class PairedMoves(whiteMoveAsString: String?, blackMoveAsString: String?) {

    internal var whiteMove: SingleMove?=null
    internal var blackMove: SingleMove?=null

    init {
        this.whiteMove = SingleMove(whiteMoveAsString)
        this.blackMove = SingleMove(blackMoveAsString)
    }


}