int foo(int a) {
    auto fp = function int(char c) {
        if (c == '1') {
            return 6;
        } else if (c == '2') {
            <caret>return 7;
        } else {
            throw new Error("Unknown");
        }
    };

    switch (a) {
        case 1: return 1;
        case 2: return 2;
        default: throw new Error("Oh no!");
    }
}
