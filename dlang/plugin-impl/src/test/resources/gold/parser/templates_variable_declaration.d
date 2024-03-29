// Templatized variable declaration

int twice(int x) = 2 * x;
const int twice(int x) = 2 * x;
immutable int twice(int x) = 2 * x;

void foo()
{
    immutable int twice(int x) = 2 * x;
}