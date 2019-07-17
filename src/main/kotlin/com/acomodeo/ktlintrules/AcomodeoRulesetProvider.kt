package com.acomodeo.ktlintrules

import com.pinterest.ktlint.core.RuleSetProvider
import com.pinterest.ktlint.core.RuleSet

/**
 * @author Mischa Holz
 */
class AcomodeoRulesetProvider : RuleSetProvider {
    override fun get(): RuleSet = RuleSet(
        "mhlz",
        UseNamedParametersRule(),
        NoNullAssertionsRule(),
        NoEmptyLineAfterFunctionDefinitionRule()
    )
}
