auto foo = bar(1, 1, 1);
auto foo = A!(int, int, int);
enum foo = bar(1, 1, 1);
enum foo = A!(int, int, int);

enum bar {
    a = Struct(a, b, c),
    b = Struct(d, e, f)
}

enum bar {
    a = Struct(a, b, c),
    b = Struct(d, e, f),
}
