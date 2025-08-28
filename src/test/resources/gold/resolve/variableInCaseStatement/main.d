
void foo(int i) {
    
    switch(i) {
        case 1:
        int <resolved>j = 3;
        <ref>j++;
        break;
    }
}