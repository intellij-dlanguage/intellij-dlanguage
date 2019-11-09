package io.github.intellij.dlanguage

import com.intellij.codeInsight.daemon.ProjectSdkSetupValidator
import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.Lexer
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.IndexNotReadyException
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.roots.ui.configuration.ProjectSettingsService
import com.intellij.openapi.util.Computable
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.search.GlobalSearchScope.*
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.tree.TokenSet.create
import io.github.intellij.dlanguage.dlanguage.DlangLexer
import io.github.intellij.dlanguage.index.DModuleIndex.getFilesByModuleName
import io.github.intellij.dlanguage.parser.ParserWrapper
import io.github.intellij.dlanguage.psi.DlangFile
import io.github.intellij.dlanguage.psi.DlangTypes
import io.github.intellij.dlanguage.resolve.processors.basic.BasicResolve
import io.github.intellij.dlanguage.stubs.types.DFileStubElementType
import org.jetbrains.annotations.NotNull

object DLanguage : Language("D")

class DLanguageLexerAdapter : FlexAdapter(DlangLexer())

/**
 * This class is used to inform users that DMD is not setup for the project
 */
class DLangProjectDmdSetupValidator : ProjectSdkSetupValidator {

    private val log = Logger.getInstance(javaClass);
    private val expectedModules = setOf("std.array", "std.ascii", "std.base64", "std.bigint", "std.bitmanip", "std.compiler", "std.complex", "std.concurrency", "std.conv", "std.csv", "std.demangle", "std.encoding", "std.exception", "std.file", "std.format", "std.functional", "std.getopt", "std.json", "std.math", "std.mathspecial", "std.meta", "std.mmfile", "std.numeric", "std.outbuffer", "std.parallelism", "std.path", "std.process", "std.random", "std.signals", "std.socket", "std.stdint", "std.stdio", "std.string", "std.system", "std.traits", "std.typecons", "std.uni", "std.uri", "std.utf", "std.uuid", "std.variant", "std.xml", "std.zip", "std.zlib")

    override fun isApplicableFor(project: Project, file: VirtualFile): Boolean {
        if (DlangFileType.INSTANCE != file.fileType) return false
        val projectSdk = ProjectRootManager.getInstance(project).projectSdk ?: return true
        val sdkType = projectSdk.sdkType as? DlangSdkType ?: return true

        if (!sdkType.isValidSdkHome(projectSdk.homePath)) {
            return true
        }

        // Don't check for object.d or phobos unless the index is ready
        if(!DumbService.isDumb(project)) {
            try {
                if (cannotFindObjectDotD(project)) {
                    return true
                }
                if (missingAnyPhobosFiles(project)) {
                    return true
                }
            } catch (e: IndexNotReadyException) {
                log.warn("Could not check for object.d and phobos as the project index is not ready", e)
            }
        }

        return false
    }

    private fun missingAnyPhobosFiles(project: Project): Boolean =
        !missingPhobosModules(project).isEmpty()

    private fun missingPhobosModules(project: Project): List<String> {
        val resolveFilesComputable = Computable({
            expectedModules.filter {
                getFilesByModuleName(project, it, allScope(project)).isEmpty()
            }
        }
        )
        return DumbService.getInstance(project).runReadActionInSmartMode(resolveFilesComputable)
    }

    private fun cannotFindObjectDotD(project: Project): Boolean {
        val resolveObjectDotD: Computable<Boolean> = Computable({
            BasicResolve.getInstance(project, profile = false).`object` == null
        })
        return DumbService.getInstance(project).runReadActionInSmartMode(resolveObjectDotD)
    }

    override fun doFix(project: Project, file: VirtualFile) =
        Unit.apply { ProjectSettingsService.getInstance(project).openProjectSettings() }

    override fun getErrorMessage(project: Project, file: VirtualFile): String? {
        try {
            if (cannotFindObjectDotD(project)) {
                return DlangBundle.message("d.ui.dmd.object.path.not.set")
            }
            if (missingAnyPhobosFiles(project)) {
                return DlangBundle.message("d.ui.dmd.phobos.path.not.set") + missingPhobosModules(project).foldRight("", { s: String, acc: String -> acc + " " + s })
            }
        } catch (e: IndexNotReadyException) {
            //todo: this try/catch block is a bit of a kludge, at some point we should roll our own EditorNotifications.Provider<EditorNotificationPanel>()
            // https://github.com/intellij-dlanguage/intellij-dlanguage/issues/402
            // https://intellij-support.jetbrains.com/hc/en-us/community/posts/360000094950
        }

        return DlangBundle.message("d.ui.dmd.path.not.set")
    }
}

class DLangParserDefinition : ParserDefinition {
    val WHITE_SPACES: TokenSet = create(TokenType.WHITE_SPACE)
    val COMMENTS: TokenSet = create(DlangTypes.LINE_COMMENT, DlangTypes.BLOCK_COMMENT, DlangTypes.NESTING_BLOCK_COMMENT)

    @NotNull
    override fun createParser(project: Project?): PsiParser = ParserWrapper()

    override fun createFile(viewProvider: FileViewProvider): PsiFile? = DlangFile(viewProvider)

    override fun spaceExistanceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): ParserDefinition.SpaceRequirements? = ParserDefinition.SpaceRequirements.MAY

    @NotNull
    override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

    override fun getFileNodeType(): DFileStubElementType = DFileStubElementType.INSTANCE

    @NotNull
    override fun getWhitespaceTokens(): TokenSet = WHITE_SPACES

    @NotNull
    override fun createLexer(project: Project?): Lexer = FlexAdapter(DlangLexer(null))

    @NotNull
    override fun createElement(node: ASTNode?): PsiElement = DlangTypes.Factory.createElement(node)

    @NotNull
    override fun getCommentTokens(): TokenSet = COMMENTS
}

