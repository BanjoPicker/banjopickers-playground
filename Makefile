SHELL := /bin/bash

build/%.o: src/test/c++/%.cpp
	mkdir -p build
	g++ -I src/test/c++ -c -o $@ $<

build/%: build/%.o
	g++ -o $@ $<

all: build/FilamentTest
