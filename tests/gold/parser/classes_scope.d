scope class Foo {  }

void func()
{
    Foo f;    // error, reference to scope class must be scope
    scope Foo g = new Foo(); // correct
}