@property bool isFunctionType() {
    with (CXTypeKind)
        return kind == CXType_FunctionNoProto || kind == CXType_FunctionProto
            ||
            func.resultType.isValid;
}

@property bool isFunctionPointerType() {
    with (CXTypeKind)
        return kind == CXType_Pointer && pointeeType.isFunctionType;
}
