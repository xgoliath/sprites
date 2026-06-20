#!/usr/bin/env python3
import subprocess
import sys
import os

# Build APK
print("Building APK...")
result = subprocess.run(["./gradlew", "assembleDebug"], cwd=".")

if result.returncode == 0:
    print("\nAPK built successfully!")
    print("Location: app/build/outputs/apk/debug/app-debug.apk")
else:
    print("Build failed")
    sys.exit(1)
