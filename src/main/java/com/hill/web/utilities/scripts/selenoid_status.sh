#!/bin/bash

set -euo pipefail

target_dir="$1"
first_dir="$PWD"

containers=(selenoid-24 selenoid-ui-24)

cd "$target_dir"

running=$(docker-compose ps)

all_running=true
for container in "${containers[@]}"; do
    if ! echo "$running" | grep -q "$container.*Up"; then
        all_running=false
        break
    fi
done

echo "$all_running"

cd "$first_dir"
exit 0