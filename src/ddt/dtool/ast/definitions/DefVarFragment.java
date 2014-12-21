package ddt.dtool.ast.definitions;

import static ddt.dtool.util.NewUtils.assertCast;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.dtool.ast.expressions.IInitializer;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.engine.analysis.CommonDefVarSemantics;
import ddt.dtool.engine.analysis.IVarDefinitionLike;

/**
 * A fragment of a variable definition in a multi-identifier variable declaration
 */
public class DefVarFragment extends DefUnit implements IVarDefinitionLike {
	
	public final IInitializer initializer;
	
	public DefVarFragment(ProtoDefSymbol defId, IInitializer initializer) {
		super(defId);
		this.initializer = parentize(initializer);
	}
	
	@Override
	public DefinitionVariable getParent_Concrete() {
		return assertCast(parent, DefinitionVariable.class);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DEFINITION_VAR_FRAGMENT;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, defname);
		acceptVisitor(visitor, initializer);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(defname);
		cp.append("= ", initializer);
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Variable;
	}
	
	@Override
	public Reference getDeclaredType() {
		return getParent_Concrete().type;
	}
	
	@Override
	public IInitializer getDeclaredInitializer() {
		return initializer;
	}
	
	/* ----------------- ----------------- */
	
	@Override
	protected CommonDefVarSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new CommonDefVarSemantics(this, pickedElement);
	}
	
}