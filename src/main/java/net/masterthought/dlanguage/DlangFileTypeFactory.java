package net.masterthought.dlanguage;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class DlangFileTypeFactory extends FileTypeFactory {

    @Override
    public void createFileTypes(@NotNull final FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(DlangFileType.INSTANCE, "d");
    }
}
