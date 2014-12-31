  foreach (i; 0 .. 10_000) {
        synchronized {
            int temp = *b;
            *b = *a;
            *a = temp;
        }
    }
