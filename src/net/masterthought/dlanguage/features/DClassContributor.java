package net.masterthought.dlanguage.features;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.ContainerUtil;
import net.masterthought.dlanguage.psi.DLanguageClassDeclaration;
import net.masterthought.dlanguage.psi.DNamedElement;
import net.masterthought.dlanguage.psi.impl.DLanguageClassDeclarationImpl;
import net.masterthought.dlanguage.stubs.index.DAllNameIndex;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static net.masterthought.dlanguage.utils.DUtil.elementHasParentFor;
import static net.masterthought.dlanguage.utils.DUtil.findElementInParent;

/**
 * The "go to symbol"
 */
public class DClassContributor implements ChooseByNameContributor {
    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        return ArrayUtil.toStringArray(StubIndex.getInstance().getAllKeys(DAllNameIndex.KEY, project));
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        GlobalSearchScope scope = includeNonProjectItems ? GlobalSearchScope.allScope(project) : GlobalSearchScope.projectScope(project);
        Collection<DNamedElement> result = StubIndex.getElements(DAllNameIndex.KEY, name, project, scope, DNamedElement.class);
        List<NavigationItem> items = ContainerUtil.newArrayListWithCapacity(result.size());
        for (DNamedElement element : result) {
            if(element.getParent().getClass() == DLanguageClassDeclarationImpl.class) {
                items.add(element);
            }
        }
        return items.toArray(new NavigationItem[items.size()]);
    }
    
}
