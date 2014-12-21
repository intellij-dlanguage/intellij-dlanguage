package ddt.dtool.ast.declarations;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.AliasSemantics.RefAliasSemantics;
import ddt.dtool.ast.declarations.ImportSelective.IImportSelectiveSelection;
import ddt.dtool.ast.definitions.DefUnit;
import ddt.dtool.ast.definitions.EArcheType;
import ddt.dtool.ast.references.RefImportSelection;
import ddt.dtool.ast.references.Reference;

public class ImportSelectiveAlias extends DefUnit implements IImportSelectiveSelection {
	
	public final RefImportSelection target;
	
	public ImportSelectiveAlias(ProtoDefSymbol defId, RefImportSelection impSelection) {
		super(defId);
		this.target = parentize(impSelection);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.IMPORT_SELECTIVE_ALIAS;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, defname);
		acceptVisitor(visitor, target);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.appendStrings(getName(), " = ");
		cp.append(target);
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Alias;
	}
	
	/* -----------------  ----------------- */
	
	
	@Override
	protected NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new RefAliasSemantics(this, pickedElement) {
			@Override
			protected Reference getAliasTarget() {
				return target;
			}
		};
	}
	
}