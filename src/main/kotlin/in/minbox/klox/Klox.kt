package `in`.minbox.klox

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import kotlin.system.exitProcess
import kotlin.text.Charsets.UTF_8

object Klox {
    val interpreter: Interpreter = Interpreter()
    var hadError = false
    var hadRuntimeError = false

    @JvmStatic
    fun main(args: Array<String>) {
        if (args.size > 1) {
            println("Usage: klox [script]")
            // https://www.freebsd.org/cgi/man.cgi?query=sysexits&apropos=0&sektion=0&manpath=FreeBSD+4.3-RELEASE&format=html
            exitProcess(64)
        } else if (args.size == 1) {
            runFile(args[0])
        } else {
            runPrompt()
        }
    }

    private fun runPrompt() {
        val input = InputStreamReader(System.`in`)
        val reader = BufferedReader(input)

        while (true) {
            println("> ")
            val line = reader.readLine() ?: break
            process(line)
        }
    }

    private fun runFile(path: String) {
        val bytes = File(path).readBytes()
        process(String(bytes, UTF_8))
        // Indicate an error in the exit code.
        if (hadError) exitProcess(65);
        if (hadRuntimeError) exitProcess(70)
    }

    private fun process(input: String) {
        val scanner = Scanner(input)
        val tokens = scanner.scanTokens()
        val parser = Parser(tokens)
        val statements = parser.parse()

        if (hadError) return

        interpreter.interpret(statements)
    }

    fun error(line: Int, message: String) {
        report(line, "", message)
    }

    fun error(token: Token, message: String) {
        if (token.type == TokenType.EOF) {
            report(token.line, " at end", message);
        } else {
            report(token.line, " at '" + token.lexeme + "'", message);
        }
    }

    fun runtimeError(error: RuntimeError) {
        System.err.println(error.message + "\n[line " + error.token.line + "]")
        hadRuntimeError = true
    }

    private fun report(line: Int, where: String, message: String) {
        System.err.println("[line $line] Error $where: $message")
        hadError = true
    }
}
