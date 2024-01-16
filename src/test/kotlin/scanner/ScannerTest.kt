package scanner

import de.chelbing.scan.Scanner
import de.chelbing.scan.Token
import de.chelbing.scan.TokenType
import kotlin.test.assertContentEquals
import org.junit.jupiter.api.Test

class ScannerTest {
    @Test
    fun `scanTokens splits the given line into serveral tokens, the line contains only operators`() {
        val expectedTokens = listOf(
            Token(TokenType.LEFT_PAREN, "(", Unit, 1),
            Token(TokenType.RIGHT_PAREN, ")", Unit, 1),
            Token(TokenType.LEFT_BRACE, "{", Unit, 1),
            Token(TokenType.RIGHT_BRACE, "}", Unit, 1),
            Token(TokenType.COMMA, ",", Unit, 1),
            Token(TokenType.DOT, ".", Unit, 1),
            Token(TokenType.MINUS, "-", Unit, 1),
            Token(TokenType.PLUS, "+", Unit, 1),
            Token(TokenType.SEMICOLON, ";", Unit, 1),
            Token(TokenType.STAR, "*", Unit, 1),
            Token(TokenType.BANG, "!", Unit, 1),
            Token(TokenType.BANG_EQUAL, "!=", Unit, 1),
            Token(TokenType.EQUAL, "=", Unit, 1),
            Token(TokenType.EQUAL_EQUAL, "==", Unit, 1),
            Token(TokenType.LESS, "<", Unit, 1),
            Token(TokenType.LESS_EQUAL, "<=", Unit, 1),
            Token(TokenType.GREATER, ">", Unit, 1),
            Token(TokenType.GREATER_EQUAL, ">=", Unit, 1),
            Token(TokenType.SLASH, "/", Unit, 1),
            Token(TokenType.EOF, "", Unit, 1),
        )

        val loxString = "( ) { } , . - + ; * ! != = == < <= > >= /"

        val tokens = Scanner(loxString).scanTokens()

        assertContentEquals(expectedTokens, tokens, "The scanned list of tokens did not match the expected list")
    }

    @Test
    fun `Line breaking character will lead into new line count for tokens afterwards`() {
        val expectedTokens = listOf(
            Token(TokenType.LEFT_PAREN, "(", Unit, 2),
            Token(TokenType.LEFT_PAREN, "(", Unit, 2),
            Token(TokenType.RIGHT_PAREN, ")", Unit, 2),
            Token(TokenType.RIGHT_PAREN, ")", Unit, 2),
            Token(TokenType.LEFT_BRACE, "{", Unit, 2),
            Token(TokenType.RIGHT_BRACE, "}", Unit, 2),
            Token(TokenType.BANG, "!", Unit, 3),
            Token(TokenType.STAR, "*", Unit, 3),
            Token(TokenType.PLUS, "+", Unit, 3),
            Token(TokenType.MINUS, "-", Unit, 3),
            Token(TokenType.SLASH, "/", Unit, 3),
            Token(TokenType.EQUAL, "=", Unit, 3),
            Token(TokenType.LESS, "<", Unit, 3),
            Token(TokenType.GREATER, ">", Unit, 3),
            Token(TokenType.LESS_EQUAL, "<=", Unit, 3),
            Token(TokenType.EQUAL_EQUAL, "==", Unit, 3),
            Token(TokenType.EOF, "", Unit, 3),
        )

        val loxString = """
            // this is a comment
            (( )){} // grouping stuff
            !*+-/=<> <= == // operators
        """.trimIndent()

        val tokens = Scanner(loxString).scanTokens()

        assertContentEquals(expectedTokens, tokens, "The scanned list of tokens did not match the expected list")
    }

    @Test
    fun `String will be scanned from starting double quotes till the closing double quotes`() {
        val expectedTokens = listOf(
            Token(TokenType.STRING, "\"TestString\"", "TestString", 1),
            Token(TokenType.EOF, "", Unit, 1),
        )

        val loxString = "\"TestString\""

        val tokens = Scanner(loxString).scanTokens()

        assertContentEquals(expectedTokens, tokens, "The scanned list of tokens did not match the expected list")
    }

