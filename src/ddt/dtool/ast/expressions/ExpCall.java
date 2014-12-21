package ddt.dtool.ast.expressions;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast.util.NodeListView;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.ResolvableSemantics;
import ddt.melnorme.lang.tooling.engine.scoping.ResolutionLookup;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.dtool.ast.definitions.DefinitionFunction;
import ddt.dtool.ast.definitions.Module;

public class ExpCall extends Expression {
	
	public final Expression callee;
	public final NodeListView<Expression> args;
	
	public ExpCall(Expression callee, NodeListView<Expression> args) {
		this.callee = parentize(callee);
		this.args = parentize(args);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.EXP_CALL;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, callee);
		acceptVisitor(visitor, args);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(callee);
		cp.appendNodeList("(", args, ", " , ")"); 
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected ResolvableSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new ResolvableSemantics(this, pickedElement) {
		
		@Override
		public Collection<INamedElement> findTargetDefElements(boolean findOneOnly) {
			// TODO: should use #resolveTypeOfUnderlyingValue():
			INamedElement calleeElem = callee.resolveTargetElement(context);
			if(calleeElem == null)
				return null;
			
			if (calleeElem instanceof DefinitionFunction) {
				DefinitionFunction defOpCallFunc = (DefinitionFunction) calleeElem;
				INamedElement calleeResult = defOpCallFunc.findReturnTypeTargetDefUnit(context);
				return Collections.singleton(calleeResult);
			}
			
			Module moduleNode = null;
			if(calleeElem instanceof ASTNode) {
				ASTNode astNode = (ASTNode) calleeElem;
				moduleNode = astNode.getModuleNode_();
			}
			if(moduleNode == null) {
				return null;
			}
			
			ResolutionLookup search = new ResolutionLookup("opCall", moduleNode, false, context);
			search.evaluateInMembersScope(calleeElem);
			
			for (Iterator<INamedElement> iter = search.getMatchedElements().iterator(); iter.hasNext();) {
				INamedElement defOpCall = iter.next();
				if (defOpCall instanceof DefinitionFunction) {
					DefinitionFunction defOpCallFunc = (DefinitionFunction) defOpCall;
					INamedElement targetDefUnit = defOpCallFunc.findReturnTypeTargetDefUnit(context);
					return Collections.singleton(targetDefUnit);
				}
			}
			return null;
		}
		
		@Override
		public Collection<INamedElement> resolveTypeOfUnderlyingValue() {
			return findTargetDefElements(true); // TODO
		}
		
	};
	}
	
}