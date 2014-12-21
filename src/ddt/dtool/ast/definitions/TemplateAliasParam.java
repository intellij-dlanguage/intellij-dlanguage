package ddt.dtool.ast.definitions;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.ErrorElement;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.TODO_NamedElementSemantics;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.dtool.ast.expressions.Resolvable;
import ddt.dtool.engine.analysis.templates.AliasElement;

public class TemplateAliasParam extends DefUnit implements ITemplateParameter {
	
	public final Resolvable specializationValue;
	public final Resolvable defaultValue;
	
	public TemplateAliasParam(ProtoDefSymbol defId, Resolvable specializationValue, Resolvable defaultValue){
		super(defId);
		this.specializationValue = parentize(specializationValue);
		this.defaultValue = parentize(defaultValue);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.TEMPLATE_ALIAS_PARAM;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, defname);
		acceptVisitor(visitor, specializationValue);
		acceptVisitor(visitor, defaultValue);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("alias ");
		cp.append(defname);
		cp.append(" : ", specializationValue);
		cp.append(" = ", defaultValue);
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Alias;
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		 // Need template instance
		return new TODO_NamedElementSemantics(this, pickedElement) {
			@Override
			protected IConcreteNamedElement doResolveConcreteElement() {
				return ErrorElement.newNotFoundError(element, null);
			}
		};
	}
	
	@Override
	public AliasElement createTemplateArgument(Resolvable argument) {
		return new AliasElement(defname, argument);
	}
	
}