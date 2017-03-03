package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import net.masterthought.dlanguage.psi.DLanguageTemplateMixinDeclaration;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis nixon.
 */
public class DLanguageTemplateMixinDeclarationStub extends NamedStubBase<DLanguageTemplateMixinDeclaration> {

    public DLanguageTemplateMixinDeclarationStub(StubElement parent, @NotNull IStubElementType elementType, StringRef name) {
        super(parent, elementType, name);
    }

    public DLanguageTemplateMixinDeclarationStub(StubElement parent, IStubElementType elementType, String name) {
        super(parent, elementType, name);
    }
}
