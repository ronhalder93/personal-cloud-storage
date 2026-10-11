# Personal Cloud Storage (PWA)

A mobile-friendly static PWA prototype. It can keep selected files in this browser using IndexedDB and upload them to Google Drive after you configure Google OAuth.

## Publish free with GitHub Pages
1. Create a **public** GitHub repository named `personal-cloud-storage`.
2. Upload the *contents* of this ZIP to the repository root (so `index.html` is at the top level, not inside a subfolder).
3. Open **Settings → Pages**.
4. Under Build and deployment, choose **Deploy from a branch**, branch `main`, folder `/(root)`, then Save.
5. Wait for GitHub Pages to publish. The URL will look like `https://YOUR-USERNAME.github.io/personal-cloud-storage/`.
6. Open that HTTPS URL on Android Chrome; use the browser menu and choose **Install app** or **Add to Home screen**.

## Configure Google Drive uploads
1. In Google Cloud Console, create/select a project and enable the Google Drive API.
2. Configure Google Auth Platform / OAuth consent and add your Google account as a test user if requested.
3. Create an OAuth client of type **Web application**.
4. Add the exact deployed HTTPS origin (for example `https://YOUR-USERNAME.github.io`) under **Authorized JavaScript origins**.
5. In the app, expand **Drive setup**, enter the OAuth Client ID, confirm the folder ID, save settings, and tap **Connect Drive**.
6. Approve access and upload local files using the up-arrow button.

Never enter your Google password in the app. Do not share your OAuth client secret (this app does not need one in browser code).

## Important limitations
- This is a static PWA, not a native Android APK.
- The live site is not published by creating this ZIP; you must complete the hosting steps above.
- Drive upload and cloud-file listing are not active until OAuth is configured and tested. The app can list/download files visible under its `drive.file` permission; it does not automatically browse every pre-existing file in your Drive. Google Workspace documents may need export handling. Cross-device conflict resolution is not implemented.
- Local files are stored only in this browser/device. Clearing browser data can remove them. Keep backups of important files.
- The existing Drive folder may require additional access/consent configuration; if upload fails, check the OAuth setup and folder permissions.
