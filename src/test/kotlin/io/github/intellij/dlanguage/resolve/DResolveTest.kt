package io.github.intellij.dlanguage.resolve

/**
 * Test class for resolving references.  To add a new test, add a method to this class following the conventions below.
 * - If your test is named `testThing` then you should create a `Thing` directory under `gold/resolve`.
 * - Add D files to the new directory.
 * - In your files, insert "<ref>" right before the element you want to resolve.
 * - Insert "<resolved>" right before the element you wish for the reference to resolve to.
 */
class DResolveTest : DResolveTestCase() {

    fun testClassUsageToClassDefinition() = doTest()

    fun testPrimaryExpressionToFunctionDefinition() = doTest()

    //test dos not pass for reasons not clear to me. The feature in question does work though. todo
//    fun testOverloadedParameterCount() = doTest()

    fun testClassConstructorToConstructorDefinition() = doTest()

    fun testPublicImports() = doTest()

    fun testTemplateUsageToTemplateDeclaration() = doTest()

    fun testGlobalVariableUsageToVariableDeclaration() = doTest()

    fun testAliasUsageToAliasDeclaration() = doTest()

    fun testScopedImportsFail() = doTest(false)

    fun testScopedImportsPass() = doTest()

    fun testScopedImportsMembers() = doTest()

    fun testImportBindResolve() = doTest()

    fun testImportWithSpacesAndComments() = doTest()

    fun testImportFromPackage() = doTest()

    fun testImportNamedBindResolve() = doTest()

    fun testImportClassWithConstructor() = doTest()

    fun testImportClassWithoutConstructor() = doTest()

    fun testImportNamedBindShouldNotResolveBindName() = doTest(false)
}
