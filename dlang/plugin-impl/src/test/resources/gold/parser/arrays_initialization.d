int[3] a = [ 1:2, 3 ]; // a[0] = 0, a[1] = 2, a[2] = 3

enum Color { red, blue, green };

int value[Color.max + 1] =
  [ Color.blue :6,
    Color.green:2,
    Color.red  :5 ];

private void failure()
{
    static struct A {
        int a, b, c;
    }

    A [2][2] tests = [
        [ {1, 2, 3}, {}],
        [ {1}, ]
    ];
}
