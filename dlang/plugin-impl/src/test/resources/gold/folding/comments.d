<fold text='/* ... */'>/*
hello
world
*/</fold>

void test() <fold text='{...}'>{}</fold>

<fold text='/* ... */'>/**
hello
*/</fold>

<fold text='/* ... */'>/*
void main() {
    writeln("Hello world");
}
*/</fold>

void main() <fold text='{...}'>{
<fold text='/* ... */'>/*
    */</fold>
}</fold>
