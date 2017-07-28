class A { }
class B : A { }

void main()
{
    writeln(typeid(int));        // int
    uint i;
    writeln(typeid(i++));        // uint
    writeln(i);                  // 1
    A a = new B();
    writeln(typeid(a));          // B
    writeln(typeid(typeof(a)));  // A
}
