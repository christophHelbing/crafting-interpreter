package de.chelbing

import kotlin.system.exitProcess


fun main(args: Array<String>) {
    if (args.size > 1) {
        println("Usage: jlox [script]")
        exitProcess(64)
    } else if (args.size == 1) {
        LoxInterpreter().runFile(args[0])
    } else {
        LoxInterpreter().runPrompt()
    }
}


