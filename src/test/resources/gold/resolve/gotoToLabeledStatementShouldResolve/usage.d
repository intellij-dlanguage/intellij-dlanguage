module usage;

void foo() {
    int i;

    int j;

    int k;

    int l;

    switch (l) {
        case 1:
        break;
        case 2:
        goto <ref>L3;
        default:
        return;
    }

    int m;
    <resolved>L3: m++;
    l4: m--;
    return;
}
