package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import net.masterthought.dlanguage.lexer.PropertyImpl;
import net.masterthought.dlanguage.parser.DLanguageParserDefinition;
import net.masterthought.dlanguage.psi.DElementType;
import net.masterthought.dlanguage.psi.impl.DImportDeclarationImpl;
import net.masterthought.dlanguage.psi.impl.DModuleDeclarationImpl;

public interface DElementTypes2 {

    IElementType MODULE = new DElementType("MODULE");
    IElementType IMPORT = new DElementType("IMPORT");

    class Factory {
        public static PsiElement createElement(ASTNode node) {
            IElementType type = node.getElementType();
            if (type == MODULE) {
                return new DModuleDeclarationImpl(node);
            } else if (type == IMPORT) {
                return new DImportDeclarationImpl(node);
            } else {
                return new DCompositeElementType(node) {
                };
            }

        }
    }
}
