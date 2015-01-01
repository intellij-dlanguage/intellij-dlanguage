package net.masterthought.dlanguage.jps;

import net.masterthought.dlanguage.jps.model.JpsDLanguageModuleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.builders.BuildTargetLoader;
import org.jetbrains.jps.builders.ModuleBasedBuildTargetType;
import org.jetbrains.jps.model.JpsDummyElement;
import org.jetbrains.jps.model.JpsModel;
import org.jetbrains.jps.model.module.JpsTypedModule;

import java.util.ArrayList;
import java.util.List;

public class DTargetType extends ModuleBasedBuildTargetType<DTarget> {
    public static final DTargetType PRODUCTION = new DTargetType("d-production", false);
    public static final DTargetType TESTS = new DTargetType("d-tests", true);
    private final boolean test;

    public DTargetType(String name, boolean inTest) {
        super(name);
        test = inTest;
    }

    @NotNull
    @Override
    public List<DTarget> computeAllTargets(@NotNull JpsModel jpsModel) {
        List<DTarget> targets = new ArrayList<DTarget>();
        for (JpsTypedModule<JpsDummyElement> module : jpsModel.getProject().getModules(JpsDLanguageModuleType.INSTANCE)) {
            targets.add(new DTarget(module, this));
        }
        return targets;
    }

    @NotNull
    @Override
    public BuildTargetLoader<DTarget> createLoader(@NotNull final JpsModel jpsModel) {
        return new BuildTargetLoader<DTarget>() {
            @Nullable
            @Override
            public DTarget createTarget(@NotNull String targetId) {
                for (JpsTypedModule<JpsDummyElement> module : jpsModel.getProject().getModules(JpsDLanguageModuleType.INSTANCE)) {
                    if (module.getName().equals(targetId)) {
                        return new DTarget(module, DTargetType.this);
                    }
                }
                return null;
            }
        };
    }

    public boolean isTests() {
        return test;
    }
}

