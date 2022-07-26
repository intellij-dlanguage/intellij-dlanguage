class Foo(<info descr="null">T</info>) {
    <info descr="null">T</info>[] bar;

    void <info descr="null">test</info>(<info descr="null">T</info> item, <info descr="null">T</info> a, <info descr="null">T</info> b) {
        if (item > 0) {
            <info descr="null">T</info> a = b;
        } else {
            <info descr="null">T</info> b = a;
        }

        static if (<info descr="null">hasIndirections</info>!<info descr="null">T</info>) {
            bar[0 .. last + 1] = <info descr="null">T</info>.init;
        }
    }
}

interface Addable(<info descr="null">T</info>) {
    final auto <info descr="null">add</info>(<info descr="null">T</info> t) {
        return this;
    }
}

class List(<info descr="null">T</info>) : Addable!<info descr="null">T</info> {
    List <info descr="null">remove</info>(<info descr="null">T</info> t) {
        return this;
    }
}

void <info descr="null">main</info>() {
    auto list = new List!int;
}

Vector!(<info descr="null">T</info>, <info descr="null">n</info>) <info descr="null">optVecValue</info>(<info descr="null">T</info>, int <info descr="null">n</info>, <info descr="null">E</info> : Exception)(
    in string path,
    Vector!(<info descr="null">T</info>, <info descr="null">n</info>) defaultVal = <info descr="null">Vector</info>!(<info descr="null">T</info>, <info descr="null">n</info>).init
) {
    Node node = findNodeByPath(path, getRootNode());

    if (node is null)
        return defaultVal;

    return <info descr="null">getVecValueFromNode</info>!(<info descr="null">T</info>, <info descr="null">n</info>, <info descr="null">E</info>)(node);
}

mixin template <info descr="null">MyMixin</info>(<info descr="null">T</info>, <info descr="null">N</info>) {
    <info descr="null">T</info> foo;
    <info descr="null">N</info> bar;
}

template <info descr="null">myTemplate</info>(<info descr="null">T</info>, alias <info descr="null">b</info>) {
}

struct S {
    const void <info descr="null">foo</info>(this <info descr="null">T</info>)(int i) {
        writeln(typeid(<info descr="null">T</info>));
    }
}

void <info descr="null">logError</info>(<info descr="null">C</info>, <info descr="null">T...</info>)(in <info descr="null">C</info>[] fmt, <info descr="null">T</info> args) {
    auto app = Application.getInstance();
    debug app.<info descr="null">logError</info>(fmt, args);
}
