#!/bin/bash

#check args
if [ $# -ne 2 ]; then
  echo "Wrong args count"
	exit 1
fi

if ! [ -f "$1" ]; then
  echo "First arg not a file"
	exit 1
fi

if ! [ -d "$2" ]; then
  mkdir -p "$2"
  echo "Target dir created"
fi

#create new file name
file=${1##*/}
filename=${file%.*}
ext=${file##*.}
timestamp=$(date "+%Y.%m.%d_%H.%M.%S")
dst_file="${filename}_$timestamp.$ext"

#copy
cp -p "$1" "$2/$dst_file"

#check result
if [ $? -eq 0 ]; then
  echo "Archived successfully $2/$dst_file"
	exit 0
else
  echo "Copy error"
	exit 1
fi