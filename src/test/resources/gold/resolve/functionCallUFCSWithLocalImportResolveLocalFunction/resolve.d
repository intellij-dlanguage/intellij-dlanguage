import other;
void <resolved>writeln(int i) {}

unittest {
    void writeln(int i) {};

    import other;
    3.<ref>writeln;
}