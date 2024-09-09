#!/bin/bash

if [ $# -eq 0 ]; then
  echo "No arguments provided!"
  exit 1
fi

target_dir="$1"
first_dir="$PWD"

cd "$target_dir"
allure generate --single-file target/allure-results --force
cd "$first_dir"
exit 0