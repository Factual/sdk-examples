# BroadcastReceiver Example

## Overview

If you would like notifications from the ObservationGraph SDK in the app’s background state, we recommend integrating Engine via BroadcastReceivers as opposed to Listeners.  We provide the abstract class `FactualClientReceiver` that extends a BroadcastReceiver and replaces `FactualClientListener`.

## Integration

The main difference between our Listener example and BroadcastReceiver example is that you must reference your Receiver in your AndroidManifest.xml.  We do the following in [our example]():

```xml
...
        <receiver android:name=".LoggingFactualClientReceiver"/>
    </application>
...
```

The [`LoggingFactualClientReceiver`]() extends `FactualClientReceiver`.

Setting or registering a receiver is also a little different, for example:

Setting the `FactualClientReceiver` in [`MainActivity.java`]():

```java
...
FactualObservationGraph.setListener(LoggingFactualClientReceiver.class);
...
```
