package io.github.intellij.dlanguage.features

import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.util.ArrayUtil
import io.github.intellij.dlanguage.psi.impl.named.DlangClassDeclarationImpl
import io.github.intellij.dlanguage.psi.impl.named.DlangEnumDeclarationImpl
import io.github.intellij.dlanguage.psi.impl.named.DlangInterfaceDeclarationImpl
import io.github.intellij.dlanguage.psi.impl.named.DlangStructDeclarationImpl
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.stubs.index.DAllNameIndex

/**
 * The "go to class"
 */
class DClassContributor : ChooseByNameContributor {
    override fun getNames(project: Project, includeNonProjectItems: Boolean): Array<String> {
        return ArrayUtil.toStringArray(StubIndex.getInstance().getAllKeys(DAllNameIndex.KEY, project))
    }

    override fun getItemsByName(
        name: String, pattern: String,
        project: Project, includeNonProjectItems: Boolean
    ): Array<NavigationItem> {
        val scope =
            if (includeNonProjectItems) GlobalSearchScope.allScope(project) else GlobalSearchScope.projectScope(project)
        val result = StubIndex.getElements(DAllNameIndex.KEY, name, project, scope, DNamedElement::class.java)
        val items: MutableList<NavigationItem> = ArrayList(result.size)
        for (element in result) {
            if (element.javaClass == DlangClassDeclarationImpl::class.java || element.javaClass == DlangInterfaceDeclarationImpl::class.java
                || element.javaClass == DlangEnumDeclarationImpl::class.java || element.javaClass == DlangStructDeclarationImpl::class.java) {
                items.add(element)
            }
        }
        return items.toTypedArray()
    }
}
