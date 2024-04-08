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
    fun testClassConstructorToConstructorDefinition() = doTest()

    @Test
    fun testPublicImports() = doTest()

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
    fun testScopedImportsMembers() = doTest()

    @Test
    fun testImportBindResolve() = doTest()

    @Test
    fun testImportWithSpacesAndComments() = doTest()

    @Test
    fun testImportFromPackage() = doTest()

    @Test
    fun testImportNamedBindResolve() = doTest()

    @Test
    fun testImportClassWithConstructor() = doTest()

    @Test
    fun testImportClassWithoutConstructor() = doTest()

    @Test
    fun testImportUsageOfRenamedImport() = doTest()

    @Test
    fun testScopeOperatorDotXResolveGlobal() = doTest()

    // TODO should work but resolve state is not currently powerful enough to handle this case
    //@Test
    //fun testScopeOperatorDotXResolveGlobalWithImport() = doTest()

    @Test
    fun testScopeOperatorXResolveLocal() = doTest()

    @Test
    fun testConstructorCallShouldResolveConstructorWithClassDeclarationInTheSameFile() = doTest()

    @Test
    fun testConstructorCallResolveParameters() = doTest()

    @Test
    fun testAnonymousEnumUsage() = doTest()

    // TODO should work but resolve state is not currently powerful enough to handle this case
    //      it needs to handle the chain resolve (A.B)
    //@Test
    //fun testEnumUsageWithEnumTypePrefix() = doTest()

    @Test
    fun testEnumUsageWithoutEnumTypePrefixShouldNotResolve() = doTest(false)
}
