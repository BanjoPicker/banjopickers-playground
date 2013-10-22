#include<iostream>  // cout
#include<algorithm> // for_each 
#include<vector>    // vector

// visitor function for each element of a vector:
void visit(int x) {
    std::cout << "visit " << x << std::endl;
}

class cIntPrinter {
    public: void operator()(int& i) { std::cout << "IntPrinter " << i << std::endl; };
};

int main(int argc, char* argv[]) {

    // create a vector of ints, populate it with some ints.
    std::vector<int> v;
    for(int i=0;i<10;i++) v.push_back(i);

    // pass function pointer as the argument to apply to each:
    std::for_each(v.begin(), v.end(), visit);

    // use a class that implements the operator() as a 
    cIntPrinter intPrinter;
    std::for_each(v.begin(), v.end(), intPrinter);
    
    return 0;
}
