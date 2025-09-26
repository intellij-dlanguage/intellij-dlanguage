
abstract class Shape {
    protected int <resolved>_width, _height;
}

class Square : Shape {

    this(int width, int height) {
        // it's the same when prefixed with super
        super.<ref>_width = width;
        super._height = height;
    }
}