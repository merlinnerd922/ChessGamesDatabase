package chess.enums

enum class ChessFile {
    A, B, C, D, E, F, G, H;

    companion object {

        val CHAR_FILE_MAPPING: Map<Char, ChessFile> =
            mapOf('a' to A, 'b' to B, 'c' to C, 'd' to D, 'e' to E, 'f' to F, 'g' to G, 'h' to H)
    }
}