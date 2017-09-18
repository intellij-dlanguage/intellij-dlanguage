package net.masterthought.dlanguage.resolve;

import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.impl.PsiMultiReference;
import net.masterthought.dlanguage.DLightPlatformCodeInsightFixtureTestCase;
import net.masterthought.dlanguage.psi.DLanguageClassDeclaration;
import net.masterthought.dlanguage.psi.DLanguageConstructor;
import net.masterthought.dlanguage.psi.DLanguageFunctionDeclaration;
import net.masterthought.dlanguage.psi.DlangIdentifier;

import java.io.File;

public abstract class DResolveTestCase extends DLightPlatformCodeInsightFixtureTestCase {
    private PsiReference referencedElement;
    private PsiElement resolvedElement;

    public DResolveTestCase() {
        super("resolve", "resolve");
    }

    @Override
    protected String getTestDataPath() {
        return this.getClass().getClassLoader().getResource("gold/resolve/" + getTestDirectoryName()).getPath();
    }

    private File[] getTestDataFiles() {
        return new File(getTestDataPath()).listFiles();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        for (final File file : getTestDataFiles()) {
            String text = FileUtil.loadFile(file, CharsetToolkit.UTF8);
            text = StringUtil.convertLineSeparators(text);
            final int referencedOffset = text.indexOf("<ref>");
            text = text.replace("<ref>", "");
            final int resolvedOffset = text.indexOf("<resolved>");
            text = text.replace("<resolved>", "");
            final PsiFile psiFile = myFixture.configureByText(file.getName(), text);
            if (referencedOffset != -1) {
                referencedElement = psiFile.findReferenceAt(referencedOffset);
            }
            if (resolvedOffset != -1) {
                final PsiReference ref = psiFile.findReferenceAt(resolvedOffset);
                if (ref == null) {
                    fail("Reference was null in " + file.getName());
                }
                resolvedElement = ref.getElement();
                ensureNotNull(file);
                // container elements like DEFINITION_FUNCTION need to be looked up by .getElement().getParent()
                if (resolvedElement instanceof DlangIdentifier) {
                    resolvedElement = ref.getElement().getParent();
                }
                //if we're resolving something within a class don't resolve the class
                if (ref instanceof PsiMultiReference && resolvedElement instanceof DLanguageClassDeclaration) {
                    for (final PsiReference psiReference : ((PsiMultiReference) ref).getReferences()) {
                        if (!(psiReference.getElement() instanceof DLanguageClassDeclaration)) {
                            resolvedElement = psiReference.getElement();
                        }
                    }

                }
                ensureNotNull(file);
            }
        }
    }

    private void ensureNotNull(final File file) {
        if (resolvedElement == null) {
            fail("Reference returned null element in " + file.getName());
        }
    }

    protected void doTest() {
        doTest(true);
    }

    protected void doTest(final boolean succeed) {
        if (succeed && referencedElement == null) {
            fail("Could not find reference at caret.");
        }
        if (succeed && resolvedElement == null) {
            fail("Could not find resolved element.");
        }
        if (succeed) {
            //function,class,constructor
            /*if (resolvedElement instanceof DlangInterfaceOrClass ) {
                assertEquals("Could not resolve expected reference.", resolvedElement, referencedElement.resolve().getParent());
            }*//* else if (resolvedElement instanceof DLanguageConstructor) {
                assertTrue(referencedElement.resolve() instanceof DLanguageConstructor);
            }*/ /*else*/
            if(resolvedElement instanceof DLanguageConstructor)
                assertEquals("Could not resolve expected reference.", resolvedElement, referencedElement.resolve());
            else if(super.getTestName(true).equals("scopedImportsMembers"))
                assertEquals("Could not resolve expected reference.", "struct_member", ((DLanguageFunctionDeclaration)referencedElement.resolve().getParent()).getName());
            else
                assertEquals("Could not resolve expected reference.", resolvedElement, referencedElement.resolve().getParent());
        } else {
            assertFalse("Resolved unexpected reference.", resolvedElement.equals(referencedElement.resolve()));
        }
    }
}

