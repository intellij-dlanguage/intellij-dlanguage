package io.github.intellij.dlanguage;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DlangFileType extends LanguageFileType {

    public static final DlangFileType INSTANCE = new DlangFileType();
    public static final String DEFAULT_EXTENSION = "d";

    private DlangFileType() {
        super(DLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "D file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return DlangBundle.INSTANCE.message("dlang.filetype");
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return DLanguage.Icons.FILE;
    }

}
