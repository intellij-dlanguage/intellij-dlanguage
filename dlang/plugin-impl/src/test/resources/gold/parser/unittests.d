unittest
{
}

class Sum
{
    int add(int x, int y) { return x + y; }

    unittest
    {
        Sum sum = new Sum;
        assert(sum.add(3,4) == 7);
        assert(sum.add(-2,0) == -2);
    }
}

void myFunc(T)(T[] data)
{
    if (data.length > 2)
        data[0] = data[1];
}

@safe nothrow unittest
{
    auto arr = [1,2,3];
    myFunc(arr);
    assert(arr == [2,2,3]);
}

/// Math class
class Math
{
    /// add function
    static int add(int x, int y) { return x + y; }

    ///
    unittest
    {
        assert(add(2, 2) == 4);
    }
}

///
unittest
{
    auto math = new Math();
    auto result = math.add(2, 2);
}

/// add function
int add(int x, int y) { return x + y; }

/// code sample generated
unittest
{
    assert(add(1, 1) == 2);
}

/// code sample not generated because the unittest is private
private unittest
{
    assert(add(2, 2) == 4);
}

unittest
{
    /// code sample not generated because the unittest isn't documented
    assert(add(3, 3) == 6);
}

/// code sample generated, even if it only includes comments (or is empty)
unittest
{
    // assert(add(4, 4) == 8);
}

