package net.masterthought.dlanguage.formatting;


import com.intellij.psi.formatter.FormatterTestCase;
import net.masterthought.dlanguage.DLanguageFileType;
import org.jetbrains.annotations.NotNull;

public class DFormatterTest extends FormatterTestCase {
    @NotNull
    @Override
    protected String getBasePath() {
        return "formatting";
    }

    protected String getTestDataPath() {
        return this.getClass().getClassLoader().getResource("gold").getPath();
    }

    @Override
    protected String getFileExtension() {
        return DLanguageFileType.DEFAULT_EXTENSION;
    }

    protected void doTest(String resultNumber) throws Exception {
        String testName = getTestName(false);
        doTest(testName + "." + getFileExtension(), testName + "-after." + getFileExtension(), resultNumber);
    }

    public void testhello() throws Exception {
        doTest();
    }

    public void testDeclSpacing() throws Exception {
        doTest();
    }

    public void testattribute_constraint() throws Exception {
        doTest();
    }

    public void testbreakOnDots() throws Exception {
        doTest();
    }

    public void testcatchExceptionNested() throws Exception {
        doTest();
    }

    public void testcomments() throws Exception {
        doTest();
    }

    public void testcontracts() throws Exception {
        doTest();
    }

    public void testfrontpage() throws Exception {
        doTest();
    }

    public void testguessnumber() throws Exception {
        doTest();
    }

    public void testhigherorder() throws Exception {
        doTest();
    }

    public void testissue0017() throws Exception {
        doTest();
    }

    public void testissue0018() throws Exception {
        doTest();
    }

    public void testissue0021() throws Exception {
        doTest();
    }

    public void testissue0022() throws Exception {
        doTest();
    }

    public void testissue0023() throws Exception {
        doTest();
    }

    public void testissue0024() throws Exception {
        doTest();
    }

    public void testissue0025() throws Exception {
        doTest();
    }

    public void testissue0026() throws Exception {
        doTest();
    }

    public void testissue0027() throws Exception {
        doTest();
    }

    public void testissue0028() throws Exception {
        doTest();
    }

    public void testissue0029() throws Exception {
        doTest();
    }

    public void testissue0030() throws Exception {
        doTest();
    }

    public void testissue0031() throws Exception {
        doTest();
    }

    public void testissue0032() throws Exception {
        doTest();
    }

    public void testissue0033() throws Exception {
        doTest();
    }

    public void testissue0034() throws Exception {
        doTest();
    }

    public void testissue0035() throws Exception {
        doTest();
    }

    public void testissue0037() throws Exception {
        doTest();
    }

    public void testissue0038() throws Exception {
        doTest();
    }

    public void testissue0039() throws Exception {
        doTest();
    }

    public void testissue0041() throws Exception {
        doTest();
    }

    public void testissue0042() throws Exception {
        doTest();
    }

    public void testissue0043() throws Exception {
        doTest();
    }

    public void testissue0044() throws Exception {
        doTest();
    }

    public void testissue0045() throws Exception {
        doTest();
    }

    public void testissue0046() throws Exception {
        doTest();
    }

    public void testissue0047() throws Exception {
        doTest();
    }

    public void testissue0048() throws Exception {
        doTest();
    }

    public void testissue0049() throws Exception {
        doTest();
    }

    public void testissue0050() throws Exception {
        doTest();
    }

    public void testissue0051() throws Exception {
        doTest();
    }

    public void testissue0052() throws Exception {
        doTest();
    }

    public void testissue0053() throws Exception {
        doTest();
    }

    public void testissue0054() throws Exception {
        doTest();
    }

    public void testissue0056() throws Exception {
        doTest();
    }

    public void testissue0057() throws Exception {
        doTest();
    }

    public void testissue0058() throws Exception {
        doTest();
    }

    public void testissue0059() throws Exception {
        doTest();
    }

    public void testissue0060() throws Exception {
        doTest();
    }

    public void testissue0061() throws Exception {
        doTest();
    }

    public void testissue0062() throws Exception {
        doTest();
    }

    public void testissue0063() throws Exception {
        doTest();
    }

    public void testissue0064() throws Exception {
        doTest();
    }

    public void testissue0065() throws Exception {
        doTest();
    }

    public void testissue0066() throws Exception {
        doTest();
    }

    public void testissue0067() throws Exception {
        doTest();
    }

    public void testissue0068() throws Exception {
        doTest();
    }

    public void testissue0069() throws Exception {
        doTest();
    }

    public void testissue0070() throws Exception {
        doTest();
    }

    public void testissue0073() throws Exception {
        doTest();
    }

    public void testissue0074() throws Exception {
        doTest();
    }

    public void testissue0076() throws Exception {
        doTest();
    }

    public void testissue0079() throws Exception {
        doTest();
    }

    public void testissue0080() throws Exception {
        doTest();
    }

    public void testissue0081() throws Exception {
        doTest();
    }

    public void testissue0082() throws Exception {
        doTest();
    }

    public void testissue0083() throws Exception {
        doTest();
    }

    public void testissue0085() throws Exception {
        doTest();
    }

    public void testissue0086() throws Exception {
        doTest();
    }

    public void testissue0088() throws Exception {
        doTest();
    }

    public void testissue0089() throws Exception {
        doTest();
    }

    public void testissue0090() throws Exception {
        doTest();
    }

    public void testissue0091() throws Exception {
        doTest();
    }

    public void testissue0092() throws Exception {
        doTest();
    }

    public void testissue0093() throws Exception {
        doTest();
    }

