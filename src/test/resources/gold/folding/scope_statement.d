
void main() <fold text='{...}'>{

    auto a = malloc();

    scope(exit)
        free(a);

    scope(success) <fold text='{...}'>{
        a = null;
    }</fold>
}</fold>