SHELL := /bin/bash



BOOST_HOME = /opt/boost-1.54.0
BOOST_LIB  = $(BOOST_HOME)/lib

PROTOC_INCS = $(PROTOC_HOME)/include
PROTOC_LIBS = $(PROTOC_HOME)/lib

GXX = $(shell which g++)
GXX_OPTS = 
GXX_INCL = -Isrc/test/c++ -I$(BOOST_HOME)/include
GXX_LIBS = -lpthread -lboost_filesystem -lboost_system

build/%.o: src/test/c++/%.cpp $(shell find src/test/c++ -type f -name '*.hpp')
	@mkdir -p build
	@echo ""
	$(GXX) $(GXX_OPTS) $(GXX_INCL) -c -o $@ $<

build/%.o: src/test/c++/%.cc $(shell find src/test/c++ -type f -name '*.hpp')
	@mkdir -p build
	@echo ""
	$(GXX) $(GXX_OPTS) $(GXX_INCL) -c -o $@ $<

build/%: build/%.o
	$(GXX) -o $@ $< -L$(BOOST_LIB) $(GXX_LIBS)

build/gen/%.h: src/test/c++/%.proto
	@echo ""
	@mkdir -p build/gen
	protoc -Isrc/test/c++ -Ibuild/gen --cpp_out=build/gen $<
	protoc -Isrc/test/c++ -Ibuild/gen --java_out=build/gen $<
	protoc -Isrc/test/c++ -Ibuild/gen --python_out=build/gen $<

TARGETS = build/FilamentTest
#TARGETS += build/rangefor
TARGETS += build/boost
TARGETS += build/explicit
TARGETS += build/self
<<<<<<< HEAD
TARGETS += build/for_each
TARGETS += build/play
=======
TARGETS += build/to_string
TARGETS += build/gen/test.h
TARGETS += build/gen/org/bp.h
>>>>>>> ef79c92b92c7380305f47eda6c2a9d2c5525d0fe

.phony: all
all: $(TARGETS)

.phony: clean
clean:
	@echo ""
	rm -rf build dist
