package io.github.intellij.dlanguage.codeinsight.render;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import io.github.intellij.dlanguage.codeinsight.dcd.DCDCompletionClient;
import io.github.intellij.dlanguage.codeinsight.dcd.completions.DCDCompletionKind;
import io.github.intellij.dlanguage.codeinsight.dcd.completions.DCDModel;
import io.github.intellij.dlanguage.icons.DlangIcons;
import org.jetbrains.annotations.NotNull;

public class BasicRenderer extends LookupElement {
    DCDModel model;

    public BasicRenderer(DCDModel model) {
        this.model = model;
    }

    @NotNull
    @Override
    public String getLookupString() {
        if (model.kind == DCDCompletionKind.Function)
            return model.value + "()";
        else
            return model.value;
    }

    @Override
    public void renderElement(LookupElementPresentation presentation) {
        presentation.setIcon(DlangIcons.FILE);
        presentation.setItemText(model.value);
        presentation.setItemTextBold(true);
        // if typeText is not null that means it is user code
        // else it is language specific code
        if (model.typeText != null)
            presentation.setTypeText(model.typeText);
        else
            presentation.setItemTextBold(false);

        switch (model.kind) {
            case ClassName:
                presentation.setIcon(DlangIcons.NODE_CLASS);
                break;
            case InterfaceName:
                presentation.setIcon(DlangIcons.NODE_INTERFACE);
                break;
            case StructName:
                presentation.setIcon(DlangIcons.NODE_STRUCT);
                break;
            case UnionName:
                presentation.setIcon(DlangIcons.NODE_UNION);
                break;
            case VariableName:
                presentation.setIcon(DlangIcons.NODE_FIELD);
                break;
            case MemberVariableName:
                presentation.setIcon(DlangIcons.NODE_FIELD);
                break;
            case Keyword:
                presentation.setIcon(null);
                break;
            case Function:
                presentation.setIcon(DlangIcons.NODE_FUNCTION);
                break;
            case EnumName:
                presentation.setIcon(DlangIcons.NODE_ENUM);
                break;
            case EnumMember:
                presentation.setIcon(DlangIcons.NODE_FIELD);
                break;
            case PackageName:
                presentation.setIcon(null);
                break;
            case ModuleName:
                presentation.setIcon(DlangIcons.MODULE);
                break;
            case Array:
                break;
            case AssociativeArray:
                break;
            case AliasName:
                presentation.setIcon(DlangIcons.NODE_ALIAS);
                break;
            case TemplateName:
                break;
            case MixinTemplate:
                presentation.setIcon(DlangIcons.NODE_MIXIN);
                break;
        }
    }
}
