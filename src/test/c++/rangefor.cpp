/*
 *  <p>Simple demonstraion of range based for loops from C++11</p>
 *
 */
#include<iostream>
#include<vector>
#include<string>

int main(int argc, char* argv[]) {

    // Simple c-style array iteration:
    int arr[] = {1,2,3,4,5};
    for(int& x : arr) {
        std::cout << "c-style array loop: x=" << x << std::endl;    
    }

    // begin()/end() iterator style loop:
    std::vector<std::string> vec = { "foo", "bar", "baz" };
    for(std::string& s : vec) {
        std::cout << "begin()/end() iterator loop: s=" << s << std::endl;    
    }

    // initializer list verion:
    for(auto d : {3.14, 3.141, 3.1415, 3.14159}) {
        std::cout << "initializer list loop: d=" << d << std::endl;    
    }

    return 0;
}
