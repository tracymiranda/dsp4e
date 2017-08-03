package org.eclipse.dsp4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.eclipse.dsp4j.DebugProtocol.DisconnectArguments;
import org.eclipse.dsp4j.DebugProtocol.DisconnectRequest;
import org.eclipse.dsp4j.DebugProtocol.DisconnectResponse;
import org.eclipse.dsp4j.DebugProtocol.InitializeRequest;
import org.eclipse.dsp4j.DebugProtocol.InitializeRequestArguments;
import org.eclipse.dsp4j.DebugProtocol.LaunchRequest;
import org.eclipse.dsp4j.DebugProtocol.LaunchRequestArguments;
import org.eclipse.dsp4j.DebugProtocol.LaunchResponse;
import org.eclipse.dsp4j.DebugProtocol.ProtocolMessage;
import org.eclipse.dsp4j.DebugProtocol.SetBreakpointsArguments;
import org.eclipse.dsp4j.DebugProtocol.SetBreakpointsRequest;
import org.eclipse.dsp4j.DebugProtocol.SetBreakpointsResponse;
import org.eclipse.dsp4j.DebugProtocol.Source;
import org.eclipse.dsp4j.DebugProtocol.SourceBreakpoint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestCode {

	// private static final String NODE_DEBUG_CMD =
	// "/scratch/node/node-v6.11.0-linux-x64/bin/node";
	// private static final String NODE_DEBUG_ARG =
	// "/home/jonah/.vscode/extensions/andreweinand.mock-debug-0.19.0/out/mockDebug.js";

	private static final String NODE_DEBUG_CMD = "C:\\\\Program Files\\\\nodejs\\\\node.exe";
	private static final String NODE_DEBUG_ARG = "C:\\\\Users\\\\artke\\\\.vscode\\\\extensions\\\\andreweinand.mock-debug-0.19.0\\\\out\\\\mockDebug.js";
	// private static final String NODE_DEBUG_CMD = "C:\\Program
	// Files\\nodejs\\node.exe";
	// private static final String NODE_DEBUG_ARG =
	// "C:\\Users\\tracy\\.vscode-insiders\\extensions\\andreweinand.mock-debug-0.19.0\\out\\mockDebug.js";

	private static final String CONTENT_LENGTH = "Content-Length: ";

	private OutputStreamWriter writer;
	private BufferedReader reader;
	private Gson gson;

	/**
	 *
	 * <pre>
	{
	"command":"initialize",
	"arguments":{
	 "clientID":"vscode",
	 "adapterID":"mock",
	 
	 "pathFormat":"path",
	 "linesStartAt1":true,
	 "columnsStartAt1":true,
	 "supportsVariableType":true,
	 "supportsVariablePaging":true,
	 "supportsRunInTerminalRequest":true
	},
	"type":"request",
	"seq":1
	}
	 * </pre>
	 * 
	 * @throws IOException
	 *
	 */

	public static void main(String[] args) throws IOException {
		new TestCode().main();

	}

	public void main() throws IOException {

		InitializeRequest initialize = new InitializeRequest();
		initialize.command = "initialize";
		initialize.arguments = new InitializeRequestArguments();
		initialize.arguments.clientID = "vscode";
		initialize.arguments.adapterID = "mock";
		initialize.arguments.pathFormat = "path";
		initialize.arguments.linesStartAt1 = true;
		initialize.arguments.columnsStartAt1 = true;
		initialize.arguments.supportsVariableType = true;
		initialize.arguments.supportsVariablePaging = true;
		initialize.arguments.supportsRunInTerminalRequest = true;
		initialize.type = "request";
		initialize.seq = 1;

		SetBreakpointsRequest breakpoint = new SetBreakpointsRequest();
		breakpoint.command = "setBreakpoints";
		breakpoint.arguments = new SetBreakpointsArguments();
		breakpoint.arguments.source = new Source();
		breakpoint.arguments.source.path = "c:\\data\\Projects\\mockdebug\\Readme.md";
		breakpoint.arguments.source.name = "Readme.md";
		breakpoint.arguments.breakpoints = new SourceBreakpoint();
		breakpoint.arguments.breakpoints.line = 1;
		breakpoint.arguments.lines = new int[] { 1 };
		breakpoint.arguments.sourceModified = false;
		breakpoint.type = "request";
		breakpoint.seq = 2;

		LaunchRequest launch = new LaunchRequest();
		launch.command = "launch";
		launch.arguments = new LaunchRequestArguments();
		launch.arguments.type = "mock";
		launch.arguments.request = "launch";
		launch.arguments.name = "Mock Debug";
		launch.arguments.program = new Source();
		launch.arguments.program.path = "c:\\data\\Projects\\mockdebug\\Readme.md";
		launch.arguments.stopOnEntry = true;
		launch.arguments.trace = true;
		launch.arguments.noDebug = false;
		launch.type = "request";
		launch.seq = 3;

		DisconnectRequest disconnect = new DisconnectRequest();
		disconnect.command = "disconnect";
		disconnect.arguments = new DisconnectArguments();
		disconnect.arguments.terminateDebuggee = true;

		GsonBuilder builder = new GsonBuilder();
		gson = builder.create();

		ProcessBuilder processBuilder = new ProcessBuilder(NODE_DEBUG_CMD, NODE_DEBUG_ARG);
		Process process = processBuilder.start();
		writer = new OutputStreamWriter(process.getOutputStream());
		reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

		sendMessage(initialize);
		recvMessage(InitializeRequest.class); // receive initialize event
		recvMessage(SetBreakpointsRequest.class); // receive initialize response

		sendMessage(breakpoint);
		recvMessage(SetBreakpointsResponse.class); // receive setBreakpoint request

		sendMessage(launch);
		recvMessage(ProtocolMessage.class);

		sendMessage(disconnect);
		recvMessage(DisconnectResponse.class);
	}

	private void sendMessage(ProtocolMessage message) throws IOException {
		String messageJson = gson.toJson(message);
		writer.write(CONTENT_LENGTH + messageJson.length() + "\r\n\r\n");
		writer.write(messageJson);
		System.out.println("Sent: " + messageJson);
		System.out.println("As object: " + message);
		writer.flush();
	}

	private <T> T recvMessage(Class<T> messageClasss) throws IOException {
		String contentLength;
		while ((contentLength = reader.readLine()) != null) {
			if (contentLength.startsWith(CONTENT_LENGTH)) {
				int length = Integer.parseInt(contentLength.substring(CONTENT_LENGTH.length()));
				char[] newline = new char[2];
				reader.read(newline);
				if ("\r\n".equals(new String(newline))) {
					char[] data = new char[length];
					int nRead = reader.read(data);
					if (nRead == length) {
						String contentJson = new String(data);
						T result = gson.fromJson(contentJson, messageClasss);
						System.out.println("Received: " + contentJson);
						System.out.println("As object: " + result);
						return result;
					}
				}
			}
		}
		throw new IOException("No messages read");
	}

}