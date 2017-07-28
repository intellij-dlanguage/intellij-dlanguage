unittest {
    switch (something) with (stuff) {
    case 1:
    case 2:
    label:
        doStuff();
    case 3: .. case 4:
        doOtherStuff();
        goto label;
    default:
        break;
    }
}
