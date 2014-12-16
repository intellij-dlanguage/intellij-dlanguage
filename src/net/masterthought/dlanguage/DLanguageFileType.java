package net.masterthought.dlanguage;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DLanguageFileType extends LanguageFileType {

    public static final LanguageFileType INSTANCE = new DLanguageFileType();

    public static final String SOURCE_EXTENSION = "d";
    public static final String HEADER_EXTENSION = "di";

    private DLanguageFileType() {
        super(DLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "D";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "D files";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return SOURCE_EXTENSION;
    }

    public String getHeaderExtension() { return HEADER_EXTENSION;}

    @Nullable
    @Override
    public Icon getIcon() {
        return DLanguageIcons.FILE;
    }
}



