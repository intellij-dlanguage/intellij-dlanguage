class Date
{
    int day;
    int hour;

    this(int d, int h)
    {
        day = d;
        hour = h;
    }

    invariant()
    {
        assert(1 <= day && day <= 31);
        assert(0 <= hour && hour < 24);
    }
}
