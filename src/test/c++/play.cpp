// General purpose main used for playing around with whatever I need.

#include <algorithm>
#include <iostream>
#include <string>

using std::cerr;
using std::cout;
using std::endl;
using std::string;

typedef struct {
  bool bad_comment;
  int dash_count;
  int newline_count;
  int tab_count;
  size_t total_comment_length;
} comment_data_t;

string to_string(const comment_data_t& data) {
  string result;
  result.reserve(64);
  result.append(std::to_string(data.bad_comment)).append(",");
  result.append(std::to_string(data.newline_count).append(","));
  result.append(std::to_string(data.total_comment_length));
  return result;
}

int main(int argc, char* argv[]) {
  const string s("this <!-- is a comment --> test!\n"
                 " with <!-- another--> comment ");

   comment_data_t result;
  size_t offset = 0;
  while ((offset = s.find("<!--", offset)) != string::npos) {
    offset += 4;  // len("<!--")
    const size_t end = s.find("-->", offset);
    if (end == string::npos) {
      result.bad_comment += 1;
    } else {
      result.total_comment_length += (end - offset);
      for (size_t i = offset; i < end; ++i) {
        cout << i << ", " << s.at(i) << endl;
        switch (s.at(i)) {
          case '-': ++result.dash_count; break;
          case '\n': ++result.newline_count; break;
          case '\t': ++result.tab_count; break;
        }
      }
    }
  }
  cout << to_string(result) << endl;
  return 0;
}
