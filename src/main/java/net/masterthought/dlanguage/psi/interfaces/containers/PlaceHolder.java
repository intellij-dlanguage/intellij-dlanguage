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
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by francis on 3/17/2017.
 */
public interface PlaceHolder extends DNamedElement {
    @Nullable
    @Override
    default PsiElement getNameIdentifier() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Nullable
    @Override
    default String getName() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default PsiElement setName(@NonNls @NotNull String name) throws IncorrectOperationException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Nullable
    @Override
    default ItemPresentation getPresentation() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default void navigate(boolean requestFocus) {

    }

    @Override
    default boolean canNavigate() {
        return false;
    }

    @Override
    default boolean canNavigateToSource() {
        return false;
    }

    @NotNull
    @Override
    default Project getProject() throws PsiInvalidElementAccessException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @NotNull
    @Override
    default Language getLanguage() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default PsiManager getManager() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @NotNull
    @Override
    default PsiElement[] getChildren() {
        return new PsiElement[0];
    }

    @Override
    default PsiElement getParent() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default PsiElement getFirstChild() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default PsiElement getLastChild() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default PsiElement getNextSibling() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default PsiElement getPrevSibling() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default PsiFile getContainingFile() throws PsiInvalidElementAccessException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default TextRange getTextRange() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default int getStartOffsetInParent() {
        return 0;
    }

    @Override
    default int getTextLength() {
        return 0;
    }

    @Nullable
    @Override
    default PsiElement findElementAt(int offset) {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Nullable
    @Override
    default PsiReference findReferenceAt(int offset) {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default int getTextOffset() {
        return 0;
    }

    @Override
    default String getText() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @NotNull
    @Override
    default char[] textToCharArray() {
        return new char[0];
    }

    @Override
    default PsiElement getNavigationElement() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default PsiElement getOriginalElement() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default boolean textMatches(@NotNull @NonNls CharSequence text) {
        return false;
    }

    @Override
    default boolean textMatches(@NotNull PsiElement element) {
        return false;
    }

    @Override
    default boolean textContains(char c) {
        return false;
    }

    @Override
    default void accept(@NotNull PsiElementVisitor visitor) {

    }

    @Override
    default void acceptChildren(@NotNull PsiElementVisitor visitor) {

    }

    @Override
    default PsiElement copy() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default PsiElement add(@NotNull PsiElement element) throws IncorrectOperationException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default PsiElement addBefore(@NotNull PsiElement element, @Nullable PsiElement anchor) throws IncorrectOperationException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default PsiElement addAfter(@NotNull PsiElement element, @Nullable PsiElement anchor) throws IncorrectOperationException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default void checkAdd(@NotNull PsiElement element) throws IncorrectOperationException {

    }

    @Override
    default PsiElement addRange(PsiElement first, PsiElement last) throws IncorrectOperationException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default PsiElement addRangeBefore(@NotNull PsiElement first, @NotNull PsiElement last, PsiElement anchor) throws IncorrectOperationException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default PsiElement addRangeAfter(PsiElement first, PsiElement last, PsiElement anchor) throws IncorrectOperationException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default void delete() throws IncorrectOperationException {

    }

    @Override
    default void checkDelete() throws IncorrectOperationException {

    }

    @Override
    default void deleteChildRange(PsiElement first, PsiElement last) throws IncorrectOperationException {

    }

    @Override
    default PsiElement replace(@NotNull PsiElement newElement) throws IncorrectOperationException {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default boolean isValid() {
        return false;
    }

    @Override
    default boolean isWritable() {
        return false;
    }

    @Nullable
    @Override
    default PsiReference getReference() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @NotNull
    @Override
    default PsiReference[] getReferences() {
        return new PsiReference[0];
    }

    @Nullable
    @Override
    default <T> T getCopyableUserData(Key<T> key) {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default <T> void putCopyableUserData(Key<T> key, @Nullable T value) {

    }

    @Override
    default boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, @Nullable PsiElement lastParent, @NotNull PsiElement place) {
        return false;
    }

    @Nullable
    @Override
    default PsiElement getContext() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default boolean isPhysical() {
        return false;
    }

    @NotNull
    @Override
    default GlobalSearchScope getResolveScope() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @NotNull
    @Override
    default SearchScope getUseScope() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default ASTNode getNode() {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default boolean isEquivalentTo(PsiElement another) {
        return false;
    }

    @Override
    default Icon getIcon(@IconFlags int flags) {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Nullable
    @Override
    default <T> T getUserData(@NotNull Key<T> key) {
        throw new IllegalStateException("you should never call this method on this class. This class is used as a placeholder for methods that will be added to declarations later");
    }

    @Override
    default <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {

    }

}
