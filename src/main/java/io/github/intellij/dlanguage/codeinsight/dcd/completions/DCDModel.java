package io.github.intellij.dlanguage.codeinsight.dcd.completions;

import org.jetbrains.annotations.Nullable;

public class DCDModel {
    public DCDCompletionKind kind;
    public String value;

    @Nullable
    public String typeText = null;

    public DCDModel(String name, String id, @Nullable String type) {
        value = name;
        kind = fromId(id);
        typeText = type;
    }

    public DCDModel(String name, String id) {
        this(name, id, null);
    }

    public DCDCompletionKind fromId(String id) {
        switch (id) {
            case "c":
                return DCDCompletionKind.ClassName;
            case "i":
                return DCDCompletionKind.InterfaceName;
            case "s":
                return DCDCompletionKind.StructName;
            case "u":
                return DCDCompletionKind.UnionName;
            case "v":
                return DCDCompletionKind.VariableName;
            case "m":
                return DCDCompletionKind.MemberVariableName;
            case "k":
                return DCDCompletionKind.Keyword;
            case "f":
                return DCDCompletionKind.Function;
            case "g":
                return DCDCompletionKind.EnumName;
            case "e":
                return DCDCompletionKind.EnumMember;
            case "P":
                return DCDCompletionKind.PackageName;
            case "M":
                return DCDCompletionKind.ModuleName;
            case "a":
                return DCDCompletionKind.Array;
            case "A":
                return DCDCompletionKind.AssociativeArray;
            case "l":
                return DCDCompletionKind.AliasName;
            case "t":
                return DCDCompletionKind.TemplateName;
            case "T":
                return DCDCompletionKind.MixinTemplate;
        }
        throw new RuntimeException("Unknow DCD Completion kind: " + id);
    }
}
