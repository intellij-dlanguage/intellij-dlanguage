
static foreach(i; [1, 2, 3])
    pragma(msg, "i");

static foreach(i; 1 .. 3) {
    pragma(msg, i);
}

struct AlgorithmConfig
{
    static foreach(a; [2, 3, 4])
    {
    }
}