void main()
{
    // Custom loop implementation, that iterates over powers of 2 with
    // alternating sign. The loop body is passed in dg.
    int myLoop(int delegate(ref int) dg)
    {
        for (int z = 1; z < 128; z *= -2)
        {
            auto ret = dg(z);

            // If the loop body contains a break, ret will be non-zero.
            if (ret != 0)
                return ret;
        }
        return 0;
    }

    // This example loop simply collects the loop index values into an array.
    int[] result;
    foreach (ref x; &myLoop)
    {
        result ~= x;
    }
    assert(result == [1, -2, 4, -8, 16, -32, 64, -128]);
}