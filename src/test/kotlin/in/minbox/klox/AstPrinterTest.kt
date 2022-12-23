package `in`.minbox.klox

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AstPrinterTest {

    @Test
    fun printAst() {
        val expression = Binary(
            Unary(
                Token(TokenType.MINUS, "-", null, 1),
                Literal(1234)
            ),
            Token(TokenType.STAR, "*", null, 1),
            Grouping(Literal(34.56))
        )

        assertEquals("(* (- 1234) (group 34.56))", AstPrinter().print(expression))
    }
}
