package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageBasicType;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.psi.interfaces.Expression;
import io.github.intellij.dlanguage.psi.interfaces.Foreach;
import io.github.intellij.dlanguage.psi.named.DLanguageClassDeclaration;
import io.github.intellij.dlanguage.psi.named.DLanguageForeachType;
import io.github.intellij.dlanguage.psi.named.DLanguageInterfaceDeclaration;
import io.github.intellij.dlanguage.psi.named.DLanguageStructDeclaration;
import io.github.intellij.dlanguage.psi.types.*;
import io.github.intellij.dlanguage.stubs.DLanguageForeachTypeStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
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

        // Search in foreach statement parent
        var foreachParent = this.getParent().getParent(); // 1st parent is type list

        if (foreachParent instanceof Foreach foreach) {
            if (foreach.getExpressions().isEmpty()) {
                return new DUnknownType();
            }

            // get the type of the expression
            DType expressionType;
            if (foreach.getExpressions().size() > 1) {
                // Specs for foreach https://dlang.org/spec/statement.html#foreach-statement
                // foreach range statement so 2 expressions
                // TODO need to determine the type from the range statement (common type of type1 and type2)
                expressionType = foreach.getExpressions().get(1).getDType();
            } else {
                expressionType = foreach.getExpressions().getFirst().getDType();
            }
            if (expressionType == null || expressionType instanceof DUnknownType) {
                return new DUnknownType();
            }

            // Now get the index of the index of the element in the list (is it the 1st, 2nd, nth variable?)
            var list = Objects.requireNonNull(foreach.getForeachTypeList()).getForeachTypes();
            var elementIdx = -1;
            for (int idx = 0; idx < list.size(); idx++) {
                if (list.get(idx) == this) {
                    elementIdx = idx;
                    break;
                }
            }
            if (elementIdx == -1) {
                // Should be impossible by construction
                return new DUnknownType();
            }
            return getTypeFromExpressionType(expressionType, elementIdx, list);
        }

        // Should be impossible
        return new DUnknownType();
    }

    private DType getTypeFromExpressionType(DType expressionType, int elementIdx, List<DLanguageForeachType> foreachTypes) {
        assert (elementIdx >= 0 && elementIdx < foreachTypes.size()) : "Element should be part of the typeList";
        switch (expressionType) {
            case DArrayType arrayExpressionType -> {
                if (foreachTypes.size() == 1) {
                    return arrayExpressionType.getBase();
                }
                if (foreachTypes.size() == 2) {
                    if (elementIdx == 0)
                        return DPrimitiveType.fromText("int"); // TODO size_t
                    else
                        return arrayExpressionType.getBase();
                }
                return new DUnknownType(); // semantic error, this variable should not exist
            }
            case DAssociativeArrayType associativeArrayType -> {
                if (foreachTypes.size() == 1) {
                    // we only unroll values
                    return associativeArrayType.getValueType();
                }
                if (foreachTypes.size() == 2) {
                    // we unroll keys and values
                    if (elementIdx == 0)
                        return associativeArrayType.getKeyType();
                    else
                        return associativeArrayType.getValueType();
                }
                return new DUnknownType(); // semantic error, this variable should not exist
            }
            case UserDefinedDType userDefinedDType -> {
                var resolved = userDefinedDType.resolve();
                if (resolved instanceof DLanguageStructDeclaration || resolved instanceof DLanguageClassDeclaration || resolved instanceof DLanguageInterfaceDeclaration) {
                    // TODO opApply or opApplyReverse, case need to find the proper method and return its return type
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
