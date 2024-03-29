unittest {
    with (SomeEnum) final switch (value) {
    case a:
        aa();
        break;
    case b:
        bb();
        break;
    }
}
