module gold.folding.import;

import <fold text='...'>a.b;
import c.d;
import e.f;</fold>

void main(string[] args) <fold text='{...}'>{
    import <fold text='...'>a.b;
    import c.d;
    import e.f;</fold>

    enum A <fold text='{...}'>{
        v1,
        v2
    }</fold>

    import <fold text='...'>a.b;
    import c.d;</fold>
}</fold>
