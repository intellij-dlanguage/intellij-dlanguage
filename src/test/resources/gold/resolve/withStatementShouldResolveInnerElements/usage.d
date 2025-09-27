
enum E { <resolved>A, B};

void foo() {
    E e;
    
    with (e) {
        switch (e) {
            case <ref>A:
            break;
        }
    }
}