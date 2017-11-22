void foo()<fold text=' { '> {
    </fold>writeln("Hello World");<fold text=' }'>
}</fold>

void bar()
<fold text='{...}'>{

    writeln("Hello World");

}</fold>

void longFn() <fold text='{...}'>{
    writeln("This line is too long to fit into the line margin after collapsing, so it will fully collapse as a large block");
}</fold>
