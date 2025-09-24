module usage;

static foreach(<resolved>myVar; [1, 2, 3]) {
    int i = <ref>myVar;
}
