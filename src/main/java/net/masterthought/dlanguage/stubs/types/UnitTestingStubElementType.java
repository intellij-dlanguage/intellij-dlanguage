package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageUnitTesting;
import net.masterthought.dlanguage.psi.impl.DLanguageUnitTestingImpl;
import net.masterthought.dlanguage.stubs.UnitTestingStubImpl;
import net.masterthought.dlanguage.stubs.interfaces.UnitTestingStub;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by francis on 4/8/2017.
 */
public class UnitTestingStubElementType extends DStubElementType<UnitTestingStub, DLanguageUnitTesting> {
    public UnitTestingStubElementType(@NotNull String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageUnitTesting createPsi(@NotNull UnitTestingStub stub) {
        return new DLanguageUnitTestingImpl(stub, this);
    }

    @NotNull
    @Override
    public UnitTestingStub createStub(@NotNull DLanguageUnitTesting psi, StubElement parentStub) {
        return new UnitTestingStubImpl(parentStub, this);
    }

    @Override
    public void serialize(@NotNull UnitTestingStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        final ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
        objectOutputStream.writeObject(stub);
        final byte[] bytes = byteOutputStream.toByteArray();
        dataStream.writeInt(bytes.length);
        dataStream.write(bytes);
    }

    @NotNull
    @Override
    public UnitTestingStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        final ObjectInputStream objectInputStream = new ObjectInputStream(dataStream);
        try {
            return (UnitTestingStub) objectInputStream.readObject();
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void indexStub(@NotNull UnitTestingStub stub, @NotNull IndexSink sink) {

    }
}
