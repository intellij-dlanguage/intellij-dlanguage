package io.github.intellij.dlanguage.run;

import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileUtil;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import io.github.intellij.dlanguage.DlangFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DlangVirtualFileVisitor extends VirtualFileVisitor<Void> {

    private final List<VirtualFile> dLangSources;
    private final @Nullable VirtualFile[] excludedRoots;


    public DlangVirtualFileVisitor(@Nullable final VirtualFile[] excludedRoots) {
        this.dLangSources = new ArrayList<>();
        this.excludedRoots = excludedRoots;
    }

    /**
     * Simple visiting method.
     * On returning {@code true} a visitor will proceed to file's children, on {@code false} - to file's next sibling.
     *
     * @param file a file to visit.
     * @return {@code true} to proceed to file's children, {@code false} to skip to file's next sibling.
     */
    @Override
    public boolean visitFile(@NotNull final VirtualFile file) {
        if (isExcluded(file)) {
            return false;
        }

        if (file.isDirectory()) {
            return true; // don't add to source as this will be used in recursive search
        } else if (VirtualFileUtil.isFile(file) &&
            (DlangFileType.DEFAULT_EXTENSION.equals(file.getExtension()) || "di".equals(file.getExtension()))) {
            dLangSources.add(file);
        }
        return false;
    }

    private boolean isExcluded(final VirtualFile srcFile) {
        if(this.excludedRoots != null) {
            for (final VirtualFile excludeDir : excludedRoots) {
                if (VfsUtilCore.isAncestor(excludeDir, srcFile, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    @NotNull
    public List<String> getDlangSources() {
        return dLangSources.stream()
            .map(VirtualFile::getCanonicalPath)
            .collect(Collectors.toList());
    }

    @NotNull
    public List<String> getDlangSourcesRelativePaths(@NotNull VirtualFile root) {
        return dLangSources.stream()
            .map(file -> VfsUtilCore.getRelativePath(file, root, File.separatorChar))
            .collect(Collectors.toList());
    }
}
