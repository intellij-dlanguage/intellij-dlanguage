package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.PsiFile;
import com.intellij.psi.StubBuilder;
import com.intellij.psi.stubs.DefaultStubBuilder;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.psi.tree.IStubFileElementType;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.psi.DlangPsiFileImpl;
import io.github.intellij.dlanguage.stubs.DlangFileStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DFileStubElementType extends IStubFileElementType<DlangFileStub> {
    public static final int VERSION = 1;
    public static final DFileStubElementType INSTANCE = new DFileStubElementType();

    public DFileStubElementType() {
        super("FILE", DLanguage.INSTANCE);
    }

    @Override
    public StubBuilder getBuilder() {
        return new DefaultStubBuilder() {
            @NotNull
            @Override
            protected StubElement createStubForFile(@NotNull final PsiFile file) {
                if (file instanceof DlangPsiFileImpl) {
                    return new DlangFileStub((DlangPsiFileImpl) file);
                }
                return super.createStubForFile(file);
            }
        };
    }

    @Override
    public int getStubVersion() {
        return VERSION;
    }

    @Override
    public void serialize(@NotNull final DlangFileStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        // todo make files named?
    }

    @NotNull
    @Override
    public DlangFileStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangFileStub(null);
    }

    @NotNull
    @Override
    public String getExternalId() {
        return "d.FILE";
    }
}

