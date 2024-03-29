void foo(char[2] a)
{
    assert(a == "bc");
}
void bar(ref const char[2] a)
{
    assert(a == "bc");
}
void baz(const char[3] a) {}

void main()
{
    string str = "abc";
    foo(str[1 .. 3]);
    bar(str[1 .. 3]);
  //baz(str[1 .. 3]); // cannot match length
}