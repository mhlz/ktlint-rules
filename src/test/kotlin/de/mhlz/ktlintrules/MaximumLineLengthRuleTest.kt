package de.mhlz.ktlintrules

import org.junit.Test
import com.github.shyiko.ktlint.test.lint
import kotlin.test.assertTrue

/**
 * @author Mischa Holz
 */
class MaximumLineLengthRuleTest {

    @Test
    fun testMaximumLineLength() {
        val errors = MaximumLineLengthRule().lint("""
fun test() {
    val str = "this is a really long line with lots of content. very long. really really really long. longer than 120 characters for sure. definitely longer"
    val otherStr = "this line is a lot shorter"
}
""")

        assertTrue("Should have error in line 3") {
            errors.singleOrNull { it.line == 3 && it.detail.contains("too long")  } != null
        }

    }

}