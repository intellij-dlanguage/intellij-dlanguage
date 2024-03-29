void foo(auto in a, auto out int b) const
out {
    assert(true);
}
do {
}

void foo() const
in {
}
out {
    assert(true);
}
do {
}

void foo() const
in {
}
out (result) {
    assert(true);
}
do {
}
