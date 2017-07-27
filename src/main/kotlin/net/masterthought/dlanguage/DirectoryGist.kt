package net.masterthought.dlanguage

import com.intellij.psi.PsiFile
import com.intellij.util.NotNullFunction
import com.intellij.util.gist.GistManager
import com.intellij.util.io.DataExternalizer
import java.io.DataInput
import java.io.DataOutput
import java.io.EOFException

/**
 * Created by francis on 7/26/2017.
 */
//not used left here as an example of hoe to use gists
val directories = GistManager.getInstance().newPsiFileGist("directories", 1, object : DataExternalizer<Set<String>> {
    override fun save(out: DataOutput, value: Set<String>?) {
        for (s in value!!) {
            out.writeUTF(s)
        }
    }

    override fun read(`in`: DataInput): Set<String> {
        val out = mutableSetOf<String>()
        try {
            out.add(`in`.readUTF())
        } catch(e: EOFException) {
        }
        return out
    }
}, object : NotNullFunction<PsiFile?, Set<String>?> {
    override fun `fun`(dom: PsiFile?): Set<String> {
        val directorySet = mutableSetOf<String>()
        var parent = dom?.parent
        while (true) {
            directorySet.add(parent!!.name)
            if (parent.parent == null) {
                break
            }
            parent = parent.parent
        }
        return directorySet
    }
})

