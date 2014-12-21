package ddt.dtool.ast.definitions;

import static ddt.dtool.ast.definitions.FunctionParameter.getStringRepresentation;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.parser.common.LexElement;


/** 
 * A nameless function parameter, such as in: <br>
 * <code> void func(int, int); </code>
 */
public class NamelessParameter extends ASTNode implements IFunctionParameter {
	
	public final FnParameterAttributes paramAttribs;
	public final Reference type;
	public final Expression defaultValue;
	public final boolean isVariadic;
	
	public NamelessParameter(ArrayView<LexElement> attribList, Reference type, Expression defaultValue, 
		boolean isVariadic) {
		this.paramAttribs = FnParameterAttributes.create(attribList); 
		this.type = parentize(assertNotNull(type));
		this.defaultValue = parentize(defaultValue);
		this.isVariadic = isVariadic;
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.NAMELESS_PARAMETER;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, type);
		acceptVisitor(visitor, defaultValue);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		paramAttribs.toStringAsCode(cp);
		cp.append(type);
		cp.append(" = ", defaultValue);
		cp.append(isVariadic, "...");
	}
	
	@Override
	public boolean isVariadic() {
		return isVariadic;
	}
	
	@Override
	public String getTypeStringRepresentation() {
		return getStringRepresentation(type, null, isVariadic);
	}
	
	@Override
	public String getInitializerStringRepresentation() {
		if(defaultValue == null)
			return null;
		return defaultValue.toStringAsCode();
	}
	
	@Override
	public String toStringForFunctionSignature() {
		return getStringRepresentation(type, null, isVariadic);
	}
	
}