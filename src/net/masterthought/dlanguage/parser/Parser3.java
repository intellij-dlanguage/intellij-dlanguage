package net.masterthought.dlanguage.parser;

import com.google.common.collect.Lists;
import com.intellij.lang.*;
import com.intellij.openapi.util.Getter;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.PsiManagerEx;
import com.intellij.psi.impl.source.CharTableImpl;
import com.intellij.psi.impl.source.PsiFileImpl;
import com.intellij.psi.impl.source.tree.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.ILightStubFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.CharTable;
import ddt.dtool.parser.DeeParser;
import ddt.melnorme.lang.tooling.ast.SourceRange;
import net.masterthought.dlanguage.DLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Parser3 implements PsiParser {
    @NotNull
    @Override
    public ASTNode parse(IElementType root, PsiBuilder builder) {

        builder.setDebugMode(true);
               PsiBuilder.Marker rootMarker = builder.mark();


//        while (!builder.eof()) {
//
//
//                    builder.advanceLexer();
//                }
        builder.advanceLexer();
               rootMarker.done(root);
//               ASTNode ret = builder.getTreeBuilt();

//        DeeParser parser = new DeeParser(builder.getOriginalText().toString());
//        parser.parseModuleDeclaration().asNode();

        String basePath = builder.getProject().getBasePath();
        ddt.melnorme.lang.tooling.ast_actual.ASTNode n = DeeParser.parseSource(builder.getOriginalText().toString(), Paths.get(basePath)).node;
//
//        String elementType = n.getNodeType().name();
//        String elementText = n.toStringAsCode();
//        int startOffSet = n.getStartPos();
//        int endOffSet = n.getEndPos();
//               SourceRange range = n.getSourceRange();

//        NodeWrapper wrapper = new NodeWrapper();


//        new MyNode();


//        MyNode wrappedNode = wrapper.process(n);

//        FileElement ret =  new FileElement(new IElementType(wrappedNode.elementText,DLanguage.INSTANCE));
//        return ret;

//        FileElement el = new MyFileElement(new IElementType(wrappedNode.elementText,DLanguage.INSTANCE));

//        ASTNode bob = builder.getTreeBuilt();
//        bob.addChild(new MyComposite(wrappedNode.getElementType()));


//        return bob;
        return null;
    }
}


//class NodeWrapper {
//    public MyNode process(ddt.melnorme.lang.tooling.ast_actual.ASTNode dnode) {
//
//
//        String elementType = dnode.getNodeType().name();
//        String elementText = dnode.toStringAsCode();
//        int startOffSet = dnode.getStartPos();
//        int endOffSet = dnode.getEndPos();
//        List<MyNode> children = Lists.newArrayList();
//
//        for(ddt.melnorme.lang.tooling.ast_actual.ASTNode an : dnode.getChildren()){
//            children.add(process(an));
//        }
//
//        MyNode node = new MyNode(elementType, elementText, startOffSet, endOffSet, children);
//
//        return node;
//    }
//}
//
//class MyComposite extends CompositeElement {
//
//    public MyComposite(@NotNull IElementType type) {
//        super(type);
//    }
//}
//
//
//class MyNode implements ASTNode {
//
//    String elementType;
//    String elementText;
//    int startOffSet;
//    int endOffSet;
//    List<MyNode> children;
//
//    public MyNode(String elementType, String elementText, int startOffSet, int endOffSet, List<MyNode> children) {
//      this.elementType = elementType;
//        this.elementText = elementText;
//        this.startOffSet = startOffSet;
//        this.endOffSet = endOffSet;
//        this.children = children;
//    }
//
//    @Override
//    public IElementType getElementType() {
//        return new IElementType(elementType, DLanguage.INSTANCE);
//    }
//
//    @Override
//    public String getText() {
//        return elementText;
//    }
//
//    @Override
//    public CharSequence getChars() {
//        return elementText;
//    }
//
//    @Override
//    public boolean textContains(char c) {
//        return elementText.contains(String.valueOf(c));
//    }
//
//    @Override
//    public int getStartOffset() {
//        return startOffSet;
//    }
//
//    @Override
//    public int getTextLength() {
//        return 0;
//    }
//
//    @Override
//    public TextRange getTextRange() {
//        return null;
//    }
//
//    @Override
//    public ASTNode getTreeParent() {
//        return null;
//    }
//
//    @Override
//    public ASTNode getFirstChildNode() {
//        return null;
//    }
//
//    @Override
//    public ASTNode getLastChildNode() {
//        return null;
//    }
//
//    @Override
//    public ASTNode getTreeNext() {
//        return null;
//    }
//
//    @Override
//    public ASTNode getTreePrev() {
//        return null;
//    }
//
//    @Override
//    public ASTNode[] getChildren(TokenSet filter) {
//        ASTNode[] array = children.toArray(new ASTNode[children.size()]);
//        return array;
//    }
//
//    @Override
//    public void addChild(@NotNull ASTNode child) {
//
//    }
//
//    @Override
//    public void addChild(@NotNull ASTNode child, ASTNode anchorBefore) {
//
//    }
//
//    @Override
//    public void addLeaf(@NotNull IElementType leafType, CharSequence leafText, ASTNode anchorBefore) {
//
//    }
//
//    @Override
//    public void removeChild(@NotNull ASTNode child) {
//
//    }
//
//    @Override
//    public void removeRange(@NotNull ASTNode firstNodeToRemove, ASTNode firstNodeToKeep) {
//
//    }
//
//    @Override
//    public void replaceChild(@NotNull ASTNode oldChild, @NotNull ASTNode newChild) {
//
//    }
//
//    @Override
//    public void replaceAllChildrenToChildrenOf(ASTNode anotherParent) {
//
//    }
//
//    @Override
//    public void addChildren(ASTNode firstChild, ASTNode firstChildToNotAdd, ASTNode anchorBefore) {
//
//    }
//
//    @Override
//    public Object clone() {
//        return null;
//    }
//
//    @Override
//    public ASTNode copyElement() {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public ASTNode findLeafElementAt(int offset) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public <T> T getCopyableUserData(Key<T> key) {
//        return null;
//    }
//
//    @Override
//    public <T> void putCopyableUserData(Key<T> key, T value) {
//
//    }
//
//    @Nullable
//    @Override
//    public ASTNode findChildByType(IElementType type) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public ASTNode findChildByType(IElementType type, @Nullable ASTNode anchor) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public ASTNode findChildByType(@NotNull TokenSet typesSet) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public ASTNode findChildByType(@NotNull TokenSet typesSet, @Nullable ASTNode anchor) {
//        return null;
//    }
//
//    @Override
//    public PsiElement getPsi() {
//        return null;
//    }
//
//    @Override
//    public <T extends PsiElement> T getPsi(@NotNull Class<T> clazz) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public <T> T getUserData(@NotNull Key<T> key) {
//        return null;
//    }
//
//    @Override
//    public <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {
//
//    }
//}



