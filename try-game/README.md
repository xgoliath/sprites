# try-game

This is an Android Studio game project (Kotlin) - initial skeleton for the Aama + Malsapro game.

How this repo is organized
- app/: Android app module
- The project includes a Gradle task that copies sprite PNGs and audio files from the repository root into the app/assets folder at build time, so you don't need to duplicate binary files. The original sprite files live at the repository root (e.g., standing.png, walking.png, marten-run.png, etc.).

Build & run (quick)
1. Open try-game in Android Studio.
2. Build -> Make Project. The Gradle copy task will copy sprites from the repository root into app/src/main/assets/ before compilation.
3. Run the app on a device or emulator (minSdk 21).

Notes
- This is the initial skeleton. I'll continue implementing gameplay (player, companion, enemies, platforms) in follow-up commits on the try-game-initial branch.
