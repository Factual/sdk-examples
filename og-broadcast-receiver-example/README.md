# BroadcastReceiver Example

## Overview

If you would like notifications from the ObservationGraph SDK in the app’s background state, we recommend integrating Engine via BroadcastReceivers as opposed to Listeners.  We provide the abstract class `FactualClientReceiver` that extends a BroadcastReceiver and replaces `FactualClientListener`.

## Integration

The main difference between our Listener example and BroadcastReceiver example is that you must reference your Receiver in your AndroidManifest.xml.  We do the following in [our example](https://github.com/Factual/engine-examples/blob/2fb4dd14e54829c4223f822663a9666594ce425f/og-broadcast-receiver-example/app/src/main/AndroidManifest.xml#L20):

```xml
...
        <receiver android:name=".LoggingFactualClientReceiver"/>
    </application>
...
```

The [`LoggingFactualClientReceiver`](https://github.com/Factual/engine-examples/blob/2fb4dd14e54829c4223f822663a9666594ce425f/og-broadcast-receiver-example/app/src/main/java/com/factual/ogbroadcastreceiverexample/LoggingFactualClientReceiver.java) extends `FactualClientReceiver`.

Setting or registering a receiver is also a little different, for example:

Setting the `FactualClientReceiver` in [`MainActivity.java`](https://github.com/Factual/engine-examples/blob/2fb4dd14e54829c4223f822663a9666594ce425f/og-broadcast-receiver-example/app/src/main/java/com/factual/ogbroadcastreceiverexample/MainActivity.java#L49):

```java
...
FactualObservationGraph.setListener(LoggingFactualClientReceiver.class);
...
```
