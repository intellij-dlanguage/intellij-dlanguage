package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.Stub;
import com.intellij.psi.stubs.StubElement;
import net.masterthought.dlanguage.psi.DLanguageFile;
import net.masterthought.dlanguage.psi.DLanguageSharedStaticConstructor;
import net.masterthought.dlanguage.psi.DLanguageSharedStaticDestructor;
import net.masterthought.dlanguage.stubs.*;
import net.masterthought.dlanguage.stubs.interfaces.DLanguageUnittestStub;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by francis on 8/5/2017.
 */
public interface HasMembers<T extends StubElement> extends StubBasedPsiElement<T> {
    default Set<NamedStubBase> getMembers() {
        Set<NamedStubBase> res = new HashSet<>();
        ((DLanguageFile) getContainingFile()).calcStubTree();
        getMembersImpl(((StubBasedPsiElementBase) this).getGreenStub(), res);
        return res;
    }

    default void getMembersImpl(Stub stub, Set<NamedStubBase> result) {
        for (Stub childStub : stub.getChildrenStubs()) {
            if (childStub instanceof NamedStubBase && !(childStub instanceof DLanguageIdentifierStub)) {
                result.add((NamedStubBase) childStub);
            }
            if (childStub instanceof DLanguageUnittestStub || (childStub instanceof DLanguageDestructorStub) || (childStub instanceof DLanguageStaticDestructorStub) || (childStub instanceof DLanguageSharedStaticDestructor) || (childStub instanceof DLanguageConstructorStub) || (childStub instanceof DLanguageStaticConstructorStub) || (childStub instanceof DLanguageSharedStaticConstructor) || (childStub instanceof DLanguageFunctionDeclarationStub) || (childStub instanceof DLanguageIdentifierStub)) {
            } else {
                getMembersImpl(childStub, result);
            }
        }
    }
}
