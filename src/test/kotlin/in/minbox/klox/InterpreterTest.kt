package `in`.minbox.klox

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

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

        Interpreter().interpret(expression)
    }
}