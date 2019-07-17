package de.mhlz.ktlintrules

import com.pinterest.ktlint.core.RuleSet
import com.pinterest.ktlint.core.RuleSetProvider

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
