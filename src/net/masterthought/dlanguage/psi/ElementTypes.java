package net.masterthought.dlanguage.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import net.masterthought.dlanguage.lexer.ElementImpl;
import net.masterthought.dlanguage.lexer.PropertyImpl;
import net.masterthought.dlanguage.parser.DLanguageParserDefinition;
import net.masterthought.dlanguage.psi.impl.DModuleDeclarationImpl;
import org.w3c.dom.Node;

public interface ElementTypes {

    class Factory {
        public static PsiElement createElement(ASTNode node) {
            IElementType type = node.getElementType();


            if (type == DLanguageParserDefinition.FILE_ELEMENT_TYPE) {
                return new DModuleDeclarationImpl(node);
            } else {
                return new PropertyImpl(node);
            }
//
//            if (type == DLanguageParserDefinition.FILE_ELEMENT_TYPE) {
//                return new PropertyImpl(node);
//            //} else if (type == UNKNOWN) {
//            } else {
//                return new PropertyImpl(node);
//            }

            //return null;
        }

        public static IElementType createElement(Node node) {
           /*if (node.getNodeName().equals("module")) {
                return ParserDefinitionImpl.FILE;
           } else {
                return UNKNOWN;
           }*/

            return new ElementImpl(node);
        }
    }
}
