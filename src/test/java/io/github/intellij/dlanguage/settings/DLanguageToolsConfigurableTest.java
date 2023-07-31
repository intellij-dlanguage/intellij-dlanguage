package io.github.intellij.dlanguage.settings;

import com.intellij.testFramework.LightPlatform4TestCase;
import org.junit.Test;

public class DLanguageToolsConfigurableTest extends LightPlatform4TestCase {

    @Test
    public void testGetVersionWithNull() {
        assertEmpty(DLanguageToolsConfigurable.getVersion(null));
    }

    @Test
    public void testGetVersionWithEmpty() {
        assertEmpty(DLanguageToolsConfigurable.getVersion(""));
    }

    @Test
    public void testGetVersionWithBlank() {
        assertEmpty(DLanguageToolsConfigurable.getVersion("  "));
    }

    @Test
    public void testGetVersionWithBogusExecutable() {
        assertEmpty(DLanguageToolsConfigurable.getVersion("sdhvb"));
    }
}
