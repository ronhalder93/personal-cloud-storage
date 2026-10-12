# Copy Happy Summer APK to Google Drive

The APK is already hosted as a GitHub Release asset. This optional workflow copies it from GitHub's runner to your personal Google Drive; it does not download the APK to your phone.

## Required one-time setup

The workflow now uses **personal Google OAuth**. Do not use a service-account JSON key: personal Drive uploads using a service account can fail with `storageQuotaExceeded`.

### 1. Enable Drive API and create OAuth credentials

1. Open [Google Cloud Credentials](https://console.cloud.google.com/apis/credentials) and select/create a project.
2. Enable the [Google Drive API](https://console.cloud.google.com/apis/library/drive.googleapis.com).
3. Configure Google Auth Platform / OAuth consent. If the app is in **Testing**, add the Google account that owns the destination Drive as a test user. Note: refresh tokens for an external app left in Testing can expire after 7 days.
4. Create an OAuth client with application type **Web application**.
5. Add this exact **Authorized redirect URI** to that client: `https://developers.google.com/oauthplayground`.
6. Copy the OAuth client ID and client secret. Keep the client secret private.

### 2. Generate a refresh token

1. Open [Google OAuth Playground](https://developers.google.com/oauthplayground).
2. Open its settings (gear icon), enable **Use your own OAuth credentials**, and enter the client ID and client secret created above.
3. In Step 1, enter this scope: `https://www.googleapis.com/auth/drive`, then authorize with the Google account whose Drive should receive the APK.
4. In Step 2, exchange the authorization code for tokens. Copy the **refresh token** from the token response. Never share it in chat or commit it to the repository.

### 3. Add GitHub repository secrets

Open [Repository Actions secrets](https://github.com/ronhalder93/personal-cloud-storage/settings/secrets/actions) and add these exact secrets:

- `DRIVE_OAUTH_CLIENT_ID` — OAuth client ID
- `DRIVE_OAUTH_CLIENT_SECRET` — OAuth client secret
- `DRIVE_OAUTH_REFRESH_TOKEN` — refresh token from OAuth Playground
- `DRIVE_FOLDER_ID` — optional; the ID from the destination Drive folder URL. If omitted, the file is uploaded to the account's Drive root.

Do not add the service-account secret `DRIVE_SERVICE_ACCOUNT_JSON`; the current workflow does not use it.

### 4. Run and verify the transfer

Open [Copy Happy Summer APK to Google Drive](https://github.com/ronhalder93/personal-cloud-storage/actions/workflows/copy-happy-summer-to-drive.yml), select **Run workflow**, and wait for the run to finish. The upload is complete only when the workflow is green and its logs say **Google Drive upload complete**.

The workflow downloads the APK on GitHub's runner and uses resumable upload, so the phone does not need to download the large APK first. The destination Drive must have enough available storage.

## Important

- This file is a third-party APK; its authenticity has not been independently verified. Scan it before installing.
- Never commit OAuth secrets, refresh tokens, or credential files to the repository.
- If the workflow reports missing secrets or an OAuth error, verify the exact secret names and regenerate/replace the refresh token if necessary.
