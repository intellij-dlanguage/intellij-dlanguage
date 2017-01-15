package net.masterthought.dlanguage.resolve;

import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.DLightPlatformCodeInsightFixtureTestCase;
import net.masterthought.dlanguage.psi.DLanguageClassDeclaration;
import net.masterthought.dlanguage.psi.DLanguageFuncDeclaration;
import net.masterthought.dlanguage.psi.DLanguageIdentifier;
import net.masterthought.dlanguage.psi.DLanguageTemplateDeclaration;


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
        for (File file : getTestDataFiles()) {
            String text = FileUtil.loadFile(file, CharsetToolkit.UTF8);
            text = StringUtil.convertLineSeparators(text);
            int referencedOffset = text.indexOf("<ref>");
            text = text.replace("<ref>", "");
            int resolvedOffset = text.indexOf("<resolved>");
            text = text.replace("<resolved>", "");
            PsiFile psiFile = myFixture.configureByText(file.getName(), text);
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
                if (resolvedElement instanceof DLanguageIdentifier) {
                    resolvedElement = ref.getElement().getParent();
                }
                ensureNotNull(file);
            }
        }
    }

    private void ensureNotNull(File file) {
        if (resolvedElement == null) {
            fail("Reference returned null element in " + file.getName());
        }
    }

    protected void doTest() {
        doTest(true);
    }

    protected void doTest(boolean succeed) {
        if (succeed && referencedElement == null) {
            fail("Could not find reference at caret.");
        }
        if (succeed && resolvedElement == null) {
            fail("Could not find resolved element.");
        }
        if (succeed) {
            if(resolvedElement instanceof DLanguageFuncDeclaration || resolvedElement instanceof DLanguageClassDeclaration || resolvedElement instanceof DLanguageTemplateDeclaration) {
                //we want to resolve the identifier but for the purpose of tests we should use getParent to get the actual declaration insteadof the identifier part of the declaration
                assertEquals("Could not resolve expected reference.", resolvedElement, referencedElement.resolve().getParent());
            }
            else
                assertEquals("Could not resolve expected reference.", resolvedElement, referencedElement.resolve());

        } else {
            assertFalse("Resolved unexpected reference.", resolvedElement.equals(referencedElement.resolve().getParent()));
        }
    }
}