    @Test
    fun `Number without decimal place will be interpreted as Number token and double type`() {
        val expectedTokens = listOf(
            Token(TokenType.NUMBER, "123", 123.0, 1),
            Token(TokenType.EOF, "", Unit, 1),
        )

        val loxString = "123"

        val tokens = Scanner(loxString).scanTokens()

        assertContentEquals(expectedTokens, tokens, "The scanned list of tokens did not match the expected list")
    }

    @Test
    fun `Number with decimal place will be interpreted as Number token and double type`() {
        val expectedTokens = listOf(
            Token(TokenType.NUMBER, "123.456", 123.456, 1),
            Token(TokenType.EOF, "", Unit, 1),
        )

        val loxString = "123.456"

        val tokens = Scanner(loxString).scanTokens()

        assertContentEquals(expectedTokens, tokens, "The scanned list of tokens did not match the expected list")
    }

    @Test
    fun `keywords will be successfully parsed and no identifier is in the list`() {
        val expectedTokens = listOf(
            Token(TokenType.AND, "and", Unit, 1),
            Token(TokenType.CLASS, "class", Unit, 1),
            Token(TokenType.ELSE, "else", Unit, 1),
            Token(TokenType.FALSE, "false", Unit, 1),
            Token(TokenType.FOR, "for", Unit, 1),
            Token(TokenType.FUN, "fun", Unit, 1),
            Token(TokenType.IF, "if", Unit, 1),
            Token(TokenType.NIL, "nil", Unit, 1),
            Token(TokenType.OR, "or", Unit, 1),
            Token(TokenType.PRINT, "print", Unit, 1),
            Token(TokenType.RETURN, "return", Unit, 1),
            Token(TokenType.SUPER, "super", Unit, 1),
            Token(TokenType.THIS, "this", Unit, 1),
            Token(TokenType.TRUE, "true", Unit, 1),
            Token(TokenType.VAR, "var", Unit, 1),
            Token(TokenType.WHILE, "while", Unit, 1),
            Token(TokenType.EOF, "", Unit, 1),
        )

        val loxString = "and class else false for fun if nil or print return super this true var while"

        val tokens = Scanner(loxString).scanTokens()

        assertContentEquals(expectedTokens, tokens, "The scanned list of tokens did not match the expected list")
    }


    @Test
    fun `keywords will be successfully parsed with an identifier in the list`() {
        val expectedTokens = listOf(
            Token(TokenType.AND, "and", Unit, 1),
            Token(TokenType.CLASS, "class", Unit, 1),
            Token(TokenType.ELSE, "else", Unit, 1),
            Token(TokenType.FALSE, "false", Unit, 1),
            Token(TokenType.FOR, "for", Unit, 1),
            Token(TokenType.FUN, "fun", Unit, 1),
            Token(TokenType.IF, "if", Unit, 1),
            Token(TokenType.NIL, "nil", Unit, 1),
            Token(TokenType.IDENTIFIER, "userId", Unit, 1),
            Token(TokenType.OR, "or", Unit, 1),
            Token(TokenType.PRINT, "print", Unit, 1),
            Token(TokenType.RETURN, "return", Unit, 1),
            Token(TokenType.SUPER, "super", Unit, 1),
            Token(TokenType.THIS, "this", Unit, 1),
            Token(TokenType.TRUE, "true", Unit, 1),
            Token(TokenType.VAR, "var", Unit, 1),
            Token(TokenType.WHILE, "while", Unit, 1),
            Token(TokenType.EOF, "", Unit, 1),
        )

        val loxString = "and class else false for fun if nil userId or print return super this true var while"

        val tokens = Scanner(loxString).scanTokens()

        assertContentEquals(expectedTokens, tokens, "The scanned list of tokens did not match the expected list")
    }
}