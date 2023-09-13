module scope_operator;

import declaration;

int scope_operator(int x, bool y) {
    if (y)
        return x; // return foo.x, not global x
    else
        return .<ref>x; // return global x
}