module usage;

class S {
    void foo(this <resolved>T)() {
        pragma(msg, <ref>T);
    }
}
