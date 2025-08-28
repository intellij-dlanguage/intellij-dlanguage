
void foo(int i) {
    
    switch(i) {
        case 1:
        break;
        default:
        int <resolved>j = 3;
        <ref>j++;
        break;
    }
}