package io.github.intellij.dlanguage.unittest;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RuntimeConfigurationError;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessOutputTypes;
import com.intellij.execution.testframework.sm.ServiceMessageBuilder;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.roots.libraries.LibraryTablesRegistrar;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.DlangBundle;
import io.github.intellij.dlanguage.psi.DLanguageAtAttribute;
import io.github.intellij.dlanguage.psi.DLanguageTemplateMixinExpression;
import io.github.intellij.dlanguage.psi.named.DlangClassDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangFunctionDeclaration;
import io.github.intellij.dlanguage.settings.ToolKey;

import java.io.File;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DUnitTestRunProcessHandler extends ProcessHandler { // consider OSProcessHandler or BaseProcessHandler<Process>

    private static final Logger LOG = Logger.getInstance(DUnitTestRunProcessHandler.class);
    private final Map<String, Integer> nodeIdsByFullTestName = new HashMap<>();
    private final Project project;
    private final DUnitTestRunConfiguration configuration;
    private final Map<String, Set<String>> testClassToTestMethodNames = new HashMap<>();
    private int nextNodeId = 0;


    public DUnitTestRunProcessHandler(final Project project, final DUnitTestRunConfiguration configuration) {
        this.project = project;
        this.configuration = configuration;
    }

    public void startProcessing() {
        // NOTE: After this, actual run your tests and, as results come back, call:
//   testFinished() when a test method finishes successfully
//   testFailed() when a test method finishes with a failure
//   testIgnored() when a test method is skipped for any reason
//   testSuiteFinished() when all methods in a test class finish
//   testRunFinished() when all tests have completed
//   testRunCancelled() if the user has canceled the run if you need to clean up anything
//   testStdOut() to print the output from a test class/method to the console view for review
//            }
        ApplicationManager.getApplication().runReadAction(() -> {

            // NOTE: This tells the test runner that we’re actually going to do something
            testRunStarted();

            // NOTE: This creates the tree in the UI by sending testSuiteStarted() and testStarted() messages
            final DUnitTestFramework unitTestFramework = new DUnitTestFramework();

            try {
                final PsiFile psiFile = PsiManager.getInstance(project).findFile(configuration.getDFile());
                final Collection<DlangClassDeclaration> cds = PsiTreeUtil.findChildrenOfType(psiFile, DlangClassDeclaration.class);

                for (final DlangClassDeclaration cd : cds) {

                    // if a class contains the UnitTest mixin assume its a valid d-unit test class
                    String testClassName = null;
                    final Collection<DLanguageTemplateMixinExpression> tmis = PsiTreeUtil.findChildrenOfType(cd, DLanguageTemplateMixinExpression.class);
                    for (final DLanguageTemplateMixinExpression tmi : tmis) {
                        if (tmi.getText().contains("UnitTest")) {
                            final PsiElement classIdentifier = cd.getIdentifier();
                            if (classIdentifier != null) {
                                testClassName = classIdentifier.getText();
                            }
                        }
                    }

                    // find methods for the class
                    final Set<String> testMethodNames = new LinkedHashSet<>();
                    final Collection<DlangFunctionDeclaration> fds = PsiTreeUtil
                        .findChildrenOfType(cd, DlangFunctionDeclaration.class);
                    for (final DlangFunctionDeclaration fd : fds) {
                        if (testClassName != null) {
                            // only add methods with @Test
                            if (isTestMethod(fd) || isIgnoreMethod(fd)) {
                                testMethodNames.add(fd.getIdentifier().getText());
                            }
                        }
                    }

                    // add class and corresponding methods to the map
                    if (testClassName != null && !testMethodNames.isEmpty()) {
                        testClassToTestMethodNames.put(testClassName, testMethodNames);
                    }
                }

            } catch (final RuntimeConfigurationError runtimeConfigurationError) {
                runtimeConfigurationError.printStackTrace();
            }
        });

        // get d-unit path
        final String dunitPath = getDUnitPath();

        if (dunitPath == null) {
            final String message = DlangBundle.INSTANCE.message("d.ui.unittest.notification.content.d-unit-missing");
            Notifications.Bus.notify(new Notification("Execute Tests",
                DlangBundle.INSTANCE.message("d.ui.unittest.notification.title.d-unit-missing"),
                message, NotificationType.ERROR), project);
            LOG.warn(message);

            testRunCancelled();
        } else {
            // Actually run tests in separate runnable to avoid blocking the GUI
            ApplicationManager.getApplication().executeOnPooledThread(() -> {
                for (final Map.Entry<String, Set<String>> pair : testClassToTestMethodNames.entrySet()) {
                    final String className = pair.getKey();
                    final Set<String> methodNames = pair.getValue();

                    testSuiteStarted(className, methodNames.size());
                    long startTime = System.currentTimeMillis();

                    for (final String testMethodName : methodNames) {
                        testStarted(className, testMethodName);
                        executeTest(className, testMethodName, dunitPath);
                    }

                    long duration = (System.currentTimeMillis() - startTime);
                    testSuiteFinished(className, duration);
                }

                testRunFinished();
            });
        }
    }

    @Nullable
    private String getDUnitPath() {
        final LibraryTable projectLibraryTable = LibraryTablesRegistrar.getInstance().getLibraryTable(project);

        final Optional<Library> dunit = Arrays.stream(projectLibraryTable.getLibraries())
            .filter(lib -> StringUtil.isNotEmpty(lib.getName()) && lib.getName().contains("d-unit-")) // todo: also check for "dunit-" ??
            .findFirst(); // it's possible for a dependency to be found multiple times

        if(dunit.isPresent()) {
            final VirtualFile[] files = dunit.get().getFiles(OrderRootType.CLASSES);
            if (files.length > 0) {
                return dubImportPath(files[0].getCanonicalPath());
            }
        }

        return null;
    }

    @Nullable
    private String dubImportPath(final String rootPath) {
        final String pathUrl = VirtualFileManager.constructUrl(LocalFileSystem.PROTOCOL, rootPath);
        final VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(pathUrl);
        if (file == null) {
            return null;
        }
        final List<String> sourcesDir = new ArrayList<>();
        VfsUtilCore.visitChildrenRecursively(file, new VirtualFileVisitor() {
            @Override
            public boolean visitFile(@NotNull final VirtualFile file) {
                if (file.isDirectory()) {
                    if (file.getName().equals("source")) {
                        sourcesDir.add("source");
                    }
                    if (file.getName().equals("src")) {
                        sourcesDir.add("src");
                    }
                }
                return true;
            }
        });
        return sourcesDir.isEmpty() ? null : rootPath + File.separator + sourcesDir.get(0); // todo: may not need 'File.separator' can prob use / on all platforms
    }


    private void executeTest(final String className, final String testMethodName, final String dunitPath) {
        final String testPath = className + "." + testMethodName;
        final String workingDirectory = project.getBasePath();
        //            final String testFile = configuration.getDFile().getCanonicalPath();

        final String dubPath = ToolKey.DUB_KEY.getPath();
        if (dubPath == null || dubPath.isEmpty()) {
            Notifications.Bus.notify(
                new Notification("Dunit Test Runner",
                    DlangBundle.INSTANCE.message("d.ui.unittest.notification.title.dub-path-missing"),
                    DlangBundle.INSTANCE.message("d.ui.unittest.notification.content.dub-path-missing"),
                    NotificationType.WARNING),
                project);
            return;
        }

        final GeneralCommandLine cmd = new GeneralCommandLine()
            .withWorkDirectory(workingDirectory)
            .withCharset(Charset.defaultCharset())
            .withExePath(dubPath)
            .withParameters("test", "--", "-v", "--filter", testPath + "$");

        long startTime = System.currentTimeMillis();

        final StringBuilder builder = new StringBuilder();
        try {
            final OSProcessHandler process = new OSProcessHandler(cmd.createProcess(), cmd.getCommandLineString());
            process.addProcessListener(new ProcessAdapter() {
                @Override
                public void onTextAvailable(@NotNull final ProcessEvent event, @NotNull final Key outputType) {
                    builder.append(event.getText());
                }
            });

            process.startNotify();
            process.waitFor();
        } catch (final ExecutionException e) {
            e.printStackTrace();
        }

        final String result = builder.toString();
        // call either finished(success) or failed
        final String successPatternString = ".*OK:.*";
        final Pattern successPattern = Pattern.compile(successPatternString);
        final Matcher successMatcher = successPattern.matcher(result);

        long duration = System.currentTimeMillis() - startTime;

        if (successMatcher.find()) {
            testFinished(className, testMethodName, duration);
            testStdOut(className, testMethodName, result);
        } else if (result.contains("FAILURE:")) {
            testFailed(className, testMethodName, duration, "Failed", result);
        } else if (result.contains("SKIP:")) {
            testIgnored(className, testMethodName);
            testStdOut(className, testMethodName, result);
        } else {
            testFailed(className, testMethodName, duration, "Failed for unknown reasons", result);
        }

    }

    private boolean isTestMethod(final DlangFunctionDeclaration fd) {
        final DLanguageAtAttribute ele = findElementUpstream(fd, DLanguageAtAttribute.class);
        return (ele != null && ele.getText().contains("@Test"));
    }

    private boolean isIgnoreMethod(final DlangFunctionDeclaration fd) {
        final DLanguageAtAttribute psiElement = findElementUpstream(fd, DLanguageAtAttribute.class);
        return (psiElement != null && psiElement.getText().contains("@Ignore"));
    }

    private <T extends PsiElement> T findElementUpstream(final PsiElement startingElement, @NotNull final Class<T> clazz) {
        final PsiElement parent = startingElement.getParent();
        if (parent == null) {
            return null;
        }
        final T element = PsiTreeUtil.findChildOfType(parent, clazz);
        if (clazz.isInstance(element)) {
            return element;
        } else {
            try {
                return findElementUpstream(parent, clazz);
            } catch (final Exception e) {
                return null;
            }
        }
    }

    private synchronized int getNodeId(final String fullTestName) {
        Integer nodeId = nodeIdsByFullTestName.get(fullTestName);
        if (nodeId == null) {
            nodeId = nextNodeId++;
            nodeIdsByFullTestName.put(fullTestName, nodeId);
        }
        return nodeId;
    }

    private void testRunStarted() {
        ApplicationManager.getApplication()
            .invokeLater(() -> notifyTextAvailable(new ServiceMessageBuilder("enteredTheMatrix").toString() + "\n", ProcessOutputTypes.STDOUT));
    }

    private void testRunFinished() {
        ApplicationManager.getApplication()
            .invokeLater(this::destroyProcess);
    }

    private void testRunCancelled() {
        ApplicationManager.getApplication()
            .invokeLater(this::destroyProcess);
    }

    private void testStdOut(final String testClassName, final String output) {
        ApplicationManager.getApplication()
            .invokeLater(() -> notifyTextAvailable(ServiceMessageBuilder
                        .testStdOut(testClassName)
                        .addAttribute("out", output)
                        .addAttribute("nodeId", String.valueOf(getNodeId(testClassName)))
                        .toString() + "\n",
                ProcessOutputTypes.STDOUT));
    }

    private void testStdOut(final String testClassName, final String testMethodName, final String output) {
        ApplicationManager.getApplication()
            .invokeLater(() -> {
            final String fullTestMethodName = testClassName + "." + testMethodName;
            notifyTextAvailable(ServiceMessageBuilder
                            .testStdOut(testMethodName)
                            .addAttribute("out", output)
                            .addAttribute("parentNodeId", String.valueOf(getNodeId(testClassName)))
                            .addAttribute("nodeId", String.valueOf(getNodeId(fullTestMethodName)))
                            .toString() + "\n",
                    ProcessOutputTypes.STDOUT);
        });
    }

    private void testSuiteStarted(final String testClassName, final int numTestMethods) {
        ApplicationManager.getApplication()
            .invokeLater(() -> notifyTextAvailable(ServiceMessageBuilder
                        .testSuiteStarted(testClassName)
                        .addAttribute("isSuite", String.valueOf(true))
                        .addAttribute("parentNodeId", String.valueOf(getNodeId("")))
                        .addAttribute("nodeId", String.valueOf(getNodeId(testClassName)))
                        .addAttribute("count", String.valueOf(numTestMethods))
                        .addAttribute("locationHint", DUnitTestLocationProvider.PROTOCOL_PREFIX + testClassName)
                        .toString() + "\n",
                ProcessOutputTypes.STDOUT));
    }

    private void testSuiteFinished(final String testClassName, final long duration) {
        ApplicationManager.getApplication()
            .invokeLater(() -> notifyTextAvailable(ServiceMessageBuilder
                        .testSuiteFinished(testClassName)
                        .addAttribute("isSuite", String.valueOf(true))
                        .addAttribute("parentNodeId", String.valueOf(getNodeId("")))
                        .addAttribute("nodeId", String.valueOf(getNodeId(testClassName)))
                        .addAttribute("duration", String.valueOf(duration))
                        .addAttribute("locationHint", DUnitTestLocationProvider.PROTOCOL_PREFIX + testClassName)
                        .toString() + "\n",
                ProcessOutputTypes.STDOUT));
    }

    private void testStarted(final String testClassName, final String testMethodName) {
        ApplicationManager.getApplication()
            .invokeLater(() -> {
                final String fullTestMethodName = testClassName + "." + testMethodName;
            notifyTextAvailable(ServiceMessageBuilder
                            .testStarted(testMethodName)
                            .addAttribute("isSuite", String.valueOf(false))
                            .addAttribute("parentNodeId", String.valueOf(getNodeId(testClassName)))
                            .addAttribute("nodeId", String.valueOf(getNodeId(fullTestMethodName)))
                            .addAttribute("locationHint", DUnitTestLocationProvider.PROTOCOL_PREFIX + testClassName + "/" + testMethodName)
                            .toString() + "\n",
                    ProcessOutputTypes.STDOUT);
        });
    }

    private void testFinished(final String testClassName, final String testMethodName, final long duration) {
        ApplicationManager.getApplication()
            .invokeLater(() -> {
            final String fullTestMethodName = testClassName + "." + testMethodName;
            notifyTextAvailable(ServiceMessageBuilder
                            .testFinished(testMethodName)
                            .addAttribute("isSuite", String.valueOf(false))
                            .addAttribute("parentNodeId", String.valueOf(getNodeId(testClassName)))
                            .addAttribute("nodeId", String.valueOf(getNodeId(fullTestMethodName)))
                            .addAttribute("duration", String.valueOf(duration))
                            .addAttribute("locationHint", DUnitTestLocationProvider.PROTOCOL_PREFIX + testClassName + "/" + testMethodName)
                            .toString() + "\n",
                    ProcessOutputTypes.STDOUT);
        });
    }

    private void testFailed(final String testClassName, final String testMethodName, final long duration, final String message, final String stackTrace) {
        ApplicationManager.getApplication()
            .invokeLater(() -> {
            final String fullTestMethodName = testClassName + "." + testMethodName;
            notifyTextAvailable(ServiceMessageBuilder
                            .testFailed(testMethodName)
                            .addAttribute("isSuite", String.valueOf(false))
                            .addAttribute("parentNodeId", String.valueOf(getNodeId(testClassName)))
                            .addAttribute("nodeId", String.valueOf(getNodeId(fullTestMethodName)))
                            .addAttribute("duration", String.valueOf(duration))
                            .addAttribute("message", message)
                            .addAttribute("details", stackTrace)
                            .addAttribute("locationHint", DUnitTestLocationProvider.PROTOCOL_PREFIX + testClassName + "/" + testMethodName)
                            .toString() + "\n",
                    ProcessOutputTypes.STDOUT);
        });
    }

    private void testIgnored(final String testClassName, final String testMethodName) {
        ApplicationManager.getApplication()
            .invokeLater(() -> {
            final String fullTestMethodName = testClassName + "." + testMethodName;
            notifyTextAvailable(ServiceMessageBuilder
                            .testIgnored(testMethodName)
                            .addAttribute("isSuite", String.valueOf(false))
                            .addAttribute("parentNodeId", String.valueOf(getNodeId(testClassName)))
                            .addAttribute("nodeId", String.valueOf(getNodeId(fullTestMethodName)))
                            .addAttribute("locationHint", DUnitTestLocationProvider.PROTOCOL_PREFIX + testClassName + "/" + testMethodName)
                            .toString() + "\n",
                    ProcessOutputTypes.STDOUT);
        });
    }

    @Override
    protected void destroyProcessImpl() {
        testRunCancelled();
        notifyProcessTerminated(0);
    }

    @Override
    protected void detachProcessImpl() {
        notifyProcessDetached();
    }

    @Override
    public boolean detachIsDefault() {
        return false;
    }

    @Nullable
    @Override
    public OutputStream getProcessInput() {
        return null;
    }
}
