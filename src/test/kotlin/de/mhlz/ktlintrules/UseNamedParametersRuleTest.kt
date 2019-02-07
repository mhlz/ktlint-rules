package de.mhlz.ktlintrules

import org.junit.Test
import com.github.shyiko.ktlint.test.lint
import kotlin.test.assertTrue

/**
 * @author Mischa Holz
 */
class UseNamedParametersRuleTest {

    @Test
    fun testNamedParametersRule() {
        val errors = UseNamedParametersRule().lint("""
fun test(a1: String, a2: String, a3: String, a4: String, a5: String) {}

fun otherTest() {
    test("", "", "", "", "")
    System.out.println("")
}

fun anotherTest() {
    test(a1 = "", a2 = "", a3 = "", a4 = "", a5 = "")
}
        """)

        assertTrue("Should have error in line 5") {
            errors.any { it.line == 5 && it.detail.contains("named") }
        }
    }
}