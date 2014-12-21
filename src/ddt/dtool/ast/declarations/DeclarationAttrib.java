package ddt.dtool.ast.declarations;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import ddt.melnorme.lang.tooling.ast.IASTNode;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast.util.NodeList;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.scoping.INonScopedContainer;
import ddt.melnorme.utilbox.collections.ArrayList2;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.melnorme.utilbox.misc.IteratorUtil;
import ddt.dtool.ast.declarations.AttribProtection.EProtection;
import ddt.dtool.ast.definitions.CommonDefinition;
import ddt.dtool.ast.statements.IStatement;

/**
 * Attribute declarations
 *  
 * Technicaly DMD doesn't accept certain attributes as statements (such as protection, align), 
 * but structurally we allow it, even though a syntax or semantic error may still be issued.
 */
public class DeclarationAttrib extends ASTNode implements INonScopedContainer, IDeclaration, IStatement {
	
	public static enum AttribBodySyntax { SINGLE_DECL, BRACE_BLOCK, COLON }
	
	public final ArrayView<Attribute> attributes;
	public final AttribBodySyntax bodySyntax;
	public final ASTNode body; // Note: can be DeclList
	
	public DeclarationAttrib(ArrayView<Attribute> attributes, AttribBodySyntax bodySyntax, ASTNode bodyDecls) {
		this.attributes = parentize(assertNotNull(attributes));
		this.bodySyntax = assertNotNull(bodySyntax);
		this.body = parentize(bodyDecls);
		
		localAnalysis();
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DECLARATION_ATTRIB;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, attributes);
		acceptVisitor(visitor, body);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.appendList(attributes, " ", true);
		cp.append(bodySyntax == AttribBodySyntax.COLON, " :\n");
		cp.append(body);
	}
	
	@Override
	public Iterable<? extends IASTNode> getMembersIterable() {
		return getBodyIterable(body);
	}
	
	public static Iterable<ASTNode> getBodyIterable(ASTNode body) {
		if(body == null) {
			return IteratorUtil.<ASTNode>emptyIterable();
		}
		if(body instanceof NodeList<?>) {
			return ((NodeList<?>) body).nodes.upcastTypeParameter();
		}
		// TODO save body node collection
		return new ArrayList2<>(body);
	}
	
	/** 
	 * If this declaration attrib contains only a single declaration, return it, otherwise return null 
	 */
	public IDeclaration getSingleDeclaration() {
		if(bodySyntax != AttribBodySyntax.SINGLE_DECL) {
			return null;
		}
		if(body instanceof IDeclaration) {
			return (IDeclaration) body;
		}
		return null;
	}
	
	protected void localAnalysis() {
		for (Attribute attribute : attributes) {
			if(attribute instanceof AttribBasic) {
				AttribBasic attribBasic = (AttribBasic) attribute;
				applyBasicAttributes(attribBasic, this);			
			}
		}
		for (int ix = attributes.size() - 1; ix >= 0; ix--) {
			Attribute attribute = attributes.get(ix);
			if(attribute instanceof AttribProtection) {
				AttribProtection attribProtection = (AttribProtection) attribute;
				applyProtectionAttributes(attribProtection.protection, this);
				break; // last atribute takes precedence
			} 
		}
	}
	
	// TODO have CommonDefinition fetch attributes upwards,
	// instead of the other way around
	protected void applyBasicAttributes(AttribBasic attribute, INonScopedContainer block) {
		
		for (IASTNode node : block.getMembersIterable()) {
			
			if(node instanceof CommonDefinition) {
				CommonDefinition def = (CommonDefinition) node;
				def.setAttribute(attribute);
			} else if(node instanceof INonScopedContainer) {
				applyBasicAttributes(attribute, (INonScopedContainer) node);
			}
		}
	}
	
	protected void applyProtectionAttributes(EProtection protection, INonScopedContainer block) {
		for (IASTNode descendantNode : block.getMembersIterable()) {
			
			if(anotherProtectionAttribPresent(descendantNode)) {
				continue; // Do not descend, other attrib takes precedence
			}
			if(descendantNode instanceof CommonDefinition) {
				CommonDefinition def = (CommonDefinition) descendantNode;
				def.setProtection(protection);
			} else if(descendantNode instanceof DeclarationImport && protection == EProtection.PUBLIC) {
				DeclarationImport declImport = (DeclarationImport) descendantNode;
				declImport.isTransitive = true;
			} else if(descendantNode instanceof INonScopedContainer) {
				applyProtectionAttributes(protection, (INonScopedContainer) descendantNode);
			}
		}
	}
	
	public boolean anotherProtectionAttribPresent(IASTNode node) {
		if(node instanceof DeclarationAttrib) {
			DeclarationAttrib declAttrib = (DeclarationAttrib) node;
			for (Attribute attrib : declAttrib.attributes) {
				if(attrib instanceof AttribProtection) 
					return true;
			}
		}
		return false;
	}
	
}