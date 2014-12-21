package ddt.dtool.ast.definitions;

import static ddt.dtool.util.NewUtils.assertCast;
import ddt.melnorme.lang.tooling.ast.SourceRange;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;


/**
 * A Symbol that is the name of a DefUnit, and that knows how to get
 * that DefUnit. Its node parent must be the corresponding DefUnit.
 */
public class DefSymbol extends Symbol {
	
	public DefSymbol(String id) {
		super(id);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.SYMBOL;
	}
	
	@Override
	protected ASTNode getParent_Concrete() {
		return assertCast(parent, DefUnit.class);
	}
	
	/** @return the defunit associated with this defSymbol. Cannot be null. */
	public DefUnit getDefUnit() {
		return (DefUnit) getParent();
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(name);
	}
	
	public DefSymbol createCopy() {
		DefSymbol defname = this;
		DefSymbol defSymbol = new DefSymbol(defname.name);
		SourceRange sourceRangeOrNull = defname.getSourceRangeOrNull();
		if(sourceRangeOrNull != null) {
			defSymbol.setSourceRange(sourceRangeOrNull);
		}
		return defSymbol;
	}
	
}