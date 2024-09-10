module usage;

void foo() {
    <resolved>outer:
    while (true) {
        int j = 0;
        int l = 30;

        while (j < 1000) {
            if (l == 3) {
                continue <ref>outer;
            }
            l /= 2;
            j++;
        }
    }

}
