module usage;

template print(<resolved>usage...) {
    void print() {
        writeln("args are ", <ref>usage);
    }
}
