class Foo(<info>T</info>) {
    <info>T</info>[] bar;

    void test(<info>T</info> item, <info>T</info> a, <info>T</info> b) {
        if (item > 0) {
            <info>T</info> a = b;
        } else {
            <info>T</info> b = a;
        }

        static if (hasIndirections!<info>T</info>) {
            bar[0 .. last + 1] = <info>T</info>.init;
        }
    }
}

interface Addable(<info>T</info>) {
    final auto add(<info>T</info> t) {
        return this;
    }
}

class List(<info>T</info>) : Addable!<info>T</info> {
    List remove(<info>T</info> t) {
        return this;
    }
}

void main() {
    auto list = new List!int;
}

Vector!(<info>T</info>, <info>n</info>) optVecValue(<info>T</info>, int <info>n</info>, <info>E</info> : Exception)(
    in string path,
    Vector!(<info>T</info>, <info>n</info>) defaultVal = Vector!(<info>T</info>, <info>n</info>).init
) {
    Node node = findNodeByPath(path, getRootNode());

    if (node is null)
        return defaultVal;

    return getVecValueFromNode!(<info>T</info>, <info>n</info>, <info>E</info>)(node);
}

mixin template MyMixin(<info>T</info>, <info>N</info>) {
    <info>T</info> foo;
    <info>N</info> bar;
}

template myTemplate(<info>T</info>, alias <info>b</info>) {
}

struct S {
    const void foo(this <info>T</info>)(int i) {
        writeln(typeid(<info>T</info>));
    }
}

void logError(<info>C</info>, <info>T...</info>)(in <info>C</info>[] fmt, <info>T</info> args) {
    auto app = Application.getInstance();
    debug app.logError(fmt, args);
}
