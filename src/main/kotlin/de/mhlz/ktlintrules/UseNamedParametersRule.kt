package de.mhlz.ktlintrules

import com.github.shyiko.ktlint.core.Rule
import org.jetbrains.kotlin.KtNodeTypes
import org.jetbrains.kotlin.KtNodeTypes.CALL_EXPRESSION
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * @author Mischa Holz
 */

val ignoredFunctions = listOf(
        "listOf",
        "mapOf",
        "mutableListOf",
        "mutableMapOf",
        "byteArrayOf",
        "setOf",
        "mutableSetOf",
        "arrayOf",
        "listOfNotNull"
)

class UseNamedParametersRule : Rule("use-named-parameters") {
    override fun visit(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        if (node is CompositeElement && node.elementType == CALL_EXPRESSION) {
            val callText = node
                    .getChildren(TokenSet.create(KtNodeTypes.REFERENCE_EXPRESSION))
                    .firstOrNull()
                    ?.text
            if (callText in ignoredFunctions)
                return

            val argumentList = node.getChildren(TokenSet.create(KtNodeTypes.VALUE_ARGUMENT_LIST))
            val arguments = argumentList
                    .singleOrNull()
                    ?.getChildren(TokenSet.create(KtNodeTypes.VALUE_ARGUMENT))
                    ?: return

            if (arguments.size > 4) {
                val error = arguments.any {
                    it.getChildren(TokenSet.create(KtNodeTypes.VALUE_ARGUMENT_NAME)).isEmpty()
                }

                if (error)
                    emit(
                            node.startOffset,
                            "Should use named parameters for function calls with more than" +
                                    "4 arguments: ${node.text}",
                            false
                    )
            }
        }
    }
}
