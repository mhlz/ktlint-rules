package de.mhlz.ktlintrules

import com.github.shyiko.ktlint.core.Rule
import org.jetbrains.kotlin.KtNodeTypes
import org.jetbrains.kotlin.KtNodeTypes.CALL_EXPRESSION
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * @author Mischa Holz
 */
class MaximumLineLengthRule : Rule("maximum-line-length") {
    override fun visit(node: ASTNode,
                       autoCorrect: Boolean,
                       emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit) {
        if (node is FileElement) {
            val text = node.text

            val lines = text.split("\n")

            val importStatements = node.getChildren(TokenSet.create(KtNodeTypes.IMPORT_LIST))
            val importTextRange = importStatements.firstOrNull()?.textRange

            lines.forEachIndexed { i, line ->
                val offset = lines.subList(0, i).map { it.length + 1 }.sum()

                val partOfImportList = importTextRange?.contains(offset) ?: false

                if (line.length < 120 || partOfImportList) return@forEachIndexed

                emit(offset, "This line is too long. Shorten it to less than 120 chars", false)
            }
        }
    }
}
