package ddt.dtool.ast.declarations;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.melnorme.utilbox.core.CoreUtil;
import ddt.dtool.ast.definitions.IFunctionParameter;
import ddt.dtool.ast.statements.IFunctionBody;

/**
 * Declaration of special function like elements, like allocator/deallocator:
 * http://dlang.org/class.html#ClassAllocator
 * http://dlang.org/class.html#ClassDeallocator
 */
public class DeclarationAllocatorFunction extends ASTNode implements IDeclaration {
	
	public final boolean isNew;
	public final ArrayView<IFunctionParameter> params;
	public final IFunctionBody fnBody;
	
	public DeclarationAllocatorFunction(boolean isNew, ArrayView<IFunctionParameter> params, IFunctionBody fnBody) {
		this.isNew = isNew;
		this.params = parentize(params);
		this.fnBody = parentize(fnBody);
	}
	
	public final ArrayView<ASTNode> getParams_asNodes() {
		return CoreUtil.blindCast(params);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DECLARATION_ALLOCATOR_FUNCTION;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, params);
		acceptVisitor(visitor, fnBody);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(isNew ? "new" : "delete");
		cp.appendList("(", getParams_asNodes(), ",", ") ");
		cp.append(fnBody);
	}
	
}