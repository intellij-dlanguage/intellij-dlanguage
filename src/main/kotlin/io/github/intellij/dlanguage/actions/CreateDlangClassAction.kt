package io.github.intellij.dlanguage.actions

import com.google.common.collect.ImmutableMap
import com.intellij.ide.actions.CreateFileAction
import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.ide.fileTemplates.FileTemplate
import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.ide.fileTemplates.FileTemplateUtil
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.ui.InputValidatorEx
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import io.github.intellij.dlanguage.DLanguage
import java.io.File
import java.util.*
import java.util.regex.Pattern
import javax.swing.Icon
import kotlin.collections.HashMap

/**
 * Created by pirocks on 9/21/16.
 * mostly copy-pasted from "CreateDlangFileAction.java"
 */
open class CreateDlangClassAction : CreateFileFromTemplateAction(NEW_D_FILE, "", DLanguage.Icons.CLASS), DumbAware {

    override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
        for (template in Template.values()) {
            with(template) {
                builder.addKind(readableName.capitalize(), icon, id)
            }
        }

        builder.setTitle(NEW_D_FILE)
            .setValidator(ClassNameValidator())
    }

    override fun createFileFromTemplate(name: String, template: FileTemplate, inRequestedDirectory: PsiDirectory): PsiFile {
        val sourceRoot = findSourceRootOrDefault(inRequestedDirectory, inRequestedDirectory.virtualFile)

        val segments = inRequestedDirectory.virtualFile.path.removePrefix(sourceRoot.path).split("/")
            .filter { it.isNotBlank() }
            .toMutableList()
        val nameSegments = name.split(".")
        segments.addAll(nameSegments)

        assert(segments.isNotEmpty())

        val module = segments.last().lowercase(Locale.getDefault())
        val pack = segments.subList(0, segments.size - 1)
        val aggregate = segments.last()

        val storeDirectory = CreateFileAction.MkDirs(nameSegments.joinToString(File.separator), inRequestedDirectory)
            .directory
        val dlangModuleName = pack.joinToString(".") + if (pack.isNotEmpty()) (if (module == "package") "" else ".$module") else module
        val element = FileTemplateUtil.createFromTemplate(
            template,
            module,
            ImmutableMap.builder<String, Any>()
                .putAll(FileTemplateManager.getInstance(inRequestedDirectory.project).defaultProperties.entries
                    .stream()
                    .collect(ImmutableMap.toImmutableMap<Map.Entry<Any, Any>, String, Any>(
                        { entry -> entry.key.toString() },
                        { it }
                    )))
                .put("DLANGUAGE_MODULE_NAME", dlangModuleName)
                .put("DLANGUAGE_CLASS_NAME", aggregate)
                .build()
                .toMap(HashMap<String, Any>()),
            storeDirectory,
            null
        )

        if (element.containingFile.virtualFile != null) {
            FileEditorManager.getInstance(inRequestedDirectory.project)
                .openFile(element.containingFile.virtualFile, true)
            if (defaultTemplateProperty != null) {
                PropertiesComponent.getInstance(inRequestedDirectory.project)
                    .setValue(defaultTemplateProperty!!, template.name)
            }
        }

        return element.containingFile
    }

    override fun getActionName(directory: PsiDirectory, newName: String, templateName: String): String {
        return NEW_D_FILE
    }

    override fun isAvailable(dataContext: DataContext): Boolean {
        if (super.isAvailable(dataContext)) {
            val project = CommonDataKeys.PROJECT.getData(dataContext)
            val dirContext = CommonDataKeys.VIRTUAL_FILE.getData(dataContext)

            project ?: return false
            dirContext ?: return false

            return ProjectRootManager.getInstance(project).contentSourceRoots.find { dirContext.path.startsWith(it.path) } != null
        }

        return false
    }

    private fun findSourceRootOrDefault(inRequestedDirectory: PsiDirectory, defaultSourceRoot: VirtualFile): VirtualFile {
        return ProjectRootManager.getInstance(inRequestedDirectory.project)
            .contentSourceRoots
            .find { inRequestedDirectory.virtualFile.path.startsWith(it.path) }
            ?: defaultSourceRoot
    }

    inner class ClassNameValidator : InputValidatorEx {
        private val VALID_MODULE_NAME_REGEX = Pattern.compile("[A-Za-z_][0-z_.]*")
        override fun getErrorText(inputString: String): String? {
            if (inputString.isEmpty()) {
                return null
            }
            return if (VALID_MODULE_NAME_REGEX.matcher(inputString).matches()) {
                null
            } else String.format("\'%s\' is not a valid D class name.", inputString)
        }

        override fun checkInput(inputString: String): Boolean {
            return true
        }

        override fun canClose(inputString: String): Boolean {
            return getErrorText(inputString) == null
        }
    }

    companion object {
        const val NEW_D_FILE = "New D File"

        enum class Template(val id: String, val readableName: String, val icon: Icon) {
            MODULE("d_language_module", "module", DLanguage.Icons.MODULE),
            CLASS("d_language_class", "class", DLanguage.Icons.NODE_CLASS),
            INTERFACE("d_language_class", "interface", DLanguage.Icons.NODE_INTERFACE),
            STRUCT("d_language_class", "struct", DLanguage.Icons.NODE_STRUCT),
            UNION("d_language_union", "union", DLanguage.Icons.NODE_UNION),
            ENUM("d_language_enum", "enum", DLanguage.Icons.NODE_ENUM)
        }
    }
}
