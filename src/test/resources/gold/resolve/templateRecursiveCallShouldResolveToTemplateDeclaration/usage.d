module usage;

template <resolved>foo(bar...) {
    alias m = <ref>foo!(names[1 ..$]);
    static if (false) {
        alias foo = m;
    } else {
        alias foo = m;
    }
}
