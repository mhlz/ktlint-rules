package de.mhlz.ktlintrules

import com.github.shyiko.ktlint.core.RuleSet
import com.github.shyiko.ktlint.core.RuleSetProvider

/**
 * @author Mischa Holz
 */
class MhlzRulesetProvider : RuleSetProvider {
    override fun get(): RuleSet {
        return RuleSet(
                "mhlz",
                UseNamedParametersRule(),
                MaximumLineLengthRule(),
                NoNullAssertionsRule()
        )
    }
}
