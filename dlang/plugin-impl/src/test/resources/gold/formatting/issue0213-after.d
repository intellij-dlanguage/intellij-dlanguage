version (linux)
    import core.sys.linux.elf;
else version (FreeBSD)
    import core.sys.freebsd.sys.elf;
else version (Solaris)
    import core.sys.solaris.elf;
