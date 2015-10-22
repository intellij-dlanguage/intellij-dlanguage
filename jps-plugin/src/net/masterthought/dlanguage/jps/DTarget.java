package net.masterthought.dlanguage.jps;

import com.intellij.util.containers.ContainerUtil;
import net.masterthought.dlanguage.jps.model.JpsDLanguageModuleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.builders.*;
import org.jetbrains.jps.builders.storage.BuildDataPaths;
import org.jetbrains.jps.incremental.CompileContext;
import org.jetbrains.jps.indices.IgnoredFileIndex;
import org.jetbrains.jps.indices.ModuleExcludeIndex;
import org.jetbrains.jps.model.JpsModel;
import org.jetbrains.jps.model.java.JavaSourceRootProperties;
import org.jetbrains.jps.model.java.JavaSourceRootType;
import org.jetbrains.jps.model.java.JpsJavaClasspathKind;
import org.jetbrains.jps.model.java.JpsJavaExtensionService;
import org.jetbrains.jps.model.module.JpsModule;
import org.jetbrains.jps.model.module.JpsTypedModuleSourceRoot;

import java.io.File;
import java.util.*;


public class DTarget extends ModuleBasedTarget<DSourceRootDescriptor> {
    public DTarget(@NotNull JpsModule module, DTargetType targetType) {
        super(targetType, module);
    }

    @Override
    public String getId() {
        return myModule.getName();
    }

    @Override
    public Collection<BuildTarget<?>> computeDependencies(BuildTargetRegistry buildTargetRegistry, TargetOutputIndex targetOutputIndex) {
        List<BuildTarget<?>> dependencies = new ArrayList<BuildTarget<?>>();
        Set<JpsModule> modules = JpsJavaExtensionService.dependencies(myModule).includedIn(JpsJavaClasspathKind.compile(isTests())).getModules();
        for (JpsModule module : modules) {
            if (module.getModuleType().equals(JpsDLanguageModuleType.INSTANCE)) {
                dependencies.add(new DTarget(module, getDTargetType()));
            }
        }
        if (isTests()) {
            dependencies.add(new DTarget(myModule, DTargetType.PRODUCTION));
        }
        return dependencies;    }

    @NotNull
    @Override
    public List<DSourceRootDescriptor> computeRootDescriptors(JpsModel jpsModel, ModuleExcludeIndex moduleExcludeIndex, IgnoredFileIndex ignoredFileIndex, BuildDataPaths buildDataPaths) {
        List<DSourceRootDescriptor> result = new ArrayList<DSourceRootDescriptor>();
        JavaSourceRootType type = isTests() ? JavaSourceRootType.TEST_SOURCE : JavaSourceRootType.SOURCE;
        for (JpsTypedModuleSourceRoot<JavaSourceRootProperties> root : myModule.getSourceRoots(type)) {
            result.add(new DSourceRootDescriptor(root.getFile(), this));
        }
        return result;
    }


    @Nullable
    @Override
    public DSourceRootDescriptor findRootDescriptor(String s, BuildRootIndex buildRootIndex) {
        return ContainerUtil.getFirstItem(buildRootIndex.getRootDescriptors(new File(s), Collections.singletonList(getDTargetType()), null));
    }

    @NotNull
    @Override
    public String getPresentableName() {
        return getId(); // TODO: Update to a proper name.
    }

    @NotNull
    @Override
    public Collection<File> getOutputRoots(CompileContext compileContext) {
        return ContainerUtil.createMaybeSingletonList(JpsJavaExtensionService.getInstance().getOutputDirectory(myModule, isTests()));
    }

    public DTargetType getDTargetType() {
        return (DTargetType) getTargetType();
    }

    @Override
    public boolean isTests() {
        return getDTargetType().isTests();
    }
}
