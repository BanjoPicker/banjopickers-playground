#ifndef SCHUMACT_WIDGET_H__
#define SCHUMACT_WIDGET_H__
#include <iostream>
#include <string>

#define WDEBUG(x) ::std::cout << "Debug: " << x << ::std::endl

namespace schumact {

class Widget {
 public:

  Widget(const std::string& name) : name_(name), data_(nullptr) {
    WDEBUG(__PRETTY_FUNCTION__ + name);
  }

  Widget(const std::string& name, int value) : name_(name), data_(nullptr) {
    WDEBUG(__PRETTY_FUNCTION__ + name);
    data_ = new int;
    *data_ = value;
  }

  Widget(const Widget& widget) : name_(widget.name_), data_(nullptr) {
    WDEBUG(__PRETTY_FUNCTION__ + name_);

    // Deep Copy:
    if (widget.data_) {
      data_ = new int;
      *data_ = *widget.data_;
    }
  }

  Widget(Widget&& widget) noexcept : name_(std::move(widget.name_)),
                                     data_(std::exchange(widget.data_, nullptr)) {
    WDEBUG(__PRETTY_FUNCTION__);
  }

  Widget& operator=(Widget&& widget) noexcept {
    WDEBUG(__PRETTY_FUNCTION__ + name_ + "=" + widget.name_);

    name_ = std::move(widget.name_);
    data_ = std::exchange(widget.data_, nullptr);
    
    return *this;
  }

  Widget& operator=(const Widget& widget) {
    WDEBUG(__PRETTY_FUNCTION__);

    // 1. Copy:
    Widget copy(widget);

    // 2. Swap:
    if (data_) { delete data_; }
    name_ = std::move(copy.name_);
    data_ = std::exchange(copy.data_, nullptr);
 
    return *this;   
  }

  std::ostream& print(std::ostream& os) {
    os << "name: " << name_ << ", " << "data: " << data_;
    if (data_) { os << " (" << *data_ << ")"; }
    os << std::endl;

    return os;
  }
    

  ~Widget() {
    WDEBUG(__PRETTY_FUNCTION__ + name_);
    if (data_) { delete data_; }
  }

 private:
  ::std::string   name_;
  int*            data_;
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
