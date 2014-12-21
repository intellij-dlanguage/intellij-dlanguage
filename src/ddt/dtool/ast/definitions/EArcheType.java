package ddt.dtool.ast.definitions;

public enum EArcheType {
	Module,
	Package,
	
	Variable,
	Function,
	Constructor,
	
	//Native,
	Struct(true),
	Union(true),
	Class(true),
	Interface(true),
	
	Template,
	TypeParameter(true),
	Mixin,
	Tuple, //This probably should not be an archetype
	
	Enum(true),
	EnumMember, // Similar to Variable
	
	Alias,
	;
	
	protected final boolean isType;
	
	private EArcheType() {
		this(false);
	}
	
	private EArcheType(boolean isType) {
		this.isType = isType;
	}
	
	/** Archetype kind is TYPE, meaning it can be used to declare variables. */
	public boolean isType() {
		return isType;
	}
	
}