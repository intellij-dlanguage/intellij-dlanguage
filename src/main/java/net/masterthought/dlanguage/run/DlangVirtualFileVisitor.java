package net.masterthought.dlanguage.run;

import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DlangVirtualFileVisitor extends VirtualFileVisitor {

    private final List<String> dLangSources;
    private final VirtualFile[] excludedRoots;


    public DlangVirtualFileVisitor(final VirtualFile[] excludedRoots) {
        dLangSources = new ArrayList<>();
        this.excludedRoots = excludedRoots;
    }

    @Override
    public boolean visitFile(@NotNull final VirtualFile file) {
        if (!file.isDirectory() && "d".equals(file.getExtension()) && !isExcluded(file)) {
            dLangSources.add(file.getCanonicalPath()); // dLangSources.add(VfsUtilCore.getRelativePath(file, sourcesRoot, File.separatorChar));
        }
        return super.visitFile(file);
    }

    private boolean isExcluded(final VirtualFile srcFile) {
        for (final VirtualFile excludeDir : excludedRoots) {
            if (VfsUtilCore.isAncestor(excludeDir, srcFile, false)) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public List<String> getDlangSources() {
        return dLangSources;
    }
}
