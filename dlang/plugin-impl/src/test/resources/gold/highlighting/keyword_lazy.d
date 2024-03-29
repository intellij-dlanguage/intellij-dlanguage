void log(lazy char[] dg)
{
    if (logging)
        fwritefln(logfile, dg());
}
