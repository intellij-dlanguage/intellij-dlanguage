
abstract class Shape {
    protected int <resolved>_width, _height;
}

class Square : Shape {

    this(int width, int height) {
        // _width & _height will have warning for 'Possibly undefined symbol'
        <ref>_width = width;
        _height = height;
    }
}
