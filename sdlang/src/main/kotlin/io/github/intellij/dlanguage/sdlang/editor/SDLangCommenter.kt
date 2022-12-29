package io.github.intellij.dlanguage.sdlang.editor

import com.intellij.lang.Commenter
import org.jetbrains.annotations.Nullable

class SDLangCommenter : Commenter {
    override fun getLineCommentPrefix(): String = "//"

    override fun getBlockCommentPrefix(): String  = "/*"

    override fun getBlockCommentSuffix(): String = "*/"

    override fun getCommentedBlockCommentPrefix(): String? = null

    override fun getCommentedBlockCommentSuffix(): String? = null
}
