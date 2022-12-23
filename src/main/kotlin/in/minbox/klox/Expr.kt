package `in`.minbox.klox


interface Visitor<out T> {
    fun visitBinary(expr: Binary) : T
    fun visitGrouping(expr: Grouping) : T
    fun visitLiteral(expr: Literal) : T
    fun visitUnary(expr: Unary) : T
}

abstract class Expr {
    abstract fun <T> accept(visitor: Visitor<T>): T
}

data class Binary(val left: Expr,  val operator: Token, val right: Expr): Expr() {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visitBinary(this)
    }
}

data class Grouping(val expr: Expr): Expr() {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visitGrouping(this)
    }
}

data class Literal(val value: Any): Expr() {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visitLiteral(this)
    }
}

data class Unary(val operator: Token, var right: Expr): Expr() {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visitUnary(this)
    }
}
