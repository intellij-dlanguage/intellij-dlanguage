unittest {
    if (imin.value > 0x10FFFFUL) // ??
        imin.value = 0x10FFFFUL; // ??
    with (stuff) switch (a) {
    case a:
        doStuff();
        break;
    }
    switch (a) {
    }
    if (something) /** whatever */
        doStuff();
    if (something) /+ comment +/ {
        doStuff();
    }
}
