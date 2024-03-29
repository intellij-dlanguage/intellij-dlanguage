void main()
{
    assert(0, "an" ~ " error message");
    assert(false, "This ", " is ", 1);
    static assert (true, "This ", char.stringof, " is stringof");
}