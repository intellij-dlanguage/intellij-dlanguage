module usage;

template foo(bar...) {
    static if (bar.length == 0) {
        alias <resolved>m = foo!(names[1 ..$]);
    }
    
    static foreach (a; [1]) {
        int i = <ref>m;
    }
}
