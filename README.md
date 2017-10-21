# dsp4e

## To setup dsp4e dev environment

Clone the following repos/branches:

- git@github.com:tracymiranda/dsp4e.git -- master

Starting with Eclipse for Committers or similar.

Import following projects:

- dsp4e/org.eclipse.dsp4e
- dsp4e/org.eclipse.dsp4j

Set target platform to org.eclipse.dsp4e/dsp4e.target (or use .tpd file if you are editing)

Install VSCode and the Mock Debug debug adapter. You need path to Node and to the Mock Debug in the launch configuration.

## To Run DSP4E

- Launch Eclipse Application
- Create a readme.md.
- Create *ReadMe* launch configuration


## To regenerate DebugProtocol.java:

```
$ git clone git@github.com:jonahkichwacoders/vscode-debugadapter-node.git
$ git checkout java_generator
$ npm install -g typescript # ? is this the right way to install typescript
$ npm install
$ tsc -p ./src/ # convert typescript
$ node out/generator.js # run generator
$ cp -v ./protocol/src/DebugProtocol.java  # path to: org.eclipse.dsp4j/src/main/java/org/eclipse/dsp4j/DebugProtocol.java
```

