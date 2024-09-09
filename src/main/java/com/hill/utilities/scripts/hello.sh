#!/bin/bash

if [ $# -eq 0 ]; then
  echo "No arguments provided!"
  exit 1
fi

name=$1
echo -e "Hello $name"
exit 0