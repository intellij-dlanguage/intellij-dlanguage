/*
 * Copyright (c) 2017 Patrick Scheibe
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package de.halirutan.mathematica.errorreporting;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationNamesInfo;
import com.intellij.openapi.application.ex.ApplicationInfoEx;
import com.intellij.openapi.diagnostic.Attachment;
import com.intellij.openapi.extensions.PluginDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.ExceptionUtil;
import io.github.intellij.dlanguage.utils.ExecUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Collects information about the running IDEA and the error
 */
public class IdeaInformationProxy {

    /**
     * To be used by the custom ErrorReportSubmitter to provide anonymised feedback about errors
     * @param error GitHubErrorBean
     * @param application Application
     * @param appInfo ApplicationInfoEx
     * @param namesInfo ApplicationNamesInfo
     * @param pluginDescriptor PluginDescriptor
     * @return a map of info about the IDE and the plugin and the error
     */
    @NotNull
    public static Map<String, String> getKeyValuePairs(final Throwable error,
        final String lastAction,
        final Application application,
        final ApplicationInfoEx appInfo,
        final ApplicationNamesInfo namesInfo,
        final PluginDescriptor pluginDescriptor) {
        final Map<String, String> params = new LinkedHashMap<>(50);

//        params.put("error.description", error.getDescription());

//        params.put("Plugin Name", error.getPluginName());
//        params.put("Plugin Version", error.getPluginVersion());

        params.put("Plugin Id", pluginDescriptor.getPluginId().getIdString());

        params.putAll(getPluginInfo(appInfo, pluginDescriptor, namesInfo));

        params.put("Last Action", lastAction);
        params.put("error.message", error.getMessage());
        params.put("error.stacktrace", ExceptionUtil.getThrowableText(error));
//        params.put("error.hash", error.getExceptionHash());
//
//        for (final Attachment attachment : error.getAttachments()) {
//            params.put("attachment.name", attachment.getName());
//            params.put("attachment.value", attachment.getEncodedBytes());
//        }

        return anonymise(params);
    }

    /**
     * @param appInfo an ApplicationInfoEx
     * @param pluginDescriptor a PluginDescriptor
     * @param applicationNamesInfo an ApplicationNamesInfo
     * @return a map of info about the IDE and the plugin that can be used for reporting usage info or errors
     */
    @NotNull
    public static Map<String, String> getPluginInfo(@NotNull final ApplicationInfoEx appInfo,
                                                    @Nullable final PluginDescriptor pluginDescriptor,
                                                    @NotNull final ApplicationNamesInfo applicationNamesInfo) {
        final Map<String, String> params = new HashMap<>();

        params.put("OS Name", SystemInfo.OS_NAME);
        params.put("Java version", SystemInfo.JAVA_VERSION);
        params.put("Java vm vendor", SystemInfo.JAVA_VENDOR);

        params.put("App Name", applicationNamesInfo.getProductName());
        params.put("App Full Name", applicationNamesInfo.getFullProductName());
        params.put("Is EAP", Boolean.toString(appInfo.isEAP()));
        params.put("App Build", appInfo.getBuild().asString());
        params.put("App Version", appInfo.getFullVersion());

        if(pluginDescriptor != null) {
            final IdeaPluginDescriptor plugin = PluginManagerCore.getPlugin(pluginDescriptor.getPluginId());
            if (plugin != null) {
                params.put("Plugin Name FallBack", plugin.getName());
                params.put("Plugin Version Fallback", plugin.getVersion());
            }
        }

        // todo: Removed temporarily as 'import io.github.intellij.dlanguage.settings.ToolKey' is no longer visible
        // and I want to rethink this part of the plugin

//        final String dubPath = ToolKey.DUB_KEY.getPath();
//        if (StringUtil.isNotEmpty(dubPath)) {
//            final String dubVersion = ExecUtil.getVersion(dubPath);
//            params.put("Dub Version", dubVersion);
//        }
//
//        final String dcdServerPath = ToolKey.DCD_SERVER_KEY.getPath();
//        if (StringUtil.isNotEmpty(dcdServerPath)) {
//            final String dcdServerVersion = ExecUtil.getVersion(dcdServerPath);
//            params.put("DCD Server Version", dcdServerVersion);
//        }
//
//        final String dcdClientPath = ToolKey.DCD_CLIENT_KEY.getPath();
//        if (StringUtil.isNotEmpty(dcdClientPath)) {
//            final String dcdClientVersion = ExecUtil.getVersion(dcdClientPath);
//            params.put("DCD Client Version", dcdClientVersion);
//        }
//
//        final String dscannerPath = ToolKey.DSCANNER_KEY.getPath();
//        if (StringUtil.isNotEmpty(dscannerPath)) {
//            final String DScannerVersion = ExecUtil.getVersion(dscannerPath);
//            params.put("DScanner Version", DScannerVersion);
//        }
//
//        final String gdbPath = ToolKey.GDB_KEY.getPath();
//        if (StringUtil.isNotEmpty(gdbPath)) {
//            final String GDBVersion = ExecUtil.getVersion(gdbPath);
//            params.put("GDB Version", GDBVersion);
//        }
//
//        if (StringUtil.isNotEmpty(ToolKey.DFORMAT_KEY.getPath())) {
//            final String DFormatVersion = ExecUtil.getVersion(ToolKey.DFORMAT_KEY.getPath());
//            params.put("D Format Version", DFormatVersion);
//        }
//
//        if (StringUtil.isNotEmpty(ToolKey.DFIX_KEY.getPath())) {
//            final String DFixVersion = ExecUtil.getVersion(ToolKey.DFIX_KEY.getPath());
//            params.put("DFix Version", DFixVersion);
//        }

        return anonymise(params);
    }

    // remove user name or project name from all values in the map
    private static Map<String, String> anonymise(@NotNull final Map<String, String> params) {
        final String userName = System.getProperty("user.name");
        final List<String> projects = Arrays.stream(ProjectManager.getInstance().getOpenProjects())
            .map(
                Project::getName).collect(Collectors.toList());

        for (final String key : params.keySet()) {
            String val = params.get(key);
            if (val != null) {
                for (final String projectName : projects) {
                    val = val.replaceAll(projectName, "<removed>");
                }
                val = val.replaceAll(userName, "<removed>");
                params.put(key, val);
            }
        }
        return params;
    }
}
