package io.github.intellij.dlanguage.unittest;

import com.intellij.execution.Location;
import com.intellij.execution.testframework.sm.runner.SMTestLocator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class DUnitTestLocationProvider implements SMTestLocator {
    private static final DUnitTestLocationProvider INSTANCE = new DUnitTestLocationProvider();

    public static final String PROTOCOL_ID = "D";
    public static final String PROTOCOL_PREFIX = PROTOCOL_ID + "://";

    public static DUnitTestLocationProvider getInstance() {
        return INSTANCE;
    }

    @NotNull
    @Override
    public List<Location> getLocation(@NotNull final String protocol,
                                      @NotNull final String path,
                                      @NotNull final Project project,
                                      @NotNull final GlobalSearchScope scope) {
        final List<Location> locations = new LinkedList<>();

        if (!StringUtil.equals(PROTOCOL_ID, protocol) || StringUtil.isEmpty(path))
        {
            return locations;
        }

        // TODO: Process the path to find the corresponding source

        return locations;
    }
}
