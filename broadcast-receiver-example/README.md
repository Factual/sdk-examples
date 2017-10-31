# BroadcastReceiver Example

## Overview

If you would like notifications from Engine in the app’s background state we recommend integrating Engine via BroadcastReceivers as opposed to Listeners.  We provide the following abstract classes that extend BroadcastReceivers:

- `FactualActionReceiver`: replaces `FactualActionHandler`
- `FactualClientReceiver`: replaces `FactualClientListener`

## Integration

The main difference between our Listener example and BroadcastReceiver example is that you must reference your Receivers in your AndroidManifest.xml.  We do the following in our example:

```xml
...
        <receiver android:name=".ConsoleLoggingActionReceiver"/>
        <receiver android:name=".ConsoleLoggingFactualClientReceiver"/>
    </application>
...
```

The `ConsoleLoggingActionReceiver` extends `FactualActionReceiver` and the `ConsoleLoggingFactualClientReceiver` extends `FactualClientReceiver`.

Setting or registering a receiver is also a little different, for example:

Setting the `FactualClientReceiver` in `MainActivity.java`:

```java
...
FactualEngine.setListener(ConsoleLoggingFactualClientReceiver.class);
...
```

Registering the `FactualActionReceiver` in `ConsoleLoggingFactualClientReceiver.java`:

```java
...
FactualEngine.registerAction(actionId, ConsoleLoggingActionReceiver.class);
...
```
