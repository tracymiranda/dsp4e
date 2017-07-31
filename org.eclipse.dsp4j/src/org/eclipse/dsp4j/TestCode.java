package org.eclipse.dsp4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.eclipse.dsp4j.DebugProtocol.InitializeRequest;
import org.eclipse.dsp4j.DebugProtocol.InitializeRequestArguments;
import org.eclipse.dsp4j.DebugProtocol.InitializedEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestCode {

//	private static final String NODE_DEBUG_CMD = "/scratch/node/node-v6.11.0-linux-x64/bin/node";
//	private	static final String NODE_DEBUG_ARG = "/home/jonah/.vscode/extensions/andreweinand.mock-debug-0.19.0/out/mockDebug.js";

	private static final String NODE_DEBUG_CMD = "C:\\Program Files\\nodejs\\node.exe";
	private	static final String NODE_DEBUG_ARG = "C:\\Users\\tracy\\.vscode-insiders\\extensions\\andreweinand.mock-debug-0.19.0\\out\\mockDebug.js";
	
	private static final String CONTENT_LENGTH = "Content-Length: ";

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
	 * @throws IOException
	 *
	 */
	public static void main(String[] args) throws IOException {
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

		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String initializeString = gson.toJson(initialize);


		ProcessBuilder processBuilder = new ProcessBuilder(NODE_DEBUG_CMD, NODE_DEBUG_ARG);
		Process process = processBuilder.start();
		OutputStreamWriter writer = new OutputStreamWriter(process.getOutputStream());
		writer.write(CONTENT_LENGTH + initializeString.length() + "\r\n\r\n");
		writer.write(initializeString);
		System.out.println("Sent: " + initializeString);
		System.out.println("As object: " + initialize);
		writer.flush();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
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
						String content = new String(data);
						System.out.println("Received: " + content);
						InitializedEvent result = gson.fromJson(content, InitializedEvent.class);
						System.out.println("As object: " + result);
					}
				}
 			}
		}
	}

}
