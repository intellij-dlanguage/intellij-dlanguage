void foo(auto in a, auto out int b) const
out {
    assert(true);
}
body {
}

void foo() const
in {
}
out {
    assert(true);
}
body {
}

void foo() const
in {
}
out (result) {
    assert(true);
}
body {
}
