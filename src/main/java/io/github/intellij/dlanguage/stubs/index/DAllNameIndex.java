package io.github.intellij.dlanguage.stubs.index;

import com.intellij.psi.stubs.StringStubIndexExtension;
import com.intellij.psi.stubs.StubIndexKey;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import org.jetbrains.annotations.NotNull;

/**
 * Stub index to store all names defined in the project; specifically for the "go to symbol" feature.
 */
public class DAllNameIndex extends StringStubIndexExtension<DNamedElement> {
    public static final StubIndexKey<String, DNamedElement> KEY = StubIndexKey.createIndexKey("d.all.name");
    public static final int VERSION = 1;

    @Override
    public int getVersion() {
        return super.getVersion() + VERSION;
    }

    @NotNull
    @Override
    public StubIndexKey<String, DNamedElement> getKey() {
        return KEY;
    }
}

