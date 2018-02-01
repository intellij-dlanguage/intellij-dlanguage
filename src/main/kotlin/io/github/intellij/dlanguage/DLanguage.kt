package io.github.intellij.dlanguage

import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.Lexer
import com.intellij.notification.Notification
import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationType
import com.intellij.notification.NotificationsConfiguration
import com.intellij.openapi.Disposable
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectBundle
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil
import com.intellij.openapi.util.Ref
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.DirectoryProjectConfigurator
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.tree.TokenSet.create
import com.intellij.util.ui.update.MergingUpdateQueue
import io.github.intellij.dlanguage.dlanguage.DlangLexer
import io.github.intellij.dlanguage.parser.ParserWrapper
import io.github.intellij.dlanguage.psi.DlangFile
import io.github.intellij.dlanguage.psi.DlangTypes
import io.github.intellij.dlanguage.stubs.types.DFileStubElementType
import org.jetbrains.annotations.NotNull

object DLanguage : Language("D")

class DLanguageLexerAdapter : FlexAdapter(DlangLexer())

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

class DubImportNotifier(val project: Project) : Disposable {

    private var notification: Notification? = null
    private val queue: MergingUpdateQueue

    init {
        NotificationsConfiguration
            .getNotificationsConfiguration()
            .register("Dub Import", NotificationDisplayType.STICKY_BALLOON, true)

        queue = MergingUpdateQueue("DubImportNotifier", 500, false, MergingUpdateQueue.ANY_COMPONENT, project)
        queue.activate()

        notification = Notification("Dub Import", ProjectBundle.message("dub.project.changed"), "", NotificationType.INFORMATION);
    }


    override fun dispose() {
        notification?.expire()
    }
}

class DlangSdkConfigurator : DirectoryProjectConfigurator {

    val log: Logger = Logger.getInstance(javaClass)

    override fun configureProject(project: Project?, baseDir: VirtualFile, moduleRef: Ref<Module>?) {
        log.info("word up")
        val dlangSdkType = DlangSdkType.getInstance()
        val sdkComparator = Comparator<Sdk>( { sdk1, sdk2 -> if (sdk1.sdkType === dlangSdkType) {
            -1
        } else if (sdk2.sdkType === dlangSdkType) {
            1
        } else {
            0
        } })

        SdkConfigurationUtil.configureDirectoryProjectSdk(project, sdkComparator, dlangSdkType)
    }
}

class DlangSourceRootConfigurator : DirectoryProjectConfigurator {

    val log: Logger = Logger.getInstance(javaClass)

    override fun configureProject(project: Project?, baseDir: VirtualFile, moduleRef: Ref<Module>?) {
        log.info("word up")
    }
}
