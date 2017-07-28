unittest {
    switch (x) {
    case a:
        return;
        version (A) {
    case b:
            return;
        }
    }
}
