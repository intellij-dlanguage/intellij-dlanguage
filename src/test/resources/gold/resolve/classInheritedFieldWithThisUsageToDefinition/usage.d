
abstract class Shape {
    protected int <resolved>_width, _height;
}

class Square : Shape {

    this(int width, int height) {
        // or when prefixed with this
        this.<ref>_width = width;
        this._height = height;
    }
}