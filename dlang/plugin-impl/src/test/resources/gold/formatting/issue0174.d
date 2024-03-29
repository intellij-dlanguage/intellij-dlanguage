void merge()
{
    static if (is(T == enum))
        *thisN = x;
}
