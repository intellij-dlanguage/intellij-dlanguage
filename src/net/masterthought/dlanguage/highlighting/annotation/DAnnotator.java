package net.masterthought.dlanguage.highlighting.annotation;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.highlighting.DHighlighter;
import net.masterthought.dlanguage.psi.DVisitor;
import net.masterthought.dlanguage.psi.interfaces.DDeclarationModule;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionFunction;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionVariable;
import net.masterthought.dlanguage.psi.interfaces.DRefModule;
import org.jetbrains.annotations.NotNull;

/**
 * Annotator that:
 * <p/>
 * 1) brushes up syntax highlighting issues from parsing a broken program.
 * 2) Registers quickfixes on broken nodes.
 */
public class DAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull final AnnotationHolder holder) {
        element.accept(new DVisitor() {
            @Override
            public void visitDDeclarationModule(@NotNull DDeclarationModule o) {
                super.visitDDeclarationModule(o);
                setHighlighting(o.getSymbol(), holder, DHighlighter.REF_MODULE);
            }

            @Override
            public void visitDRefModule(@NotNull DRefModule o) {
                super.visitDRefModule(o);
                setHighlighting(o, holder, DHighlighter.REF_MODULE);
            }

            @Override
            public void visitDDefinitionVariable(@NotNull DDefinitionVariable o) {
                super.visitDDefinitionVariable(o);
                setHighlighting(o.getSymbol(), holder, DHighlighter.VARIABLE);
            }

            @Override
            public void visitDDefinitionFunction(@NotNull DDefinitionFunction o) {
                super.visitDDefinitionFunction(o);
                setHighlighting(o.getSymbol(), holder, DHighlighter.FUNCTION_DEFINITION);
            }

//
//            @Override
//            public void visitQcon(@NotNull HaskellQcon o) {
//                super.visitQcon(o);
//                // Highlight the () unit type as a CONID if it's not in an import, e.g. `import Foo ()`.
//                if (o.getText().equals("()")) {
//                    final PsiElement prev1 = o.getPrevSibling();
//                    final PsiElement prev2 = prev1 == null ? null : prev1.getPrevSibling();
//                    final boolean inImport = prev1 instanceof HaskellImpdecl || prev2 instanceof HaskellImpdecl;
//                    if (!inImport) {
//                        setHighlighting(o, holder, HaskellSyntaxHighlighter.CONID);
//                    }
//                }
//            }
//
//            @Override
//            public void visitAtype(@NotNull HaskellAtype o) {
//                super.visitAtype(o);
//                // Highlight the () unit type as a CONID.
//                if (o.getText().equals("()")) {
//                    setHighlighting(o, holder, HaskellSyntaxHighlighter.CONID);
//                }
//            }
//
//            @Override
//            public void visitPstringtoken(@NotNull HaskellPstringtoken o) {
//                super.visitPstringtoken(o);
//                setHighlighting(o, holder, HaskellSyntaxHighlighter.STRING);
//            }
//
//            @Override
//            public void visitShebang(@NotNull HaskellShebang o) {
//                super.visitShebang(o);
//                setHighlighting(o, holder, HaskellSyntaxHighlighter.COMMENT);
//            }
//
//            @Override
//            public void visitModuledecl(@NotNull HaskellModuledecl o) {
//                super.visitModuledecl(o);
//                final HaskellQconid qc = o.getQconid();
//                if (qc == null) {
//                    return;
//                }
//                final List<HaskellConid> conidList = qc.getConidList();
//                final HaskellConid lastConid = conidList.get(conidList.size() - 1);
//                String moduleName = lastConid.getText();
//                String fullFileName = o.getContainingFile().getName();
//                //noinspection ConstantConditions
//                if (fullFileName == null) {
//                    return;
//                }
//                String fileSuffix = fullFileName.substring(fullFileName.lastIndexOf('.'));
//                String fileName = fullFileName.substring(0, fullFileName.length() - fileSuffix.length());
//                if (!moduleName.equals(fileName) && !"Main".equals(moduleName)) {
//                    HaskellModuleFilenameFix fixFile = new HaskellModuleFilenameFix(moduleName + fileSuffix);
//                    HaskellModuleNameFix fixName = new HaskellModuleNameFix(lastConid, fileName);
//                    holder.createErrorAnnotation(qc, MSG).registerFix(fixFile);
//                    holder.createErrorAnnotation(qc, MSG).registerFix(fixName);
//                }
//            }
        });
    }

    private static void setHighlighting(@NotNull PsiElement element, @NotNull AnnotationHolder holder,
                                        @NotNull TextAttributesKey key) {
        holder.createInfoAnnotation(element, null).setEnforcedTextAttributes(
                EditorColorsManager.getInstance().getGlobalScheme().getAttributes(key));
    }
}

