#!/bin/bash
for DEVICE in $(adb devices | awk '{print $1}'); do
   if [[ "$DEVICE" =~ ^emulator-* ]]; then
       echo "Stopping ${DEVICE}"
       adb -s "${DEVICE}" emu kill
   fi
done