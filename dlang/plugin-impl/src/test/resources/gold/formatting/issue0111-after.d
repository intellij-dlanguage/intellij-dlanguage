struct Test {
    this(string name, string[] aliasList, string briefDescription, string examDesc, string onOpenDesc,
            string openDesc, string onCloseDesc, string closeDesc,
            Flag!"canOpen" canOpen, Flag!"canClose" canClose, Flag!"isOpen" isOpen) {
    }
}
