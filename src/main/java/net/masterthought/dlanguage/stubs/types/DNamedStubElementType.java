package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import net.masterthought.dlanguage.psi.DLanguageFile;
import net.masterthought.dlanguage.psi.DLanguageModuleDeclaration;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.*;
import net.masterthought.dlanguage.stubs.index.DAllNameIndex;
import net.masterthought.dlanguage.stubs.index.DModuleDeclarationIndex;
import net.masterthought.dlanguage.stubs.index.DTopLevelDeclarationIndex;
import net.masterthought.dlanguage.stubs.index.DTopLevelDeclarationsByModule;
import net.masterthought.dlanguage.stubs.interfaces.UnitTestingStub;
import org.jetbrains.annotations.NotNull;


public abstract class DNamedStubElementType<S extends NamedStubBase<T>, T extends DNamedElement> extends DStubElementType<S, T> {
    public DNamedStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public void indexStub(@NotNull S stub, @NotNull IndexSink sink) {
        final String name = stub.getName();
        if (name == null) {
            return;
        }
        sink.occurrence(DAllNameIndex.KEY, name);
        if (stub instanceof DLanguageModuleDeclaration) {
            sink.occurrence(DModuleDeclarationIndex.Companion.getKEY(), name);
        }
        if ((!(stub instanceof DLanguageIdentifierStub)) && topLevelDeclaration(stub)) {
            sink.occurrence(DTopLevelDeclarationIndex.Companion.getKEY(), name);
        }
        if ((!(stub instanceof DLanguageIdentifierStub)) && topLevelDeclaration(stub)) {
            final String fileName = ((DLanguageFile) stub.getPsi().getContainingFile()).getModuleOrFileName();
            sink.occurrence(DTopLevelDeclarationsByModule.Companion.getKEY(), fileName);
        }
    }

    private boolean topLevelDeclaration(S stub) {
        //stuff within unittests does not count as top level
        //stuff within func declarations does not count as top level b/c not globally accessible todo check if this is true for all declaration types
        //todo switch the topLevel declaration to a file based index

        if (stub instanceof DLanguageParameterStub || stub instanceof DLanguageForeachTypeStub || stub instanceof DLanguageTemplateParameterStub) {
            return false;
        }

        StubElement stubParent = stub;
        while (true) {
            stubParent = stubParent.getParentStub();
            if (stubParent == null) {
                return true;
            }
            if (stubParent instanceof DLanguageFuncDeclarationStub) {
                return false;
            }
            if (stubParent instanceof DLanguageConstructorStub || stubParent instanceof DLanguageSharedStaticConstructorStub || stubParent instanceof DLanguageStaticConstructorStub || stubParent instanceof DLanguageDestructorStub || stubParent instanceof DLanguageSharedStaticDestructorStub || stubParent instanceof DLanguageStaticDestructorStub) {
                return false;
            }
            if (stubParent instanceof UnitTestingStub) {
                return false;
            }
        }
    }


}

