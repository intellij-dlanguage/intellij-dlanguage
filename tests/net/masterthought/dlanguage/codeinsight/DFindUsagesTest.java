package net.masterthought.dlanguage.codeinsight;

import net.masterthought.dlanguage.DLightPlatformCodeInsightFixtureTestCase;

public class DFindUsagesTest extends DLightPlatformCodeInsightFixtureTestCase {
    public DFindUsagesTest() {
        super("codeinsight", "codeinsight");
    }

    public void testFunctionUsagesInSingleFile()    { doTest(1); }
//    public void testFunctionUsagesInMultipleFiles00001() { doTest(3, "FunctionUsagesInSingleFile00001.hs");}
//    public void testFunctionUsagesInSingleFile00002()    { doTest(2); }

    private void doTest(int expectedResult, String ... extraFiles) {
        String[] files = new String[1 + extraFiles.length];
        files[0] = getTestName(false) + ".d";
        System.arraycopy(extraFiles, 0, files, 1, extraFiles.length);
        assertEquals(expectedResult, myFixture.testFindUsages(files).size());
    }
}
