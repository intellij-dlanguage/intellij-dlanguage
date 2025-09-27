module crash_alias;

template Bar(T) {
    static if (false) {
        alias Foo = <ref>Foo!int;
    } else {
        alias Foo = <ref>Foo!string;
    }
}