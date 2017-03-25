package net.masterthought.dlanguage;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.masterthought.dlanguage.codeinsight.DFindUsagesTest;
import net.masterthought.dlanguage.features.DFoldingBuilderTest;
import net.masterthought.dlanguage.lexer.DLanguageLexerTest;
import net.masterthought.dlanguage.parser.DLanguageParserTest;
import net.masterthought.dlanguage.resolve.DResolveTest;

/**
 * Main testsuite driver. Specifies which components that should be tested.
 * Test cases belong in the individual test suites.
 */
@SuppressWarnings("ALL")
public class DTestCase extends TestCase {
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();

        suite.addTestSuite(DLanguageLexerTest.class);
        suite.addTestSuite(DLanguageParserTest.class);

        // not working in ant for some reason
        suite.addTestSuite(DResolveTest.class);
        suite.addTestSuite(DFindUsagesTest.class);
        suite.addTestSuite(DFoldingBuilderTest.class);
//        suite.addTestSuite(DPsiImplUtilTest.class);

//        suite.addTestSuite(HaskellParsingLexerTest.class);
//        suite.addTestSuite(HaskellParserTest.class);
//        suite.addTestSuite(HaskellFeaturesTest.class);
//        suite.addTestSuite(HaskellFoldingBuilderTest.class);
//        suite.addTestSuite(HaskellCompletionTest.class);
//        suite.addTestSuite(HaskellFindUsagesTest.class);
//        suite.addTestSuite(HaskellRenameTest.class);
//        suite.addTestSuite(HaskellResolveTest.class);
        return suite;
    }
}

