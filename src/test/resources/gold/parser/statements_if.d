module rpg;

void test() const
{
    if (this == "foo")
        p = "bar";
    
    if (auto a = "foo") {}
    
    if (ref a = 3) a++;
    
    if (scope a = 0) {}
    
    if (const int i = 3) {}
    
    if(MyEnum myVar = MyEnum.X) {}
}