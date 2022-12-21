package `in`.minbox.klox

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ScannerTest {

    @Test
    fun scanParens() {
        val input = "()"
        val scanner = Scanner(input)

        val tokens = scanner.scanTokens()
        assertEquals(3, tokens.size)
        assertEquals(Token(TokenType.LEFT_PAREN, "(", null, 1), tokens[0])
        assertEquals(Token(TokenType.RIGHT_PAREN, ")", null, 1), tokens[1])
        assertEquals(TokenType.EOF, tokens[2].type)
    }

}
