package io.github.intellij.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.NamedStubBase;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.index.DAllNameIndex;
import io.github.intellij.dlanguage.stubs.index.DModuleDeclarationIndex;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.index.*;
import org.jetbrains.annotations.NotNull;


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
        DModuleDeclarationIndex.Companion.indexModuleDeclarations(stub, sink, name);
        DTopLevelDeclarationIndex.Companion.indexTopLevelDeclarations(stub, sink, name);
        DTopLevelDeclarationsByModule.Companion.indexTopLevelDeclarationsByModule(stub, sink);
        DPublicImportIndex.Companion.indexPublicImports(stub, sink);
        DMembersIndex.Companion.indexMembers(stub, sink);

    }

    @Override
    public boolean shouldCreateStub(final ASTNode node) {
        return ((DNamedElement) node.getPsi()).hasAName();
    }
}

