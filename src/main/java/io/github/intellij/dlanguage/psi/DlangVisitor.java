// This is a generated file. Not intended for manual editing.
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import io.github.intellij.dlanguage.psi.impl.DLanguageAddExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAliasDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAliasThisDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAlignAttributeImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAndAndExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAndExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAnonymousEnumDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAnonymousEnumMemberImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageArgumentListImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageArgumentsImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageArrayInitializerImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageArrayLiteralImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageArrayMemberInitializationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAsmAddExpImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAsmAndExpImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAsmBrExpImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAsmEqualExpImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAsmExpImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAsmInstructionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAsmLogAndExpImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAsmLogOrExpImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAsmMulExpImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAsmOrExpImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAsmPrimaryExpImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAsmRelExpImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAsmShiftExpImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAsmStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAsmTypePrefixImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAsmUnaExpImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAsmXorExpImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAssertExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAssignExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAssocArrayLiteralImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAtAttributeImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAttributeDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAttributeImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageAutoDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageBaseClassImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageBaseClassListImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageBlockStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageBodyStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageBreakStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageBuiltinTypeImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageCaseRangeStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageCaseStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageCastExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageCastQualifierImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageCatchesImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageClassDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageCmpExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageCompileConditionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageConditionalDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageConditionalStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageConstraintImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageContinueStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageDebugConditionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageDebugSpecificationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageDeclarationOrStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageDeclarationsAndStatementsImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageDefaultStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageDeleteExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageDeleteStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageDeprecatedImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageDestructorImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageDoStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageEnumBodyImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageEqualExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageExpressionStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageFinalSwitchStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageFinallyImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageForStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageForeachStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageForeachTypeListImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageFunctionAttributeImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageFunctionBodyImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageFunctionCallExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageFunctionLiteralExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageGotoStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageIdentifierChainImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageIdentifierListImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageIdentifierOrTemplateChainImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageIdentifierOrTemplateInstanceImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageIdentityExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageIfStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageImportBindImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageImportBindingsImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageImportDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageImportExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageInExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageInStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageIndexExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageIndexImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageInitializerImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageInterfaceDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageInvariantImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageIsExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageKeyValuePairImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageKeyValuePairsImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageLambdaExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageLastCatchImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageLinkageAttributeImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageMemberFunctionAttributeImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageMixinDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageMixinExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageMixinTemplateDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageMixinTemplateNameImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageMulExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageNewAnonClassExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageNewExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageNonVoidInitializerImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageOperandsImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageOrExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageOrOrExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageOutStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageParameterAttributeImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageParametersImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguagePostblitImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguagePowExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguagePragmaDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguagePragmaExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguagePrimaryExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageRegisterImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageRelExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageReturnStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageScopeGuardStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageSharedStaticConstructorImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageSharedStaticDestructorImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageShiftExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageSliceExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageStatementNoCaseNoDefaultImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageStaticAssertDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageStaticAssertStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageStaticConstructorImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageStaticCtorDtorCommonImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageStaticDestructorImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageStaticIfConditionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageStorageClassImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageStringImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageStructBodyImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageStructInitializerImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageStructMemberInitializerImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageStructMemberInitializersImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageSwitchStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageSymbolImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageSynchronizedStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTemplateAliasParameterImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTemplateArgumentImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTemplateArgumentListImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTemplateArgumentsImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTemplateInstanceImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTemplateMixinDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTemplateMixinExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTemplateParameterListImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTemplateParametersImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTemplateSingleArgumentImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTemplateThisParameterImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTemplateTupleParameterImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTemplateTypeParameterImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTemplateValueParameterDefaultImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTemplateValueParameterImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTernaryExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageThrowStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTraitsExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTryStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTypeConstructorImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTypeConstructorsImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTypeImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTypeSpecializationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTypeSuffixImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageType_2Impl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTypeidExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageTypeofExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageUnaryExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageVariableDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageVectorImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageVersionConditionImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageWhileStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageWithStatementImpl;
import io.github.intellij.dlanguage.psi.impl.DLanguageXorExpressionImpl;
import io.github.intellij.dlanguage.psi.impl.DlangUnittestImpl;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageAliasInitializerImpl;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageAutoDeclarationPartImpl;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageCatchImpl;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageConstructorImpl;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageDeclaratorImpl;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageEnumMemberImpl;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageEponymousTemplateDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageForeachTypeImpl;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageFunctionDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageIfConditionImpl;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageLabeledStatementImpl;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageModuleDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageNamedImportBindImpl;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageParameterImpl;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageTemplateParameterImpl;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageVersionSpecificationImpl;
import io.github.intellij.dlanguage.psi.impl.named.DlangEnumDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.named.DlangIdentifierImpl;
import io.github.intellij.dlanguage.psi.impl.named.DlangInterfaceOrClassImpl;
import io.github.intellij.dlanguage.psi.impl.named.DlangSingleImportImpl;
import io.github.intellij.dlanguage.psi.impl.named.DlangStructDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.named.DlangTemplateDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.named.DlangUnionDeclarationImpl;
import io.github.intellij.dlanguage.psi.interfaces.DCompositeElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.Mixin;
import org.jetbrains.annotations.NotNull;


