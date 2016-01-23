package net.masterthought.dlanguage.settings;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.util.containers.ContainerUtil;
import net.masterthought.dlanguage.DLanguageFileType;
import net.masterthought.dlanguage.DLanguageIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.masterthought.dlanguage.highlighting.DHighlighter;

import javax.swing.Icon;
import java.util.Map;

import static net.masterthought.dlanguage.highlighting.DHighlighter.*;


public class DColorSettingsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("Illegal character", ILLEGAL),
            new AttributesDescriptor("Line Comment", LINE_COMMENT),
            new AttributesDescriptor("Block Comment", BLOCK_COMMENT),
            new AttributesDescriptor("String", STRING),
            new AttributesDescriptor("Number", NUMBER),
            new AttributesDescriptor("Keyword", KEYWORD),
            new AttributesDescriptor("Parenthesis", PARENTHESES),
            new AttributesDescriptor("Braces", BRACES),
            new AttributesDescriptor("Dot", DOT),
            new AttributesDescriptor("Comma", COMMA),
            new AttributesDescriptor("Semicolon", SEMICOLON),
            new AttributesDescriptor("Brackets", BRACKETS),
            new AttributesDescriptor("Operation sign", OPERATOR),
//            new AttributesDescriptor("Function definition", FUNCTION_DEFINITION),
//            new AttributesDescriptor("Std Imports", STD_IMPORT),
            new AttributesDescriptor("Module definition", MODULE_DEFINITION),
//            new AttributesDescriptor("Basic type", BASIC_TYPE),
//            new AttributesDescriptor("Aggregate definition", AGGREGATE_DEFINITION),
//            new AttributesDescriptor("User defined attribute", USER_DEFINED_ATTRIBUTE)
    };

    private static Map<String, TextAttributesKey> ATTRIBUTES_KEY_MAP = ContainerUtil.newHashMap();

    static {
        ATTRIBUTES_KEY_MAP.put("k", KEYWORD);
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return DLanguageIcons.FILE;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new DHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        // Example from dlang.org
        String code = "/*\n" +
                "  Some block commments\n" +
                "  go here\n" +
                "*/\n" +
                "// Line comment\n" +
                "/+\n" +
                " Nesting comment\n" +
                "+/\n" +
                "module net.masterthought.cucumber.report_information;\n" +
                "\n" +
                "import std.algorithm;\n" +
                "import std.array;\n" +
                "\n" +
                "import jsonizer.tojson;\n" +
                "import net.masterthought.cucumber.report_parser;\n" +
                "\n" +
                "class ReportInformation{\n" +
                "\n" +
                "  string runId;\n" +
                "  Feature[] features;\n" +
                "\n" +
                "  this(ReportParser parser){\n" +
                "  \tthis.runId = parser.getRunId();\n" +
                "    this.features = parser.getReports().map!(report => report.getFeatures()).joiner.array;\n" +
                "  }\n" +
                "\n" +
                "  private Feature[] processFeatures(Feature[] features){\n" +
                "    return features.map!((f){\n" +
                "        f.featureInformation = calculateFeatureInformation(f);\n" +
                "        f.scenarios = addScenarioInformation(f);\n" +
                "        return f;\n" +
                "      }).array;\n" +
                "  }\n" +
                "\n" +
                "\n" +
                "  public auto getTotalNumberOfBackgroundScenariosUnknown(){\n" +
                "   return features.map!(f => f.getBackgroundScenariosUnknown().length).sum;\n" +
                " }\n" +
                "\n" +
                " struct {\n" +
                "  string name;\n" +
                " }\n" +
                "\n" +
                " union {\n" +
                "  string day;\n" +
                " }\n" +
                "\n" +
                "  unittest {\n" +
                "\n" +
                "     // load test json from file\n" +
                "     auto testJson = to!string(read(\"src/test/resources/project1.json\"));\n" +
                "     string runId = \"run 1\";\n" +
                "     ReportInformation ri = new ReportInformation(new ReportParser(runId,[testJson]));\n" +
                "\n" +
                "     // should have correct number of features\n" +
                "     ri.getFeatures().length.assertEqual(2);\n" +
                "\n" +
                "     // overall status\n" +
                "     ri.getOverallStatus.assertEqual(to!string(Status.Failed));\n" +
                "\n" +
                "     // feature totals\n" +
                "     Feature feature = ri.getFeatures().front;\n" +
                "\n" +
                "}\n" +
                "}";

        return code;
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return ATTRIBUTES_KEY_MAP;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return DLanguageFileType.INSTANCE.getName();
    }
}

