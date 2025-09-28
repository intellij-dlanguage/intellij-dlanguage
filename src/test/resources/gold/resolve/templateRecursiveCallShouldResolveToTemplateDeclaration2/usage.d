module usage;

template <resolved>foo(bar...) {
    static if (bar.length == 0) {
        alias foo = int;
    } else {
        alias m = <ref>foo!(names[1 ..$]);
        static if (false) {
            alias foo = m;
        } else {
            alias foo = m;
        }
    }
}
