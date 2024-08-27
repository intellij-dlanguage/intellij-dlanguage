package io.github.intellij.dlanguage;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.WritingAccessProvider;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class DlangWritingAccessProvider extends WritingAccessProvider {

    private final Project myProject;

    public DlangWritingAccessProvider(final Project project) {
        myProject = project;
    }

    public static boolean isInDLanguageSdkOrDLanguagePackagesFolder(final @NotNull PsiFile psiFile) {
        final VirtualFile vFile = psiFile.getOriginalFile().getVirtualFile();
        return vFile != null && isInDLanguageSdkOrDLanguagePackagesFolder(psiFile.getProject(), vFile);
    }

    public static boolean isInDLanguageSdkOrDLanguagePackagesFolder(final @NotNull Project project, final @NotNull VirtualFile file) {
        final ProjectFileIndex fileIndex = ProjectRootManager.getInstance(project).getFileIndex();

        if (fileIndex.isInLibraryClasses(file)) {
            return true; // file in SDK or in custom package root
        }

//    if (fileIndex.isExcluded(file) || (fileIndex.isInContent(file) && isInDartPackagesFolder(fileIndex, file))) {
//      return true; // symlinked child of 'packages' folder. Real location is in user cache folder for Dart packages, not in project
//    }

        return false;
    }

    @Override
    public boolean isPotentiallyWritable(@NotNull final VirtualFile file) {
        if (DlangFileType.INSTANCE != file.getFileType()) return true;
        return !isInDLanguageSdkOrDLanguagePackagesFolder(myProject, file);
    }

//  private static boolean isInDartPackagesFolder(final ProjectFileIndex fileIndex, final VirtualFile file) {
//    VirtualFile parent = file;
//    while ((parent = parent.getParent()) != null && fileIndex.isInContent(parent)) {
//      if (DartUrlResolver.PACKAGES_FOLDER_NAME.equals(parent.getName())) {
//        return VfsUtilCore.findRelativeFile("../" + PUBSPEC_YAML, parent) != null;
//      }
//    }
//
//    return false;
//  }
}

