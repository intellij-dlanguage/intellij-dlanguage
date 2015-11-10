void func(int x)
{
    int x;       // error, hides previous definition of x
    double y;
    ...
    {
        char y;  // error, hides previous definition of y
        int z;
    }
    {
        wchar z; // legal, previous z is out of scope
    }
}