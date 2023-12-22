package de.chelbing

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.exitProcess

class LoxInterpreter {
    companion object {
        private var hadError: Boolean = false

        fun error(line: Int, message: String) {
            report(line = line, message = message)
        }

        private fun report(
            line: Int,
            where: String = "",
            message: String
        ) {
            System.err.println(
                "[line $line] Error$where: $message"
            )
            hadError = true
        }
    }

    @Throws(IOException::class)
    fun runFile(path: String) {
        val bytes = Files.readAllBytes(Paths.get(path))
        run(String(bytes, Charset.defaultCharset()))

        // Indicate an error in the exit code.
        if (hadError) exitProcess(65)
    }

    @Throws(IOException::class)
    fun runPrompt() {
        val input = InputStreamReader(System.`in`)
        val reader = BufferedReader(input)

        while (true) {
            print("> ")
            val line = reader.readLine() ?: break
            run(line)
            hadError = false
        }
    }


    private fun run(source: String) {
//        val scanner: Scanner = Scanner(source)
//        val tokens: List<Token> = scanner.scanTokens()
//
//        // For now, just print the tokens.
//        for (token in tokens) {
//            println(token)
//        }
    }
}