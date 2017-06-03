package net.masterthought.dlanguage.parser;

import com.intellij.lang.ParserDefinition;
import com.intellij.mock.MockVirtualFile;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.ParsingTestCase;
import com.intellij.testFramework.TestDataFile;
import org.jetbrains.annotations.NonNls;

import java.io.File;
import java.io.IOException;


public abstract class DLanguageParserTestBase extends ParsingTestCase {

    public DLanguageParserTestBase(String dataPath, String fileExt, ParserDefinition... definitions) {
        super(dataPath, fileExt, definitions);
    }

    @Override
    protected String getTestDataPath() {
        return this.getClass().getClassLoader().getResource("gold").getPath();
    }

    @Override
    protected boolean skipSpaces() {
        return true;
    }

    /**
     * Perform a test. Add tests that should work but does not work yet with
     * doTestModule(false, false).
     */
    protected void doTest(boolean checkResult, boolean shouldPass) {
//        String name = getTestName();
//        try {
//            String text = loadFile(name + "." + myFileExt);
//            myFile = createPsiFile(name, text);
//        }catch (IOException e){
//            e.printStackTrace();
//        }
////        if(overwriteFile){
//            String content = toParseTreeText(myFile, skipSpaces(), includeRanges());
//            final String path = myFullDataPath.replace("/",File.separator).replace("build","src" +File.separator + "test").replace("resources" + File.separator + "test","resources") + File.separator +"expected" + File.separator + getTestName() + ".txt";
//            try {
//                Files.write(Paths.get(path.replace('\\','/').replace(":","").replace("/C","")), content.getBytes(), StandardOpenOption.CREATE);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        doTest(checkResult);
        if (shouldPass) {
            assertFalse(
                    "PsiFile contains error elements",
                    toParseTreeText(myFile, skipSpaces(), includeRanges()).contains("PsiErrorElement")
            );
        }
    }


    @Override
    protected void checkResult(@NonNls @TestDataFile String targetDataName,
                               final PsiFile file) throws IOException {
        doCheckResult(myFullDataPath, file, checkAllPsiRoots(),
                "expected" + File.separator + targetDataName, skipSpaces(),
                includeRanges());
/* TODO: Re-enable if we return to parser-helper.
        String phPath = ExecUtil.locateExecutableByGuessing("parser-helper");
        if (phPath != null && !phPath.isEmpty()) {
            String expectedFile = myFullDataPath + File.separator +
                    "expectedJson" + File.separator + targetDataName + ".txt";
            assertSameLinesWithFile(expectedFile,
                    jsonParser.getJson(file.getText(), phPath));
        } else {
            assertEquals(true, false); // Always false.
        }
*/
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        VirtualFile m = new MockVirtualFile(true,myFullDataPath);
        myProject.setBaseDir(m);
    }

}
