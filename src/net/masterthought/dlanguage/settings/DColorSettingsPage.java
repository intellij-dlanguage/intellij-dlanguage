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

//    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[] {
//            new AttributesDescriptor(DHighlighter.OPERATOR.getExternalName(), DHighlighter.OPERATOR),
//            new AttributesDescriptor(DHighlighter.STRING_LITERAL.getExternalName(), DHighlighter.STRING_LITERAL),
//            new AttributesDescriptor(DHighlighter.LINE_COMMENT.getExternalName(), DHighlighter.LINE_COMMENT),
//            new AttributesDescriptor(DHighlighter.BLOCK_COMMENT.getExternalName(), DHighlighter.BLOCK_COMMENT),
//            new AttributesDescriptor(DHighlighter.DOC_COMMENT.getExternalName(), DHighlighter.DOC_COMMENT),
//            new AttributesDescriptor(DHighlighter.KEYWORD.getExternalName(), DHighlighter.KEYWORD),
//            new AttributesDescriptor(DHighlighter.OPERATOR.getExternalName(), DHighlighter.OPERATOR),
//            new AttributesDescriptor(DHighlighter.PARENS.getExternalName(), DHighlighter.PARENS),
//            new AttributesDescriptor(DHighlighter.BRACES.getExternalName(), DHighlighter.BRACES),
//            new AttributesDescriptor(DHighlighter.BRACKETS.getExternalName(), DHighlighter.BRACKETS),
//    };



    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("Illegal character", ILLEGAL),
            new AttributesDescriptor("Comment", COMMENT),
            new AttributesDescriptor("String", STRING),
            new AttributesDescriptor("Number", NUMBER),
            new AttributesDescriptor("Keyword", KEYWORD),
            new AttributesDescriptor("Parenthesis", PARENTHESES),
            new AttributesDescriptor("Braces", BRACES),
            new AttributesDescriptor("Brackets", BRACKETS),
            new AttributesDescriptor("Operation sign", OP_SIGN),
            new AttributesDescriptor("Variables", VARIABLE),
            new AttributesDescriptor("Function definition", FUNCTION_DEFINITION)
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
        String code = "" +
                "/*\n" +
                " * Computes average line length for standard input.\n" +
                " */\n" +
                "import std.stdio;\n" +
                "\n" +
                "void main() {\n" +
                "    ulong lines = 0;\n" +
                "    double sumLength = 0;\n" +
                "    foreach (line; stdin.byLine()) {\n" +
                "        ++lines; // increment\n" +
                "        sumLength += line.length;\n" +
                "    }\n" +
                "    writeln(\"Average line length: \",\n" +
                "        lines ? sumLength / lines : 0);\n" +
                " }\n" +
                "";

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

