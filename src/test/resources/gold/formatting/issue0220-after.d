static if (someCondition)
    void doStuff() {
    }
else
    void doStuff() {
    }

static if (someCondition)
    void doStuff() {
    }
else static if (otherCondition)
    void doStuff() {
    }

static if (someCondition)
    void doStuff() {
    }
else static if (otherCondition)
    void doStuff() {
    }
else
    void doStuff() {
    }

static if (condition)
    int a;
else
    int b;

static if (condition)
    int a;
else static if (otherCondition)
    int c;
else
    int b;

void doStuff();

static if (stuff)
    int a;
else
    class C {
    public:
        void aFunction();
    private:
        int a;
        int b;
    }

static if (condition)
    int a;
else
    int b;

static if (condition)
    int a;
else static if (otherCondition)
    int c;
else
    int b;
