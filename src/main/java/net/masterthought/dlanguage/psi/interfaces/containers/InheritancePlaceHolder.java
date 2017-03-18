package net.masterthought.dlanguage.psi.interfaces.containers;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.util.IncorrectOperationException;
import net.masterthought.dlanguage.psi.DLanguageIdentifier;
import net.masterthought.dlanguage.psi.interfaces.CanInherit;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

/**
 * Created by francis on 3/17/2017.
 */
public class InheritancePlaceHolder implements DNamedElement, PlaceHolder {
    private CanInherit canInherit;

    public InheritancePlaceHolder(CanInherit canInherit) {

        this.canInherit = canInherit;
    }

    @Nullable
    @Override
    public PsiElement getNameIdentifier() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Nullable
    @Override
    public String getName() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public PsiElement setName(@NonNls @NotNull String name) throws IncorrectOperationException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Nullable
    @Override
    public ItemPresentation getPresentation() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public void navigate(boolean requestFocus) {

    }

    @Override
    public boolean canNavigate() {
        return false;
    }

    @Override
    public boolean canNavigateToSource() {
        return false;
    }

    @NotNull
    @Override
    public Project getProject() throws PsiInvalidElementAccessException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @NotNull
    @Override
    public Language getLanguage() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public PsiManager getManager() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @NotNull
    @Override
    public PsiElement[] getChildren() {
        return new PsiElement[0];
    }

    @Override
    public PsiElement getParent() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public PsiElement getFirstChild() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public PsiElement getLastChild() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public PsiElement getNextSibling() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public PsiElement getPrevSibling() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public PsiFile getContainingFile() throws PsiInvalidElementAccessException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public TextRange getTextRange() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public int getStartOffsetInParent() {
        return 0;
    }

    @Override
    public int getTextLength() {
        return 0;
    }

    @Nullable
    @Override
    public PsiElement findElementAt(int offset) {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Nullable
    @Override
    public PsiReference findReferenceAt(int offset) {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public int getTextOffset() {
        return 0;
    }

    @Override
    public String getText() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @NotNull
    @Override
    public char[] textToCharArray() {
        return new char[0];
    }

    @Override
    public PsiElement getNavigationElement() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public PsiElement getOriginalElement() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public boolean textMatches(@NotNull @NonNls CharSequence text) {
        return false;
    }

    @Override
    public boolean textMatches(@NotNull PsiElement element) {
        return false;
    }

    @Override
    public boolean textContains(char c) {
        return false;
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {

    }

    @Override
    public void acceptChildren(@NotNull PsiElementVisitor visitor) {

    }

    @Override
    public PsiElement copy() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public PsiElement add(@NotNull PsiElement element) throws IncorrectOperationException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public PsiElement addBefore(@NotNull PsiElement element, @Nullable PsiElement anchor) throws IncorrectOperationException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public PsiElement addAfter(@NotNull PsiElement element, @Nullable PsiElement anchor) throws IncorrectOperationException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public void checkAdd(@NotNull PsiElement element) throws IncorrectOperationException {

    }

    @Override
    public PsiElement addRange(PsiElement first, PsiElement last) throws IncorrectOperationException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public PsiElement addRangeBefore(@NotNull PsiElement first, @NotNull PsiElement last, PsiElement anchor) throws IncorrectOperationException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public PsiElement addRangeAfter(PsiElement first, PsiElement last, PsiElement anchor) throws IncorrectOperationException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public void delete() throws IncorrectOperationException {

    }

    @Override
    public void checkDelete() throws IncorrectOperationException {

    }

    @Override
    public void deleteChildRange(PsiElement first, PsiElement last) throws IncorrectOperationException {

    }

    @Override
    public PsiElement replace(@NotNull PsiElement newElement) throws IncorrectOperationException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public boolean isWritable() {
        return false;
    }

    @Nullable
    @Override
    public PsiReference getReference() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @NotNull
    @Override
    public PsiReference[] getReferences() {
        return new PsiReference[0];
    }

    @Nullable
    @Override
    public <T> T getCopyableUserData(Key<T> key) {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public <T> void putCopyableUserData(Key<T> key, @Nullable T value) {

    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, @Nullable PsiElement lastParent, @NotNull PsiElement place) {
        return false;
    }

    @Nullable
    @Override
    public PsiElement getContext() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public boolean isPhysical() {
        return false;
    }

    @NotNull
    @Override
    public GlobalSearchScope getResolveScope() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @NotNull
    @Override
    public SearchScope getUseScope() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public ASTNode getNode() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public boolean isEquivalentTo(PsiElement another) {
        return false;
    }

    @Override
    public Icon getIcon(@IconFlags int flags) {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Nullable
    @Override
    public <T> T getUserData(@NotNull Key<T> key) {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {

    }

    public CanInherit getCanInherit() {
        return canInherit;
    }

    public Map<String, DLanguageIdentifier> getSuperClassNames() {
        return canInherit.getSuperClassNames();
    }
}
