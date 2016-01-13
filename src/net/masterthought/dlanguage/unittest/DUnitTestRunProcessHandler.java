package net.masterthought.dlanguage.unittest;

import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessOutputTypes;
import com.intellij.execution.testframework.sm.ServiceMessageBuilder;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.Nullable;

import java.io.OutputStream;
import java.util.*;

public class DUnitTestRunProcessHandler extends ProcessHandler {
    private List<String> testClassNames;
    private Map<String, Set<String>> testClassToTestMethodNames;

    private final Map<String, Integer> nodeIdsByFullTestName = new HashMap<String, Integer>();
    private int nextNodeId = 0;

    private final Project project;
    private final DUnitTestRunConfiguration configuration;

    public DUnitTestRunProcessHandler(Project project, DUnitTestRunConfiguration configuration) {
        this.project = project;
        this.configuration = configuration;
    }

    public void startProcessing() {
        ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
            @Override
            public void run() {

                // NOTE: This tells the test runner that we’re actually going to do something
                testRunStarted();

                // NOTE: This creates the tree in the UI by sending testSuiteStarted() and testStarted() messages
                DUnitTestFramework unitTestFramework = new DUnitTestFramework();
                for (final String testClassName : testClassNames) {
                    List<String> testMethodNames = new LinkedList<String>();
                    // Populate the test method for the current class as appropriate for your framework


                    if (!ContainerUtil.isEmpty(testMethodNames)) {
                        Collections.sort(testMethodNames);
                        testSuiteStarted(testClassName, testMethodNames.size());

                        for (String testMethodName : testMethodNames) {
//                            testStarted(simpleTestClassName, testMethodName);
                        }
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
            }
        });
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
//        notifyProcessTerminated(0);
    }

    @Override
    protected void detachProcessImpl() {
//        notifyProcessDetached();
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
