package net.masterthought.dlanguage.unittest;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

public class DUnitTestRunConfigurationProducer
        extends com.intellij.execution.actions.RunConfigurationProducer<DUnitTestRunConfiguration>
{
    public DUnitTestRunConfigurationProducer()
    {
        super(DUnitTestRunConfigurationType.getInstance());
    }

    @Override
    protected boolean setupConfigurationFromContext(DUnitTestRunConfiguration configuration, ConfigurationContext configurationContext, Ref<PsiElement> ref)
    {
        return false;
    }

    @Nullable
    @Override
    public com.intellij.execution.actions.ConfigurationFromContext createConfigurationFromContext(ConfigurationContext context)
    {
        return null;
    }

    @Override
    public boolean isConfigurationFromContext(DUnitTestRunConfiguration unitTestRunConfiguration, ConfigurationContext configurationContext)
    {
        return false;
    }
}