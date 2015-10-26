class Foo
{
    uint[2] array;

    int opApply(int delegate(ref uint) dg)
    {
        int result = 0;

        for (int i = 0; i < array.length; i++)
        {
            result = dg(array[i]);
            if (result)
                break;
        }
        return result;
    }
}

void test()
{
    Foo a = new Foo();

    a.array[0] = 73;
    a.array[1] = 82;

    foreach (uint u; a)
    {
        writefln("%d", u);
    }
}