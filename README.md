# Personal Cloud Storage (PWA)

A mobile-friendly static Progressive Web App (PWA). It stores selected files in this browser using IndexedDB and can upload them to Google Drive after OAuth is configured.

## Publish free with GitHub Pages

1. Open [repository Pages settings](https://github.com/ronhalder93/personal-cloud-storage/settings/pages).
2. Under **Build and deployment**, select **Deploy from a branch**.
3. Choose branch **main** and folder **/(root)**, then press **Save**.
4. Wait for GitHub Pages to finish publishing. This repository's expected URL is [https://ronhalder93.github.io/personal-cloud-storage/](https://ronhalder93.github.io/personal-cloud-storage/).
5. Open the HTTPS site in Android Chrome. Use the browser menu and choose **Install app** or **Add to Home screen**.

If the site shows 404, revisit Pages settings and check the deployment status in the repository's **Actions** tab.

## Configure Google Drive

1. Open the [Google Drive API page](https://console.cloud.google.com/apis/library/drive.googleapis.com) and enable the API in your Google Cloud project.
2. Configure Google Auth Platform / OAuth consent. If the app is in testing mode, add the Google account you will use as a test user.
3. In [OAuth Clients](https://console.cloud.google.com/auth/clients), create an OAuth client with application type **Web application**.
4. Under **Authorized JavaScript origins**, add exactly `https://ronhalder93.github.io` (no path and no trailing slash).
5. In the app, expand **Drive setup**, paste the OAuth Client ID (ends in `.apps.googleusercontent.com`), the app pre-fills your saved folder ID; replace it or clear it if you want a different destination, and tap **Save settings**.
6. Tap **Connect Drive** and approve the permission request. Upload local files with the **↑** button or **Sync pending**.

Never enter your Google password in this app. Do not put an OAuth client secret in browser code; this app only needs the public client ID.

## What works and what to know

- **Local files:** Selected files are stored in IndexedDB on this browser/device. The browser may run out of storage; if saving fails, free space and retry. Clearing browser data can delete local copies.
- **Drive upload/list/download:** These require a configured OAuth client and user consent. The app requests the `drive.file` scope; it does not automatically gain access to every existing file in your Drive.
- **Google Workspace files:** Google Docs/Sheets/Slides files visible to the app are downloaded as PDF exports. Drive folders are listed but are not downloadable as individual files.
- **Offline/PWA:** The app shell is cached for offline opening. A new service-worker version now activates immediately so updated app files can replace older cached files.
- **Troubleshooting:** Drive errors now show Google's API error message when available. Check the OAuth origin, test-user configuration, API enablement, and any folder permissions.
- **Not an APK:** This is a website/PWA, not a native Android application. Cross-device conflict resolution and a full backup system are not implemented. Keep separate backups of important files.
