package net.masterthought.dlanguage.features;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.ResolveResult;
import net.masterthought.dlanguage.psi.references.DReference;
import net.masterthought.dlanguage.resolve.processors.parameters.DAttributesFinder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by francis on 7/18/2017.
 */
public class DDocumentationProvider extends AbstractDocumentationProvider implements DocumentationProvider {

    /**
     * Returns the text to show in the Ctrl-hover popup for the specified element.
     *
     * @param element         the element for which the documentation is requested (for example, if the mouse is over
     *                        a method reference, this will be the method to which the reference is resolved).
     * @param originalElement the element under the mouse cursor
     * @return the documentation to show, or null if the provider can't provide any documentation for this element.
     */
    @Nullable
    @Override
    public String getQuickNavigateInfo(PsiElement element, PsiElement originalElement) {
        return null;
    }

    @Nullable
    @Override
    public List<String> getUrlFor(PsiElement element, PsiElement originalElement) {
        return new ArrayList<>();
    }

    @Nullable
    @Override
    public String generateDoc(final PsiElement element, @Nullable final PsiElement originalElement) {

        try {
            DAttributesFinder a;
            a = new DAttributesFinder(element);
            a.recurseUp();
            if (element.getReference() != null) {
                final ResolveResult[] resolveResults = ((DReference) element.getReference()).multiResolve(true);
                final Set<DAttributesFinder> attributesFinders = new HashSet<>(resolveResults.length);
                if (resolveResults.length > 1) {
                    for (final ResolveResult resolveResult : resolveResults) {
                        final DAttributesFinder dAttributesFinder = new DAttributesFinder(resolveResult.getElement());
                        dAttributesFinder.recurseUp();
                        attributesFinders.add(dAttributesFinder);
                    }
                    if (attributesFinders.size() == 1) {
                        a = (DAttributesFinder) attributesFinders.toArray()[0];
                    } else
                        return "Unable to resolve symbol to one declaration";
                }
            }
            if (element.equals(originalElement) && element.getReference() != null) {
                return "Unable to resolve symbol to one declaration";
            }
            String blurb = "This symbol is:";//todo
            if (!a.isLocal()) {
                blurb += String.format("%10s%n", a.isPrivate() ? "private " : "");
                blurb += String.format("%10s%n", a.isPublic() ? "public " : "");
                blurb += String.format("%10s%n", a.isProtected() ? "protected " : "");
            }
            blurb += String.format("%10s%n", a.isProperty() ? "property " : "");
            blurb += String.format("%10s%n", a.isStatic() ? "static " : "");
            blurb += String.format("%10s%n", a.isExtern() ? "extern " : "");
            blurb += String.format("%10s%n", a.isNoGC() ? "nogc" : "");
            blurb += String.format("%10s%n", a.isLocal() ? "local" : "");
            return blurb;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    @Override
    public PsiElement getDocumentationElementForLookupItem(PsiManager psiManager, Object object, PsiElement element) {
        return null;
    }

    @Nullable
    @Override
    public PsiElement getDocumentationElementForLink(PsiManager psiManager, String link, PsiElement context) {
        return null;
    }
}
