#!/bin/bash
set -e

echo "Building APK..."
chmod +x gradlew
./gradlew clean assembleDebug

echo ""
echo "✓ APK built successfully!"
echo "Location: app/build/outputs/apk/debug/app-debug.apk"
