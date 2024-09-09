#!/bin/bash
if curl -s http://localhost:4723/status | grep -q '"ready":true'; then
    echo 'true'
else
    echo 'false'
fi
exit 0