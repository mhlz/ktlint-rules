package de.mhlz.ktlintrules

import com.github.shyiko.ktlint.core.Rule
import org.jetbrains.kotlin.KtNodeType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtFunctionLiteral
import org.jetbrains.kotlin.psi.KtLambdaExpression
import org.jetbrains.kotlin.psi.psiUtil.children
import org.jetbrains.kotlin.psi.stubs.elements.KtFunctionElementType

class NoEmptyLineAfterFunctionDefinitionRule : Rule("no-empty-line-after-function-definition") {
    override fun visit(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        if (node is CompositeElement) {
            val type = node.elementType
            if (node is KtLambdaExpression) {
                val functionLiteral = node.firstChild as? KtFunctionLiteral ?: return
                val block = functionLiteral
                    .children
                    .filterIsInstance<KtBlockExpression>()
                    .firstOrNull()
                    ?: return
                val whitespace = block.prevSibling as? PsiWhiteSpace ?: return
                lintWhitespace(whitespace, autoCorrect, emit)
            }
            if (type is KtFunctionElementType) {
                val block = node.children()
                    .filterIsInstance<CompositeElement>()
                    .firstOrNull { it.elementType is KtNodeType }
                    ?: return handleExpressionFunction(node, autoCorrect, emit)

                val isBlock = block.text.startsWith("{") && block.text.endsWith("}")
                if (!isBlock)
                    return handleExpressionFunction(node, autoCorrect, emit)

                val children = block.children().take(2).toList()
                val possibleWhitespace = children.getOrNull(1) ?: return

                if (possibleWhitespace is PsiWhiteSpace) {
                    val whitespace = possibleWhitespace as PsiWhiteSpace

                    lintWhitespace(whitespace, autoCorrect, emit)
                }
            }
        }
    }

    private fun autoCorrect(whitespace: PsiWhiteSpace) {
        val lastLineBreak = whitespace.text.lastIndexOf("\n")
        val newText = whitespace.text.substring(lastLineBreak)
        (whitespace as LeafPsiElement).rawReplaceWithText(newText)
    }

    private fun handleExpressionFunction(
        node: CompositeElement,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        val children = node
            .children()
            .dropWhile {
                if (it !is PsiElement)
                    return@dropWhile true

                val psiElement = it as PsiElement

                psiElement.text != "="
            }
            .take(2)
            .toList()

        val possibleWhitespace = children.getOrNull(1) ?: return

        if (possibleWhitespace is PsiWhiteSpace)
            lintWhitespace(possibleWhitespace, autoCorrect, emit)
    }

    private fun lintWhitespace(
        whitespace: PsiWhiteSpace,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        if (whitespace.text.count { it == '\n' } > 1) {
            val firstLineBreak = whitespace.text.indexOf("\n")
            val secondLineBreak = whitespace.text.indexOf("\n", firstLineBreak + 1)

            emit(
                whitespace.textOffset + secondLineBreak,
                "No empty new line after function definitions",
                true
            )

            if (autoCorrect) {
                autoCorrect(whitespace)
            }
        }
    }
}
