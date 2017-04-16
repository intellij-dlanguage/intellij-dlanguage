package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import net.masterthought.dlanguage.psi.DLanguageStaticDestructor;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis on 1/14/2017.
 */
public class DLanguageStaticDestructorStub extends NamedStubBase<DLanguageStaticDestructor> {
    public DLanguageStaticDestructorStub(StubElement parent, @NotNull IStubElementType elementType, StringRef name) {
        super(parent, elementType, name);
    }

    public DLanguageStaticDestructorStub(StubElement parent, @NotNull IStubElementType elementType, String name) {
        super(parent, elementType, name);
    }
}
