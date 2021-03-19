package io.github.intellij.dlanguage.run;

import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DlangVirtualFileVisitor extends VirtualFileVisitor {

    private final List<String> dLangSources;
    private final VirtualFile[] excludedRoots;


    public DlangVirtualFileVisitor(final VirtualFile[] excludedRoots) {
        this.dLangSources = new ArrayList<>();
        this.excludedRoots = excludedRoots;
    }

    @Override
    public boolean visitFile(@NotNull final VirtualFile file) {
        if (!file.isDirectory() && ("d".equals(file.getExtension()) || "di".equals(file.getExtension())) && !isExcluded(file)) {
            dLangSources.add(file.getCanonicalPath()); // dLangSources.add(VfsUtilCore.getRelativePath(file, sourcesRoot, File.separatorChar));
        }
        return super.visitFile(file);
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
        return dLangSources;
    }
}
