
class Foo(T) if (is(T : Bar) && is(T : Baz))
{}

class Foo(T) if (is(T : Bar) || is(T : Baz))
{}
