package net.masterthought.dlanguage.util;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.DLightPlatformCodeInsightFixtureTestCase;
import net.masterthought.dlanguage.psi.DLanguageIdentifier;
import net.masterthought.dlanguage.psi.DLanguageModuleDeclaration;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by franc on 2/25/2017.
 */
public class DPsiImplUtilTestCase extends DLightPlatformCodeInsightFixtureTestCase {

    String expected = "";

    /**
     * Sets the expected input and outputs and calls the constructor of the parent.
     */
    protected DPsiImplUtilTestCase() {
        super("util", "util");
    }

    @Override
    protected String getTestDataPath() {
        return this.getClass().getClassLoader().getResource("gold/util/" + getTestDirectoryName() + "/").getPath();
    }

    private File[] getTestDataFiles() {
        return new File(getTestDataPath()).listFiles();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        for (File file : getTestDataFiles()) {
            String path = file.getPath();
            if (path.substring(path.length() - 4, path.length()).equals(".txt")) {
                byte[] encoded = Files.readAllBytes(Paths.get(path));
                expected = new String(encoded, Charset.defaultCharset());
            } else {
                assertTrue("invalid file?", path.substring(path.length() - 2, path.length()).equals(".d"));
                myFixture.configureByFile(path.replace("C:\\", "\\").replace("\\", "/"));

            }
        }
    }

    protected void doTestModule() {
        doTestModule(true);
    }

    protected void doTestModule(boolean succeed) {
        String result = "";
        final DLanguageModuleDeclaration module = PsiTreeUtil.findChildOfType(myFixture.getFile(), DLanguageModuleDeclaration.class);
        List<PsiElement> elements = new ArrayList<>();
//        elements.addAll(module.g);
//        elements.addAll(module.g);
//        elements.addAll(module.g);
//        elements.addAll(module.g);
//        elements.addAll(module.g);
//        elements.addAll(module.g);
//        elements.addAll(module.g);
//        elements.addAll(module.g);
//        elements.addAll(module.g);
//        elements.addAll(module.g);
//        elements.addAll(module.g);
//        elements.addAll(module.g);

        for (PsiElement element : elements) {
            result += PsiTreeUtil.findChildOfType(element, DLanguageIdentifier.class).getName();
            result += '\n';
        }

        assertEquals(result, expected);
    }
}
