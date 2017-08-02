# dsp4e

To regenerate DebugProtocol.java:

```
$ git clone git@github.com:jonahkichwacoders/vscode-debugadapter-node.git
$ git checkout java_generator
$ npm install # ? needed?
$ npm install -g typescript # ? is this the right way to install typescript
$ tsc -p ./src/ # convert typescript
$ node out/generator.js # run generator
$ cp -v ./protocol/src/DebugProtocol.java  dsp4e/org.eclipse.dsp4j/src/org/eclipse/dsp4j/DebugProtocol.java
```

Then DebugProtocol has some modifications made, for now the bodies Request, Event, Response of are cleared, i.e.:

	/** A client or server-initiated request. */
	public static class Request extends ProtocolMessage {
	}

	/** Server-initiated event. */
	public static class Event extends ProtocolMessage {
	}

	/** Response to a request. */
	public static class Response extends ProtocolMessage {
	}

