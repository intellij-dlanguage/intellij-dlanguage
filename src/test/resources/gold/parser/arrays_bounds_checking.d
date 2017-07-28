unittest
{
try
{
    for (i = 0; ; i++)
    {
        array[i] = 5;
    }
}
catch (RangeError)
{
    // terminate loop
}


for (i = 0; i < array.length; i++)
{
    array[i] = 5;
}


int[3] foo;
int x = foo[3]; // error, out of bounds
}
