package ddt.dtool.ast.expressions;

import java.util.Collection;
import java.util.Collections;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.ResolvableSemantics.ExpSemantics;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.dtool.ast.definitions.DefinitionClass;

public class ExpThis extends Expression {
	
	public ExpThis() {
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.EXP_THIS;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("this");
	}
	
	public static DefinitionClass getClassNodeParent(ASTNode node) {
		do {
			node = node.getParent();
			if(node instanceof DefinitionClass) {
				DefinitionClass definitionClass = (DefinitionClass) node;
				return definitionClass;
			}
		} while(node != null);
		return null;
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected ExpSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new ExpSemantics(this, pickedElement) {
		
			@Override
			public Collection<INamedElement> findTargetDefElements(boolean findOneOnly) {
				DefinitionClass definitionClass = getClassNodeParent(ExpThis.this);
				if(definitionClass == null) {
					return null;
				}
				return Collections.<INamedElement>singleton(definitionClass);
			}
			
		};
	}
	
}