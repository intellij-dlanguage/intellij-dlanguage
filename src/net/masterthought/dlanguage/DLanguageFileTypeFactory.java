package net.masterthought.dlanguage;

import com.intellij.openapi.fileTypes.ExtensionFileNameMatcher;
import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class DLanguageFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull final FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(DLanguageFileType.INSTANCE,
                new ExtensionFileNameMatcher(DLanguageFileType.SOURCE_EXTENSION),
                new ExtensionFileNameMatcher(DLanguageFileType.HEADER_EXTENSION));
    }
}
