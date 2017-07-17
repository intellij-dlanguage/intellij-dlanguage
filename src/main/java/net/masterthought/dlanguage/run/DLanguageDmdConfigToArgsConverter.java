package net.masterthought.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import net.masterthought.dlanguage.run.exception.NoSourcesException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class DLanguageDmdConfigToArgsConverter {
    public static List<String> getDmdParameters(DLanguageRunDmdConfiguration config, Module module)
        throws NoSourcesException, ExecutionException {
        VirtualFile sourcesRoot = getSourceRoot(module);
        VirtualFile[] excludedRoots = getExcludedRoots(module);
        List<String> dmdParameters = new LinkedList<String>();

        dmdParameters.addAll(configToParameters(config));
        dmdParameters.add(getOutputPathArgument(module));
        dmdParameters.add(getOutputFileArgument(module, config));
        dmdParameters.addAll(getAllDLangSources(sourcesRoot, excludedRoots));

        return dmdParameters;
    }

    private static VirtualFile getSourceRoot(Module module) {
        if (module != null) {
            VirtualFile[] sourcesRoots = ModuleRootManager.getInstance(module).getSourceRoots();
            if (sourcesRoots.length >= 1) {
                return sourcesRoots[0];
            }
        }
        return null;
    }

    private static VirtualFile[] getExcludedRoots(Module module) {
        if (module != null) {
            return ModuleRootManager.getInstance(module).getExcludeRoots();
        }
        return null;
    }

    @NotNull
    private static String getOutputPathArgument(Module module) {
        String outputDirUrl = getOutputDir(module);
        File outputDir = new File(VfsUtilCore.urlToPath(outputDirUrl), "obj");
        return "-od" + outputDir.getPath();
    }

    @NotNull
    private static String getOutputFileArgument(Module module, DLanguageRunDmdConfiguration config) {
        return "-of" + getOutputFilePath(module, config);
    }

    @NotNull
    private static String getOutputFilePath(Module module, DLanguageRunDmdConfiguration config) {
        String filename = module.getName();
        if (config.isLibrary()) {
            filename += ".lib";
        } else if (SystemInfo.isWindows) {
            filename += ".exe";
        }
        String outputDirUrl = getOutputDir(module);
        File outputFile = new File(VfsUtilCore.urlToPath(outputDirUrl), filename);
        return outputFile.getPath();
    }

    private static String getOutputDir(Module module) {
        return ModuleRootManager.getInstance(module).getModuleExtension(CompilerModuleExtension.class).getCompilerOutputUrl();
    }


    private static List<String> configToParameters(DLanguageRunDmdConfiguration config) {
        LinkedList<String> result = new LinkedList<String>();
        buildCompilerParameters(config, result);
        buildOutputParameters(config, result);
        buildDebugParameters(config, result);

        return result;
    }

    private static void buildCompilerParameters(DLanguageRunDmdConfiguration config, List<String> parameters) {
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

    private static void buildOutputParameters(DLanguageRunDmdConfiguration config, List<String> parameters) {
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

    private static void buildDebugParameters(DLanguageRunDmdConfiguration config, List<String> parameters) {
        if (config.isAddSymbolicDebugInfo()) {
            parameters.add("-g");
        }
        if (config.isAddSymbolicDebugInfoC()) {
            parameters.add("-gc");
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
    private static List<String> getAllDLangSources(final VirtualFile sourcesRoot, final VirtualFile[] excludedRoots)
        throws NoSourcesException, ExecutionException {
        DLanguageVirtualFileVisitor visitor = new DLanguageVirtualFileVisitor(sourcesRoot, excludedRoots);
        VfsUtilCore.visitChildrenRecursively(sourcesRoot, visitor);

        //Build list of *.D source files
        List<String> sources = visitor.getdLangSources();
        if (sources.isEmpty()) {
            throw new NoSourcesException(sourcesRoot.getCanonicalPath());
        }
        return sources;
    }
}
