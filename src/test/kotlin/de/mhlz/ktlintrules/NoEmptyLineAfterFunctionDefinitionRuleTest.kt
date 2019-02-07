package de.mhlz.ktlintrules

import com.github.shyiko.ktlint.test.format
import com.github.shyiko.ktlint.test.lint
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NoEmptyLineAfterFunctionDefinitionRuleTest {

    @Test
    fun `should report empty lines after function definitions`() {
        val test = """
fun test() {

    println("out")
}
"""
        val errors = NoEmptyLineAfterFunctionDefinitionRule().lint(test)
        assertTrue { errors.isNotEmpty() }
        assertEquals(3, errors[0].line)
    }

    @Test
    fun `should not report spaces after function definitions`() {
        val test = """
fun test() {
    println("out")
}
"""
        val errors = NoEmptyLineAfterFunctionDefinitionRule().lint(test)
        assertTrue { errors.isEmpty() }
    }

    @Test
    fun `should not report on function expressions`() {
        val test = """
fun test() = println("test")
"""
        val errors = NoEmptyLineAfterFunctionDefinitionRule().lint(test)
        assertTrue { errors.isEmpty() }
    }

    @Test
    fun `should not report on function expressions2`() {
        val test = """
fun test() =
    println("test")
"""
        val errors = NoEmptyLineAfterFunctionDefinitionRule().lint(test)
        assertTrue { errors.isEmpty() }
    }

    @Test
    fun `should report on function expressions`() {
        val test = """
fun test() =




    "test"
"""
        val errors = NoEmptyLineAfterFunctionDefinitionRule().lint(test)
        assertTrue { errors.isNotEmpty() }
    }

    @Test
    fun `should report on function expressions with lambdas`() {
        val test = """
fun test2() = ::println

fun test() = { a, b ->




    "test"
}
"""
        val errors = NoEmptyLineAfterFunctionDefinitionRule().lint(test)
        assertTrue { errors.isNotEmpty() }
    }

    @Test
    fun `should report on function expressions with lambdas2`() {
        val test = """
fun test2() = ::println

fun test() = {




    "test"
}
"""
        val errors = NoEmptyLineAfterFunctionDefinitionRule().lint(test)
        assertTrue { errors.isNotEmpty() }
    }

    @Test
    fun `should not report on lambdas without linebreaks`() {
        val test = """
fun test() {
    doSomething("abc") { "test" }
}
"""
        val errors = NoEmptyLineAfterFunctionDefinitionRule().lint(test)
        assertTrue { errors.isEmpty() }
    }

    @Test
    fun `should report on lambdas`() {
        val test = """
fun test() {
    doSomething("abc") {


        "test"
    }
}
"""
        val errors = NoEmptyLineAfterFunctionDefinitionRule().lint(test)
        assertTrue { errors.isNotEmpty() }
    }

    @Test
    fun `should report on lambdas2`() {
        val test = """
fun test() {
    doSomething("abc") { a, b ->


        "test"
    }
}
"""
        val errors = NoEmptyLineAfterFunctionDefinitionRule().lint(test)
        assertTrue { errors.isNotEmpty() }
    }

    @Test
    fun `should fix empty lines after function definitions`() {
        val test = """
fun test() {





    println("out")
}
"""
        val afterFormatting = """
fun test() {
    println("out")
}
"""

        val formatted = NoEmptyLineAfterFunctionDefinitionRule().format(test)
        assertEquals(afterFormatting, formatted)
        val errors = NoEmptyLineAfterFunctionDefinitionRule().lint(formatted)
        assertTrue { errors.isEmpty() }
    }
}
