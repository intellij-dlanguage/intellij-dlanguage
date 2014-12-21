package ddt.dtool.ast.declarations;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.IASTNode;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.engine.scoping.INonScopedContainer;
import ddt.melnorme.utilbox.collections.ChainedIterable;
import ddt.melnorme.utilbox.misc.IteratorUtil;
import ddt.dtool.ast.declarations.DeclarationAttrib.AttribBodySyntax;
import ddt.dtool.ast.definitions.Symbol;
import ddt.dtool.ast.statements.BlockStatement;
import ddt.dtool.ast.statements.IStatement;

public abstract class AbstractConditionalDeclaration extends ASTNode 
	implements INonScopedContainer, IDeclaration, IStatement 
{
	
	// Note: value can be an integer or keyword
	public static class VersionSymbol extends Symbol {
		public VersionSymbol(String value) {
			super(value);
		}
	}
	
	public final boolean isStatement;
	public final AttribBodySyntax bodySyntax;
	public final ASTNode body; // Note: can be DeclList
	public final ASTNode elseBody;
	
	public AbstractConditionalDeclaration(AttribBodySyntax bodySyntax, ASTNode bodyDecls, ASTNode elseDecls) {
		this.isStatement = false;
		this.bodySyntax = bodySyntax;
		this.body = parentize(bodyDecls);
		this.elseBody = parentize(elseDecls);
	}
	
	public AbstractConditionalDeclaration(IStatement thenBody, IStatement elseBody) {
		this.isStatement = true;
		this.bodySyntax = AttribBodySyntax.SINGLE_DECL;
		this.body = parentize((ASTNode) thenBody);
		this.elseBody = parentize((ASTNode) elseBody);
		assertTrue(!(thenBody instanceof BlockStatement));
		assertTrue(!(elseBody instanceof BlockStatement));
	}
	
	public boolean isStatement() {
		return isStatement;
	}
	
	@Override
	public Iterable<? extends IASTNode> getMembersIterable() {
		if(body == null && elseBody == null)
			return IteratorUtil.<ASTNode>emptyIterable();
		if(elseBody == null)
			return DeclarationAttrib.getBodyIterable(body);
		if(body == null)
			return DeclarationAttrib.getBodyIterable(elseBody);
		
		return ChainedIterable.create(DeclarationAttrib.getBodyIterable(body), 
			DeclarationAttrib.getBodyIterable(elseBody)); 
	}
	
	public void toStringAsCodeBodyAndElseBody(ASTCodePrinter cp) {
		cp.append(bodySyntax == AttribBodySyntax.COLON, " :\n");
		cp.append(body);
		if(elseBody != null) {
			cp.append("else ");
			cp.append(elseBody);
		}
	}
	
}