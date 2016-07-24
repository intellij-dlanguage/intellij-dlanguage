struct Foo
{
    @property int data() { return m_data; } // read property

    @property int data(int value) { return m_data = value; } // write property

  private:
    int m_data;
}

int test()
{
    Foo f;

    f.data = 3;        // same as f.data(3);
    return f.data + 3; // same as return f.data() + 3;
}

void main()
{
    @property int[] delegate() bar1 = { return [1, 2]; };
    auto x1 = bar1.ptr; // points to array data

    struct Foo { int* ptr; }
    @property Foo delegate() bar2 = { return Foo(); };
    auto x2 = bar2.ptr; // gets value of Foo.ptr
}