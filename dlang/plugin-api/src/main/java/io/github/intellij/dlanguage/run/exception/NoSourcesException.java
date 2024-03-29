package io.github.intellij.dlanguage.run.exception;

import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class NoSourcesException extends Exception {
    private final VirtualFile sourcesRoot;

    public NoSourcesException(@NotNull final VirtualFile sourcesRoot) {
        super("No D sources found in " + sourcesRoot.getCanonicalPath());
        this.sourcesRoot = sourcesRoot;
    }

    public String getSourcesRoot() {
        return sourcesRoot.getCanonicalPath();
    }
}
