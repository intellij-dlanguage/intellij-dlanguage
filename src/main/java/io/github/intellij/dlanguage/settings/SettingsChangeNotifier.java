package io.github.intellij.dlanguage.settings;

import com.intellij.util.messages.Topic;
import org.jetbrains.annotations.NotNull;

/**
 * DO NOT USE!
 * This class was removed in v1.6 but that caused a problem for anyone that had installed the first release
 * of the DUB plugin. I've put it back temporarily just to prevent a class not found error.
 *
 * You should be using {@link io.github.intellij.dlanguage.messagebus.ToolChangeListener}
 */
@Deprecated
public interface SettingsChangeNotifier {
    Topic<SettingsChangeNotifier> DCD_TOPIC = Topic.create("dcd", SettingsChangeNotifier.class);

    void onSettingsChanged(@NotNull ToolSettings settings);
}
