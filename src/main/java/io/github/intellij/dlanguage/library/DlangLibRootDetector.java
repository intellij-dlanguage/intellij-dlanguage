package io.github.intellij.dlanguage.library;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.ui.RootDetector;
import com.intellij.openapi.vfs.JarFileSystem;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import io.github.intellij.dlanguage.DlangFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DlangLibRootDetector extends RootDetector {
    protected DlangLibRootDetector(final OrderRootType rootType, final String presentableRootTypeName) {
        super(rootType, false, presentableRootTypeName);
    }

    private static boolean containsDLangFiles(final VirtualFile dir) {
        final VirtualFileVisitor.Result result = VfsUtilCore.visitChildrenRecursively(dir, new VirtualFileVisitor() {
            @NotNull
            @Override
            public Result visitFileEx(@NotNull final VirtualFile file) {
                return !file.isDirectory() && DlangFileType.DEFAULT_EXTENSION.equalsIgnoreCase(file.getExtension()) ? skipTo(dir) : CONTINUE;
            }
        });
        return result.skipToParent != null;
    }

    @NotNull
    @Override
    public Collection<VirtualFile> detectRoots(@NotNull final VirtualFile rootCandidate, @NotNull final ProgressIndicator progressIndicator) {
        final List<VirtualFile> result = new ArrayList<VirtualFile>();
        collectRoots(rootCandidate, result, progressIndicator);
        return result;
    }

    public void collectRoots(final VirtualFile file, final List<VirtualFile> result, @Nullable final ProgressIndicator progressIndicator) {
        if (file.getFileSystem() instanceof JarFileSystem) {
            return;
        }

        final OrderRootType rootType = getRootType();

        VfsUtilCore.visitChildrenRecursively(file, new VirtualFileVisitor() {
            @Override
            public boolean visitFile(@NotNull final VirtualFile file) {
                if (progressIndicator != null) {
                    progressIndicator.checkCanceled();
                }
                if (!file.isDirectory()) {
                    if (rootType.equals(LibFileRootType.getInstance()) && "lib".equals(file.getExtension())) {
                        result.add(file);
                    }
                    return false;
                }

                if (progressIndicator != null) {
                    progressIndicator.setText2(file.getPresentableUrl());
                }

                if (rootType.equals(OrderRootType.CLASSES) && file.getName().equals("source")) {
                    if (containsDLangFiles(file)) {
                        result.add(file);
                    }
                    return false;
                }

                return true;
            }
        });
    }
}
