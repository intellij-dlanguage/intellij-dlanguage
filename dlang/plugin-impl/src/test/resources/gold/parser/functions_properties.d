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