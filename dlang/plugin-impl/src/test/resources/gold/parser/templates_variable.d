enum string constant(TL...) = TL.stringof;
//ubyte[T.sizeof] storage(T) = 0;//not valid d
auto array(alias a) = a;

template constant(TL...)
{
    enum string constant = TL.stringof;
}
template storage(T)
{
    ubyte[T.sizeof] storage = 0;
}
template array(alias a)
{
    auto array = a;
}
