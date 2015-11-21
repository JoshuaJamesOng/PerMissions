# PerMissions

PerMission is a Android library for easily handling Android M permissions. 

## How To Use

From your activity, simply create a new instance of PerMissions using the PerMissionsBuilder
```
new PerMissionsBuilder().init(this, this, mBus).build();
```

When an action will require a permission, post an event to the bus with a callback to continue flow then let PerMissions handle the rest

```
bus.post(new PermissionEvent(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new Runnable() {
      @Override
      public void run() {
        backupService.backupToExternalStorage();
      }
}));
```

## Customization
The PerMissionsBuilder can be given your own implementations of PermissionHandler and PermissionResultHandler if you wish to take care of handling yourself. Example could be extending PermissionHandler so you don't use an event bus, or extending PermissionResultHandler so you can display a custom explanation dialog.

```
new PerMissionsBuilder().init(this, this).handler(getActivity()).callback(getActivity()).build();
```

You can also change the dialog string by simply overriding the string resources in your own strings.xml
```
<string name="permissions_explanation_storage">The Storage permission is required to save backups of your data. Denying will mean we can't backup your data, and as a result, you may lose all your photos</string>
```
