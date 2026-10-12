# Copy Happy Summer APK to Google Drive

The APK is already hosted as a GitHub Release asset. This optional workflow copies it from GitHub's server to Google Drive; it does not download the APK to your phone.

## One-time setup (requires Google Cloud configuration)

1. In Google Cloud Console, create/select a project and enable the **Google Drive API**.
2. Create a **service account** and generate a JSON key. Keep the key private.
3. In Google Drive, create or choose the destination folder and share that folder with the service account's email address as an Editor.
4. In this GitHub repository, open **Settings → Secrets and variables → Actions → New repository secret**.
5. Add secret **DRIVE_SERVICE_ACCOUNT_JSON** and paste the entire service-account JSON key file contents into the secret field. Never paste the key into a chat, commit, or public file.
6. Optionally add secret **DRIVE_FOLDER_ID** with the destination folder ID from its Google Drive URL. If omitted, the service account's own Drive root is used (this may not appear in your personal Drive unless you use a shared folder).
7. Open **Actions → Copy Happy Summer APK to Google Drive → Run workflow**.

The workflow downloads the existing 1.69 GB release asset on GitHub's runner and uploads it with resumable upload. The phone does not need to download the APK during this server-side transfer. It can take a while and Google Drive storage/quota must have enough space.

## Important

- This file is a third-party APK; its authenticity has not been independently verified. Scan it before installing.
- Do not store service-account JSON in the repository or expose it publicly.
- This transfer has not happened until the workflow run finishes successfully.
