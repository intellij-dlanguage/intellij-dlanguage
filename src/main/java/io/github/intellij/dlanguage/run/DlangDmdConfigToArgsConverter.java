package io.github.intellij.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.intellij.dlanguage.run.exception.NoSourcesException;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DlangDmdConfigToArgsConverter {

    private static final Logger log = Logger.getInstance(DlangDmdConfigToArgsConverter.class);

    public static List<String> getDmdParameters(@NotNull final DlangRunDmdConfiguration config, @NotNull final Module module)
                                                                throws NoSourcesException, ExecutionException {
        @NotNull final VirtualFile[] sourcesRoots = ModuleRootManager.getInstance(module).getSourceRoots();
        @Nullable final VirtualFile[] excludedRoots = getExcludedRoots(module);
        final List<String> dmdParameters = new LinkedList<>();

        dmdParameters.addAll(configToParameters(config));
        dmdParameters.add(getOutputPathArgument(module));
        dmdParameters.add(getOutputFileArgument(module, config));
        for (final VirtualFile sourcesRoot : sourcesRoots) {
            dmdParameters.addAll(getAllDLangSources(sourcesRoot, excludedRoots));
        }

        return dmdParameters;
    }

    @Nullable
    private static VirtualFile[] getExcludedRoots(@Nullable final Module module) {
        if (module != null && !module.isDisposed()) {
            return ModuleRootManager.getInstance(module).getExcludeRoots();
        }
        return null;
    }

    @NotNull
    private static String getOutputPathArgument(final Module module) {
        final String outputDirUrl = getOutputDir(module);
        final File outputDir = new File(VfsUtilCore.urlToPath(outputDirUrl), "obj");
        return "-od" + outputDir.getAbsolutePath();
    }

    @NotNull
    private static String getOutputFileArgument(@NotNull final Module module, @NotNull final DlangRunDmdConfiguration config) {
        return "-of" + getOutputFilePath(module, config);
    }

    @NotNull
    private static String getOutputFilePath(@NotNull final Module module, @NotNull final DlangRunDmdConfiguration config) {
        String filename = module.getName();
        if (config.isLibrary()) {
            filename += ".lib";
        } else if (SystemInfo.isWindows) {
            filename += ".exe";
        }
        final String outputDirUrl = getOutputDir(module);
        final File outputFile = new File(VfsUtilCore.urlToPath(outputDirUrl), filename);
        return outputFile.getAbsolutePath();
    }

    /*
    * Will attempt to get a build directory (such as "out") in the project's root or alternatively just fall back
    * to the project root path
    */
    @Nullable
    private static String getOutputDir(@NotNull final Module module) {
        return StringUtil.defaultIfEmpty(ModuleRootManager.getInstance(module)
            .getModuleExtension(CompilerModuleExtension.class)
            .getCompilerOutputUrl(),
            module.getProject().getBasePath()
        );
    }


    @NotNull
    private static List<String> configToParameters(final DlangRunDmdConfiguration config) {
        final LinkedList<String> result = new LinkedList<>();
        buildCompilerParameters(config, result);
        buildOutputParameters(config, result);
        buildDebugParameters(config, result);

        return result;
    }

    private static void buildCompilerParameters(final DlangRunDmdConfiguration config, final List<String> parameters) {
        if (config.isBetterC()) {
            parameters.add("-betterC");
        }
        if (config.isRelease()) {
            parameters.add("-release");
        }
        if (config.isDebug()) {
            parameters.add("-debug");
        }
        if (config.isUnitTest()) {
            parameters.add("-unittest");
        }
        if (config.isLink()) {
            parameters.add("-link");
        }
        if (config.isCoverageAnalysis()) {
            parameters.add("-cov");
        }
        if (config.isAllowDeprecated()) {
            parameters.add("-d");
        }
        if (config.isIgnorePragmas()) {
            parameters.add("-ignore");
        }
        if (config.isFunctionInlining()) {
            parameters.add("-inline");
        }
        if (config.isLibrary()) {
            parameters.add("-lib");
        }
        if (config.isNoArrayBoundsCheck()) {
            parameters.add("-noboundscheck");
        }
        if (config.isNoFloatingPointReferences()) {
            parameters.add("-nofloat");
        }
        if (config.isOptimize()) {
            parameters.add("-O");
        }
        if (config.isEnforcePropertySyntax()) {
            parameters.add("-property");
        }
        if (config.isQuiet()) {
            parameters.add("-quiet");
        }
        if (config.isVerbose()) {
            parameters.add("-v");
        }
        if (config.isListThreadLocalStorage()) {
            parameters.add("-vtls");
        }
        if (config.isWarnings()) {
            parameters.add("-w");
        }
        if (config.isInfoWarnings()) {
            parameters.add("-wi");
        }
        if (!StringUtil.isEmptyOrSpaces(config.getDefaultLibrary())) {
            parameters.add("-defaultlib=" + config.getDefaultLibrary());
        }
        if (!StringUtil.isEmptyOrSpaces(config.getImportsPath())) {
            parameters.add("-I" + config.getImportsPath());
        }
        if (!StringUtil.isEmptyOrSpaces(config.getStringImportsPath())) {
            parameters.add("-J" + config.getStringImportsPath());
        }
        if (!StringUtil.isEmptyOrSpaces(config.getLinkerArgs())) {
            parameters.add("-L" + config.getLinkerArgs());
        }
    }

    private static void buildOutputParameters(final DlangRunDmdConfiguration config,
        final List<String> parameters) {
        if (config.isGenerateDocs()) {
            parameters.add("-D");
            if (!StringUtil.isEmptyOrSpaces(config.getDocsPath())) {
                parameters.add("-Dd" + config.getDocsPath());
            }
            if (!StringUtil.isEmptyOrSpaces(config.getDocsFilename())) {
                parameters.add("-Df" + config.getDocsFilename());
            }
        }

        if (!StringUtil.isEmptyOrSpaces(config.getModuleDepsFilename())) {
            parameters.add("-deps=" + config.getModuleDepsFilename());
        }

        if (config.isGenerateHeader()) {
            parameters.add("-H");
            if (!StringUtil.isEmptyOrSpaces(config.getHeaderDir())) {
                parameters.add("-Hd" + config.getHeaderDir());
            }
            if (!StringUtil.isEmptyOrSpaces(config.getHeaderFilename())) {
                parameters.add("-Hf" + config.getHeaderFilename());
            }
        }

        if (config.isGenerateMap()) {
            parameters.add("-map");
        }

        if (config.isNoObjectFiles()) {
            parameters.add("-o-");
        }

        if (config.isNoStripPaths()) {
            parameters.add("-op");
        }

        if (config.isGenerateJson()) {
            parameters.add("-X");
            if (!StringUtil.isEmptyOrSpaces(config.getJsonFilename())) {
                parameters.add("-Xf" + config.getJsonFilename());
            }
        }
    }

    // todo: should we add -gdwarf=3 (3, 4, or 5), -gf, and -gs ?
    private static void buildDebugParameters(final DlangRunDmdConfiguration config,
        final List<String> parameters) {
        if (config.isAddSymbolicDebugInfo()) {
            parameters.add("-g");
        }
        if (config.isGenerateStandardStackFrame()) {
            parameters.add("-gs");
        }
        if (config.isProfile()) {
            parameters.add("-profile");
        }

        if (!StringUtil.isEmptyOrSpaces(config.getSymbolicLibrary())) {
            parameters.add("-debuglib=" + config.getSymbolicLibrary());
        }
    }

    @NotNull
    private static List<String> getAllDLangSources(@NotNull final VirtualFile sourcesRoot, @Nullable final VirtualFile[] excludedRoots) throws NoSourcesException {
        final DlangVirtualFileVisitor visitor = new DlangVirtualFileVisitor(excludedRoots);
        VfsUtilCore.visitChildrenRecursively(sourcesRoot, visitor);

        //Build list of *.D source files
        final List<String> sources = visitor.getDlangSources();

        if (sources.isEmpty()) {
            log.warn("No sources found");
            throw new NoSourcesException(sourcesRoot);
        }
        return sources;
    }
}
