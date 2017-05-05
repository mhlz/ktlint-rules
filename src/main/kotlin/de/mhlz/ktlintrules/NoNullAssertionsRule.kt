package de.mhlz.ktlintrules

import com.github.shyiko.ktlint.core.Rule
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.lexer.KtSingleValueToken

/**
 * @author Mischa Holz
 */
class NoNullAssertionsRule : Rule("no-null-assertion") {
    override fun visit(
            node: ASTNode,
            autoCorrect: Boolean,
            emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        if (node is LeafPsiElement) {
            println()
            val nodeValue = (node.elementType as? KtSingleValueToken)?.value

            if (nodeValue == "!!")
                emit(node.startOffset, "Do not use '!!' assertions as they produce unhelpful " +
                        "error messages. Try to use ?.let or ?. instead", false)
        }
    }
}
