# dsp4e

## To setup dsp4e dev environment

Clone the following repos/branches:

- git@github.com:tracymiranda/dsp4e.git -- master
- http://git.eclipse.org/gitroot/lsp4e/lsp4e.git -- master (last tested with a19c8a44be8b80cdf172f4b42164e65c08a8cbc9)
- git@github.com:jonahkichwacoders/lsp4j.git -- master

Starting with Eclipse SDK or similar, ensure you have the following:

- PDE & JDT
- Gradle (aka Buildship)
- EGit
- XText Complete SDK

Import following projects:

- dsp4e/org.eclipse.dsp4e
- dsp4e/org.eclipse.dsp4j
- lsp4e -- only using target platform from here
- lsp4j
- lsp4j/org.eclipse.lsp4j.jsonrpc

Set target platform to lsp4e/target-platforms/target-platform-oxygen/target-platform-oxygen.target

Run the Buildship launch configuration lsp4j/lsp4j-build-gradle.launch

**Expected error**

The following expected error occurs, presumbaly because we are using oxygen instead of photon. TODO upgrade to photon?

`Unknown extension point: 'org.eclipse.ui.genericeditor.reconcilers'	plugin.xml	/org.eclipse.lsp4e	line 330	Plug-in Problem`

Install VSCode and the Mock Debug debug adapter.
Note the location and update ReadmeLaunchDelegate.MOCK_DEBUG_ARG

Install Node and update ReadmeLaunchDelegate.NODE_DEBUG_CMD

## To Run DSP4E

- Launch Eclipse Application
- Create a readme.md file and update ReadmeLaunchDelegate.README_MD with the location.
- Create *ReadMe* launch configuration and Debug it


## To regenerate DebugProtocol.java:

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

