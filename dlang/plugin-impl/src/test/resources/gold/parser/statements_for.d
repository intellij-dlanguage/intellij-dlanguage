unittest
{
for (int i = 0; i < 10; i++)
    foo(i);

{
    int i;
    for (i = 0; i < 10; i++)
        foo(i);
}

for (int i = 0; i < 10; i++)
{
}
}
