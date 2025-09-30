module usage;

template foo(bar...) {
    alias <resolved>m = foo!(names[1 ..$]);
    alias foo = <ref>m;
}

