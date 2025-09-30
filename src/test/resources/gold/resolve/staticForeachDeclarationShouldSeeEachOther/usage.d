module usage;

static foreach(myVar; [1, 2, 3]) {
    void <resolved>i() {}

    void bar() {<ref>i();}
}
