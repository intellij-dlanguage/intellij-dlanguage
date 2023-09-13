module scope_operator;

int <resolved>x;

int scope_operator(int x, bool y) {
    if (y)
        return x; // return foo.x, not global x
    else
        return .<ref>x; // return global x
}