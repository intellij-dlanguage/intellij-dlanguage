package ddt.dtool.ast.declarations;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast.util.NodeListView;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.ast.references.RefIdentifier;
import ddt.dtool.ast.references.RefTemplateInstance;
import ddt.dtool.ast.references.Reference;

/**
 * Node for User Defined Attributes ( http://dlang.org/attribute.html#uda )
 * Note: the spec/compiler, as of 2.063 only allows an RefIdentifier as base reference 
 * (and it's a reference and not a symbol def, it should refer to another element.
 * 
 */
public class AttribCustom extends Attribute {
	
	public final Reference ref;
	public final NodeListView<Expression> args; // if null, no argument list
	
	public AttribCustom(Reference ref, NodeListView<Expression> args) {
		this.ref = parentize(ref);
		this.args = parentize(args);
		
		assertTrue(ref == null || ref instanceof RefIdentifier || ref instanceof RefTemplateInstance);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.ATTRIB_CUSTOM;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, ref);
		acceptVisitor(visitor, args);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("@");
		cp.append(ref);
		cp.appendNodeList("(", args, ",", ")");
	}
	
}