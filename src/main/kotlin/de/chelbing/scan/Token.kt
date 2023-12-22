package de.chelbing.scan

data class Token(
    val tokenType: TokenType,
    val lexeme: String,
    val literal: Any,
    val line: Int,
)
