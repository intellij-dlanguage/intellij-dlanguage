package io.github.intellij.dlanguage.run;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import io.github.intellij.dlanguage.DlangSdkType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DlangVirtualFileVisitor extends VirtualFileVisitor<Void> {

    private static final Logger log = Logger.getInstance(DlangVirtualFileVisitor.class);

    private final List<String> dLangSources;
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
        if (!file.isDirectory() && ("d".equals(file.getExtension()) || "di".equals(file.getExtension())) && !isExcluded(file)) {
            dLangSources.add(file.getCanonicalPath()); // dLangSources.add(VfsUtilCore.getRelativePath(file, sourcesRoot, File.separatorChar));
        }
        return super.visitFile(file);
    }

    private boolean isExcluded(final VirtualFile srcFile) {
        @Nullable final Project project = ProjectUtil.guessProjectForFile(srcFile);
        if(project != null) {
            final ProjectRootManager rootManager = ProjectRootManager.getInstance(project);
            @Nullable final Sdk sdk = rootManager.getProjectSdk();

            boolean notDlangSdk = !(sdk != null && (sdk.getSdkType() instanceof DlangSdkType));

            if(notDlangSdk) {
                log.debug(String.format("Visited file '%s' but excluding as project sdk (%s) is not a D lang SDK", srcFile.getName(), sdk != null ? sdk.getSdkType() : null));
            }
        }

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
