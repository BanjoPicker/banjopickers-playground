#include<iostream>

/**
 *  This play program demonstrates the use of the keyword 'explicit' on constructors for classes.
 *  The basic idea is the for single argument constructors, the compiler will automaticcaly (implicitly) 
 *  convert from the argument type to the class type when needed.  You can prevent this behaviour by 
 *  using the lkeyword explicit on any constructor you do not wish to behave this way.
 *
 */

class Foo {

    private:
        int  x;
        bool y;

    public:
        explicit Foo(int x) : x(x), y(false) {}  // disallow automatic conversion from int to Foo by using keyword explicit
        Foo(bool y)         : x(0), y(y)     {}  // allow automatic conversion from bool to Foo by not using keyword explicit
        Foo(int x, bool y)  : x(x), y(y)     {}

        int  getX(void) const { return this->x; }
        bool getY(void) const { return this->y; }
};

int f(Foo foo) {
    return foo.getX() + static_cast<int>(foo.getY());
}

int main(int argc, char* argv[]) {

    Foo foo(5);
    Foo bar(5,6); 


    std::cout << f(bar) << std::endl;           // No problem, call f with an already existing instance of Foo
    // std::cout << f(10) << std::endl;         // Does not compile, no conversion from int to Foo because Foo(int) is explicit!
    std::cout << f(true) << std::endl;          // No problem here! because Foo(bool) is not marked explicit, we get an implicit call to Foo(bool) for the function argument
    // std::cout << f(10, true) << std::endl;   // also does not compile, cannot do implicit conversions with more than one argument (wrong signature for f - too many arguments!)
    std::cout << f(Foo(4,true)) << std::endl;   // No problem, can pass in instances of Foo directly.
    return 0;
}

