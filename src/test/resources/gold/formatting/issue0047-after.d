unittest {

    FuncDeclaration* pFd = cast(FuncDeclaration*) dmd_aaGet(&arrayfuncs, cast(void*) ident);
    FuncDeclaration fd = *pFd;
    {
        auto dd = new DtorDeclaration(declLoc, Loc(), stc, Identifier.idPool("__fieldDtor"));
        auto dd = new DtorDeclaration(declLoc, Loc(), stc, extraParam,
                midLengthFun(param, param), longIdentifier, Identifier.idPool("__fieldDtor"));

        memcpy(&saved_idents, &rvl.saved_idents, (const(char)*).sizeof * VC_SAVED_IDENT_CNT);
        memcpy(&saved_types, &rvl.saved_types, (Type).sizeof * VC_SAVED_TYPE_CNT);

        auto ed = new EnumDeclaration(loc, ident, memtype ? memtype.syntaxCopy() : null);
    }
}

void doStuff(const Token[] tokens, ref const State current,
        const FormatterConfig* formatterConfig, int currentLineLength, int indentLevel, int depth) {
    return;
}

unittest {
    if (x) {
        if (y) {
            auto z = doCond(e.thisexp) || doCond(e.newargs) || doCond(e.arguments) || applyTo(e);
        }
    }
}
