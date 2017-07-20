void presumed(out uint column) @trusted {
    CXString cxstring;

    clang_getPresumedLocation(cx, &cxstring, &line, &column);
    filename = toD(cxstring);
}
