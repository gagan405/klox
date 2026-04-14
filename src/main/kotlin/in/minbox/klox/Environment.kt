package `in`.minbox.klox


class Environment {
    private val env = mutableMapOf<String, Any?>()

    fun define(name: String, value: Any?) {
        env[name] = value
    }

    operator fun get(name: Token): Any? {
        if (name.lexeme in env) {
            return env[name.lexeme]
        }

        throw RuntimeException("Environment variable ${name.lexeme} not found.")
    }

    fun assign(name: Token, value: Any?) {
        if (name.lexeme in env) {
            env[name.lexeme] = value
            return
        }

        throw RuntimeError(
            name,
            "Undefined variable '" + name.lexeme + "'."
        )
    }
}