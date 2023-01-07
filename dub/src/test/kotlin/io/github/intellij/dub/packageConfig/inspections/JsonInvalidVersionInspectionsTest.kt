package io.github.intellij.dub.packageConfig.inspections

import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.intellij.lang.annotations.Language

class JsonInvalidVersionInspectionsTest : BasePlatformTestCase() {

    fun `test version should be correctly detected`() = doTest("""
        {
          "dependencies": {
            "foo": "*",
            "foo-sub": {
              "version": "1.0.0"
            },
            "foo0": "~>1.0.0",
            "foo1": "==1.0.0",
            "foo2": ">1.0.0",
            "foo3": "<1.0.0",
            "foo4": ">=1.0.0",
            "foo5": "<=1.0.0",
            "foo6": "1.0.0",
            "foo7": ">=1.0.0 <=2.0.0",
            "foo8": "~master",
            "foo9": "1.0.0-beta.1",
            "foo10": "1.0.0+foo",
            "foo11": "1.0.0-beta.1+foo",
            "foo12": <error descr="Invalid version requirement ~>master">"~>master"</error>,
            "foo13": <error descr="Invalid version requirement ==master">"==master"</error>,
            "foo13-sub": {
              "version": <error descr="Invalid version requirement ==master">"==master"</error>
             },
            "foo14": <error descr="Invalid version requirement ==1.0.0 <=2.0.0">"==1.0.0 <=2.0.0"</error>,
            "foo15": <error descr="Invalid version requirement *1.2.3">"*1.2.3"</error>,
            "foo16": <error descr="First comparison operator expected to be either > or >=, not <=">"<=2.0.0 >=2.1.0"</error>,
            "foo17": <error descr="First comparison operator should not be greater to the second">">=4.0.0 <=2.1.0"</error>,
            "foo18": <error descr="Version must be a string">null</error>
          }
        }
    """.trimIndent())

    fun doTest(@Language("JSON") dubJson: String) {
        myFixture.addFileToProject("dub.json", dubJson)
        myFixture.enableInspections(PackageVersionValidationInspection())
        myFixture.configureByFile("dub.json")
        FileDocumentManager.getInstance().saveAllDocuments()
        myFixture.testHighlighting(true, false, true)
    }
}
