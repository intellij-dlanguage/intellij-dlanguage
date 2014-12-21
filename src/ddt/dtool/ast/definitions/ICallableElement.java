package ddt.dtool.ast.definitions;

import ddt.melnorme.utilbox.collections.ArrayView;

public interface ICallableElement {
	
	ArrayView<IFunctionParameter> getParameters();
	
}