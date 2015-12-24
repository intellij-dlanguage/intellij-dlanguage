package net.masterthought.dlanguage.settings;

import com.intellij.util.messages.Topic;
import org.jetbrains.annotations.NotNull;

public interface SettingsChangeNotifier {
    Topic<SettingsChangeNotifier> DCD_TOPIC = Topic.create("dcd", SettingsChangeNotifier.class);

    void onSettingsChanged(@NotNull ToolSettings settings);
}
