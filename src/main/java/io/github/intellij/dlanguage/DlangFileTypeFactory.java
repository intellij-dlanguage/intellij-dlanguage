package io.github.intellij.dlanguage;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * todo: remove this class. See https://github.com/intellij-dlanguage/intellij-dlanguage/issues/555
 */
public class DlangFileTypeFactory extends FileTypeFactory {

    @Override
    public void createFileTypes(@NotNull final FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(DlangFileType.INSTANCE, "d;di");
    }
}
