package net.masterthought.dlanguage.features;

import com.intellij.openapi.editor.FoldRegion;
import net.masterthought.dlanguage.DLightPlatformCodeInsightFixtureTestCase;

/**
 * Folding builder test driver. Add new folding builder testcases here.
 */
public class DFoldingBuilderTest extends DLightPlatformCodeInsightFixtureTestCase {
    public DFoldingBuilderTest() {
        super("features", "features");
    }

    // The tests.

    // Fold00001 should really result in "  ". OPENCOM and CLOSECOM remains in
    // the text, but the rest gets collapsed to "  ".
    public void testFold00001() throws Throwable {
        doTest("main (Function) ...");
    }
//    public void testFold00002() throws Throwable { doTestModule("--"); }

    /**
     * Folds the region at the caret and verifies that it is indeed collapsed
     * and the placeholder text corresponds to the expected one.
     * <p>
     * There is supposedly support in the base class for testing folding but
     * I had little luck when following the tutorial verbatim.
     */
    private void doTest(String collapsedText) {
        myFixture.configureByFile(getTestDataPath(getTestName(false) + ".d"));
        myFixture.performEditorAction("CollapseAllRegions");
        FoldRegion[] all = myFixture.getEditor().getFoldingModel().getAllFoldRegions();
        for (FoldRegion region : all) {
            assertEquals(collapsedText, region.getPlaceholderText());
            assertFalse(region.isExpanded());
        }
    }
}
