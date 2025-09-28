module usage;

template foo(bar...) {
    alias <resolved>foo = foo!(names[1 ..$]);
    
    static foreach (a; [1]) {
        int i = <ref>foo; // name collision with the template name, local symbols have priority
    }
}
