struct S
{
    float x;
}

void main()
{
    int x;
    S s;
    with (s)
    {
        x++;  // error, shadows the int x declaration
    }
}
