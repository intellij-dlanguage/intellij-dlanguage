package net.masterthought.dlanguage;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DLanguageFileType extends LanguageFileType {

    public static final DLanguageFileType INSTANCE = new DLanguageFileType();

    private DLanguageFileType() {
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
        return "D language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "d";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return DLanguageIcons.FILE;
    }

}
