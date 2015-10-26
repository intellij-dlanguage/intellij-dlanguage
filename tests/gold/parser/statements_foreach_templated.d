struct S
{
    import std.traits : ParameterTypeTuple;  // introspection template

    int opApply(Dg)(scope Dg dg)
        if (ParameterTypeTuple!Dg.length == 2)  // foreach function takes 2 parameters
    {
        return 0;
    }

    int opApply(Dg)(scope Dg dg)
        if (ParameterTypeTuple!Dg.length == 3)  // foreach function takes 3 parameters
    {
        return 0;
    }
}

void main()
{
    foreach (int a, int b; S()) { }  // calls first opApply function
    foreach (int a, int b, float c; S()) { }  // calls second opApply function
}