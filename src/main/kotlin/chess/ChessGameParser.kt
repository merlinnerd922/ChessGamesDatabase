package chess

import getStandardCsvReader
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import java.io.File
import java.time.LocalDate

class ChessGameParser {
    fun parseFile(resourceFile: File): ChessGame {

        val csvFormat = getStandardCsvReader()
        val parser = csvFormat!!.parse(resourceFile.bufferedReader())
        val headerRecords: List<CSVRecord> = parser.take(5);

        val newChessGame = ChessGame()
        newChessGame.whitePlayerName = headerRecords[0][0].toString().removePrefix("White: ")
        newChessGame.blackPlayerName = headerRecords[1][0].toString().removePrefix("Black: ")
        newChessGame.date = LocalDate.parse(headerRecords[2][0].toString().removePrefix("Date: "))
        newChessGame.moves = parseMoves(parser)

        return newChessGame
    }

    private fun parseMoves(parser: CSVParser?): List<PairedMoves>? {
        val remainingMoves = parser!!.records
        val returnLst = mutableListOf<PairedMoves>();
        for ((index, move: CSVRecord) in remainingMoves.withIndex()) {
            println("${index + 1}. ${move[0]} ${move[1]}")
            returnLst.add(PairedMoves(move[0], move[1]));
        }
        return returnLst
    }
}