package ddt.dtool.ast.definitions;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.VarSemantics;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.ast.expressions.Resolvable;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.parser.common.LexElement;

public class FunctionParameter extends DefUnit implements IFunctionParameter, IConcreteNamedElement {
	
	public final FnParameterAttributes paramAttribs;
	public final Reference type;
	public final Expression defaultValue;
	public final boolean isVariadic;
	
	public FunctionParameter(ArrayView<LexElement> attribList, Reference type, ProtoDefSymbol defId, 
		Expression defaultValue, boolean isVariadic) {
		super(defId);
		this.paramAttribs = FnParameterAttributes.create(attribList);
		this.type = parentize(assertNotNull(type));
		this.defaultValue = parentize(defaultValue);
		assertTrue(!isVariadic || defaultValue == null);
		this.isVariadic = isVariadic;
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.FUNCTION_PARAMETER;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, type);
		acceptVisitor(visitor, defname);
		acceptVisitor(visitor, defaultValue);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		paramAttribs.toStringAsCode(cp);
		cp.append(type, " ");
		cp.append(defname);
		cp.append(" = ", defaultValue);
		cp.append(isVariadic, "...");
	}
	
	@Override
	public boolean isVariadic() {
		return isVariadic;
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Variable;
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
		return getStringRepresentation(type, getName(), isVariadic);
	}
	
	public static String getStringRepresentation(Reference type, String name, boolean isVariadic) {
		String nameStr = name == null ? "": " " + name;
		return type.toStringAsCode() + nameStr + (isVariadic ? "..." : "");
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new VarSemantics(this, pickedElement) {
			@Override
			protected Resolvable getTypeReference() {
				return type;
			}
		};
	}
	
}