    public void testissue0094() throws Exception {
        doTest();
    }

    public void testissue0095() throws Exception {
        doTest();
    }

    public void testissue0096() throws Exception {
        doTest();
    }

    public void testissue0097() throws Exception {
        doTest();
    }

    public void testissue0098() throws Exception {
        doTest();
    }

    public void testissue0099() throws Exception {
        doTest();
    }

    public void testissue0100() throws Exception {
        doTest();
    }

    public void testissue0101() throws Exception {
        doTest();
    }

    public void testissue0102() throws Exception {
        doTest();
    }

    public void testissue0106() throws Exception {
        doTest();
    }

    public void testissue0107() throws Exception {
        doTest();
    }

    public void testissue0108() throws Exception {
        doTest();
    }

    public void testissue0109() throws Exception {
        doTest();
    }

    public void testissue0111() throws Exception {
        doTest();
    }

    public void testissue0112() throws Exception {
        doTest();
    }

    public void testissue0114() throws Exception {
        doTest();
    }

    public void testissue0116() throws Exception {
        doTest();
    }

    public void testissue0117() throws Exception {
        doTest();
    }

    public void testissue0118() throws Exception {
        doTest();
    }

    public void testissue0119() throws Exception {
        doTest();
    }

    public void testissue0120() throws Exception {
        doTest();
    }

    public void testissue0123() throws Exception {
        doTest();
    }

    public void testissue0125() throws Exception {
        doTest();
    }

    public void testissue0126() throws Exception {
        doTest();
    }

    public void testissue0127() throws Exception {
        doTest();
    }

    public void testissue0128() throws Exception {
        doTest();
    }

    public void testissue0130() throws Exception {
        doTest();
    }

    public void testissue0136() throws Exception {
        doTest();
    }

    public void testissue0138() throws Exception {
        doTest();
    }

    public void testissue0139() throws Exception {
        doTest();
    }

    public void testissue0140() throws Exception {
        doTest();
    }

    public void testissue0142() throws Exception {
        doTest();
    }

    public void testissue0146() throws Exception {
        doTest();
    }

    public void testissue0147() throws Exception {
        doTest();
    }

    public void testissue0148() throws Exception {
        doTest();
    }

    public void testissue0150() throws Exception {
        doTest();
    }

    public void testissue0151() throws Exception {
        doTest();
    }

    public void testissue0152() throws Exception {
        doTest();
    }

    public void testissue0153() throws Exception {
        doTest();
    }

    public void testissue0154() throws Exception {
        doTest();
    }

    public void testissue0155() throws Exception {
        doTest();
    }

    public void testissue0156() throws Exception {
        doTest();
    }

    public void testissue0158() throws Exception {
        doTest();
    }

    public void testissue0162() throws Exception {
        doTest();
    }

    public void testissue0166() throws Exception {
        doTest();
    }

    public void testissue0169() throws Exception {
        doTest();
    }

    public void testissue0172() throws Exception {
        doTest();
    }

    public void testissue0174() throws Exception {
        doTest();
    }

    public void testissue0177() throws Exception {
        doTest();
    }

    public void testissue0185() throws Exception {
        doTest();
    }

    public void testissue0186() throws Exception {
        doTest();
    }

    public void testissue0187() throws Exception {
        doTest();
    }

    public void testissue0189() throws Exception {
        doTest();
    }

    public void testissue0190() throws Exception {
        doTest();
    }

    public void testissue0194() throws Exception {
        doTest();
    }

    public void testissue0204() throws Exception {
        doTest();
    }

    public void testissue0205() throws Exception {
        doTest();
    }

    public void testissue0206() throws Exception {
        doTest();
    }

    public void testissue0207() throws Exception {
        doTest();
    }

    public void testissue0208() throws Exception {
        doTest();
    }

    public void testissue0209() throws Exception {
        doTest();
    }

    public void testissue0210() throws Exception {
        doTest();
    }

    public void testissue0212() throws Exception {
        doTest();
    }

    public void testissue0213() throws Exception {
        doTest();
    }

    public void testissue0215a() throws Exception {
        doTest();
    }

    public void testissue0215b() throws Exception {
        doTest();
    }

    public void testissue0215c() throws Exception {
        doTest();
    }

    public void testissue0215d() throws Exception {
        doTest();
    }

    public void testissue0216() throws Exception {
        doTest();
    }

    public void testissue0219() throws Exception {
        doTest();
    }

    public void testissue0220() throws Exception {
        doTest();
    }

    public void testissue0221() throws Exception {
        doTest();
    }

    public void testissue0222() throws Exception {
        doTest();
    }

    public void testissue0223() throws Exception {
        doTest();
    }

    public void testissue0224() throws Exception {
        doTest();
    }

    public void testissue0225() throws Exception {
        doTest();
    }

    public void testissue0226() throws Exception {
        doTest();
    }

    public void testissue0229() throws Exception {
        doTest();
    }

    public void testissue0241() throws Exception {
        doTest();
    }

    public void testissue0251() throws Exception {
        doTest();
    }

    public void testissue0273() throws Exception {
        doTest();
    }

    public void testlongParamList() throws Exception {
        doTest();
    }

    public void testminimizeLength() throws Exception {
        doTest();
    }

    public void testmultiline_string() throws Exception {
        doTest();
    }

    public void testparenIndent() throws Exception {
        doTest();
    }

    public void testpropertySpacing() throws Exception {
        doTest();
    }

    public void testswap() throws Exception {
        doTest();
    }

    public void testwrapping1() throws Exception {
        doTest();
    }


}
