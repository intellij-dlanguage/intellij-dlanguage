package net.masterthought.dlanguage.project;

import java.util.List;
import java.util.Objects;

/**
 * was previously an inner class of DubConfigurationParser.
 */
public class DubPackage {

    private final String name;
    private final String path;
    private final List<String> dependencies;
    private final String sourcesDir; // todo: this should prob be String[]
    private final List<String> resources;
    private final String version;
    private final boolean isRootPackage;

    public DubPackage(String name, String path, List<String> dependencies, String sourcesDir, List<String> resources, String version, boolean isRootPackage) {
        this.name = name;
        this.path = path;
        this.dependencies = dependencies;
        this.sourcesDir = sourcesDir;
        this.resources = resources;
        this.version = version;
        this.isRootPackage = isRootPackage;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public String getSourcesDir() {
        return sourcesDir;
    }

    public List<String> getResources() {
        return resources;
    }

    public String getVersion() {
        return version;
    }

    public boolean isRootPackage() {
        return isRootPackage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DubPackage that = (DubPackage) o;
        return isRootPackage == that.isRootPackage &&
            Objects.equals(name, that.name) &&
            Objects.equals(path, that.path) &&
            Objects.equals(dependencies, that.dependencies) &&
            Objects.equals(sourcesDir, that.sourcesDir) &&
            Objects.equals(resources, that.resources) &&
            Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path, dependencies, sourcesDir, resources, version, isRootPackage);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DubPackage{");
        sb.append("name='").append(name).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append(", dependencies=").append(dependencies);
        sb.append(", sourcesDir='").append(sourcesDir).append('\'');
        sb.append(", resources=").append(resources);
        sb.append(", version='").append(version).append('\'');
        sb.append(", isRootPackage=").append(isRootPackage);
        sb.append('}');
        return sb.toString();
    }
}
