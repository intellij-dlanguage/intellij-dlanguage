class C
{
    // implicit virtual function table pointer not marked
    // implicit monitor field not marked, usually managed manually
    C next;
    size_t sz;
    void* p;
    void function () fn; // not a GC managed pointer
}

struct S
{
    size_t val1;
    void* p;
    C c;
    byte[] arr;          // { length, ptr }
    void delegate () dg; // { context, func }
}

static assert (__traits(getPointerBitmap, C) == [6*size_t.sizeof, 0b010100]);
static assert (__traits(getPointerBitmap, S) == [7*size_t.sizeof, 0b0110110]);