package ddt.dtool.ast.definitions;

import ddt.melnorme.lang.tooling.ast.IASTNode;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.scoping.INonScopedContainer;
import ddt.melnorme.utilbox.misc.IteratorUtil;
import ddt.dtool.ast.declarations.IDeclaration;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.ast.statements.IStatement;

/**
 * Declaration of a template mixin with no name:
 * http://dlang.org/template-mixin.html#TemplateMixinDeclaration
 * (without MixinIdentifier)
 */
public class DeclarationMixin extends ASTNode implements INonScopedContainer, IDeclaration, IStatement {
	
	public final Reference templateInstance;
	
	public DeclarationMixin(Reference templateInstance) {
		this.templateInstance = parentize(templateInstance);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DECLARATION_MIXIN;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, templateInstance);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("mixin ");
		cp.append(templateInstance);
		cp.append(";");
	}
	
	@Override
	public Iterable<? extends IASTNode> getMembersIterable() {
		return IteratorUtil.emptyIterable();
		// TODO: mixin container
		/*
		DefUnit defunit = type.findTargetDefUnit();
		if(defunit == null)
			return IteratorUtil.getEMPTY_ITERATOR();
		return (Iterator) defunit.getMembersScope().getMembersIterator();
		 */
	}
	
}