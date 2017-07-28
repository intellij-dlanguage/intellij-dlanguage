// Test used for fixing the bitwise or bug (the pipe symbol)

unittest{
    assertEquals(2 | 4, 2);
}
auto x = true | false;
