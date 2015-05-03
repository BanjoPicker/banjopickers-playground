#include <vector>

#include "schumact/widget.h"

using schumact::Widget;
using schumact::WidgetFactory;
using std::cout;
using std::endl;
using std::vector;

int main(int argc, char* argv[]) {
  Widget a("test1");
  Widget b("test2");
  Widget c = std::move(b);
  swap(a,b);

  vector<Widget> widgets;
  widgets.emplace_back("foo");
  widgets.emplace_back("bar");
  widgets.emplace_back("baz");

  c = std::move(WidgetFactory::MakeWidget("makewidget"));
  return 0;
}
