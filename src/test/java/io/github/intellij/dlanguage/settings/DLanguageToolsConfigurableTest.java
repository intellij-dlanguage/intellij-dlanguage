package io.github.intellij.dlanguage.settings;

import com.intellij.testFramework.LightPlatformTestCase;

public class DLanguageToolsConfigurableTest extends LightPlatformTestCase {

    public void testGetVersionWithNull() {
        assertEmpty(DLanguageToolsConfigurable.getVersion(null));
    }

    public void testGetVersionWithEmpty() {
        assertEmpty(DLanguageToolsConfigurable.getVersion(""));
    }

    public void testGetVersionWithBlank() {
        assertEmpty(DLanguageToolsConfigurable.getVersion("  "));
    }

    public void testGetVersionWithBogusExecutable() {
        assertEmpty(DLanguageToolsConfigurable.getVersion("sdhvb"));
    }
}
