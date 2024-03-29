package io.github.intellij.dlanguage.unittest;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.Location;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.testframework.AbstractTestProxy;
import com.intellij.execution.testframework.actions.AbstractRerunFailedTestsAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComponentContainer;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.MultiMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class DUnitTestRerunFailedTestsAction extends AbstractRerunFailedTestsAction {
    private final Project project;

    public DUnitTestRerunFailedTestsAction(final Project project, final ComponentContainer componentContainer) {
        super(componentContainer);
        this.project = project;
    }

    @Nullable
    @Override
    protected MyRunProfile getRunProfile(@NotNull final ExecutionEnvironment environment) {
        final Project project = environment.getProject();

        return project != null? getRunProfile(project) : null;
    }

    @Nullable
    private MyRunProfile getRunProfile(final Project project) {
        final List<AbstractTestProxy> failedTests = getFailedTests(project);
        if (ContainerUtil.isEmpty(failedTests))
        {
            return null;
        }

        final MultiMap<Module, String> moduleToFailedTestNames = MultiMap.createLinked();
        final GlobalSearchScope searchScope = GlobalSearchScope.projectScope(project);

        for (final AbstractTestProxy failedTest : failedTests) {
            final Location location = failedTest.getLocation(project, searchScope);
            if (location != null) {
                final PsiElement psiElement = location.getPsiElement();
                // TODO: Use the associated element to determine the failed test method/class
//                moduleToFailedTestNames.putValue(module, failedTestName);
            }
        }

        if (moduleToFailedTestNames.isEmpty()) {
            return null;
        }

        return new MyRunProfile(new DUnitTestRunConfiguration(project)) {
            @NotNull
            @Override
            public Module[] getModules() {
                final Set<Module> modules = moduleToFailedTestNames.keySet();
                return modules.toArray(new Module[modules.size()]);
            }

            @Nullable
            @Override
            public RunProfileState getState(@NotNull final Executor executor,
                                            @NotNull final ExecutionEnvironment environment) throws ExecutionException {
                return new DUnitTestRunProfileState(environment);
//                return new DUnitTestRunProfileState(environment, moduleToFailedTestNames);
            }
        };
    }
}
