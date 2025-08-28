
void foo(int i) {
    
    switch(i) {
        case 14: .. case 15:
        int <resolved>j = 3;
        <ref>j++;
        break;
    }
}