void msgpackToGValue(MsgValue input) {
    with (MsgValue.Type) switch (input.type) {
    case boolean:
        a();
        break;
    case unsigned:
        b();
        break;
    default:
        assert(false);
    }
    return retVal;
}
