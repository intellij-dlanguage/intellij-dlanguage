package net.masterthought.dlanguage.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import net.masterthought.dlanguage.icons.DLanguageIcons;
import net.masterthought.dlanguage.psi.references.DReference;
import net.masterthought.dlanguage.resolve.DResolveUtil;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;

import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import net.masterthought.dlanguage.stubs.DLanguageIdentifierStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.stubs.IStubElementType;

import javax.swing.*;
import java.util.List;

public class DLanguageIdentifierImpl extends DNamedStubbedPsiElementBase<DLanguageIdentifierStub> implements DLanguageIdentifier {

  public DLanguageIdentifierImpl(DLanguageIdentifierStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageIdentifierImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitIdentifier(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @NotNull
  public String getName() {
      DLanguageIdentifierStub stub = this.getStub();
      if (stub != null) return StringUtil.notNullize(stub.getName());
      return getText();
  }

  @Nullable
  public PsiElement getNameIdentifier() {
      ASTNode keyNode = getNode();
      return keyNode != null ? keyNode.getPsi() : null;
  }

  @NotNull
  public PsiReference getReference() {
      return new DReference(this, TextRange.from(0, DPsiImplUtil.getName(this).length()));
  }

  @NotNull
  public PsiElement setName(String newName) {
    return DPsiImplUtil.setName(this, newName);
  }

  @NotNull
  public ItemPresentation getPresentation() {
      return new ItemPresentation() {
          @NotNull
          @Override
          public String getPresentableText() {
              //todo keep this up to date
              PsiNamedElement funcDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageFunctionDeclaration.class);
              PsiNamedElement classDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageClassDeclaration.class);
              String description = "";
              if (funcDecl != null) {
                  description = " [Function] (" + funcDecl.getName() + ")";
              }
              if (classDecl != null) {
                  description = " [Class] (" + classDecl.getName() + ")";
              }
              return getName() + description;
          }

          /**
           * This is needed to decipher between files when resolving multiple references.
           */
          @Nullable
          @Override
          public String getLocationString() {
              final PsiFile psiFile = getContainingFile();
              return psiFile instanceof DLanguageFile ? ((DLanguageFile) psiFile).getModuleOrFileName() : null;
          }

          @Nullable
          @Override
          public Icon getIcon(boolean unused) {
              return DLanguageIcons.FILE;
          }
      };
  }

  public void delete() {
      final List<PsiNamedElement> definitionNode = DResolveUtil.INSTANCE.findDefinitionNode(getProject(), this);
      if (definitionNode.size() != 1)
          throw new IllegalStateException();
      definitionNode.get(0).delete();
  }

    @Nullable
    @Override
    public PsiElement getID() {
        return findChildByType(ID);
    }
}
