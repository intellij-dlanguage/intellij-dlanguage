modulescope_operator;

int x;

int scope_operator(int <resolved>x, bool y) {
    if (y)
        return <ref>x; // return foo.x, not global x
    else
        return .x; // return global x
}
