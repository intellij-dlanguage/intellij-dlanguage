package io.github.intellij.dlanguage.features;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.ContainerUtil;
import io.github.intellij.dlanguage.psi.impl.DLanguageClassDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.named.DlangEnumDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.named.DlangStructDeclarationImpl;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.index.DAllNameIndex;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

/**
 * The "go to class"
 */
public class DClassContributor implements ChooseByNameContributor {
    @NotNull
    @Override
    public String[] getNames(final Project project, final boolean includeNonProjectItems) {
        return ArrayUtil.toStringArray(StubIndex.getInstance().getAllKeys(DAllNameIndex.KEY, project));
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(final String name, final String pattern,
                                           final Project project, final boolean includeNonProjectItems) {
        final GlobalSearchScope scope = includeNonProjectItems ? GlobalSearchScope.allScope(project) : GlobalSearchScope.projectScope(project);
        final Collection<DNamedElement> result = StubIndex.getElements(DAllNameIndex.KEY, name, project, scope, DNamedElement.class);
        final List<NavigationItem> items = ContainerUtil.newArrayListWithCapacity(result.size());
        for (final DNamedElement element : result) {
            if(element.getParent().getClass() == DLanguageClassDeclarationImpl.class
                        || element.getClass() == DlangEnumDeclarationImpl.class
                        || element.getClass() == DlangStructDeclarationImpl.class) {
                items.add(element);
            }
        }
        return items.toArray(new NavigationItem[items.size()]);
    }

}
