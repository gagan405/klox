package `in`.minbox.klox

class RuntimeError(val token: Token, message: String) : RuntimeException(message)