package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import net.masterthought.dlanguage.psi.DLanguageStaticDestructor;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis on 1/14/2017.
 */
public class DLanguageStaticDestructorStub extends StubBase<DLanguageStaticDestructor> implements StubElement<DLanguageStaticDestructor> {
    public DLanguageStaticDestructorStub(StubElement parent, @NotNull IStubElementType elementType) {
        super(parent, elementType);
    }
}
