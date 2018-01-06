package io.github.intellij.dlanguage.psi.impl;

import com.intellij.psi.tree.IElementType;
import io.github.intellij.dlanguage.stubs.types.AliasInitializerStubElementType;
import io.github.intellij.dlanguage.stubs.types.AutoDeclarationPartStubElementType;
import io.github.intellij.dlanguage.stubs.types.CatchStubElementType;
import io.github.intellij.dlanguage.stubs.types.ConstructorStubElementType;
import io.github.intellij.dlanguage.stubs.types.DLanguageNamedImportBindStubElementType;
import io.github.intellij.dlanguage.stubs.types.DeclaratorStubElementType;
import io.github.intellij.dlanguage.stubs.types.DestructorStubElementType;
import io.github.intellij.dlanguage.stubs.types.DlangEnumMemberStubElementType;
import io.github.intellij.dlanguage.stubs.types.DlangEponymousTemplateDeclarationStubElementType;
import io.github.intellij.dlanguage.stubs.types.DlangForeachTypeStubElementType;
import io.github.intellij.dlanguage.stubs.types.DlangIfConditionStubElementType;
import io.github.intellij.dlanguage.stubs.types.DlangParameterStubElementType;
import io.github.intellij.dlanguage.stubs.types.DlangTemplateDeclarationStubElementType;
import io.github.intellij.dlanguage.stubs.types.DlangTemplateParameterStubElementType;
import io.github.intellij.dlanguage.stubs.types.DlangUnionDeclarationStubElementType;
import io.github.intellij.dlanguage.stubs.types.EnumDeclarationStubElementType;
import io.github.intellij.dlanguage.stubs.types.FunctionDeclarationStubElementType;
import io.github.intellij.dlanguage.stubs.types.IdentifierStubElementType;
import io.github.intellij.dlanguage.stubs.types.InterfaceOrClassStubElementType;
import io.github.intellij.dlanguage.stubs.types.LabeledStatementStubElementType;
import io.github.intellij.dlanguage.stubs.types.ModuleDeclarationStubElementType;
import io.github.intellij.dlanguage.stubs.types.SharedStaticConstructorStubElementType;
import io.github.intellij.dlanguage.stubs.types.SharedStaticDestructorStubElementType;
import io.github.intellij.dlanguage.stubs.types.SingleImportStubElementType;
import io.github.intellij.dlanguage.stubs.types.StaticConstructorStubElementType;
import io.github.intellij.dlanguage.stubs.types.StaticDestructorStubElementType;
import io.github.intellij.dlanguage.stubs.types.StructDeclarationStubElementType;
import io.github.intellij.dlanguage.stubs.types.UnittestStubElementType;
import io.github.intellij.dlanguage.stubs.types.VersionSpecificationElementType;
import org.jetbrains.annotations.NotNull;

public class DElementTypeFactory {
    private DElementTypeFactory() {
    }

    @NotNull
    public static IElementType factory(@NotNull final String name) {
        if (name.equals("IDENTIFIER")) return new IdentifierStubElementType(name);
        if (name.equals("FUNCTION_DECLARATION")) return new FunctionDeclarationStubElementType(name);
        if (name.equals("INTERFACE_OR_CLASS")) return new InterfaceOrClassStubElementType(name);
        if (name.equals("TEMPLATE_DECLARATION")) return new DlangTemplateDeclarationStubElementType(name);
        if (name.equals("CONSTRUCTOR")) return new ConstructorStubElementType(name);
        if (name.equals("DESTRUCTOR")) return new DestructorStubElementType(name);
        if (name.equals("STRUCT_DECLARATION")) return new StructDeclarationStubElementType(name);
        if (name.equals("ALIAS_INITIALIZER")) return new AliasInitializerStubElementType(name);
        if (name.equals("MODULE_DECLARATION")) return new ModuleDeclarationStubElementType(name);
        if (name.equals("DECLARATOR")) return new DeclaratorStubElementType(name);
        if (name.equals("LABELED_STATEMENT")) return new LabeledStatementStubElementType(name);
        if (name.equals("SHARED_STATIC_CONSTRUCTOR")) return new SharedStaticConstructorStubElementType(name);
        if (name.equals("SHARED_STATIC_DESTRUCTOR")) return new SharedStaticDestructorStubElementType(name);
        if (name.equals("STATIC_CONSTRUCTOR")) return new StaticConstructorStubElementType(name);
        if (name.equals("STATIC_DESTRUCTOR")) return new StaticDestructorStubElementType(name);
        if (name.equals("AUTO_DECLARATION_PART")) return new AutoDeclarationPartStubElementType(name);
        if (name.equals("ENUM_DECLARATION")) return new EnumDeclarationStubElementType(name);
        if (name.equals("UNION_DECLARATION")) return new DlangUnionDeclarationStubElementType(name);
        if (name.equals("SINGLE_IMPORT")) return new SingleImportStubElementType(name);
        if (name.equals("UNITTEST")) return new UnittestStubElementType(name);
        if (name.equals("CATCH")) return new CatchStubElementType(name);
        if (name.equals("IF_CONDITION")) return new DlangIfConditionStubElementType(name);
        if (name.equals("FOREACH_TYPE")) return new DlangForeachTypeStubElementType(name);
        if (name.equals("PARAMETER")) return new DlangParameterStubElementType(name);
        if (name.equals("TEMPLATE_PARAMETER")) return new DlangTemplateParameterStubElementType(name);
        if (name.equals("EPONYMOUS_TEMPLATE_DECLARATION"))
            return new DlangEponymousTemplateDeclarationStubElementType(name);
        if (name.equals("ENUM_MEMBER")) return new DlangEnumMemberStubElementType(name);
        if (name.equals("NAMED_IMPORT_BIND")) return new DLanguageNamedImportBindStubElementType(name);
        if (name.equals("VERSION_SPECIFICATION")) {
            return new VersionSpecificationElementType(name);
        }
        throw new RuntimeException("Unknown element type: " + name);
    }
}
