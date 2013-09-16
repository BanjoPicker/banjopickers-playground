SHELL := /bin/bash

BOOST_HOME = /opt/boost-1.54.0
BOOST_LIB  = $(BOOST_HOME)/lib

GXX = $(shell which clang++)
GXX_OPTS = --std=c++11
GXX_INCL = -Isrc/test/c++ -I$(BOOST_HOME)/include
GXX_LIBS = -lpthread -lboost_filesystem -lboost_system

build/%.o: src/test/c++/%.cpp $(shell find src/test/c++ -type f -name '*.hpp')
	@mkdir -p build
	@echo ""
	$(GXX) $(GXX_OPTS) $(GXX_INCL) -c -o $@ $<

build/%: build/%.o
	$(GXX) -o $@ $< -L$(BOOST_LIB) $(GXX_LIBS)

TARGETS = build/FilamentTest
#TARGETS += build/rangefor
TARGETS += build/boost
TARGETS += build/explicit
TARGETS += build/self

.phony: all
all: $(TARGETS)

.phony: clean
clean:
	@echo ""
	rm -rf build dist
