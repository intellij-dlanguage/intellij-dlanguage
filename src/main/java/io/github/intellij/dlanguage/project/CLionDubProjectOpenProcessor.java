package io.github.intellij.dlanguage.project;

import com.intellij.ide.GeneralSettings;
import com.intellij.ide.impl.ProjectUtil;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ex.ProjectManagerEx;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.projectImport.ProjectOpenProcessor;
import com.intellij.util.ArrayUtil;
import io.github.intellij.dlanguage.DlangSdkType;
import io.github.intellij.dlanguage.icons.DlangIcons;
import io.github.intellij.dlanguage.module.DlangModuleBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

/**
 * Used when opening a dub project within a non-Java IDE
 */
public class CLionDubProjectOpenProcessor extends ProjectOpenProcessor {
  static final String NAME = "Dub";
  static final String[] SUPPORTED_FILES = {"dub.json", "dub.sdl"};

  @Nullable
  @Override
  public Icon getIcon() {
    return DlangIcons.FILE;
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public boolean canOpenProject(final VirtualFile file) {
    return findSupportedFile(file) != null;
  }

  @Nullable
  private VirtualFile findSupportedFile(final VirtualFile file) {
    if (file.isDirectory()) {
      final VirtualFile[] children = file.getChildren();
      if (children != null) {
        for (final VirtualFile child : children) {
          if (ArrayUtil.contains(child.getName(), SUPPORTED_FILES)) return child;
        }
      }
      return null;
    } else {
      return ArrayUtil.contains(file.getName(), SUPPORTED_FILES) ? file : null;
    }
  }

  @Nullable
  @Override
  public Project doOpenProject(@NotNull final VirtualFile virtualFile,
                               @Nullable final Project projectToClose,
                               final boolean forceOpenInNewFrame) {
    if (projectToClose != null && !forceOpenInNewFrame) {
      final int exitCode = ProjectUtil.confirmOpenNewProject(false);
      if (exitCode == GeneralSettings.OPEN_PROJECT_SAME_WINDOW) {
        if (!ProjectUtil.closeAndDispose(projectToClose)) {
          return null;
        }
      } else if (exitCode != GeneralSettings.OPEN_PROJECT_NEW_WINDOW) {
        // not in a new window
        return null;
      }
    }

    final VirtualFile baseDir = virtualFile.isDirectory() ? virtualFile : virtualFile.getParent();
    final Project project = ProjectManagerEx.getInstanceEx()
        .newProject(baseDir.getName(), baseDir.getPath(), true, false);
    if (project != null) {
      WriteAction.run(() -> {
        final Sdk sdk = DlangSdkType.findOrCreateSdk();
        ProjectRootManager.getInstance(project).setProjectSdk(sdk);
        final DlangModuleBuilder builder = new DlangModuleBuilder();
        builder.setModuleJdk(sdk);
        builder.commit(project);
      });
      ProjectManagerEx.getInstanceEx().openProject(project);
    }
    return project;
  }
}
