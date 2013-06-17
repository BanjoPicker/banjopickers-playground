SHELL := /bin/bash

build/%.o: src/test/c++/%.cpp $(shell find src/test/c++ -type f -name '*.hpp')
	mkdir -p build
	g++ -I src/test/c++ -c -o $@ $<

build/%: build/%.o
	g++ -o $@ $<

TARGETS = build/FilamentTest

.phony: all
all: $(TARGETS)

.phony: clean
clean:
	@echo ""
	rm -rf build dist
