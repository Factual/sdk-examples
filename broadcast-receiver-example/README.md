# BroadcastReceiver Example

## Overview

If you would like notifications from Engine in the app’s background state we recommend integrating Engine via BroadcastReceivers as opposed to Listeners.  We provide the following abstract classes that extend BroadcastReceivers:

- `FactualActionReceiver`: replaces `FactualActionHandler`
- `FactualClientReceiver`: replaces `FactualClientListener`

## Integration

The main difference between our Listener example and BroadcastReceiver example is that you must reference your Receivers in your AndroidManifest.xml.  We do the following in [our example](https://github.com/Factual/engine-examples/blob/b66a0357f492f2041d53c5ab97350704c801371e/broadcast-receiver-example/app/src/main/AndroidManifest.xml#L20-L21):

```xml
...
        <receiver android:name=".ConsoleLoggingActionReceiver"/>
        <receiver android:name=".ConsoleLoggingFactualClientReceiver"/>
    </application>
...
```

The [`ConsoleLoggingActionReceiver`](https://github.com/Factual/engine-examples/blob/ffb014c019c9218fbf36bfef5752bc112e00327b/broadcast-receiver-example/app/src/main/java/com/factual/broadcastreceiverexample/ConsoleLoggingActionReceiver.java) extends `FactualActionReceiver` and the [`ConsoleLoggingFactualClientReceiver`](https://github.com/Factual/engine-examples/blob/ffb014c019c9218fbf36bfef5752bc112e00327b/broadcast-receiver-example/app/src/main/java/com/factual/broadcastreceiverexample/ConsoleLoggingFactualClientReceiver.java) extends `FactualClientReceiver`.

Setting or registering a receiver is also a little different, for example:

Setting the `FactualClientReceiver` in [`MainActivity.java`](https://github.com/Factual/engine-examples/blob/ffb014c019c9218fbf36bfef5752bc112e00327b/broadcast-receiver-example/app/src/main/java/com/factual/broadcastreceiverexample/MainActivity.java#L44):

```java
...
FactualEngine.setListener(ConsoleLoggingFactualClientReceiver.class);
...
```

Registering the `FactualActionReceiver` in [`MainActivity.java`](https://github.com/Factual/engine-examples/blob/ffb014c019c9218fbf36bfef5752bc112e00327b/broadcast-receiver-example/app/src/main/java/com/factual/broadcastreceiverexample/MainActivity.java#L49):

```java
...
FactualEngine.registerAction(actionId, ConsoleLoggingActionReceiver.class);
...
```
