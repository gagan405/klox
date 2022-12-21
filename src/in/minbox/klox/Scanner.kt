package `in`.minbox.klox

class Scanner(val input: String) {

    private var tokens: MutableList<Token> = mutableListOf()
    private var start = 0
    private var current = 0
    private var line = 1

    private val keywords = mapOf<String, TokenType>(
        "and"   to TokenType.AND,
        "class" to TokenType.CLASS,
        "else"  to TokenType.ELSE,
        "false" to TokenType.FALSE,
        "for"   to TokenType.FOR,
        "fun"   to TokenType.FUN,
        "if"    to TokenType.IF,
        "nil"   to TokenType.NIL,
        "or"    to TokenType.OR,
        "print" to TokenType.PRINT,
        "return" to TokenType.RETURN,
        "super" to TokenType.SUPER,
        "this"  to TokenType.THIS,
        "true"  to TokenType.TRUE,
        "var"   to TokenType.VAR,
        "while" to TokenType.WHILE
    )

    fun scanTokens(): MutableList<Token> {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme.
            start = current
            scanToken()
        }

        tokens.add(Token(TokenType.EOF, "", Any(), line))
        return tokens
    }

    private fun isAtEnd(): Boolean {
        return current >= input.length
    }

    private fun scanToken() {
        when (val c: Char = advance()) {
            '(' -> addToken(TokenType.LEFT_PAREN)
            ')' -> addToken(TokenType.RIGHT_PAREN)
            '{' -> addToken(TokenType.LEFT_BRACE)
            '}' -> addToken(TokenType.RIGHT_BRACE)
            ',' -> addToken(TokenType.COMMA)
            '.' -> addToken(TokenType.DOT)
            '-' -> addToken(TokenType.MINUS)
            '+' -> addToken(TokenType.PLUS)
            ';' -> addToken(TokenType.SEMICOLON)
            '*' -> addToken(TokenType.STAR)
            '!' -> addToken(if (match('=')) TokenType.BANG_EQUAL else TokenType.BANG)
            '=' -> addToken(if (match('=')) TokenType.EQUAL_EQUAL else TokenType.EQUAL)
            '<' -> addToken(if (match('=')) TokenType.LESS_EQUAL else TokenType.LESS)
            '>' -> addToken(if (match('=')) TokenType.GREATER_EQUAL else TokenType.GREATER)
            '/' ->
                if (match('/')) {
                    // A comment goes until the end of the line.
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(TokenType.SLASH)
                }
            '\n' -> line++
            ' ', '\r', '\t' -> {}
            '"' -> string()
            else -> {
                if (isDigit(c)) {
                    number()
                } else if (isAlpha(c)) {
                    identifier()
                } else {
                    Klox.error(line, "Unexpected character: $c")
                }
            }
        }
    }

    private fun isAlpha(c: Char) : Boolean {
        return (c in 'a'..'z') || (c in 'A'..'Z') || c == '_'
    }

    private fun identifier() {
        while (isAlphaNumeric(peek())) advance()
        addToken(keywords.getOrDefault(input.substring(start, current), TokenType.IDENTIFIER))
    }

    private fun isAlphaNumeric(c: Char): Boolean {
        return isAlpha(c) || isDigit(c)
    }
    private fun number() {
        while (isDigit(peek())) advance()
        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
            // Consume the "."
            advance()
            while (isDigit(peek())) advance()
        }

        addToken(TokenType.NUMBER, input.substring(start, current).toDouble())
    }

    private fun peekNext() : Char {
        if (current + 1 >= input.length) return '\u0000'
        return input.elementAt(current + 1)
    }
    private fun isDigit(c : Char) : Boolean {
        return c in '0'..'9'
    }
    private fun string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++
            advance();
        }

        if (isAtEnd()) {
            Klox.error(line, "Unterminated string.");
            return
        }

        // The closing \".
        advance()
        // Trim the surrounding quotes.
        addToken(TokenType.STRING, input.substring(start + 1, current - 1))
    }

    private fun peek(): Char {
        return if (isAtEnd()) '\u0000' else input.elementAt(current)
    }
    private fun match(expected: Char): Boolean {
        if (isAtEnd()) return false
        if (input.elementAt(current) != expected) return false
        current++
        return true
    }
    private fun advance(): Char {
        return input.elementAt(current++)
    }

    private fun addToken(type: TokenType) {
        addToken(type, null)
    }

    private fun addToken(type: TokenType, literal: Any?) {
        val text: String = input.substring(start, current)
        tokens.add(Token(type, text, literal, line))
    }
}