public class DlangVisitor extends PsiElementVisitor {

    public void visitAliasDeclaration(@NotNull final DLanguageAliasDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitAliasInitializer(@NotNull final DLanguageAliasInitializerImpl o) {
        visitPsiElement(o);
    }

    public void visitAliasThisDeclaration(@NotNull final DLanguageAliasThisDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitAndExpression(@NotNull final DLanguageAndExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitAnonymousEnumDeclaration(@NotNull final DLanguageAnonymousEnumDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitAnonymousEnumMember(@NotNull final DLanguageAnonymousEnumMemberImpl o) {
        visitPsiElement(o);
    }

    public void visitArgumentList(@NotNull final DLanguageArgumentListImpl o) {
        visitPsiElement(o);
    }

    public void visitArguments(@NotNull final DLanguageArgumentsImpl o) {
        visitPsiElement(o);
    }

    public void visitArrayInitializer(@NotNull final DLanguageArrayInitializerImpl o) {
        visitPsiElement(o);
    }

    public void visitArrayLiteral(@NotNull final DLanguageArrayLiteralImpl o) {
        visitPsiElement(o);
    }

    public void visitArrayMemberInitialization(@NotNull final DLanguageArrayMemberInitializationImpl o) {
        visitPsiElement(o);
    }

    public void visitAsmAddExp(@NotNull final DLanguageAsmAddExpImpl o) {
        visitPsiElement(o);
    }

    public void visitAsmAndExp(@NotNull final DLanguageAsmAndExpImpl o) {
        visitPsiElement(o);
    }

    public void visitAsmBrExp(@NotNull final DLanguageAsmBrExpImpl o) {
        visitPsiElement(o);
    }

    public void visitAsmEqualExp(@NotNull final DLanguageAsmEqualExpImpl o) {
        visitPsiElement(o);
    }

    public void visitAsmExp(@NotNull final DLanguageAsmExpImpl o) {
        visitPsiElement(o);
    }

    public void visitAsmInstruction(@NotNull final DLanguageAsmInstructionImpl o) {
        visitPsiElement(o);
    }

    public void visitAsmLogAndExp(@NotNull final DLanguageAsmLogAndExpImpl o) {
        visitPsiElement(o);
    }

    public void visitAsmLogOrExp(@NotNull final DLanguageAsmLogOrExpImpl o) {
        visitPsiElement(o);
    }

    public void visitAsmMulExp(@NotNull final DLanguageAsmMulExpImpl o) {
        visitPsiElement(o);
    }

    public void visitAsmOrExp(@NotNull final DLanguageAsmOrExpImpl o) {
        visitPsiElement(o);
    }

    public void visitAsmPrimaryExp(@NotNull final DLanguageAsmPrimaryExpImpl o) {
        visitPsiElement(o);
    }

    public void visitAsmRelExp(@NotNull final DLanguageAsmRelExpImpl o) {
        visitPsiElement(o);
    }

    public void visitAsmShiftExp(@NotNull final DLanguageAsmShiftExpImpl o) {
        visitPsiElement(o);
    }

    public void visitAsmStatement(@NotNull final DLanguageAsmStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitAsmTypePrefix(@NotNull final DLanguageAsmTypePrefixImpl o) {
        visitPsiElement(o);
    }

    public void visitAsmUnaExp(@NotNull final DLanguageAsmUnaExpImpl o) {
        visitPsiElement(o);
    }

    public void visitAsmXorExp(@NotNull final DLanguageAsmXorExpImpl o) {
        visitPsiElement(o);
    }

    public void visitAssertExpression(@NotNull final DLanguageAssertExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitAssignExpression(@NotNull final DLanguageAssignExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitAssocArrayLiteral(@NotNull final DLanguageAssocArrayLiteralImpl o) {
        visitPsiElement(o);
    }

    public void visitAtAttribute(@NotNull final DLanguageAtAttributeImpl o) {
        visitPsiElement(o);
    }

    public void visitAttribute(@NotNull final DLanguageAttributeImpl o) {
        visitPsiElement(o);
    }

    public void visitAttributeDeclaration(@NotNull final DLanguageAttributeDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitAutoDeclaration(@NotNull final DLanguageAutoDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitAutoDeclarationPart(@NotNull final DLanguageAutoDeclarationPartImpl o) {
        visitPsiElement(o);
    }

    public void visitBlockStatement(@NotNull final DLanguageBlockStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitBodyStatement(@NotNull final DLanguageBodyStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitBreakStatement(@NotNull final DLanguageBreakStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitBaseClass(@NotNull final DLanguageBaseClassImpl o) {
        visitPsiElement(o);
    }

    public void visitBaseClassList(@NotNull final DLanguageBaseClassListImpl o) {
        visitPsiElement(o);
    }

    public void visitCaseRangeStatement(@NotNull final DLanguageCaseRangeStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitCaseStatement(@NotNull final DLanguageCaseStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitCastExpression(@NotNull final DLanguageCastExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitCastQualifier(@NotNull final DLanguageCastQualifierImpl o) {
        visitPsiElement(o);
    }

    public void visitCatch(@NotNull final DLanguageCatchImpl o) {
        visitPsiElement(o);
    }

    public void visitCatches(@NotNull final DLanguageCatchesImpl o) {
        visitPsiElement(o);
    }

    public void visitClassDeclaration(@NotNull final DLanguageClassDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitCmpExpression(@NotNull final DLanguageCmpExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitCompileCondition(@NotNull final DLanguageCompileConditionImpl o) {
        visitPsiElement(o);
    }

    public void visitConditionalDeclaration(@NotNull final DLanguageConditionalDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitConditionalStatement(@NotNull final DLanguageConditionalStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitConstraint(@NotNull final DLanguageConstraintImpl o) {
        visitPsiElement(o);
    }

    public void visitConstructor(@NotNull final DLanguageConstructorImpl o) {
        visitPsiElement(o);
    }

    public void visitContinueStatement(@NotNull final DLanguageContinueStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitDebugCondition(@NotNull final DLanguageDebugConditionImpl o) {
        visitPsiElement(o);
    }

    public void visitDebugSpecification(@NotNull final DLanguageDebugSpecificationImpl o) {
        visitPsiElement(o);
    }

    public void visitDeclaration(@NotNull final DLanguageDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitDeclarationOrStatement(@NotNull final DLanguageDeclarationOrStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitDeclarationsAndStatements(@NotNull final DLanguageDeclarationsAndStatementsImpl o) {
        visitPsiElement(o);
    }

    public void visitDeclarator(@NotNull final DLanguageDeclaratorImpl o) {
        visitPsiElement(o);
    }

    public void visitDefaultStatement(@NotNull final DLanguageDefaultStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitDeleteExpression(@NotNull final DLanguageDeleteExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitDeleteStatement(@NotNull final DLanguageDeleteStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitDeprecated(@NotNull final DLanguageDeprecatedImpl o) {
        visitPsiElement(o);
    }

    public void visitDestructor(@NotNull final DLanguageDestructorImpl o) {
        visitPsiElement(o);
    }

    public void visitDoStatement(@NotNull final DLanguageDoStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitEnumBody(@NotNull final DLanguageEnumBodyImpl o) {
        visitPsiElement(o);
    }

    public void visitEnumDeclaration(@NotNull final DlangEnumDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitEnumMember(@NotNull final DLanguageEnumMemberImpl o) {
        visitPsiElement(o);
    }

    public void visitEqualExpression(@NotNull final DLanguageEqualExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitExpression(@NotNull final DLanguageExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitExpressionStatement(@NotNull final DLanguageExpressionStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitFinalSwitchStatement(@NotNull final DLanguageFinalSwitchStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitFinally(@NotNull final DLanguageFinallyImpl o) {
        visitPsiElement(o);
    }

    public void visitForStatement(@NotNull final DLanguageForStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitForeachStatement(@NotNull final DLanguageForeachStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitForeachType(@NotNull final DLanguageForeachTypeImpl o) {
        visitPsiElement(o);
    }

    public void visitForeachTypeList(@NotNull final DLanguageForeachTypeListImpl o) {
        visitPsiElement(o);
    }

    public void visitFunctionAttribute(@NotNull final DLanguageFunctionAttributeImpl o) {
        visitPsiElement(o);
    }

    public void visitFunctionBody(@NotNull final DLanguageFunctionBodyImpl o) {
        visitPsiElement(o);
    }

    public void visitFunctionCallExpression(@NotNull final DLanguageFunctionCallExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitFunctionDeclaration(@NotNull final DLanguageFunctionDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitFunctionLiteralExpression(@NotNull final DLanguageFunctionLiteralExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitGotoStatement(@NotNull final DLanguageGotoStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitIdentifierChain(@NotNull final DLanguageIdentifierChainImpl o) {
        visitPsiElement(o);
    }

    public void visitIdentifierList(@NotNull final DLanguageIdentifierListImpl o) {
        visitPsiElement(o);
    }

    public void visitIdentifierOrTemplateChain(@NotNull final DLanguageIdentifierOrTemplateChainImpl o) {
        visitPsiElement(o);
    }

    public void visitIdentifierOrTemplateInstance(@NotNull final DLanguageIdentifierOrTemplateInstanceImpl o) {
        visitPsiElement(o);
    }

    public void visitIdentityExpression(@NotNull final DLanguageIdentityExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitIfStatement(@NotNull final DLanguageIfStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitImportBind(@NotNull final DLanguageImportBindImpl o) {
        visitPsiElement(o);
    }

    public void visitImportBindings(@NotNull final DLanguageImportBindingsImpl o) {
        visitPsiElement(o);
    }

    public void visitImportDeclaration(@NotNull final DLanguageImportDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitImportExpression(@NotNull final DLanguageImportExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitIndexExpression(@NotNull final DLanguageIndexExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitInExpression(@NotNull final DLanguageInExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitInStatement(@NotNull final DLanguageInStatementImpl o) {
        visitPsiElement(o);
    }

//    public void visitInitialize(@NotNull DLanguageInitializeImpl o) {
//        visitPsiElement(o);
//    }

    public void visitInitializer(@NotNull final DLanguageInitializerImpl o) {
        visitPsiElement(o);
    }

    public void visitInterfaceDeclaration(@NotNull final DLanguageInterfaceDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitInvariant(@NotNull final DLanguageInvariantImpl o) {
        visitPsiElement(o);
    }

    public void visitIsExpression(@NotNull final DLanguageIsExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitKeyValuePair(@NotNull final DLanguageKeyValuePairImpl o) {
        visitPsiElement(o);
    }

    public void visitKeyValuePairs(@NotNull final DLanguageKeyValuePairsImpl o) {
        visitPsiElement(o);
    }

    public void visitLabeledStatement(@NotNull final DLanguageLabeledStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitLastCatch(@NotNull final DLanguageLastCatchImpl o) {
        visitPsiElement(o);
    }

    public void visitLinkageAttribute(@NotNull final DLanguageLinkageAttributeImpl o) {
        visitPsiElement(o);
    }

    public void visitMemberFunctionAttribute(@NotNull final DLanguageMemberFunctionAttributeImpl o) {
        visitPsiElement(o);
    }

    public void visitMixinDeclaration(@NotNull final DLanguageMixinDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitMixinExpression(@NotNull final DLanguageMixinExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitMixinTemplateDeclaration(@NotNull final DLanguageMixinTemplateDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitMixinTemplateName(@NotNull final DLanguageMixinTemplateNameImpl o) {
        visitPsiElement(o);
    }

//    public void visitModule(@NotNull DLanguageModuleImpl o) {
//        visitPsiElement(o);
//    }

    public void visitModuleDeclaration(@NotNull final DLanguageModuleDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitMulExpression(@NotNull final DLanguageMulExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitNewAnonClassExpression(@NotNull final DLanguageNewAnonClassExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitNewExpression(@NotNull final DLanguageNewExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitNonVoidInitializer(@NotNull final DLanguageNonVoidInitializerImpl o) {
        visitPsiElement(o);
    }

    public void visitOperands(@NotNull final DLanguageOperandsImpl o) {
        visitPsiElement(o);
    }

    public void visitOrExpression(@NotNull final DLanguageOrExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitOrOrExpression(@NotNull final DLanguageOrOrExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitOutStatement(@NotNull final DLanguageOutStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitParameter(@NotNull final DLanguageParameterImpl o) {
        visitPsiElement(o);
    }

    public void visitParameters(@NotNull final DLanguageParametersImpl o) {
        visitPsiElement(o);
    }

    public void visitPostblit(@NotNull final DLanguagePostblitImpl o) {
        visitPsiElement(o);
    }

    public void visitPowExpression(@NotNull final DLanguagePowExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitPragmaDeclaration(@NotNull final DLanguagePragmaDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitPragmaExpression(@NotNull final DLanguagePragmaExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitPrimaryExpression(@NotNull final DLanguagePrimaryExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitRegister(@NotNull final DLanguageRegisterImpl o) {
        visitPsiElement(o);
    }

    public void visitRelExpression(@NotNull final DLanguageRelExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitReturnStatement(@NotNull final DLanguageReturnStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitScopeGuardStatement(@NotNull final DLanguageScopeGuardStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitSharedStaticConstructor(@NotNull final DLanguageSharedStaticConstructorImpl o) {
        visitPsiElement(o);
    }

    public void visitSharedStaticDestructor(@NotNull final DLanguageSharedStaticDestructorImpl o) {
        visitPsiElement(o);
    }

    public void visitShiftExpression(@NotNull final DLanguageShiftExpressionImpl o) {
        visitPsiElement(o);
    }

//    public void visitSingleImport(@NotNull DlangSingleImportImpl o) {
//        visitPsiElement(o);
//    }

    public void visitIndex(@NotNull final DLanguageIndexImpl o) {
        visitPsiElement(o);
    }

    public void visitStatement(@NotNull final DLanguageStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitStatementNoCaseNoDefault(@NotNull final DLanguageStatementNoCaseNoDefaultImpl o) {
        visitPsiElement(o);
    }

    public void visitStaticAssertDeclaration(@NotNull final DLanguageStaticAssertDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitStaticAssertStatement(@NotNull final DLanguageStaticAssertStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitStaticConstructor(@NotNull final DLanguageStaticConstructorImpl o) {
        visitPsiElement(o);
    }

    public void visitStaticDestructor(@NotNull final DLanguageStaticDestructorImpl o) {
        visitPsiElement(o);
    }

    public void visitStaticIfCondition(@NotNull final DLanguageStaticIfConditionImpl o) {
        visitPsiElement(o);
    }

    public void visitStorageClass(@NotNull final DLanguageStorageClassImpl o) {
        visitPsiElement(o);
    }

    public void visitStructBody(@NotNull final DLanguageStructBodyImpl o) {
        visitPsiElement(o);
    }

    public void visitStructDeclaration(@NotNull final DlangStructDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitStructInitializer(@NotNull final DLanguageStructInitializerImpl o) {
        visitPsiElement(o);
    }

    public void visitStructMemberInitializer(@NotNull final DLanguageStructMemberInitializerImpl o) {
        visitPsiElement(o);
    }

    public void visitStructMemberInitializers(@NotNull final DLanguageStructMemberInitializersImpl o) {
        visitPsiElement(o);
    }

    public void visitSwitchStatement(@NotNull final DLanguageSwitchStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitSymbol(@NotNull final DLanguageSymbolImpl o) {
        visitPsiElement(o);
    }

    public void visitSynchronizedStatement(@NotNull final DLanguageSynchronizedStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitTemplateAliasParameter(@NotNull final DLanguageTemplateAliasParameterImpl o) {
        visitPsiElement(o);
    }

    public void visitTemplateArgument(@NotNull final DLanguageTemplateArgumentImpl o) {
        visitPsiElement(o);
    }

    public void visitTemplateArgumentList(@NotNull final DLanguageTemplateArgumentListImpl o) {
        visitPsiElement(o);
    }

    public void visitTemplateArguments(@NotNull final DLanguageTemplateArgumentsImpl o) {
        visitPsiElement(o);
    }

    public void visitTemplateDeclaration(@NotNull final DlangTemplateDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitTemplateInstance(@NotNull final DLanguageTemplateInstanceImpl o) {
        visitPsiElement(o);
    }

    public void visitTemplateMixinExpression(@NotNull final DLanguageTemplateMixinExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitTemplateParameter(@NotNull final DLanguageTemplateParameterImpl o) {
        visitPsiElement(o);
    }

    public void visitTemplateParameterList(@NotNull final DLanguageTemplateParameterListImpl o) {
        visitPsiElement(o);
    }

    public void visitTemplateParameters(@NotNull final DLanguageTemplateParametersImpl o) {
        visitPsiElement(o);
    }

    public void visitTemplateSingleArgument(@NotNull final DLanguageTemplateSingleArgumentImpl o) {
        visitPsiElement(o);
    }

    public void visitTemplateThisParameter(@NotNull final DLanguageTemplateThisParameterImpl o) {
        visitPsiElement(o);
    }

    public void visitTemplateTupleParameter(@NotNull final DLanguageTemplateTupleParameterImpl o) {
        visitPsiElement(o);
    }

    public void visitTemplateTypeParameter(@NotNull final DLanguageTemplateTypeParameterImpl o) {
        visitPsiElement(o);
    }

    public void visitTemplateValueParameter(@NotNull final DLanguageTemplateValueParameterImpl o) {
        visitPsiElement(o);
    }

    public void visitTemplateValueParameterDefault(@NotNull final DLanguageTemplateValueParameterDefaultImpl o) {
        visitPsiElement(o);
    }


    public void visitDCompositeElement(@NotNull final DCompositeElement o) {
        visitElement(o);
    }

    public void visitDNamedElement(@NotNull final DNamedElement o) {
        visitElement(o);
    }

    public void visitMixin(@NotNull final Mixin o) {
        visitElement(o);
    }

    public void visitPsiElement(@NotNull final PsiElement o) {
        visitElement(o);
    }

    public void visitXorExpression(@NotNull final DLanguageXorExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitWithStatement(@NotNull final DLanguageWithStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitWhileStatement(@NotNull final DLanguageWhileStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitVersionSpecification(@NotNull final DLanguageVersionSpecificationImpl o) {
        visitPsiElement(o);
    }

    public void visitVersionCondition(@NotNull final DLanguageVersionConditionImpl o) {
        visitPsiElement(o);
    }

    public void visitVector(@NotNull final DLanguageVectorImpl o) {
        visitPsiElement(o);
    }

    public void visitVariableDeclaration(@NotNull final DLanguageVariableDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitUnionDeclaration(@NotNull final DlangUnionDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitUnaryExpression(@NotNull final DLanguageUnaryExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitTypeSuffix(@NotNull final DLanguageTypeSuffixImpl o) {
        visitPsiElement(o);
    }

    public void visitTypeSpecialization(@NotNull final DLanguageTypeSpecializationImpl o) {
        visitPsiElement(o);
    }

    public void visitTypeofExpression(@NotNull final DLanguageTypeofExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitType(@NotNull final DLanguageTypeImpl o) {
        visitPsiElement(o);
    }

    public void visitTypeidExpression(@NotNull final DLanguageTypeidExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitTryStatement(@NotNull final DLanguageTryStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitTraitsExpression(@NotNull final DLanguageTraitsExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitThrowStatement(@NotNull final DLanguageThrowStatementImpl o) {
        visitPsiElement(o);
    }

    public void visitTernaryExpression(@NotNull final DLanguageTernaryExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitStaticCtorDtorCommon(@NotNull final DLanguageStaticCtorDtorCommonImpl o) {
        visitPsiElement(o);
    }

    public void visitAddExpression(@NotNull final DLanguageAddExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitIdentifier(@NotNull final DlangIdentifierImpl identifier) {
        visitPsiElement(identifier);
    }

    public void visitType_2(@NotNull final DLanguageType_2Impl o) {
        visitPsiElement(o);
    }

    public void visitParameterAttribute(@NotNull final DLanguageParameterAttributeImpl o) {
        visitPsiElement(o);
    }

    public void visitTypeConstructors(@NotNull final DLanguageTypeConstructorsImpl o) {
        visitPsiElement(o);
    }

    public void visitEponymousTemplateDeclaration(
        @NotNull final DLanguageEponymousTemplateDeclarationImpl o) {
        visitPsiElement(o);
    }


    public void visitUnittest(@NotNull final DlangUnittestImpl o) {
        visitPsiElement(o);
    }

    public void visitTypeConstructor(@NotNull final DLanguageTypeConstructorImpl o) {
        visitPsiElement(o);
    }

    public void visitTemplateMixinDeclaration(
        @NotNull final DLanguageTemplateMixinDeclarationImpl o) {
        visitPsiElement(o);
    }

    public void visitSliceExpression(@NotNull final DLanguageSliceExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitAndAndExpression(@NotNull final DLanguageAndAndExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitAlignAttribute(@NotNull final DLanguageAlignAttributeImpl o) {
        visitPsiElement(o);
    }

    public void visitLambdaExpression(@NotNull final DLanguageLambdaExpressionImpl o) {
        visitPsiElement(o);
    }

    public void visitInterfaceOrClass(@NotNull final DlangInterfaceOrClassImpl o) {
        visitPsiElement(o);
    }

    public void visitString(@NotNull final DLanguageStringImpl o) {
        visitPsiElement(o);
    }

    public void visitBuiltinType(@NotNull final DLanguageBuiltinTypeImpl o) {
        visitPsiElement(o);
    }

    public void visitNamedImportBind(
        @NotNull final DLanguageNamedImportBindImpl dLanguageNamedImportBind) {
        visitPsiElement(dLanguageNamedImportBind);
    }

    public void visitIfCondition(DLanguageIfConditionImpl dLanguageIfCondition) {
        visitPsiElement(dLanguageIfCondition);
    }

    public void visitSingleImport(DlangSingleImportImpl dlangSingleImport) {
        visitPsiElement(dlangSingleImport);
    }
}
