auto amap(Args args)
{

    static if (
        randAssignable!(Args[$ - 1]) &&
        is(MapType!(Args[0], functions) : ElementType!(Args[$ - 1]))
        )
    {
    }
}
