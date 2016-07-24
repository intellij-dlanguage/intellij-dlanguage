char[][] foo;
foo = new char[][30];


foo = new char[][](30);

int[][][] bar;
bar = new int[][][](5, 20, 30);

bar = new int[][][5];
foreach (ref a; bar)
{
    a = new int[][20];
    foreach (ref b; a)
    {
        b = new int[30];
    }
}