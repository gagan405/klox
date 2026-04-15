package `in`.minbox.klox


object Nil {
    override fun toString() = "nil"
}

class Interpreter : Visitor<Any>, Stmt.Visitor<Unit> {
    private var env = Environment()

    fun interpret(statements: List<Stmt>) {
        try {
            for (statement in statements) {
                execute(statement)
            }
        } catch (error: RuntimeError) {
            Klox.runtimeError(error)
        }
    }

    override fun visitBinary(expr: Binary): Any {
        val left = evaluate(expr.left)
        val right = evaluate(expr.right)

        when (expr.operator.type) {
            TokenType.MINUS -> {
                checkNumberOperands(expr.operator, left, right)
                return left as Double - right as Double
            }
            TokenType.PLUS -> {
                if (left is String && right is String) {
                    return left.plus(right)
                }
                if (left is Double && right is Double) {
                    return left + right
                }
                throw RuntimeError(
                    expr.operator,
                    "Operands must be two numbers or two strings."
                )
            }
            TokenType.SLASH -> {
                checkNumberOperands(expr.operator, left, right)
                return left as Double / right as Double
            }
            TokenType.STAR -> {
                checkNumberOperands(expr.operator, left, right)
                return left as Double * right as Double
            }
            TokenType.GREATER -> {
                checkNumberOperands(expr.operator, left, right)
                return left as Double > right as Double
            }
            TokenType.GREATER_EQUAL -> {
                checkNumberOperands(expr.operator, left, right)
                return left as Double >= right as Double
            }
            TokenType.LESS -> {
                checkNumberOperands(expr.operator, left, right)
                return (left as Double) < (right as Double)
            }
            TokenType.LESS_EQUAL -> {
                checkNumberOperands(expr.operator, left, right)
                return left as Double <= right as Double
            }
            TokenType.BANG_EQUAL -> return !isEqual(left, right)
            TokenType.EQUAL_EQUAL -> return isEqual(left, right)
            else -> {}
        }

        // unreachable
        return Any()
    }

    override fun visitGrouping(expr: Grouping): Any {
        return evaluate(expr.expr)
    }

    override fun visitLiteral(expr: Literal): Any {
        if (expr.value is Int) return expr.value.toDouble()
        if (expr.value == null) return Nil
        return expr.value
    }

    override fun visitUnary(expr: Unary): Any {
        val right = evaluate(expr.right)
        when (expr.operator.type) {
            TokenType.MINUS -> {
                checkNumberOperand(expr.operator, right)
                return (-1 * right as Double)
            }
            TokenType.PLUS -> return right
            TokenType.BANG -> return !isTruthy(right)
            else -> {}
        }
        // unreachable
        return Any()
    }

    override fun visitVariableExpr(expr: Variable): Any {
        return env[expr.name] ?: Nil
    }

    override fun visitExpressionStmt(expression: ExpressionStmt) {
        evaluate(expression.expression)
    }

    override fun visitPrintStmt(printStmt: PrintStmt) {
        val result = evaluate(printStmt.expression)
        println(stringify(result))
    }

    override fun visitVarStmt(varStmt: Var) {
        val value = varStmt.initializer?.let { evaluate(it) } ?: Nil
        env.define(varStmt.name.lexeme, value)
    }

    override fun visitBlockStmt(block: BlockStmt) {
        executeBlock(block.statements, Environment(env));
    }

    override fun visitAssignExpr(expr: Assign): Any {
        val value = evaluate(expr.value)
        env.assign(expr.name, value)
        return value
    }

    private fun isTruthy(item: Any?): Boolean {
        return when (item) {
            null -> false
            is Boolean -> item
            is Nil -> false
            else -> true
        }
    }

    private fun execute(stmt: Stmt) {
        stmt.accept(this)
    }

    private fun evaluate(expr: Expr): Any {
        return expr.accept(this)
    }

    private fun isEqual(left: Any?, right: Any?): Boolean {
        if (left == null && right == null) {
            return true
        }

        if (left == null) return false
        return left == right
    }

    private fun checkNumberOperand(operator: Token, item: Any) {
        if (item is Double) return
        throw RuntimeError(operator, "Operand must be a number.")
    }

    private fun checkNumberOperands(operator: Token, left: Any, right: Any) {
        if (left is Double && right is Double) return
        throw RuntimeError(operator, "Operands must be numbers.")
    }

    private fun stringify(item: Any?): String {
        if (item == null) return "nil"
        if (item is Double) {
            var text = item.toString()
            if (text.endsWith(".0")) {
                text = text.dropLast(2)
            }
            return text
        }
        return item.toString()
    }

    private fun executeBlock(statements: List<Stmt>, environment: Environment) {
        withEnvironment(environment) {
            for (stmt in statements) {
                execute(stmt)
            }
        }
    }

    private inline fun <T> withEnvironment(environment: Environment, block: () -> T): T {
        val previous = env
        try {
            env = environment
            return block()
        } finally {
            env = previous
        }
    }
}
