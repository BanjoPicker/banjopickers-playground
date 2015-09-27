#ifndef SCHUMACT_WIDGET_H__
#define SCHUMACT_WIDGET_H__
#include <iostream>
#include <string>

#define WDEBUG(x) ::std::cout << "Debug: " << x << ::std::endl

namespace schumact {

class Widget {
 public:
  friend void swap(Widget& a, Widget& b) {
    WDEBUG(__PRETTY_FUNCTION__);
    ::std::swap(a.name_, b.name_);
  }

  Widget(const std::string& name) : name_(name) {
    WDEBUG(__PRETTY_FUNCTION__ + name_);
  }

  Widget(const Widget& widget) : name_(widget.name_) {
    WDEBUG(__PRETTY_FUNCTION__ + name_);
  }

  // Note that without the noexcept, the regular copy ctor is called
  // when growing vectors.
  Widget(Widget&& widget) noexcept : name_(std::move(widget.name_)) {
    WDEBUG(__PRETTY_FUNCTION__ + name_ + "=" + widget.name_);
  }

  Widget& operator=(Widget&& widget) noexcept {
    WDEBUG(__PRETTY_FUNCTION__ + name_ + "=" + widget.name_);
    name_ = std::move(widget.name_);
    return *this;
  }

  Widget& operator=(Widget widget) {
    WDEBUG(__PRETTY_FUNCTION__ + name_ + "=" + widget.name_);
    swap(*this, widget);
  }

  ~Widget() {
    WDEBUG(__PRETTY_FUNCTION__ + name_);
  }

 private:
  std::string name_;
};  // class Widget

class WidgetFactory {
 public:
  static Widget MakeWidget(const std::string& name) {
    WDEBUG(__PRETTY_FUNCTION__);
    return Widget(name);
  }
};  // class WidgetFactory

}  // namespace schumact

#endif
