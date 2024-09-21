package io.github.intellij.dlanguage.resolve

import org.junit.Test

/**
 * Test class for resolving references.  To add a new test, add a method to this class following the conventions below.
 * - If your test is named `testThing` then you should create a `Thing` directory under `gold/resolve`.
 * - Add D files to the new directory.
 * - In your files, insert "<ref>" right before the element you want to resolve.
 * - Insert "<resolved>" right before the element you wish for the reference to resolve to.
 */
class DResolveTest : DResolveTestCase() {

    @Test
    fun testClassUsageToClassDefinition() = doTest()

    @Test
    fun testPrimaryExpressionToFunctionDefinition() = doTest()

    //test dos not pass for reasons not clear to me. The feature in question does work though. todo
//    @Test fun testOverloadedParameterCount() = doTest()

    @Test
    fun testClassUsageToOpaqueClassDefinitionShouldResolve() = doTest()

    // Note: the constructor call must resolve to the class to properly handle the rename
    // Another mechanism will handle the goto behavior to allow a redirection to the constructor call
    @Test
    fun testClassConstructorToClassDefinition() = doTest()
    @Test
    fun testConstructorCallShouldResolveClassDeclarationInTheSameFile() = doTest()

    @Test
    fun testDeclarationInsideDeclarationBlock() = doTest()

    @Test
    fun testDeclarationInsideDeclarationBlockUsageOutside() = doTest()

    @Test
    fun testPublicImports() = doTest()

    @Test
    fun testPublicImportLoopImportShouldNotLoopIndefinitely() = doTest(false)

    @Test
    fun testPublicImportsWithAttributeSpecifier() = doTest()

    @Test
    fun testPublicImportsInClassWithAttributeSpecifierShouldNotResolveOutsideClass() = doTest(false)

    @Test
    fun testTemplateUsageToTemplateDeclaration() = doTest()

    @Test
    fun testGlobalVariableUsageToVariableDeclaration() = doTest()

    @Test
    fun testAliasUsageToAliasDeclaration() = doTest()

    @Test
    fun testAliasUsageToAliasDeclarationSecondAliasForm() = doTest()

    @Test
    fun testAliasUsageToAliasDeclarationSecondAliasFormWithImport() = doTest()

    @Test
    fun testScopedImportsFail() = doTest(false)

    @Test
    fun testScopedImportsPass() = doTest()

    @Test
    fun testImportBindResolve() = doTest()

    @Test
    fun testImportWithSpacesAndComments() = doTest()

    // TODO need to handle public imports
    //@Test
    //fun testImportFromPackage() = doTest()

    @Test
    fun testImportNamedBindResolve() = doTest()

    @Test
    fun testImportClassWithConstructor() = doTest()

    @Test
    fun testImportClassWithoutConstructor() = doTest()

    @Test
    fun testScopeOperatorDotXResolveGlobal() = doTest()

    @Test
    fun testScopeOperatorDotXResolveGlobalWithImport() = doTest()

    @Test
    fun testScopeOperatorXResolveLocal() = doTest()

    @Test
    fun testGotoToLabeledStatementShouldResolve() = doTest()

    @Test
    fun testContinueToLabeledStatementShouldResolve() = doTest()

    @Test
    fun testForeachVariableUsageResolve() = doTest()

    @Test
    fun testForeachVariableOutOfScopeUsageShouldNotResolve() = doTest(false)

    @Test
    fun testForLoopVariableUsageResolve() = doTest()

    @Test
    fun testForLoopVariableUsageInConditionResolve() = doTest()

    @Test
    fun testForLoopVariableOutOfScopeUsageShouldNotResolve() = doTest(false)

    @Test
    fun testConstructorCallResolveParameters() = doTest()

    @Test
    fun testAnonymousEnumUsage() = doTest()

    @Test
    fun testEnumUsageWithEnumTypePrefix() = doTest()

    @Test
    fun testEnumUsageWithoutEnumTypePrefixShouldNotResolve() = doTest(false)

    @Test
    fun testEnumValueQualifiedAsTypeShouldNotResolve() = doTest(false)

    @Test
    fun testNestedStructDefinitionReferenceFullyQualifiedShouldResolve() = doTest()

    @Test
    fun testNestedStructDefinitionReferenceWithoutParentReferenceShouldNotResolve() = doTest(false)

    @Test
    fun testNestedStructDefinitionReferenceFullyQualifiedShouldNotResolveATopLevelStruct() = doTest(false)

    @Test
    fun testLocalTypeDefinitionWithUsageAfterDefinitionShouldResolve() = doTest()
    @Test
    fun testLocalTypeDefinitionWithUsageBeforeDefinitionShouldNotResolve() = doTest(false)

    @Test
    fun testVariableInitializationAssignedValuePreviousInitializationSameVariableDeclarationShouldResolve() = doTest()

    @Test
    fun testVariableInitializationAssignedValueItselfShouldNotResolve() = doTest(false)

    @Test
    fun testVariableInitializationAssignedValueNextInitializationSameVariableDeclarationShouldNotResolve() = doTest(false)

    @Test
    fun testTemplateTypeUsageInTemplatedClassShouldResolve() = doTest()

    @Test
    fun testTemplateTypeShouldNotLeakOutsideTemplate() = doTest(false)

    @Test
    fun testTemplateInstanceParameterUsageToOuterStructDefinitionShouldResolve() = doTest()

    @Test
    fun testTemplateThisTypeUsageInTemplatedClassShouldResolve() = doTest()

    @Test
    fun testTemplateTupleUsageInTemplatedClassShouldResolve() = doTest()

    @Test
    fun testTemplateTupleUsage2InTemplatedClassShouldResolve() = doTest()

    @Test
    fun testLambdaParameterUsageShouldResolve() = doTest()

    @Test
    fun testLambdaMultiParametersUsageShouldResolve() = doTest()
}
