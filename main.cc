#include <vector>

#include "schumact/widget.h"

using schumact::Widget;

int main(int argc, char* argv[]) {

 Widget w("w", 1);

 Widget x("x");
 Widget y("y", 3);

 w.print(std::cout);
 x.print(std::cout);
 y.print(std::cout);

 std::vector<Widget> v = {};
 v.push_back(w);
 v.emplace_back("rvalue");

 x = std::move(y);
 x.print(std::cout);
 y.print(std::cout);
 

 return 0;
}
