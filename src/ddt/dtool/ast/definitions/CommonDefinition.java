package ddt.dtool.ast.definitions;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertFail;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertUnreachable;
import ddt.dtool.ast.declarations.AttribBasic;
import ddt.dtool.ast.declarations.AttribBasic.AttributeKinds;
import ddt.dtool.ast.declarations.AttribProtection.EProtection;
import ddt.dtool.ast.declarations.DeclarationAttrib;
import ddt.dtool.ast.declarations.IDeclaration;
import ddt.dtool.parser.common.Token;

/**
 * Abstract class for all declaration DefUnits that have preceding attributes and DDoc.
 * {@link CommonDefinition} have and extended source range, which includes not only this node,
 * but attached single-decl {@link DeclarationAttrib} and doc comments.
 */
public abstract class CommonDefinition extends DefUnit implements IDeclaration {
	
	public final Token[] comments;
	protected int extendedStartPos = -1;
	protected int defAttributesBitMask;
	
	public CommonDefinition(Token[] comments, ProtoDefSymbol defId) {
		super(defId);
		this.comments = comments;
		this.defAttributesBitMask = 0;
	}
	
	@Override
	public Token[] getDocComments() {
		return comments;
	}
	
	public int getExtendedStartPos() {
		assertTrue(this.extendedStartPos != -1);
		return extendedStartPos;
	}
	
	public void setExtendedStartPos(int extendedStartPos) {
		assertTrue(this.extendedStartPos == -1); // can only set once
		this.extendedStartPos = extendedStartPos;
	}
	
	public int getExtendedEndPos() {
		return getEndPos();
	}
	
	/** Sets protection attribute. Can only set once. */
	public void setProtection(EProtection protection) {
		assertTrue(getProtectionFromAttributesBitMask(defAttributesBitMask) == null);
		defAttributesBitMask |= getBitMaskForProtectionAttribute(protection) ;
	}
	
	public EProtection getProtection() {
		return getProtectionFromAttributesBitMask(defAttributesBitMask);
	}
	
	public EProtection getEffectiveProtection() {
		EProtection protection = getProtection();
		return protection == null ? EProtection.PUBLIC : protection;
	}
	
	public void setAttribute(AttribBasic declBasicAttrib) {
		defAttributesBitMask |= getBitMaskForBasicAttribute(declBasicAttrib.attribKind);
	}
	
	public boolean hasAttribute(AttributeKinds attrib) {
		return (defAttributesBitMask & getBitMaskForBasicAttribute(attrib)) != 0;
	}
	
	
	public static int getBitMaskForProtectionAttribute(EProtection protection) {
		switch (protection) {
		case PRIVATE: return 0x1;
		case PACKAGE: return 0x2;
		case PROTECTED: return 0x3;
		case PUBLIC: return 0x4;
		case EXPORT: return 0x5;
		}
		throw assertUnreachable();
	}
	
	public static int getBitMaskForBasicAttribute(AttributeKinds basicAttrib) {
		//Shift by 3 spaces first, first 3 bits are for prot attributes
		return (1 << 3) << basicAttrib.ordinal();
	}
	
	public static EProtection getProtectionFromAttributesBitMask(int attributesBitMask) {
		switch (attributesBitMask & 0x7) {
		case 0x0: return null;
		case 0x1: return EProtection.PRIVATE;
		case 0x2: return EProtection.PACKAGE;
		case 0x3: return EProtection.PROTECTED;
		case 0x4: return EProtection.PUBLIC;
		case 0x5: return EProtection.EXPORT;
		default: throw assertFail();
		}
	}
	
}