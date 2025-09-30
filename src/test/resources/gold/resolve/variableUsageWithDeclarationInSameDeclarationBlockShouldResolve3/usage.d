module usage;

template foo(bar...) {
    alias <resolved>m = foo!(names[1 ..$]);
    
    static foreach (a; [1]) {
        int i = <ref>m;
    }
}
