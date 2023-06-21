package `in`.minbox.klox

abstract class Stmt {
    interface Visitor<out T> {
        fun visitExpressionStmt(expression: ExpressionStmt): T
        fun visitPrintStmt(printStmt: PrintStmt): T
        fun visitVarStmt(varStmt: Var): T
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

/**
 * The book separates it from Stmt. It says Statements are of 2 types: print and
 * expr. And 'declaration statements' are different. However, they are extended from the same Stmt.
 */
data class Var(val name: Token, val initializer: Expr?): Stmt() {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visitVarStmt(this)
    }

}