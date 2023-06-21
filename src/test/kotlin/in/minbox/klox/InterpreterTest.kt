package `in`.minbox.klox

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InterpreterTest {

    @Test
    fun addition() {
        val expression = Binary(
            Unary(
                Token(TokenType.MINUS, "-", null, 1),
                Literal(1234)
            ),
            Token(TokenType.PLUS, "+", null, 1),
            Literal(100)
        )

        Interpreter().interpret(listOf(ExpressionStmt(expression)))
    }

    @Test
    fun variableDeclaration() {
        val program = "var x = 2;"
        val scanner = Scanner(program)
        val tokens = scanner.scanTokens()
        val parser = Parser(tokens)
        val statements = parser.parse()
        assertEquals(1, statements.size)
        val v = statements[0]
        assertEquals(Var(Token(TokenType.IDENTIFIER, "x", null, 1), Literal(2.0)), v)
    }
}