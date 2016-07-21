int[3] abc;              // static array of 3 ints
int[] def = [ 1, 2, 3 ]; // dynamic array of 3 ints

void dibb(int* array)
{
    array[2];     // means same thing as *(array + 2)
    *(array + 2); // get 3rd element
}

void diss(int[] array)
{
    array[2];     // ok
    *(array + 2); // error, array is not a pointer
}

void ditt(int[3] array)
{
    array[2];     // ok
    *(array + 2); // error, array is not a pointer
}