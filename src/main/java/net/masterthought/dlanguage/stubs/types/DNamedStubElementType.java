package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.Stub;
import com.intellij.psi.stubs.StubElement;
import net.masterthought.dlanguage.psi.DLanguageFile;
import net.masterthought.dlanguage.psi.DLanguageModuleDeclaration;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.HasMembers;
import net.masterthought.dlanguage.stubs.*;
import net.masterthought.dlanguage.stubs.index.*;
import net.masterthought.dlanguage.stubs.interfaces.DLanguageUnittestStub;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;


public abstract class DNamedStubElementType<S extends NamedStubBase<T>, T extends DNamedElement> extends DStubElementType<S, T> {
    public DNamedStubElementType(final String debugName) {
        super(debugName);
    }
//todo reanable this:
//    @Override
//    public void serialize(@NotNull S stub, @NotNull StubOutputStream dataStream) throws IOException {
//        dataStream.writeName(stub.getName());
//    }

    @Override
    public void indexStub(@NotNull final S stub, @NotNull final IndexSink sink) {
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
        if (((stub instanceof DLanguageSingleImportStub)) && topLevelDeclaration(stub)) {
            if (((DLanguageSingleImportStub) stub).isPublic()) {
                final String fileName = ((DLanguageFile) stub.getPsi().getContainingFile()).getModuleOrFileName();
                sink.occurrence(DPublicImportIndex.Companion.getKEY(), fileName);
            }
        }
        for (final HasMembers hasMembers : getParentHasMembers(stub)) {
            sink.occurrence(DMembersIndex.Companion.getKEY(), hasMembers.getName());
        }

    }

    private boolean topLevelDeclaration(final S stub) {
        //stuff within unittests does not count as top level
        //stuff within func declarations does not count as top level b/c not globally accessible todo check if this is true for all declaration types
        //switch the topLevel declaration to a file gist maybe

        if (stub instanceof DLanguageParameterStub || stub instanceof DLanguageForeachTypeStub || stub instanceof DLanguageTemplateParameterStub) {
            return false;
        }


        StubElement stubParent = stub;
        while (true) {
            stubParent = stubParent.getParentStub();
            if (stubParent == null) {
                return true;
            }
            if (stubParent instanceof DLanguageFunctionDeclarationStub) {
                return false;
            }
            if (stubParent instanceof DLanguageConstructorStub || stubParent instanceof DLanguageSharedStaticConstructorStub || stubParent instanceof DLanguageStaticConstructorStub || stubParent instanceof DLanguageDestructorStub || stubParent instanceof DLanguageSharedStaticDestructorStub || stubParent instanceof DLanguageStaticDestructorStub) {
                return false;
            }
            if (stubParent instanceof DLanguageUnittestStub) {
                return false;
            }
        }
    }

    private Set<HasMembers> getParentHasMembers(final Stub stub) {
        final HashSet<HasMembers> result = new HashSet<>();
        getParentHasMembersImpl(stub, result);
        return result;
    }

    private void getParentHasMembersImpl(final Stub stub, final Set<HasMembers> result) {

        if (stub instanceof HasMembers) {
            result.add((HasMembers) stub);
        }
        if (stub.getParentStub() == null) {
            return;
        }
        getParentHasMembersImpl(stub.getParentStub(), result);
        if (stub instanceof DLanguageUnittestStub || stub instanceof DLanguageFunctionDeclarationStub || stub instanceof DLanguageConstructorStub || stub instanceof DLanguageSharedStaticConstructorStub || stub instanceof DLanguageStaticConstructorStub || stub instanceof DLanguageDestructorStub || stub instanceof DLanguageSharedStaticDestructorStub || stub instanceof DLanguageStaticDestructorStub) {
            result.clear();
            return;
        }
    }


}

