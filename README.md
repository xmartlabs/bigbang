# Android Base Project

This is the Xmartlabs' Android base project. Remember to follow [the style guide]
(https://github.com/xmartlabs/Android-Style-Guide) when working on projects, and also when contributing here.
An example usage of this project lays in [the demo branch]
(https://github.com/xmartlabs/Android-Base-Project/tree/demo).

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
`app/src/production` respectively. These are created using [a Google app]
(https://developers.google.com/mobile/add?platform=android&cntapi=gcm).

## Genymotion

We recommend using [Genymotion](https://www.genymotion.com) emulator.

## Useful plugins

The plugins for Android Studio that we consider useful are:

<dl>
    <dt>.gitignore</dt>
    <dd>For highlighting .gitignore files and finding unused paths in it.</dd>
    
    <dt>Android ButterKnife Zelezny</dt>
    <dd>For automatically binding views with ButterKnife.</dd>
    
    <dt>Android Material Design Icon Generator</dt>
    <dd>Used to add common icons to the projects. Icons come from the <a href="https://design.google.com/icons/">
    Material Icons</a>.</dd>

    <dt>BashSupport</dt>
    <dd>For code highlighting in bash script files.</dd>

    <dt>Fabric for Android Studio</dt>
    <dd>Mainly for Crashlytics Beta releases (AFAIK it's the only way to do this). Also for viewing
    crashes and other Fabric stuff.</dd>

    <dt>Genymotion</dt>
    <dd>For quick access to Genymotion's emulators.</dd>

    <dt>Lombok Plugin</dt>
    <dd>In order to avoid receiving "Cannot resolve symbol/method" errors when using Lombok.</dd>

    <dt>Markdown</dt>
    <dd>Markdown language support.</dd>
</dl>

## Design tips

You can find common color material combinations in [MaterialUp](https://www.materialpalette.com/), which is based on
[Google 500 colors](https://www.google.com/design/spec/style/color.html). Also, [Color Hunt]
(http://colorhunt.co/) can be used for general color combination inspiration.

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

## Check for outdated libraries

Run the following:

```bash
./gradlew dependencyUpdates
```

## cURL

[Ok2Curl](https://github.com/mrmike/Ok2Curl) is added so all network activity logs a curl command to be reproduced
easily if desired.

## Use Google Play Services in Genymotion

[Follow this StackOverflow answer](http://stackoverflow.com/a/20013322/1165181).

## Asset Studio

[Android Asset Studio](https://romannurik.github.io/AndroidAssetStudio/index.html) should be used when trying to
generate icons for different screen densities.

## 10 non-intuitive and helpful keymaps

These are not common keymaps in other IDEs and programs, and they are helpful too.

| Description                            | Mac                         | Non-Mac                    |
| -------------------------------------- | --------------------------- | -------------------------- |
| Cut or copy current line               | `⌘ X` / `⌘ V`               | `Ctrl + X/C`               |
| Extract variable, field or method      | `⌘ ⌥ V` / `⌘ ⌥ F` / `⌘ ⌥ M` | `Ctrl + Shift + V/F/M`     |
| File structure popup                   | `⌘ F12`                     | `Ctrl + F12`               |
| Generate code                          | `⌘ N`                       | `Alt + Insert`             |
| Go to declaration                      | `⌘ Click`, `⌘ B`            | `Ctrl + Click`, `Ctrl + B` |
| Go to previous or next method          | `^ ↑` / `^ ↓`               | `Alt + Up/Down`            |
| Move line up or down                   | `⌘ ⇧ ↑` / `⌘ ⇧ ↓`           | `Ctrl + Shift + Up/Down`   |
| Rename                                 | `⇧ F6`                      | `Shift + F6`               |
| Search everywhere                      | `Double ⇧`                  | `Double Shift`             |
| Show intention actions and quick-fixes | `⌥ ⏎`                       | `Alt + Enter`              |

Take into account that some may conflict with OS ones. For more, check out the [IntelliJ IDEA Default Keymap]
(https://resources.jetbrains.com/assets/products/intellij-idea/IntelliJIDEA_ReferenceCard.pdf) or the [IntelliJ IDEA Mac
OS X Default Keymap](https://resources.jetbrains.com/assets/products/intellij-idea/IntelliJIDEA_ReferenceCard_mac.pdf).
