package de.mhlz.ktlintrules

// import com.github.shyiko.ktlint.core.RuleSet
import com.pinterest.ktlint.core.RuleSetProvider
import com.pinterest.ktlint.core.RuleSet

/**
 * @author Mischa Holz
 */
class MhlzRulesetProvider : RuleSetProvider {
    override fun get(): RuleSet = RuleSet(
        "mhlz",
        UseNamedParametersRule(),
        NoNullAssertionsRule(),
        NoEmptyLineAfterFunctionDefinitionRule()
    )
}
