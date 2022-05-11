package io.github.intellij.dlanguage.formatting

import com.intellij.psi.formatter.FormatterTestCase
import io.github.intellij.dlanguage.DlangFileType

class DFormatterTest : FormatterTestCase() {
    override fun getBasePath(): String {
        return "formatting"
    }

    override fun getTestDataPath(): String {
        return this.javaClass.classLoader.getResource("gold")!!.path
    }

    override fun getFileExtension(): String {
        return DlangFileType.DEFAULT_EXTENSION
    }

    @Throws(Exception::class)
    override fun doTest(resultNumber: String?) {
        val testName = getTestName(false)
        doTest("$testName.$fileExtension", "$testName-after.$fileExtension", resultNumber)
    }

//    fun testFormattingFunctionOpeningBrace() {
//        doTextTest("void main(){\n}", "void main() {\n}");
//    }

    fun testFormattingFunctionClosingBrace() {
        doTextTest("void main() {\n  }", "void main() {\n}")
    }

    fun testFormattingLinesWithinFunction() {
        doTextTest("void main() {\n    foo();\n  bar();\n}", "void main() {\n    foo();\n    bar();\n}")
    }

    // todo: fix this in DFormattingModelBuilder
//    fun testFormattingImport() {
//        doTextTest("import std.stdio:stderr,writeln;", "import std.stdio : stderr, writeln;");
//    }

    @Throws(Exception::class)
    fun testhello() {
        doTest()
    }

    @Throws(Exception::class)
    fun testDeclSpacing() {
        doTest()
    }

    @Throws(Exception::class)
    fun testattribute_constraint() {
        doTest()
    }

    @Throws(Exception::class)
    fun testdlanguage_issue_497() {
        doTest()
    }

//
//    @Throws(Exception::class)
//    fun testbreakOnDots() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testcatchExceptionNested() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testcomments() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testcontracts() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testfrontpage() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testguessnumber() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testhigherorder() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0017() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0018() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0021() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0022() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0023() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0024() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0025() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0026() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0027() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0028() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0029() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0030() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0031() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0032() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0033() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0034() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0035() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0037() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0038() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0039() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0041() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0042() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0043() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0044() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0045() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0046() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0047() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0048() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0049() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0050() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0051() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0052() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0053() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0054() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0056() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0057() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0058() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0059() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0060() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0061() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0062() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0063() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0064() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0065() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0066() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0067() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0068() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0069() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0070() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0073() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0074() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0076() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0079() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0080() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0081() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0082() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0083() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0085() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0086() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0088() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0089() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0090() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0091() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0092() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0093() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0094() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0095() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0096() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0097() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0098() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0099() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0100() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0101() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0102() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0106() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0107() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0108() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0109() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0111() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0112() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0114() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0116() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0117() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0118() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0119() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0120() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0123() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0125() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0126() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0127() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0128() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0130() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0136() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0138() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0139() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0140() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0142() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0146() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0147() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0148() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0150() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0151() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0152() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0153() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0154() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0155() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0156() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0158() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0162() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0166() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0169() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0172() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0174() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0177() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0185() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0186() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0187() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0189() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0190() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0194() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0204() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0205() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0206() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0207() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0208() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0209() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0210() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0212() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0213() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0215a() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0215b() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0215c() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0215d() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0216() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0219() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0220() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0221() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0222() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0223() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0224() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0225() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0226() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0229() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0241() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0251() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testissue0273() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testlongParamList() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testminimizeLength() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testmultiline_string() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testparenIndent() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testpropertySpacing() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testswap() {
//        doTest();
//    }
//
//    @Throws(Exception::class)
//    fun testwrapping1() {
//        doTest();
//    }
}
