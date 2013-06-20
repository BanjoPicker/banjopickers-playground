#!/bin/bash

function onexit() {
    echo "Bye-bye!"
}

function onsigint() {
    echo "got a sigint!"
}

function onsighup() {
    echo "You'll have to try harder than that to hang up!"
}

trap onsighup   SIGHUP
trap onsigint   SIGINT
trap onexit     EXIT

echo "start of script"


