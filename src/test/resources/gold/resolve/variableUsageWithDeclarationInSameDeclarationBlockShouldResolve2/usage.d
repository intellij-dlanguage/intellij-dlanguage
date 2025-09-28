module usage;

template foo(bar...) {
    static if (bar.length == 0) {
        alias foo = int;
    } else {
        alias <resolved>m = foo!(names[1 ..$]);
        static if (false) {
            alias foo = <ref>m;
        } else {
            alias foo = m;
        }
    }
}
