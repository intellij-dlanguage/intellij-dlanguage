class Foo(<info descr="">T</info>) {
    <info descr="">T</info>[] bar;

    void test(<info descr="">T</info> item, <info descr="">T</info> a, <info descr="">T</info> b) {
        if (item > 0) {
            <info descr="">T</info> a = b;
        } else {
            <info descr="">T</info> b = a;
        }

        static if (hasIndirections!<info descr="">T</info>) {
            bar[0 .. last + 1] = <info descr="">T</info>.init;
        }
    }
}

interface Addable(<info descr="">T</info>) {
    final auto add(<info descr="">T</info> t) {
        return this;
    }
}

class List(<info descr="">T</info>) : Addable!<info descr="">T</info> {
    List remove(<info descr="">T</info> t) {
        return this;
    }
}

void main() {
    auto list = new List!int;
}

Vector!(<info descr="">T</info>, <info descr="">n</info>) optVecValue(<info descr="">T</info>, <info descr="">int n</info>, <info descr="">E : Exception</info>)(
    in string path,
    Vector!(<info descr="">T</info>, <info descr="">n</info>) defaultVal = Vector!(<info descr="">T</info>, <info descr="">n</info>).init
) {
    Node node = findNodeByPath(path, getRootNode());

    if (node is null)
        return defaultVal;

    return getVecValueFromNode!(<info descr="">T</info>, <info descr="">n</info>, <info descr="">E</info>)(node);
}

mixin template MyMixin(<info descr="">T</info>, <info descr="">N</info>) {
    <info descr="">T</info> foo;
    <info descr="">N</info> bar;
}

template myTemplate(<info descr="">T</info>, <info descr="">alias b</info>) {
}

struct S {
    const void foo(<info descr="">this T</info>)(int i) {
        writeln(typeid(<info descr="">T</info>));
    }
}

void logError(<info descr="">C</info>, <info descr="">T...</info>)(in <info descr="">C</info>[] fmt, <info descr="">T</info> args) {
    auto app = Application.getInstance();
    debug app.logError(fmt, args);
}
