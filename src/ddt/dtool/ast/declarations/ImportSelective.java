package ddt.dtool.ast.declarations;

import static ddt.dtool.util.NewUtils.assertCast;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertFail;
import ddt.melnorme.lang.tooling.ast.IASTNode;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.engine.scoping.INonScopedContainer;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.melnorme.utilbox.core.CoreUtil;
import ddt.dtool.ast.declarations.DeclarationImport.IImportFragment;
import ddt.dtool.ast.references.RefImportSelection;
import ddt.dtool.ast.references.RefModule;

public class ImportSelective extends ASTNode implements INonScopedContainer, IImportFragment {
	
	public static interface IImportSelectiveSelection extends IASTNode {
		//String getTargetName();
	}
	
	public final IImportFragment fragment;
	public final ArrayView<ASTNode> impSelFrags;
	
	public ImportSelective(IImportFragment subFragment, ArrayView<IImportSelectiveSelection> frags) {
		this.impSelFrags = CoreUtil.<ArrayView<ASTNode>>blindCast(parentizeFrags(frags));
		this.fragment = parentize(subFragment);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.IMPORT_SELECTIVE;
	}
	
	@Override
	protected DeclarationImport getParent_Concrete() {
		return assertCast(parent, DeclarationImport.class);
	}
	
	public DeclarationImport getDeclarationImport() {
		return getParent_Concrete();
	}
	
	public ArrayView<IImportSelectiveSelection> parentizeFrags(ArrayView<IImportSelectiveSelection> frags) {
		if (frags != null) {
			for (IImportSelectiveSelection selection : frags) {
				((ASTNode) selection).setParent(this);
				if (selection instanceof ImportSelectiveAlias) {
					((ImportSelectiveAlias) selection).target.impSel = this;
				} else if (selection instanceof RefImportSelection) {
					((RefImportSelection) selection).impSel = this;
				} else {
					assertFail();
				}
			}
		}
		return frags;
	}
	
	@Override
	public RefModule getModuleRef() {
		return fragment.getModuleRef();
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, fragment);
		acceptVisitor(visitor, impSelFrags);
	}
	
	@Override
	public Iterable<? extends IASTNode> getMembersIterable() {
		return impSelFrags;
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(fragment, " : ");
		cp.appendList(impSelFrags, ", ");
	}
	
	/* -----------------  ----------------- */
	
	@Override
	public void searchInSecondaryScope(CommonScopeLookup search) {
		findDefUnitInSelectiveImport(this, search);
	}
	
	public static void findDefUnitInSelectiveImport(ImportSelective impSelective, CommonScopeLookup search) {
		
		INamedElement targetModule = ImportContent.findImportTargetModule(search.modResolver, impSelective);
		if (targetModule == null)
			return;
			
		for(ASTNode impSelFrag: impSelective.impSelFrags) {
			if(impSelFrag instanceof RefImportSelection) {
				RefImportSelection refImportSelection = (RefImportSelection) impSelFrag;
				String name = refImportSelection.getDenulledIdentifier();
				// Do pre-emptive matching
				if(!search.matchesName(name)) {
					continue;
				}
				INamedElement namedElement = refImportSelection.findTargetDefElement(search.modResolver);
				
				/*FIXME: BUG here if element not found*/
				if(namedElement != null) { 
					search.visitElement(namedElement);
				}
			}
		}
	}
	
}