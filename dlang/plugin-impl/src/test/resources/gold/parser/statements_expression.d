unittest
{
int x;
x++;               // ok
x;                 // illegal
1+1;               // illegal
cast(void)(x + x); // ok
}
