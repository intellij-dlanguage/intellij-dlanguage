package net.masterthought.dlanguage;

import com.intellij.openapi.fileTypes.LanguageFileType;
import net.masterthought.dlanguage.icons.DLanguageIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DLanguageFileType extends LanguageFileType {

    public static final DLanguageFileType INSTANCE = new DLanguageFileType();
    public static final String DEFAULT_EXTENSION = "d";

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
        return DEFAULT_EXTENSION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return DLanguageIcons.FILE;
    }

}
