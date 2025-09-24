package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageBasicType;
import io.github.intellij.dlanguage.psi.DLanguageForeachStatement;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.psi.interfaces.Expression;
import io.github.intellij.dlanguage.psi.named.DLanguageClassDeclaration;
import io.github.intellij.dlanguage.psi.named.DLanguageForeachType;
import io.github.intellij.dlanguage.psi.named.DLanguageStructDeclaration;
import io.github.intellij.dlanguage.psi.types.*;
import io.github.intellij.dlanguage.stubs.DLanguageForeachTypeStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;

public class DLanguageForeachTypeImpl extends
    DNamedStubbedPsiElementBase<DLanguageForeachTypeStub> implements DLanguageForeachType {

    public DLanguageForeachTypeImpl(final DLanguageForeachTypeStub stub, final IStubElementType type) {
        super(stub, type);
    }

    public DLanguageForeachTypeImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DlangVisitor visitor) {
        visitor.visitDNamedElement(this);
        visitor.visitForeachType(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public PsiElement getIdentifier() {
        return findChildByType(ID);
    }

    @Override
    @Nullable
    public PsiElement getKW_REF() {
        return findChildByType(KW_REF);
    }

    @Override
    @Nullable
    public PsiElement getKW_ALIAS() {
        return findChildByType(KW_ALIAS);
    }

    @Override
    @Nullable
    public PsiElement getKW_ENUM() {
        return findChildByType(KW_ENUM);
    }

    @Override
    @Nullable
    public PsiElement getOP_EQ() {
        return findChildByType(OP_EQ);
    }

    @Override
    @Nullable
    public Expression getExpression() {
        return PsiTreeUtil.getChildOfType(this, Expression.class);
    }

    @Override
    @Nullable
    public DLanguageBasicType getBasicType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageBasicType.class);
    }

    @Nullable
    public PsiElement getNameIdentifier() {
        return getIdentifier();
    }

    @Override
    @NotNull
    public DType getDType() {
        if (getBasicType() != null) {
            return getBasicType().getDType();
        }
        var foreach = PsiTreeUtil.getParentOfType(this, DLanguageForeachStatement.class);
        assert foreach != null;
        if (foreach.getExpression() == null)
            return new DUnknownType();
        var expressionType = foreach.getExpression().getDType();
        if (expressionType == null || expressionType instanceof DUnknownType) {
            return new DUnknownType();
        }
        var elementIdx = -1;
        var list = Objects.requireNonNull(foreach.getForeachTypeList()).getForeachTypes();
        for(int idx = 0; idx < list.size(); idx++) {
            if (list.get(idx) == this) {
                elementIdx = idx;
                break;
            }
        }
        if (elementIdx == -1) {
            return new DUnknownType(); // safety shouldn't happen by construction
        }

        // Specs for foreach https://dlang.org/spec/statement.html#foreach-statement
        if (foreach.getOP_DDOT() != null) {
            // foreach range statement
            // TODO need to determine the type from the range statement (type1 or type2 if possible)
            return new DUnknownType();
        }
        switch (expressionType) {
            case DArrayType arrayExpressionType -> {
                if (elementIdx == 0)
                    return arrayExpressionType.getBase();
                return new DUnknownType(); // semantic error, this variable should not exist
            }
            case DAssociativeArrayType associativeArrayType -> {
                if (elementIdx == 0) {
                    if (list.size() > 1) // we unroll keys and values
                        return associativeArrayType.getKeyType();
                    return associativeArrayType.getValueType(); // we only unroll values
                } else if (elementIdx == 1)
                    return associativeArrayType.getValueType();
                return new DUnknownType(); // semantic error, this variable shouldn't exist
            }
            case UserDefinedDType userDefinedDType -> {
                var resolved = userDefinedDType.resolve();
                if (resolved instanceof DLanguageStructDeclaration || resolved instanceof DLanguageClassDeclaration) {
                    // TODO opApply or opApplyReverse, complex case need to advance semantic to find the proper method
                    // TODO can also be range (popFront or popBack method)
                }
                return new DUnknownType(); // semantic error, we can't foreach over union and enum
            }
            // TODO delegate
            // TODO sequence
            default -> {
                return new DUnknownType();
            }
        }
    }

}
