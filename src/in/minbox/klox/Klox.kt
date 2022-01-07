package `in`.minbox.klox

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import kotlin.text.Charsets.UTF_8

object Klox {
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.size > 1) {
            println("Usage: klox [script]")
            System.exit(64)
        } else if (args.size == 1) {
            runFile(args.get(0))
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
    }

    private fun process(input: String) {
        val scanner = Scanner(input)
        val tokens = scanner.scanTokens()
        println(tokens)
    }
}