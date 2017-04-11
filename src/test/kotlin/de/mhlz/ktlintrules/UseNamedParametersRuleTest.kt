package de.mhlz.ktlintrules

import org.junit.Test
import com.github.shyiko.ktlint.test.lint

/**
 * @author Mischa Holz
 */
class UseNamedParametersRuleTest {

    @Test
    fun testNamedParametersRule() {
        UseNamedParametersRule().lint("""
fun test(a1: String, a2: String, a3: String, a4: String) {}

fun otherTest() {
    test("", "", "", "")
    System.out.println("")
}

fun anotherTest() {
    test(a1 = "", a2 = "", a3 = "", a4 = "")
}
        """).forEach {
            println(it)
        }
    }

}