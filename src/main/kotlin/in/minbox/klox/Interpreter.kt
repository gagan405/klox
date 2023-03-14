package `in`.minbox.klox

//TODO can we make use of native kotlin type `Any` here?
class Interpreter : Visitor<Object> {
    override fun visitBinary(expr: Binary): Object {
        TODO("Not yet implemented")
    }

    override fun visitGrouping(expr: Grouping): Object {
        return evaluate(expr.expr) as Object
    }

    override fun visitLiteral(expr: Literal): Object {
        return expr.value as Object
    }

    override fun visitUnary(expr: Unary): Object {
        val right = evaluate(expr.right)
        when (expr.operator.type) {
            TokenType.MINUS -> return (-1 * right as Double) as Object
            TokenType.PLUS -> return right as Object
            TokenType.BANG -> return !isTruthy(right) as Object
            else -> {}
        }
        // unreachable
        return Object()
    }

    private fun isTruthy(right: Any?): Boolean {
        return when (right) {
            null -> false
            is Boolean -> right
            else -> true
        }
    }

    private fun evaluate(expr: Expr): Any? {
        return expr.accept(this)
    }

}
