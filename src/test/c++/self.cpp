/**
 *  This is a play program to demonstrate the self-assignment 
 *  problem with operator=()
 *
 *  The class we will use is a class that contains a pointer to an int.
 */

#include<iostream>
#include<sstream>
#include<string>

class Foo {

    public: Foo(const int& val) {
        std::cout << "Foo::Foo(const int&)" << std::endl;
        this->x = new int;
        *x = val;
    }

    public: ~Foo() {
        std::cout << "Foo::~Foo()" << std::endl;
        delete x;
    }

    public: Foo(const Foo& foo) {
        std::cout << "Foo::Foo(const Foo&)" << std::endl;
        this->x = new int;
        *x = *(foo.x);
    }
    
    public: void swap(Foo& foo) {
        int * const t = x;
        x = foo.x;
        foo.x = t;
    }

    public: const Foo& operator=(const Foo& foo) {
        std::cout << "Foo::operator=()" << std::endl;
        if(this == &foo) { 
            std::cout << "Self assignment attempted, no-op!" << std::endl;
            return *this;  // do not allow self assignments!
        }
        *x = foo.getX();
        return *this;
    }

    public: std::string toString(void) const {
        std::stringstream result;
        result << "Foo[" << x << ", " << *x << "]";
        return result.str();
    }

    public: int getX(void) const { 
        return *x; 
    }

    public: void setX(const int val) { 
        *x = val; 
    }

    // data //////////////////////////

    protected: int* x;
};

int main(int argc, char* argv[]) { {
    Foo a(9);
    Foo b = a;  // same as Foo b(a);
    Foo c(5);
    std::cout << a.toString() << std::endl;
    std::cout << b.toString() << std::endl;
    std::cout << c.toString() << std::endl;
    c = a;
    std::cout << c.toString() << std::endl;
    c = c;
    std::cout << c.toString() << std::endl;

    std::cout << "Swap test ------------------" << std::endl; 
    b.setX(10);
    std::cout << a.toString() << " " << b.toString() << std::endl;
    a.swap(b);
    std::cout << a.toString() << " " << b.toString() << std::endl;
    }  // scoped to print end of main AFTER objects get destructed
    std::cout << "::main()" << std::endl;
    return 0;
}
