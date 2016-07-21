@disable void foo() { }

void main()
{
    foo();   // error, foo is disabled
}