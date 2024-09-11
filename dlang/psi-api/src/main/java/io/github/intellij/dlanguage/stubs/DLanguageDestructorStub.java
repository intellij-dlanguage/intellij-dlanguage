package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import io.github.intellij.dlanguage.psi.named.DLanguageDestructor;

/**
 * Created by franc on 1/14/2017.
 */
public class DLanguageDestructorStub extends StubBase<DLanguageDestructor> implements
    StubElement<DLanguageDestructor> {

    public DLanguageDestructorStub(final StubElement parent, final IStubElementType elementType) {
        super(parent, elementType);
    }
}
