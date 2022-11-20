package exe

import chess.ChessGame
import chess.ChessGameParser
import chess.ChessDatabaseManager
import readResourceFile
import java.io.File

fun main() {
    val resourceFile: File = readResourceFile("/sampleChessGame.csv")

    val chessGameParser = ChessGameParser();
    val newChessGame: ChessGame = chessGameParser.parseFile(resourceFile);

    val dbManager = ChessDatabaseManager.init()
    dbManager.insertGame(newChessGame);

}

