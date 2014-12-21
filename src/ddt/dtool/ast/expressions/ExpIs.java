package ddt.dtool.ast.expressions;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.dtool.ast.references.Reference;

public class ExpIs extends Expression {
	
	public static enum ExpIsSpecialization {
		TYPE_SUBTYPE, // Type ref with " : " syntax
		TYPE_EXACT, // Type ref with " == " syntax
		STRUCT,
		UNION,
		CLASS,
		INTERFACE,
		ENUM,
		FUNCTION,
		TYPEDEF,
		DELEGATE,
		SUPER,
		CONST,
		IMMUTABLE,
		INOUT,
		SHARED,
		RETURN,
		__PARAMETERS,
		;
		
	}
	
	public final Reference typeRef;
	public final ExpIsSpecialization specKind;
	public final Reference specTypeRef;
	
	public ExpIs(Reference typeRef, ExpIsSpecialization specKind, Reference specTypeRef) {
		this.typeRef = parentize(typeRef);
		this.specKind = specKind;
		this.specTypeRef = parentize(specTypeRef);
		assertTrue((specTypeRef == null) ==
			(specKind != ExpIsSpecialization.TYPE_SUBTYPE && specKind != ExpIsSpecialization.TYPE_EXACT)); 
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.EXP_IS;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, typeRef);
		acceptVisitor(visitor, specTypeRef);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("is");
		if(typeRef != null) {
			cp.append("(", typeRef);
			toStringAsCode_isExpSpecKind(cp, specKind, specTypeRef);
			cp.append(")");
		}
	}
	
	public static void toStringAsCode_isExpSpecKind(ASTCodePrinter cp, 
		ExpIsSpecialization specKind, Reference specTypeRef) {
		if(specKind == ExpIsSpecialization.TYPE_SUBTYPE) {
			cp.append(" : ");
		} else if(specKind == ExpIsSpecialization.TYPE_EXACT) {
			cp.append(" == ");
		} else if(specKind != null) {
			cp.appendStrings(" == ", specKind.toString().toLowerCase());
		}
		cp.append(specTypeRef);
	}
	
}