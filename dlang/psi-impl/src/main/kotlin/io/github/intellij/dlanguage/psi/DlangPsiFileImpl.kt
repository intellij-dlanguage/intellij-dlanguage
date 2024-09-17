package io.github.intellij.dlanguage.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.search.GlobalSearchScope.allScope
import com.intellij.util.IncorrectOperationException
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.index.DModuleIndex
import io.github.intellij.dlanguage.psi.interfaces.Declaration
import io.github.intellij.dlanguage.psi.named.DLanguageModuleDeclaration
import io.github.intellij.dlanguage.resolve.PROCESSED_FILES_KEY
import io.github.intellij.dlanguage.resolve.ScopeProcessorImplUtil.processDeclaration
import io.github.intellij.dlanguage.utils.getImportText
import org.apache.commons.lang3.StringUtils
import java.util.*
import javax.swing.Icon

// todo: consider making this class abstract so that there can be x2 classes that inherit from it.
// One for .di files (interfaces) and another for regular .d source files
class DlangPsiFileImpl(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, DLanguage), DlangPsiFile {
    override fun getFileType(): FileType = DlangFileType

    override fun toString(): String {
        return "D Language File"
    }

    override fun getIcon(flags: Int): Icon? {
        return super.getIcon(flags)
    }

    private fun findModuleDeclaration(): Optional<String> {
        return Optional.ofNullable(
            findChildByClass(
                DLanguageModuleDeclaration::class.java
            )
        )
            .map { obj: DLanguageModuleDeclaration -> obj.identifierChain }
            .map { obj: DLanguageIdentifierChain? -> getImportText(obj!!) }
    }

    /**
     * A fully qualified module name is the package (if defined) followed by the module name.
     * Both should be lowercase ASCII characters (letters, digits, underscore). Packages correspond
     * to directory names in the source file path. Package and module names cannot be Keywords.
     *
     * An example of such would be "c.stdio", where the stdio module is in the c package.
     *
     * If the module name is not defined within the file then the filename (without extension) is used as the module name.
     */
    override fun getFullyQualifiedModuleName(): String {
        val moduleDeclaration = findModuleDeclaration()

        return moduleDeclaration.orElseGet { this.getModuleName() }
    }

    /**
     * D module names are, by default, the file name with the path and extension stripped off.
     * They can be set explicitly with the module declaration.
     * This method attempts to find an explicitly defined module declaration first and then return the filename (without extension)
     * if no module declaration is found.
     */
    override fun getModuleName(): String {
        val moduleDeclaration = findModuleDeclaration()

        return moduleDeclaration
            .filter { s: String? -> StringUtil.isNotEmpty(s) }
            .map { fullDeclaration: String ->
                val identifiers = fullDeclaration.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                identifiers[identifiers.size - 1]
            }
            .orElseGet { StringUtil.trimExtensions(super.getName()) }
    }

    override fun getPackageName(): String? {
        return StringUtils.trimToNull(
            StringUtil.trimEnd(
                StringUtil.trimEnd(getFullyQualifiedModuleName(), this.getModuleName()),
                "."
            )
        )
    }

    override fun processDeclarations(
        processor: PsiScopeProcessor,
        state: ResolveState,
        lastParent: PsiElement?,
        place: PsiElement
    ): Boolean {
        var toContinue = true
        var processFiles = state.get<MutableList<String>>(PROCESSED_FILES_KEY)
        var newState = state
        if (processFiles == null) {
            processFiles = mutableListOf<String>()
            newState = state.put(PROCESSED_FILES_KEY, processFiles)
        }
        processFiles.add(getFullyQualifiedModuleName())
        for (element in children) {
            if (element is DLanguageModuleDeclaration) {
                if (!processor.execute(element, state)) {
                    toContinue = false
                }
            }
            else if (element is Declaration) {
                if (!processDeclaration(element, processor, newState, lastParent, place)) {
                    toContinue = false
                }
            }
        }
        if (toContinue && getFullyQualifiedModuleName() != "object" && this == place.containingFile) {
            var objects = DModuleIndex.getFilesByModuleName(project, "object", allScope(project)).toSet()
            // FIXME Hack hack hack for dmd repository (that otherwise resolve to an object module defined in tests)
            if (objects.size > 1)
                objects = objects.filter { it.name == "object.d" }.toSet()
            if (objects.size > 1)
                objects = objects.filter {it.containingDirectory.name == "dmd" }.toSet()

            val objectModule = objects.firstOrNull()?.containingFile as DlangPsiFile?
            toContinue = objectModule?.processDeclarations(processor, newState, objectModule, objectModule) != false
        }
        return toContinue
    }

    /**
     * @return the filename including the extension
     */
    override fun getName(): String {
        return super.getName()
    }

    /**
     * Called when a D source file is renamed
     * @param name filename of the source file being renamed
     * @return the new element after the file has been replaced
     * @throws IncorrectOperationException if the user attempts to rename the file in an illegal way. Eg:
     * D file names should be composed of the ASCII characters lower case letters, digits or _ and should also not be a Keyword.
     */
    @Throws(IncorrectOperationException::class)
    override fun setName(name: String): PsiElement {
        val module = findChildByClass(
            DLanguageModuleDeclaration::class.java
        )
        val extensionLessName = StringUtils.removeEnd(name, ".d")
        val packageName = getPackageName()
        var newModuleName = extensionLessName
        if (!packageName.isNullOrEmpty()) newModuleName = "$packageName.$extensionLessName"

        module?.setName(newModuleName!!)

        return super.setName("$extensionLessName.d")
    }
}
