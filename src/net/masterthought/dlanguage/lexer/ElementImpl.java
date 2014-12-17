package net.masterthought.dlanguage.lexer;

import com.intellij.psi.tree.IElementType;
import net.masterthought.dlanguage.DLanguage;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Node;

public class ElementImpl extends IElementType {
    private Node node;

    public ElementImpl() {
        super("UNKNOWN", DLanguage.INSTANCE);
        this.node = null;
    }

    public ElementImpl(@NotNull Node node) {
        super(node.getNodeName(), DLanguage.INSTANCE);
        this.node = node;
    }

    public Node getNode() {
        return node;
    }
}

