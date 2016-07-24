class C
{
    int num;
    this()
    {
        num = 1;  // initialize
        num = 2;  // assignment
    }
}

struct A
{
    this(int n) {}
    void opAssign(A rhs) {}
}
class C
{
    A val;
    this()
    {
        val = A(1);  // A(1) is moved in this.val for initializing
        val = A(2);  // rewritten to val.opAssign(A(2))
    }
}

class C
{
    immutable int num;
    this()
    {
        num = 1;  // OK
        num = 2;  // Error: multiple field initialization
    }
}

class C
{
    immutable int num;
    immutable string str;
    this()
    {
        foreach (i; 0..2)
        {
            num = 1;    // Error: field initialization not allowed in loops
        }
        size_t i = 0;
    Label:
        str = "hello";  // Error: field initialization not allowed after labels
        if (i++ < 2)
            goto Label;
    }
}

