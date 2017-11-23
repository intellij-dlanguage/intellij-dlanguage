int foo(int a) {
    switch (a) {
        case 1: return 1;
        case 2: return 2;
        default: <caret>throw new Error("Oh no!");
    }
}
