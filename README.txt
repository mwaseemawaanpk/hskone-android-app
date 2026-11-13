HSK 1 Audio Companion - Android test app project

This project packages your supplied HTML learning tool inside a basic Android WebView app.
The HTML file is stored at app/src/main/assets/index.html.

What is included:
- Existing HTML/CSS/JavaScript learning tool bundled inside the app
- JavaScript and DOM storage enabled
- Internet permission for the existing online Urdu text-to-speech feature
- Android back-button support
- Minimum Android 6.0 (API 23), target Android 16 (API 36)

NO ANDROID STUDIO REQUIRED ON YOUR COMPUTER: build the test APK in GitHub's cloud.

Steps (using a web browser):
1. Sign in to https://github.com/ or create a free account.
2. Create a new repository. Name it hsk1-audio-companion. You may keep it private.
3. On the new repository page, choose Add file > Upload files.
4. Extract this ZIP on your computer first. Open the extracted hsk1-android-project folder.
5. Upload the CONTENTS of that folder (including .github, app, build.gradle.kts,
   settings.gradle.kts and gradle.properties) into the GitHub repository root.
   Make sure the .github/workflows/build.yml file is included.
6. Commit the uploaded files to the main branch.
7. Open the repository's Actions tab. Select "Build Android test APK".
8. Click "Run workflow" if it has not started automatically. Wait for the run to finish
   with a green check mark.
9. Open the completed workflow run. At the bottom, download the artifact named
   HSK1-Audio-Companion-test-APK.
10. Extract the downloaded artifact ZIP. It contains app-debug.apk.
11. Transfer app-debug.apk to your Android phone and tap it to install. If Android asks,
    allow your browser or file manager to install unknown apps for this installation.

Notes:
- This is a DEBUG/test APK for your own device, not a Play Store release.
- You do not need a Play Console account or payment to test it.
- The APK is built by GitHub Actions; Android Studio is not needed on your PC.
- Test the lessons, pronunciation, quizzes, screen rotation and Urdu audio on your phone.
- The Urdu text-to-speech feature uses an online Google Translate endpoint in the supplied
  HTML, so that particular feature needs internet access and may change independently.
- This project has not been tested on your physical phone yet.
