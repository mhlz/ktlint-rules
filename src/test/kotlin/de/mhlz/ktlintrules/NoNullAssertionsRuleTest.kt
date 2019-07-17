package de.mhlz.ktlintrules

import com.pinterest.ktlint.test.lint
import kotlin.test.assertTrue
import org.junit.Test

/**
 * @author Mischa Holz
 */
class NoNullAssertionsRuleTest {

    @Test
    fun `should report null assertions`() {
        val test = """
val test = t!!
val otherTest = t!!.bla
val shouldNotTrigger = "t!!"
"""
        val errors = NoNullAssertionsRule().lint(test)
        assertTrue("Should have 2 error messages in line 2 and 3") {
            errors.size == 2 && errors.map { it.line }.toSet() == setOf(2, 3)
        }
    }
}
