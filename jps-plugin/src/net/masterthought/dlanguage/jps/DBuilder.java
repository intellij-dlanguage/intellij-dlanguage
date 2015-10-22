package net.masterthought.dlanguage.jps;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.incremental.BuilderService;
import org.jetbrains.jps.incremental.ModuleLevelBuilder;

import java.util.Arrays;
import java.util.List;

public class DBuilder extends BuilderService {
//    @NotNull
//    @Override
//    public List<? extends ModuleLevelBuilder> createModuleLevelBuilders() {
//        return Arrays.asList(new DBuilder());
//    }

    @NotNull
    @Override
    public List<DTargetType> getTargetTypes() {
        return Arrays.asList(DTargetType.PRODUCTION, DTargetType.TESTS);
    }
}

