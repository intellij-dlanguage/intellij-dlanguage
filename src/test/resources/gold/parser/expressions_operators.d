unittest
{
    a += b;
    a = cast(typeof(a))(a + b);
    //previously:
    //a op= b;
    //a = cast(typeof(a))(a op b);

}
