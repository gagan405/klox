package `in`.minbox.klox

class AstPrinter : Visitor<String> {

    fun print(expr: Expr): String {
        return expr.accept(this)
    }

    override fun visitBinary(expr: Binary): String {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right)
    }

    override fun visitGrouping(expr: Grouping): String {
        return parenthesize("group", expr.expr)
    }

    override fun visitLiteral(expr: Literal): String {
        return expr.value.toString();
    }

    override fun visitUnary(expr: Unary): String {
        return parenthesize(expr.operator.lexeme, expr.right);
    }

    private fun parenthesize(name: String, vararg expressions: Expr): String {
        val sb = StringBuilder()
            .append("(")
            .append(name)

        expressions.forEach { sb.append(" ").append(it.accept(this)) }
        sb.append(")")

        return sb.toString()
    }
}
