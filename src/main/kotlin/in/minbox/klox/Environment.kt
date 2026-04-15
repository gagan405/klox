package `in`.minbox.klox

// enclosing -> the env that encloses this one, or we can say 'parent'
class Environment(val enclosing: Environment? = null) {
    private val env = mutableMapOf<String, Any?>()

    fun define(name: String, value: Any?) {
        env[name] = value
    }

    operator fun get(name: Token): Any? {
        if (name.lexeme in env) {
            return env[name.lexeme]
        }
        if (enclosing != null) return enclosing[name]

        throw RuntimeException("Environment variable ${name.lexeme} not found.")
    }

    fun assign(name: Token, value: Any?) {
        if (name.lexeme in env) {
            env[name.lexeme] = value
            return
        }

        if (enclosing != null) {
            enclosing.assign(name, value)
            return
        }

        throw RuntimeError(
            name,
            "Undefined variable '" + name.lexeme + "'."
        )
    }
}