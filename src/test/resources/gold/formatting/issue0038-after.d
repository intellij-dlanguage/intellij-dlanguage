static int isInfinity(double r) {
    auto a = r is double.infinity || r is -double.infinity;
    auto b = r is double.infinity || r !is -double.infinity;
}
