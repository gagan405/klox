package `in`.minbox.klox

abstract class Stmt {
    interface Visitor<out T> {
        fun visitExpressionStmt(expression: ExpressionStmt): T
        fun visitPrintStmt(printStmt: PrintStmt): T
    }
    abstract fun <T> accept(visitor: Visitor<T>) : T
}

data class PrintStmt(val expression: Expr): Stmt() {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visitPrintStmt(this)
    }

}

data class ExpressionStmt(val expression: Expr): Stmt() {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visitExpressionStmt(this);
    }
}