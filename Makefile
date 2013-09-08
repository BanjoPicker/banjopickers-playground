SHELL := /bin/bash

build/%.o: src/test/c++/%.cpp $(shell find src/test/c++ -type f -name '*.hpp')
	mkdir -p build
	g++ -std=c++11 -I src/test/c++ -c -o $@ $<

build/%: build/%.o
	g++ -o $@ $< -lpthread

TARGETS = build/FilamentTest
TARGETS += build/rangefor

.phony: all
all: $(TARGETS)

.phony: clean
clean:
	@echo ""
	rm -rf build dist
