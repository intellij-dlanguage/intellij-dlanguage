unittest {
    version (Windows)
        __locale_decpoint = save;
}

unittest {
    version (Windows)
        __locale_decpoint = save;
    else
        __locale_decpoint = save;
    version (Win32) int x;
}
