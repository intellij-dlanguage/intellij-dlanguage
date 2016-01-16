package net.masterthought.dlanguage.unittest;

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
import com.intellij.util.containers.LinkedMultiMap;
import com.intellij.util.containers.MultiMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class DUnitTestRerunFailedTestsAction extends AbstractRerunFailedTestsAction
{
    private final Project project;

    public DUnitTestRerunFailedTestsAction(Project project, ComponentContainer componentContainer)
    {
        super(componentContainer);
        this.project = project;
    }

    @Nullable
    @Override
    protected MyRunProfile getRunProfile(@NotNull ExecutionEnvironment environment)
    {
        Project project = environment.getProject();
        if (project == null)
        {
            return null;
        }

        return getRunProfile(project);
    }

    @Nullable
    private MyRunProfile getRunProfile(final Project project)
    {
        List<AbstractTestProxy> failedTests = getFailedTests(project);
        if (ContainerUtil.isEmpty(failedTests))
        {
            return null;
        }

        final MultiMap<Module, String> moduleToFailedTestNames = new LinkedMultiMap<Module, String>();
        GlobalSearchScope searchScope = GlobalSearchScope.projectScope(project);
        for (AbstractTestProxy failedTest : failedTests)
        {
            Location location = failedTest.getLocation(project, searchScope);
            if (location != null)
            {
                PsiElement psiElement = location.getPsiElement();
                // TODO: Use the associated element to determine the failed test method/class
//                moduleToFailedTestNames.putValue(module, failedTestName);
            }
        }

        if (moduleToFailedTestNames.isEmpty())
        {
            return null;
        }

        return new MyRunProfile(new DUnitTestRunConfiguration(project))
        {
            @NotNull
            @Override
            public Module[] getModules()
            {
                Set<Module> modules = moduleToFailedTestNames.keySet();
                return modules.toArray(new Module[modules.size()]);
            }

            @Nullable
            @Override
            public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) throws ExecutionException
            {
                return new DUnitTestRunProfileState(environment);
//                return new DUnitTestRunProfileState(environment, moduleToFailedTestNames);
            }
        };
    }
}
