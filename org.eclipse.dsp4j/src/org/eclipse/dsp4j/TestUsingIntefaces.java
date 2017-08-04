package org.eclipse.dsp4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.eclipse.dsp4j.DebugProtocol.Capabilities;
import org.eclipse.dsp4j.DebugProtocol.InitializeRequestArguments;
import org.eclipse.dsp4j.DebugProtocol.LaunchRequestArguments;
import org.eclipse.dsp4j.DebugProtocol.OutputEvent;
import org.eclipse.dsp4j.DebugProtocol.SetBreakpointsArguments;
import org.eclipse.dsp4j.DebugProtocol.SetBreakpointsResponse;
import org.eclipse.dsp4j.DebugProtocol.Source;
import org.eclipse.dsp4j.DebugProtocol.SourceBreakpoint;
import org.eclipse.dsp4j.DebugProtocol.StoppedEvent;
import org.eclipse.lsp4j.jsonrpc.DebugLauncher;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.junit.Test;

public class TestUsingIntefaces {

	// private static final String NODE_DEBUG_CMD =
	// "/scratch/node/node-v6.11.0-linux-x64/bin/node";
	// private static final String NODE_DEBUG_ARG =
	// "/home/jonah/.vscode/extensions/andreweinand.mock-debug-0.19.0/out/mockDebug.js";

	private static final String NODE_DEBUG_CMD = "C:\\Program Files\\nodejs\\node.exe";
	private static final String NODE_DEBUG_ARG = "C:\\Users\\jonah\\.vscode\\extensions\\andreweinand.mock-debug-0.19.0\\out\\mockDebug.js";

	public static interface IDebugProtocolServer {
		@JsonRequest
		CompletableFuture<Capabilities> initialize(InitializeRequestArguments param);

		@JsonRequest
		CompletableFuture<SetBreakpointsResponse.Body> setBreakpoints(SetBreakpointsArguments setBreakpointsArgs);

		@JsonRequest
		CompletableFuture<Void> launch(Either<Map<String, Object>, LaunchRequestArguments> launchArguments);
	}

	public static interface IDebugProtocolClient {

		@JsonNotification
		default void initialized() {
			// System.out.println("initialized");
		};

		@JsonNotification
		default void output(OutputEvent.Body body) {
			// System.out.println("output body");
		}

		@JsonNotification
		default void stopped(StoppedEvent.Body body) {
			System.out.println("stopped: " + body);
		}

	}

	public static class DebugProtocolClient implements IDebugProtocolClient {
	}

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		new TestUsingIntefaces().test();
	}
	
	@Test
	public void test() throws IOException, InterruptedException, ExecutionException {
			
		
		ProcessBuilder processBuilder = new ProcessBuilder(NODE_DEBUG_CMD, NODE_DEBUG_ARG);
		Process process = processBuilder.start();
		DebugLauncher<IDebugProtocolServer> debugProtocolLauncher = DebugLauncher.createLauncher(
				new DebugProtocolClient(), IDebugProtocolServer.class, process.getInputStream(),
				process.getOutputStream());

		Future<?> listening = debugProtocolLauncher.startListening();
		IDebugProtocolServer debugProtocolServer = debugProtocolLauncher.getRemoteProxy();

		getAndPrint(debugProtocolServer.initialize(
				new InitializeRequestArguments().setClientID("test").setAdapterID("mock").setPathFormat("path")));

		getAndPrint(debugProtocolServer.setBreakpoints(new SetBreakpointsArguments()
				.setSource(new Source().setPath("D:\\debug\\mockdebug\\Readme.md").setName("Readme.md"))));

		Map<String, Object> launchArguments = new HashMap<>();
		launchArguments.put("type", "mock");
		launchArguments.put("request", "launch");
		launchArguments.put("name", "Mock Debug");
		launchArguments.put("program", "D:\\debug\\mockdebug\\Readme.md");
		launchArguments.put("stopOnEntry", true);
		launchArguments.put("trace", false);
		launchArguments.put("noDebug", false);
		getAndPrint(debugProtocolServer.launch(Either.forLeft(launchArguments)));
		
		
		process.destroy();
		listening.cancel(true);
	}

	private static void getAndPrint(CompletableFuture<?> future) {
		try {
			System.out.println(future.get());
		} catch (InterruptedException | ExecutionException e) {
			String message = e.getMessage();
			String lines[] = message.split("\\r?\\n");
			System.err.println(lines[0]);
		}
	}

}
