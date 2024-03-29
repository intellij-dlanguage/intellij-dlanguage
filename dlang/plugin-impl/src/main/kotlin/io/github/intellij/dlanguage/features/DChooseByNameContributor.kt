package io.github.intellij.dlanguage.features

import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.util.ArrayUtil
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.stubs.index.DAllNameIndex

/**
 * The "go to symbol"
 */
class DChooseByNameContributor : ChooseByNameContributor {
    override fun getNames(project: Project, includeNonProjectItems: Boolean): Array<String> {
        return ArrayUtil.toStringArray(StubIndex.getInstance().getAllKeys(DAllNameIndex.KEY, project))
    }

    override fun getItemsByName(
        name: String, pattern: String,
        project: Project, includeNonProjectItems: Boolean
    ): Array<NavigationItem> {
        val scope =
            if (includeNonProjectItems) GlobalSearchScope.allScope(project) else GlobalSearchScope.projectScope(project)
        val classes = StubIndex.getElements(DAllNameIndex.KEY, name, project, scope, DNamedElement::class.java)
        val items: MutableList<NavigationItem> = ArrayList(classes.size)
        items.addAll(classes)
        return items.toTypedArray()
    }
}
