SHELL := /bin/bash

BOOST_HOME = /opt/boost-1.54.0
BOOST_LIB  = $(BOOST_HOME)/lib

build/%.o: src/test/c++/%.cpp $(shell find src/test/c++ -type f -name '*.hpp')
	mkdir -p build
	g++ -std=c++11 -I src/test/c++ -c -o $@ $<

build/%: build/%.o
	g++ -o $@ $< -L$(BOOST_LIB) -lpthread -lboost_filesytem -lboost_system

TARGETS = build/FilamentTest
TARGETS += build/rangefor
TARGETS += build/boost

.phony: all
all: $(TARGETS)

.phony: clean
clean:
	@echo ""
	rm -rf build dist
