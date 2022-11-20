package chess

import chess.enums.ChessColour
import chess.enums.ChessFile
import chess.enums.ChessPiece

class SingleMove(var moveAsString: String?) {

    init {
        when (moveAsString!![0]) {
            in ChessPiece.CHAR_PIECE_MAPPING.keys -> parsePieceMove(moveAsString)
            in ChessFile.CHAR_FILE_MAPPING.keys -> parsePawnMove(moveAsString)
            '0' -> parseCastling(moveAsString!!)
            else -> { throw IllegalChessMoveException("The chess move $moveAsString is invalid!" +
                    " Must start with a-h, N, K, Q, B, R, or 0!") }
        }
    }

    private fun parseCastling(moveAsString: String) {
        when (moveAsString) {
            "0-0-0" -> {}
            "0-0" -> {}
            else -> {}
        }
    }

    private fun parsePawnMove(pawnMove: String?) {
        ChessFile.CHAR_FILE_MAPPING[pawnMove!![0]]
    }

    private fun parsePieceMove(pieceMove: String?) {
        movingPiece = ChessPiece.CHAR_PIECE_MAPPING[moveAsString!![0]]

    }

    var gameId: Int? =null
    val moveType : MoveType? = null

    var movingPiece : ChessPiece? = null


    val originSquare : ChessSquare? = null


    val isCheck : Boolean? = null
    val isCheckmate : Boolean? = null
    val promotedPiece : ChessPiece? = null
    val castleSide : CastleSide? = null
    val targetSquare : ChessSquare? = null
    val playerMove: ChessColour?= null


}
