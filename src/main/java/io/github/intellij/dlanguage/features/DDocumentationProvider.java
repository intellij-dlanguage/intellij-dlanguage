package io.github.intellij.dlanguage.features;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.ResolveResult;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.references.DReference;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributesFinder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.intellij.lang.documentation.DocumentationMarkup.*;

/**
 * Created by francis on 7/18/2017.
 */
public class DDocumentationProvider extends AbstractDocumentationProvider implements DocumentationProvider {

    private static final Logger LOG = Logger.getInstance(DDocumentationProvider.class);

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
    public String getQuickNavigateInfo(final PsiElement element, final PsiElement originalElement) {
        return null;
    }

    @Nullable
    @Override
    public List<String> getUrlFor(final PsiElement element, final PsiElement originalElement) {
        return new ArrayList<>();
    }

    /**
     * <p>Callback for asking the doc provider for the complete documentation.
     * Underlying implementation may be time-consuming, that's why this method is expected not to be called from EDT.</p>
     *
     * <p>One can use {@link com.intellij.lang.documentation.DocumentationMarkup} to get proper content layout. Typical sample will look like this:
     * <pre>
     * DEFINITION_START + definition + DEFINITION_END +
     * CONTENT_START + main description + CONTENT_END +
     * SECTIONS_START +
     *   SECTION_HEADER_START + section name +
     *     SECTION_SEPARATOR + "&lt;p&gt;" + section content + SECTION_END +
     *   ... +
     * SECTIONS_END
     * </pre>
     * </p>
     * To show different content on mouse hover in editor, {@link #generateHoverDoc(PsiElement, PsiElement)} should be implemented.
     *
     * @param element         the element for which the documentation is requested (for example, if the mouse is over
     *                        a method reference, this will be the method to which the reference is resolved).
     * @param originalElement the element under the mouse cursor
     * @return target element's documentation, or {@code null} if provider is unable to generate documentation
     * for the given element
     */
    @Nullable
    @Override
    public String generateDoc(final PsiElement element, @Nullable final PsiElement originalElement) {
        // todo: check out JavaDocumentationProvider for some ideas. Depending on the PsiElement being passed in
        // we should probably generate the documentation in different formats
        if(element instanceof DNamedElement && element.getParent() instanceof DNamedElement) {
            try {
                DAttributesFinder a = new DAttributesFinder(element);
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
                return buildDocumentationString(a);
            } catch (final Exception e) {
                LOG.error("Could not generate documentation", e);
            }
        }
        return null;
    }

    @Nullable
    @Override
    public PsiElement getDocumentationElementForLookupItem(final PsiManager psiManager, final Object object, final PsiElement element) {
        return null;
    }

    @Nullable
    @Override
    public PsiElement getDocumentationElementForLink(final PsiManager psiManager, final String link, final PsiElement context) {
        return null;
    }

    /*
    * This method builds up the html text that will be used for tooltip content in the IDE while hovering over variables and
    * function names.
    *
    * Use the html markup as specified in DocumentationMarkup
    */
    private String buildDocumentationString(@NotNull final DAttributesFinder a) {
        final StringBuilder sb = new StringBuilder();
        sb.append(DEFINITION_START);
        if (!a.isLocal()) {
            if(a.isPrivate()) {
                sb.append("private");
            } else if(a.isPublic()) {
                sb.append("public");
            } else if(a.isProtected()) {
                sb.append("protected");
            }
            sb.append(" ");
        }
        sb.append("symbol").append(" ");

        //sb.append(CONTENT_START);

        if(a.isProperty()) {
            sb.append(GRAYED_START).append("property").append(GRAYED_END).append(" ");
        }

        if(a.isStatic()) {
            sb.append(GRAYED_START).append("static").append(GRAYED_END).append(" ");
        }

        if(a.isExtern()) {
            sb.append(GRAYED_START).append("extern").append(GRAYED_END).append(" ");
        }

        if(a.isNoGC()) {
            sb.append(GRAYED_START).append("nogc").append(GRAYED_END).append(" ");
        }

        if(a.isLocal()) {
            sb.append(GRAYED_START).append("local").append(GRAYED_END).append(" ");
        }

        //sb.append(CONTENT_END);

        sb.append(DEFINITION_END);

        return sb.toString();
    }
}
