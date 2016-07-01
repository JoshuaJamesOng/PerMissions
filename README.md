# PerMissions

PerMission is a Android library for easily handling Android M permissions.

* Handles display of explanation and denied dialogs
* Supplies builder so you can use your own handlers and callbacks
* Supports `@RequiresPermission` annotations so you can add compile-time safety
* Easily override default dialog text in strings.xml

## How To Use

From your activity, simply create a new instance of PerMissions using the `PerMissionsBuilder`
```
new PerMissionsBuilder().init(this, getSupportFragmentManager(), mBus)
                        .build();
```

When an action will require a permission, post an event to the bus with a callback to continue flow

```
bus.post(new PermissionEvent(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new Runnable() {
      @Override
      public void run() {
        backupService.backupToExternalStorage();
      }
}));
```

If you're using the Support Annotations library, pass an `PerMissionsFlow` instead of `Runnable` to remove the warning 
```
bus.post(new PermissionEvent(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PerMissionsFlow() {
      @Override
      public void onGranted() throws SecurityException {
        backupService.backupToExternalStorage();
      }
}));
```

PerMissions will handle displaying dialogs for you when permission is not granted, allowing you to reduce boilerplate code

## Example Dialogs
![Image of explanation dialog](/attachments/explanation.png)

![Image of denied dialog](/attachments/denied.png)

## Custom Messages
To change the titles and bodys of dialogs, simply override the corresponding string resources in your own strings.xml. There is an exaplantion and denied string for each permission group.

```
<string name="permissions_title_explanation">Hello User</string>
<string name="permission_title_denied">Sorry :(</string>
<string name="permissions_explanation_storage">The Storage permission is required to save backups of your data. Denying will mean we can't backup your data, and as a result, you may lose all your photos</string>
<string name="permissions_denied_storage">Sorry, MyApp requires the Storage permission to backup. Please enable in Settings > Apps</string>
```
## Custom Handlers
The `PerMissionsBuilder` can be given your own implementations of `PermissionHandler` and `PermissionResultHandler` if you wish to take care of handling yourself. 

An example could be extending `PermissionHandler` so you don't use an event bus, or extending `PermissionResultHandler` so you can display a custom explanation dialog.

```
// Assuming the activity implements both interfaces
new PerMissionsBuilder().init(this, this)
                        .handler(getActivity())
                        .callback(getActivity())
                        .build();
```

## License
    Copyright 2015 Joshua Ong

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

