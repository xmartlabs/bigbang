# Android Base Project

## Assets

The configuration assets are in a [Google Drive folder](PUT LINK).
To access them, ask via email.

The easiest way to copy them in the repo folder, is by downloading the GDrive folder and running the
following:

```bash
./copy-assets.sh path-of-assets-folder
```

If you want to do it manually, or understand the process in order to add keys, see the following
subsections.

### Key stores

`debug.keystore` and `release.keystore` files need to be present in the `app` folder. Also the file
`app/keystore.properties` is needed with the following inside (replace where needed):

```data
store_password=STORE_PASSWORD
key_alias=KEY_ALIAS
key_password=KEY_PASSWORD
```

If you need to create them, run the following in the `app` folder:

```bash
keytool -genkey -v -keystore debug.keystore -alias KEY_ALIAS -keyalg RSA -keysize 2048 -validity 10000
keytool -genkey -v -keystore release.keystore -alias KEY_ALIAS -keyalg RSA -keysize 2048 -validity 10000
```

### Generic keys file

For the following services, add the files `app/src/production/res/values/keys.xml` and
`app/src/staging/res/values/keys.xml` (for each environment) with this empty content:

```xml
<?xml version="1.0" encoding="utf-8" ?>
<resources>
    <string name="SOME_SERVICE" translatable="false">SOME_SERVICE_TOKEN</string>
</resources>
```

### Fabric

In order to use Fabric, create a file `app/fabric.properties` with the content (replace with the
right values):

```data
apiSecret=YOUR_BUILD_SECRET
apiKey=YOUR_API_KEY
```

### GCM

Place the staging and production `google-services.json` files in `app/src/staging` and
`app/src/production` respectively.

## Warning `libpng warning: iCCP: Not recognizing known sRGB profile that has been edited` when building an APK

This happens when unrecognised and unnecessary metadata is present in PNG files. To remove it (you
need `exiftool`):

```bash
find . -path '*src/main/res/*' -name '*.png' -exec exiftool -overwrite_original -all= {} \;
```

[Source](http://stackoverflow.com/a/29162323/1165181)

## Location problems in Genymotion

A Genymotion emulator (with Google Play Services installed) will have more problems with Location
than a mobile. Getting the last known location shouldn't throw null if location is enabled. See
[the different answers and comments here](http://stackoverflow.com/questions/16830047/locationclient-getlastlocation-return-null)
for more info.
