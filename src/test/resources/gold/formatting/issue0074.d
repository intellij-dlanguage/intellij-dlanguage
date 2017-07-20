@property bool isFunctionType()
{
	with (CXTypeKind)
		return kind == CXType_FunctionNoProto || kind == CXType_FunctionProto
			||  // FIXME: This "hack" shouldn't be needed.
			func.resultType.isValid;
}

@property bool isFunctionPointerType()
{
	with (CXTypeKind)
		return kind == CXType_Pointer && pointeeType.isFunctionType;
}
