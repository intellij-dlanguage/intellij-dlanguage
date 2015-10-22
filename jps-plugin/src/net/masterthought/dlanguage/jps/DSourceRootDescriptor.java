package net.masterthought.dlanguage.jps;

import org.jetbrains.jps.builders.BuildRootDescriptor;
import org.jetbrains.jps.builders.BuildTarget;

import java.io.File;

public class DSourceRootDescriptor extends BuildRootDescriptor {
    private final File root;
    private final DTarget target;

    public DSourceRootDescriptor(File inRoot, DTarget inTarget) {
        root = inRoot;
        target = inTarget;
    }

    @Override
    public String getRootId() {
        return "SourceRootDescriptor";
    }

    @Override
    public File getRootFile() {
        return root;
    }

    @Override
    public BuildTarget<?> getTarget() {
        return target;
    }
}
