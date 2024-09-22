package io.github.intellij.dlanguage.psi.interfaces;

import io.github.intellij.dlanguage.psi.types.UserDefinedDType;
import org.jetbrains.annotations.NotNull;

/**
 * Represent a new <a href="https://dlang.org/spec/type.html#user-defined-types">Type defined by user</a>.
 */
public interface UserDefinedType extends DNamedElement, DTypedElement {

    @NotNull
    @Override
    default UserDefinedDType getDType() {
        var name = getName();
        if (name == null) {
            name = "";
        }
        return new UserDefinedDType(name, this);
    }
}
