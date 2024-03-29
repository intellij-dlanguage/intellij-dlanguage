@Test public void assertAssocArrayEqualsFailure()
{
    string[int] expected = [1 : "foo", 2 : "bar"];
    string[int] actual = [1 : "foo", 2 : "baz"];

    assertArrayEquals(expected, actual);
}
