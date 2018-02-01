# BroadcastReceiver Example

## Overview

If you would like notifications from Engine in the app’s background state we recommend integrating Engine via BroadcastReceivers as opposed to Listeners.  We provide the following abstract classes that extend BroadcastReceivers:

- `FactualActionReceiver`: replaces `FactualActionHandler`
- `FactualClientReceiver`: replaces `FactualClientListener`

## Integration

The main difference between our Listener example and BroadcastReceiver example is that you must reference your Receivers in your AndroidManifest.xml.  We do the following in [our example](https://github.com/Factual/engine-examples/blob/f0f74e1b8955605ad57d0bf4c8fcd590cfacd661/broadcast-receiver-example/app/src/main/AndroidManifest.xml#L20-L22):

```xml
...
        <receiver android:name=".ConsoleLoggingActionReceiver"/>
        <receiver android:name=".ConsoleLoggingFactualClientReceiver"/>
        <receiver android:name=".ConsoleLoggingUserJourneyReceiver"/>
    </application>
...
```

The [`ConsoleLoggingActionReceiver`](https://github.com/Factual/engine-examples/blob/f0f74e1b8955605ad57d0bf4c8fcd590cfacd661/broadcast-receiver-example/app/src/main/java/com/factual/broadcastreceiverexample/ConsoleLoggingActionReceiver.java) extends `FactualActionReceiver`, the [`ConsoleLoggingUserJourneyReceiver`](https://github.com/Factual/engine-examples/blob/f0f74e1b8955605ad57d0bf4c8fcd590cfacd661/broadcast-receiver-example/app/src/main/java/com/factual/broadcastreceiverexample/ConsoleLoggingUserJourneyReceiver.java) extends `UserJourneyReceiver`, and the [`ConsoleLoggingFactualClientReceiver`](https://github.com/Factual/engine-examples/blob/f0f74e1b8955605ad57d0bf4c8fcd590cfacd661/broadcast-receiver-example/app/src/main/java/com/factual/broadcastreceiverexample/ConsoleLoggingFactualClientReceiver.java) extends `FactualClientReceiver`.

Setting or registering a receiver is also a little different, for example:

Setting the `FactualClientReceiver` in [`MainActivity.java`](https://github.com/Factual/engine-examples/blob/f0f74e1b8955605ad57d0bf4c8fcd590cfacd661/broadcast-receiver-example/app/src/main/java/com/factual/broadcastreceiverexample/MainActivity.java#L44):

```java
...
FactualEngine.setListener(ConsoleLoggingFactualClientReceiver.class);
...
```

Setting the `UserJourneyReceiver` in [`MainActivity.java`](https://github.com/Factual/engine-examples/blob/f0f74e1b8955605ad57d0bf4c8fcd590cfacd661/broadcast-receiver-example/app/src/main/java/com/factual/broadcastreceiverexample/MainActivity.java#L47):
```java
...
FactualEngine.setUserJourneyListener(ConsoleLoggingUserJourneyReceiver.class);
...
```

Registering the `FactualActionReceiver` in [`MainActivity.java`](https://github.com/Factual/engine-examples/blob/f0f74e1b8955605ad57d0bf4c8fcd590cfacd661/broadcast-receiver-example/app/src/main/java/com/factual/broadcastreceiverexample/MainActivity.java#L52):

```java
...
FactualEngine.registerAction(actionId, ConsoleLoggingActionReceiver.class);
...
```
