package net.masterthought.dlanguage.index;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.indexing.*;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import net.masterthought.dlanguage.DLanguageFileType;
import net.masterthought.dlanguage.psi.DLanguageFile;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DModuleIndex extends ScalarIndexExtension<String> {
    public static final FileBasedIndex.InputFilter D_MODULE_FILTER = new FileBasedIndex.InputFilter() {
        @Override
        public boolean acceptInput(@NotNull VirtualFile file) {
            //noinspection ObjectEquality
            return file.getFileType() == DLanguageFileType.INSTANCE;
        }
    };
    private static final ID<String, Void> D_MODULE_INDEX = ID.create("DModuleIndex");
    private static final int INDEX_VERSION = 0;
    private static final EnumeratorStringDescriptor KEY_DESCRIPTOR = new EnumeratorStringDescriptor();
    private static final MyDataIndexer INDEXER = new MyDataIndexer();

    @NotNull
    public static List<DLanguageFile> getFilesByModuleName(@NotNull final Project project,
                                                           @NotNull final String moduleName,
                                                           @NotNull final GlobalSearchScope searchScope) {
        final PsiManager psiManager = PsiManager.getInstance(project);
        final Collection<VirtualFile> virtualFiles = getVirtualFilesByModuleName(moduleName, searchScope);
        return ContainerUtil.mapNotNull(virtualFiles, virtualFile -> {
            final PsiFile psiFile = psiManager.findFile(virtualFile);
            return psiFile instanceof DLanguageFile ? (DLanguageFile) psiFile : null;
        });
    }

    @NotNull
    public static Collection<VirtualFile> getVirtualFilesByModuleName(@NotNull final String moduleName,
                                                                      @NotNull final GlobalSearchScope searchScope) {
        return FileBasedIndex.getInstance().getContainingFiles(D_MODULE_INDEX, moduleName, searchScope);
    }

    @NotNull
    @Override
    public ID<String, Void> getName() {
        return D_MODULE_INDEX;
    }

    @NotNull
    @Override
    public DataIndexer<String, Void, FileContent> getIndexer() {
        return INDEXER;
    }

    @NotNull
    @Override
    public KeyDescriptor<String> getKeyDescriptor() {
        return KEY_DESCRIPTOR;
    }

    @NotNull
    @Override
    public FileBasedIndex.InputFilter getInputFilter() {
        return D_MODULE_FILTER;
    }

    @Override
    public boolean dependsOnFileContent() {
        return true;
    }

    @Override
    public int getVersion() {
        return INDEX_VERSION;
    }

    private static class MyDataIndexer implements DataIndexer<String, Void, FileContent> {
        @NotNull
        @Override
        public Map<String, Void> map(@NotNull final FileContent inputData) {
            final PsiFile psiFile = inputData.getPsiFile();
            String moduleName;
            if (psiFile instanceof DLanguageFile) {
                moduleName = ((DLanguageFile) psiFile).getModuleName();
                if (moduleName == null)
                    moduleName = psiFile.getName().replace(".d", "");
            } else {
                moduleName = null;
            }
            if (moduleName == null) {
                return Collections.emptyMap();
            }
            return Collections.singletonMap(moduleName, null);
        }
    }
}

