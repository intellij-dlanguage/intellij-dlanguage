void foo()
{
    int i = 7;
    struct SS
    {
        int x,y;
        int bar() { return x + i + 1; }
    }
    SS s;
    s.x = 3;
    s.bar(); // returns 11
}

void foo()
{
    int i = 7;
    static struct SS
    {
        int x, y;
        int bar()
        {
            return i; // error, SS is not a nested struct
        }
    }
}