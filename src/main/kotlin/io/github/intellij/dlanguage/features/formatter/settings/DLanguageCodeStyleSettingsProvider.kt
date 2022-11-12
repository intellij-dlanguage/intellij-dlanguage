package io.github.intellij.dlanguage.features.formatter.settings

import com.intellij.application.options.CodeStyleAbstractConfigurable
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.application.options.IndentOptionsEditor
import com.intellij.application.options.SmartIndentOptionsEditor
import com.intellij.lang.Language
import com.intellij.psi.codeStyle.*
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.features.formatter.DCodeStyleSettings

class DLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {

    companion object {
        @org.intellij.lang.annotations.Language("D")
        private const val DEFAULT_CODE_SAMPLE =
"""
module foo.bar.example;

import std.stdio : writeln;

class A {
private:
    int a;
public:
    void foo() {
        import std.stdio : writefln;
        string bar = "test";
        writefln("variable bar = `%s`", bar);
    }
    protected float[] n;
}

alias AClass = A;

version (Test) {
    static assert (10 == 5 * 2, "The computation arithmetic is buggy");
}

string bar(int i) {
    string ret = "got ";
    switch (i) {
        case 1:
            return ret ~ "one";
        case 2:
            throw Exception("two is forbidden");
        case 3: .. case 12:
            ret ~= "three to twelve";
        case 13:
        case 14:
            ret ~= "thirteen or fourteen";
        default:
            assert(false);
    }
    return ret;
}

void foo(int x, string...) {
    try {
        writeln(bar(3));
    } catch (Exception e) {
        writeln(e);
    }
}

long square_root(long x) pure @system @nogc
in {
    assert(x >= 0);
} out (result) {
    assert((result * result) <= x
        && (result+1) * (result+1) > x);
} do {
    return cast(long)std.math.sqrt(cast(real)x);
}

@safe unittest
{
    import std.exception : assertThrown, assertNotThrown;
    static import std.file;

    auto deleteme = testFilename();
    std.file.write(deleteme, "foo");
    scope(exit) std.file.remove(deleteme);
    auto f = File(deleteme);
    assert(f.readln() == "foo");
}

void doSomething(int function(int, int) doer) {
    // call passed function
    doer(5,5);
}

auto add(T)(T lhs, T rhs) {
    return lhs + rhs;
}

void main() {
    string a = "this
    is
    a
    multiline string";

    mixin(`string b = r"raw \n string";`);

    "Hello".writeln;
    add(3f, 12.67f);
    doSomething(&add);

    bool compiles = __traits(compiles, obvious error - ${'$'}%42);

    int[string] assoc_array;
    assoc_array["shoe"] = 12;
    assoc_array["tie"] = 1;
}
"""
    }

    override fun getLanguage(): Language = DLanguage

    override fun getCodeSample(settingsType: SettingsType): String = DEFAULT_CODE_SAMPLE

    override fun getIndentOptionsEditor(): IndentOptionsEditor = SmartIndentOptionsEditor()

    override fun createConfigurable(baseSettings: CodeStyleSettings,
                                    modelSettings: CodeStyleSettings): CodeStyleConfigurable =
        object : CodeStyleAbstractConfigurable(baseSettings, modelSettings, configurableDisplayName) {
            override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel =
                DCodeStyleMainPanel(currentSettings, settings)
        }

    override fun createCustomSettings(settings: CodeStyleSettings): CustomCodeStyleSettings = DCodeStyleSettings(settings)

    override fun customizeDefaults(commonSettings: CommonCodeStyleSettings,
                                   indentOptions: CommonCodeStyleSettings.IndentOptions) {
        indentOptions.INDENT_SIZE = 4
        indentOptions.CONTINUATION_INDENT_SIZE = 4
        indentOptions.TAB_SIZE = 4
        indentOptions.USE_TAB_CHARACTER = false

        commonSettings.BLOCK_COMMENT_AT_FIRST_COLUMN = false
        commonSettings.LINE_COMMENT_AT_FIRST_COLUMN = false

        super.customizeDefaults(commonSettings, indentOptions)
    }

    override fun customizeSettings(consumer: CodeStyleSettingsCustomizable, settingsType: SettingsType) {
        when(settingsType) {
            SettingsType.SPACING_SETTINGS -> {
                consumer.showCustomOption(DCodeStyleSettings::class.java,
                    "SPACE_BEFORE_IMPORT_BINDS_COLON",
                    "Space before import colon",
                    CodeStyleSettingsCustomizableOptions.getInstance().SPACES_OTHER)
            }
            SettingsType.WRAPPING_AND_BRACES_SETTINGS -> {
            }
            SettingsType.BLANK_LINES_SETTINGS -> {
            }
            SettingsType.COMMENTER_SETTINGS -> {
            }
            else -> consumer.showStandardOptions()
        }
    }

}
