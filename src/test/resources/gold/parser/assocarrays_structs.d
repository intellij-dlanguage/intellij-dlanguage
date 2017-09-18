size_t toHash() const @safe pure nothrow;
bool opEquals(ref const typeof(this) s) @safe pure nothrow;

import std.string;

struct MyString
{
    string str;

    size_t toHash() const @safe pure nothrow
    {
        size_t hash;
        foreach (char c; str)
            hash = (hash * 9) + c;
        return hash;
    }

    bool opEquals(ref const MyString s) const @safe pure nothrow
    {
        return std.string.cmp(this.str, s.str) == 0;
    }
}
