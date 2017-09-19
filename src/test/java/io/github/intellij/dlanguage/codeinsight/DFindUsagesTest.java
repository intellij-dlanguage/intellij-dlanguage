package io.github.intellij.dlanguage.codeinsight;

import io.github.intellij.dlanguage.DLightPlatformCodeInsightFixtureTestCase;

public class DFindUsagesTest extends DLightPlatformCodeInsightFixtureTestCase {
    public DFindUsagesTest() {
        super("codeinsight", "codeinsight");
    }

    @Override
    protected String getTestDataPath() {
        return this.getClass().getClassLoader().getResource("gold/codeinsight").getPath();
    }

    public void testFunctionUsagesInSingleFile() {
        doTest(1);
    }
//    public void testFunctionUsagesInMultipleFiles00001() { doTestModule(3, "FunctionUsagesInSingleFile00001.hs");}
//    public void testFunctionUsagesInSingleFile00002()    { doTestModule(2); }

    private void doTest(final int expectedResult, final String... extraFiles) {
        final String[] files = new String[1 + extraFiles.length];
        files[0] = getTestName(false) + ".d";
        System.arraycopy(extraFiles, 0, files, 1, extraFiles.length);
        assertEquals(expectedResult, myFixture.testFindUsages(files).size());
    }
}
