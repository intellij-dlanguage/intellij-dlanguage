package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import io.github.intellij.dlanguage.psi.DLanguageDestructor;

/**
 * Created by franc on 1/14/2017.
 */
public class DlangDestructorStub extends StubBase<DLanguageDestructor> implements StubElement<DLanguageDestructor> {

    public DlangDestructorStub(final StubElement parent, final IStubElementType elementType) {
        super(parent, elementType);
    }
}
