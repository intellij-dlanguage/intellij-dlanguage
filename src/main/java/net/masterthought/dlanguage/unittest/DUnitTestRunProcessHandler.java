package net.masterthought.dlanguage.unittest;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.configurations.RuntimeConfigurationError;
import com.intellij.execution.process.*;
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
import com.intellij.openapi.vfs.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.settings.ToolKey;
import net.masterthought.dlanguage.utils.DToolsNotificationListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.OutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DUnitTestRunProcessHandler extends ProcessHandler {
    private static final Logger LOG = Logger.getInstance(DUnitTestRunProcessHandler.class);
    private Map<String, Set<String>> testClassToTestMethodNames = new HashMap<>();

    private final Map<String, Integer> nodeIdsByFullTestName = new HashMap<>();
    private int nextNodeId = 0;

    private final Project project;
    private final DUnitTestRunConfiguration configuration;


    public DUnitTestRunProcessHandler(Project project, DUnitTestRunConfiguration configuration) {
        this.project = project;
        this.configuration = configuration;
    }

    public void startProcessing() {
        ApplicationManager.getApplication().runReadAction(new Runnable() {
            @Override
            public void run() {

                // NOTE: This tells the test runner that we’re actually going to do something
                testRunStarted();

                // NOTE: This creates the tree in the UI by sending testSuiteStarted() and testStarted() messages
                DUnitTestFramework unitTestFramework = new DUnitTestFramework();

                try {
                    PsiFile psiFile = PsiManager.getInstance(project).findFile(configuration.getDFile());
                    Collection<DLanguageClassDeclaration> cds = PsiTreeUtil.findChildrenOfType(psiFile, DLanguageClassDeclaration.class);
                    for (DLanguageClassDeclaration cd : cds) {

                        // if a class contains the UnitTest mixin assume its a valid d-unit test class
                        String testClassName = null;
                        Collection<DLanguageTemplateMixin> tmis = PsiTreeUtil.findChildrenOfType(cd, DLanguageTemplateMixin.class);
                        for (DLanguageTemplateMixin tmi : tmis) {
                            if (tmi.getText().contains("UnitTest")) {
                                DLanguageIdentifier classIdentifier = cd.getIdentifier();
                                if (classIdentifier != null) {
                                    testClassName = classIdentifier.getText();
                                }
                            }
                        }

                        // find methods for the class
                        Set<String> testMethodNames = new LinkedHashSet<>();
                        Collection<DLanguageFuncDeclaration> fds = PsiTreeUtil.findChildrenOfType(cd, DLanguageFuncDeclaration.class);
                        for (DLanguageFuncDeclaration fd : fds) {
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

                } catch (RuntimeConfigurationError runtimeConfigurationError) {
                    runtimeConfigurationError.printStackTrace();
                }
            }


            // NOTE: After this, actual run your tests and, as results come back, call:
            //   testFinished() when a test method finishes successfully
            //   testFailed() when a test method finishes with a failure
            //   testIgnored() when a test method is skipped for any reason
            //   testSuiteFinished() when all methods in a test class finish
            //   testRunFinished() when all tests have completed
            //   testRunCanceled() if the user has canceled the run if you need to clean up anything
            //   testStdOut() to print the output from a test class/method to the console view for review
//            }
        });

        // get d-unit path
        final String dunitPath = getDUnitPath();

        if (dunitPath == null) {
            String message = "Please add d-unit to your dub.json/dub.sdl and run Tools > Process D Libraries";
            Notifications.Bus.notify(new Notification(
                    "Execute Tests", "No d-unit dependency found", message, NotificationType.ERROR), project);
            LOG.warn(message);
        } else {
            // Actually run tests in separate runnable to avoid blocking the GUI
            ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {

                @Override
                public void run() {
                    for (Object o : testClassToTestMethodNames.entrySet()) {
                        Map.Entry pair = (Map.Entry) o;
                        String className = (String) pair.getKey();
                        Set<String> methodNames = (Set<String>) pair.getValue();

                        testSuiteStarted(className, methodNames.size());

                        for (String testMethodName : methodNames) {
                            testStarted(className, testMethodName);
                            executeTest(className, testMethodName, dunitPath);
                        }

                        testSuiteFinished(className, 0);
                    }

                    testRunFinished();
                }
            });
        }

    }

    private String getDUnitPath() {
        LibraryTable projectLibraryTable = LibraryTablesRegistrar.getInstance().getLibraryTable(project);
        for (Library lib : projectLibraryTable.getLibraries()) {
            String name = lib.getName();
            if (name != null) {
                if (name.contains("d-unit-")) {
                    VirtualFile[] files = lib.getFiles(OrderRootType.CLASSES);
                    if (files.length > 0) {
                        return dubImportPath(files[0].getCanonicalPath());
                    }
                }
            }
        }
        return null;
    }

    private String dubImportPath(String rootPath) {
        String pathUrl = VirtualFileManager.constructUrl(LocalFileSystem.PROTOCOL, rootPath);
        VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(pathUrl);
        if (file == null) {
            return null;
        }
        final List<String> sourcesDir = new ArrayList<>();
        VfsUtilCore.visitChildrenRecursively(file, new VirtualFileVisitor() {
            @Override
            public boolean visitFile(@NotNull VirtualFile file) {
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
        return sourcesDir.isEmpty() ? null : rootPath + File.separator + sourcesDir.get(0);
    }


    private void executeTest(String className, String testMethodName, String dunitPath) {
        String testPath = className + "." + testMethodName;
        final String workingDirectory = project.getBasePath();
        //            final String testFile = configuration.getDFile().getCanonicalPath();

        final String dubPath = ToolKey.DUB_KEY.getPath(project);
        if (dubPath == null || dubPath.isEmpty()) {
            Notifications.Bus.notify(
                    new Notification("Dunit Test Runner", "Dub path must be specified",
                            "Dub executable path is empty" +
                                    "<br/><a href='configureDLanguageTools'>Configure</a>",
                            NotificationType.WARNING, new DToolsNotificationListener(project)), project);
            return;
        }

        GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.setWorkDirectory(workingDirectory);
        commandLine.setExePath(dubPath);
        ParametersList parametersList = commandLine.getParametersList();
        parametersList.addParametersString("--");
        parametersList.addParametersString("-v");
        parametersList.addParametersString("--filter");
        parametersList.addParametersString(testPath + "$"); //regex to locate exact test

        final StringBuilder builder = new StringBuilder();
        try {
            OSProcessHandler process = new OSProcessHandler(commandLine.createProcess());
            process.addProcessListener(new ProcessAdapter() {
                @Override
                public void onTextAvailable(ProcessEvent event, Key outputType) {
                    builder.append(event.getText());
                }
            });

            process.startNotify();
            process.waitFor();


        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        String result = builder.toString();
        // call either finished(success) or failed
        String successPatternString = ".*OK:.*";
        Pattern successPattern = Pattern.compile(successPatternString);
        Matcher successMatcher = successPattern.matcher(result);

        if (successMatcher.find()) {
            testFinished(className, testMethodName, 0);
            testStdOut(className, testMethodName, result);
        } else if (result.contains("FAILURE:")) {
            testFailed(className, testMethodName, 0, "Failed", result);
        } else if (result.contains("SKIP:")) {
            testIgnored(className, testMethodName);
            testStdOut(className, testMethodName, result);
        } else {
            testFailed(className, testMethodName, 0, "Failed for unknown reasons", result);
        }
       

    }

    private boolean isTestMethod(DLanguageFuncDeclaration fd) {
        PsiElement ele = findElementUpstream(fd, DLanguageUserDefinedAttribute.class);
        return (ele != null && ele.getText().contains("@Test"));
    }

    private boolean isIgnoreMethod(DLanguageFuncDeclaration fd) {
        PsiElement ele = findElementUpstream(fd, DLanguageUserDefinedAttribute.class);
        return (ele != null && ele.getText().contains("@Ignore"));
    }

    private PsiElement findElementUpstream(PsiElement startingElement, Class className) {
        PsiElement parent = startingElement.getParent();
        if (parent == null) {
            return null;
        }
        PsiElement element = PsiTreeUtil.findChildOfType(parent, className);
        if (className.isInstance(element)) {
            return element;
        } else {
            try {
                return findElementUpstream(parent, className);
            } catch (Exception e) {
                return null;
            }
        }
    }

    private synchronized int getNodeId(String fullTestName) {
        Integer nodeId = nodeIdsByFullTestName.get(fullTestName);
        if (nodeId == null) {
            nodeId = nextNodeId++;
            nodeIdsByFullTestName.put(fullTestName, nodeId);
        }
        return nodeId;
    }

    private void testRunStarted() {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                notifyTextAvailable(new ServiceMessageBuilder("enteredTheMatrix").toString() + "\n", ProcessOutputTypes.STDOUT);
            }
        });
    }

    private void testRunFinished() {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                destroyProcess();
            }
        });
    }

    private void testRunCanceled() {
        // TODO: Whatever you’d need to do here
    }

    private void testStdOut(final String testClassName, final String output) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                notifyTextAvailable(ServiceMessageBuilder
                                .testStdOut(testClassName)
                                .addAttribute("out", output)
                                .addAttribute("nodeId", String.valueOf(getNodeId(testClassName)))
                                .toString() + "\n",
                        ProcessOutputTypes.STDOUT);
            }
        });
    }

    private void testStdOut(final String testClassName, final String testMethodName, final String output) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                String fullTestMethodName = testClassName + "." + testMethodName;
                notifyTextAvailable(ServiceMessageBuilder
                                .testStdOut(testMethodName)
                                .addAttribute("out", output)
                                .addAttribute("parentNodeId", String.valueOf(getNodeId(testClassName)))
                                .addAttribute("nodeId", String.valueOf(getNodeId(fullTestMethodName)))
                                .toString() + "\n",
                        ProcessOutputTypes.STDOUT);
            }
        });
    }

    private void testSuiteStarted(final String testClassName, final int numTestMethods) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                notifyTextAvailable(ServiceMessageBuilder
                                .testSuiteStarted(testClassName)
                                .addAttribute("isSuite", String.valueOf(true))
                                .addAttribute("parentNodeId", String.valueOf(getNodeId("")))
                                .addAttribute("nodeId", String.valueOf(getNodeId(testClassName)))
                                .addAttribute("count", String.valueOf(numTestMethods))
                                .addAttribute("locationHint", DUnitTestLocationProvider.PROTOCOL_PREFIX + testClassName)
                                .toString() + "\n",
                        ProcessOutputTypes.STDOUT);
            }
        });
    }

    private void testSuiteFinished(final String testClassName, final long duration) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                notifyTextAvailable(ServiceMessageBuilder
                                .testSuiteFinished(testClassName)
                                .addAttribute("isSuite", String.valueOf(true))
                                .addAttribute("parentNodeId", String.valueOf(getNodeId("")))
                                .addAttribute("nodeId", String.valueOf(getNodeId(testClassName)))
                                .addAttribute("duration", String.valueOf(duration))
                                .addAttribute("locationHint", DUnitTestLocationProvider.PROTOCOL_PREFIX + testClassName)
                                .toString() + "\n",
                        ProcessOutputTypes.STDOUT);
            }
        });
    }

    private void testStarted(final String testClassName, final String testMethodName) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                String fullTestMethodName = testClassName + "." + testMethodName;
                notifyTextAvailable(ServiceMessageBuilder
                                .testStarted(testMethodName)
                                .addAttribute("isSuite", String.valueOf(false))
                                .addAttribute("parentNodeId", String.valueOf(getNodeId(testClassName)))
                                .addAttribute("nodeId", String.valueOf(getNodeId(fullTestMethodName)))
                                .addAttribute("locationHint", DUnitTestLocationProvider.PROTOCOL_PREFIX + testClassName + "/" + testMethodName)
                                .toString() + "\n",
                        ProcessOutputTypes.STDOUT);
            }
        });
    }

    private void testFinished(final String testClassName, final String testMethodName, final long duration) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                String fullTestMethodName = testClassName + "." + testMethodName;
                notifyTextAvailable(ServiceMessageBuilder
                                .testFinished(testMethodName)
                                .addAttribute("isSuite", String.valueOf(false))
                                .addAttribute("parentNodeId", String.valueOf(getNodeId(testClassName)))
                                .addAttribute("nodeId", String.valueOf(getNodeId(fullTestMethodName)))
                                .addAttribute("duration", String.valueOf(duration))
                                .addAttribute("locationHint", DUnitTestLocationProvider.PROTOCOL_PREFIX + testClassName + "/" + testMethodName)
                                .toString() + "\n",
                        ProcessOutputTypes.STDOUT);
            }
        });
    }

    private void testFailed(final String testClassName, final String testMethodName, final long duration, final String message, final String stackTrace) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                String fullTestMethodName = testClassName + "." + testMethodName;
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
            }
        });
    }

    private void testIgnored(final String testClassName, final String testMethodName) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                String fullTestMethodName = testClassName + "." + testMethodName;
                notifyTextAvailable(ServiceMessageBuilder
                                .testIgnored(testMethodName)
                                .addAttribute("isSuite", String.valueOf(false))
                                .addAttribute("parentNodeId", String.valueOf(getNodeId(testClassName)))
                                .addAttribute("nodeId", String.valueOf(getNodeId(fullTestMethodName)))
                                .addAttribute("locationHint", DUnitTestLocationProvider.PROTOCOL_PREFIX + testClassName + "/" + testMethodName)
                                .toString() + "\n",
                        ProcessOutputTypes.STDOUT);
            }
        });
    }

    @Override
    protected void destroyProcessImpl() {
        testRunCanceled();
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
