package net.masterthought.dlanguage.unittest;

import com.intellij.execution.Location;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.testIntegration.TestLocationProvider;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class DUnitTestLocationProvider implements TestLocationProvider
{
    public static final String PROTOCOL_ID = "D";
    public static final String PROTOCOL_PREFIX = PROTOCOL_ID + "://";

    @NotNull
    @Override
    public List<Location> getLocation(@NotNull String protocolId, @NotNull String locationData, Project project)
    {
        List<Location> locations = new LinkedList<Location>();

        if (!StringUtil.equals(PROTOCOL_ID, protocolId) || StringUtil.isEmpty(locationData))
        {
            return locations;
        }

        // TODO: Process the location data to find the corresponding source

        return locations;
    }
}
