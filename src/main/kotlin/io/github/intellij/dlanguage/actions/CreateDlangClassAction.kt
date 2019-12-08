package io.github.intellij.dlanguage.actions

import com.google.common.collect.ImmutableMap
import com.intellij.ide.actions.CreateFileAction
import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.ide.fileTemplates.FileTemplate
import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.ide.fileTemplates.FileTemplateUtil
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.ui.InputValidatorEx
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import io.github.intellij.dlanguage.icons.DlangIcons
import java.io.File
import java.util.regex.Pattern
import javax.swing.Icon

/**
 * Created by pirocks on 9/21/16.
 * mostly copy-pasted from "CreateDlangFileAction.java"
 */
class CreateDlangClassAction : CreateFileFromTemplateAction(NEW_D_FILE, "", DlangIcons.CLASS), DumbAware {
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

        val module = segments.last().toLowerCase()
        val pack = segments.subList(0, segments.size - 1)
        val aggregate = segments.last()

        val storeDirectory = CreateFileAction.MkDirs(nameSegments.joinToString(File.separator), inRequestedDirectory)
            .directory

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
                .put("DLANGUAGE_MODULE_NAME", pack.joinToString(".") + if (pack.isNotEmpty()) ".$module" else module)
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

    private fun findSourceRootOrDefault(inRequestedDirectory: PsiDirectory, defaultSourceRoot: VirtualFile): VirtualFile {
        return ProjectRootManager.getInstance(inRequestedDirectory.project)
            .contentSourceRoots
            .find { inRequestedDirectory.virtualFile.path.startsWith(it.path) }
            ?: defaultSourceRoot
    }

    override fun getActionName(directory: PsiDirectory, newName: String, templateName: String): String {
        return NEW_D_FILE
    }

    inner class ClassNameValidator : InputValidatorEx {
        private val VALID_MODULE_NAME_REGEX = Pattern.compile("[A-z_.]+")
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
        private const val NEW_D_FILE = "New D File"

        enum class Template(val id: String, val readableName: String, val icon: Icon) {
            MODULE("d_language_module", "module", DlangIcons.MODULE),
            CLASS("d_language_class", "class", DlangIcons.NODE_CLASS),
            INTERFACE("d_language_class", "interface", DlangIcons.NODE_INTERFACE),
            STRUCT("d_language_class", "struct", DlangIcons.NODE_STRUCT),
            UNION("d_language_union", "union", DlangIcons.NODE_UNION),
            ENUM("d_language_enum", "enum", DlangIcons.NODE_ENUM)
        }
    }
}
