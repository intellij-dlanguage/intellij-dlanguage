package io.github.intellij.dlanguage.psi.interfaces;

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.Stub;
import com.intellij.psi.stubs.StubElement;
import io.github.intellij.dlanguage.stubs.*;
import io.github.intellij.dlanguage.psi.DLanguageSharedStaticConstructor;
import io.github.intellij.dlanguage.psi.DLanguageSharedStaticDestructor;
import io.github.intellij.dlanguage.stubs.DLanguageUnittestStub;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis on 8/5/2017.
 */
public interface HasMembers<T extends StubElement> extends StubBasedPsiElement<T>, DNamedElement {
    default Set<NamedStubBase> getMembers() {
        final Set<NamedStubBase> res = new HashSet<>();

        final StubElement greenStub = ((StubBasedPsiElementBase<T>) this).getGreenStub();
        if (greenStub == null) {
            Logger.getInstance(this.getClass())
                .warn("Green stub was null for file:" + getContainingFile());
            return Collections.emptySet();
        }
        getMembersImpl(greenStub, res);
        return res;
    }

    default void getMembersImpl(final @NotNull Stub stub, final Set<NamedStubBase> result) {
        for (final Stub childStub : stub.getChildrenStubs()) {
            if (childStub instanceof NamedStubBase) {
                result.add((NamedStubBase) childStub);
            }
            if (childStub instanceof DLanguageUnittestStub || (childStub instanceof DLanguageDestructorStub) || (childStub instanceof DLanguageStaticDestructorStub) || (childStub instanceof DLanguageSharedStaticDestructor) || (childStub instanceof DLanguageConstructorStub) || (childStub instanceof DLanguageStaticConstructorStub) || (childStub instanceof DLanguageSharedStaticConstructor) || (childStub instanceof DLanguageFunctionDeclarationStub)) {
            } else {
                getMembersImpl(childStub, result);
            }
        }
    }
}
