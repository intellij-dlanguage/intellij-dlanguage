unittest
{
    int func(int);
    fp = &func;   // fp points to func

    class OB
    {
        int member(int);
    }
    OB o;
    dg = &o.member; // dg is a delegate to object o and
                    // member function member
}
