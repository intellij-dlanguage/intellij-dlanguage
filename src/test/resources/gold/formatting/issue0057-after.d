~this() {
}

extern (C++) ~this() {
    global.gag = oldgag;
}

struct S {
    public ~this() {
    }
}
