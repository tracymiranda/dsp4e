package org.eclipse.dsp4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.eclipse.dsp4j.DebugProtocol.DisconnectArguments;
import org.eclipse.dsp4j.DebugProtocol.InitializeRequestArguments;
import org.eclipse.dsp4j.DebugProtocol.NextArguments;
import org.eclipse.dsp4j.DebugProtocol.SetBreakpointsArguments;
import org.eclipse.dsp4j.DebugProtocol.Source;
import org.eclipse.dsp4j.DebugProtocol.StackTraceArguments;
import org.eclipse.dsp4j.DebugProtocol.VariablesArguments;
import org.eclipse.lsp4j.jsonrpc.DebugLauncher;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.junit.Test;

public class TestUsingIntefaces {

	// private static final String NODE_DEBUG_CMD =
	// "/scratch/node/node-v6.11.0-linux-x64/bin/node";
	// private static final String NODE_DEBUG_ARG =
	// "/home/jonah/.vscode/extensions/andreweinand.mock-debug-0.19.0/out/mockDebug.js";
	private static final String NODE_DEBUG_CMD = "C:\\\\Program Files\\\\nodejs\\\\node.exe";
	private static final String NODE_DEBUG_ARG = "C:\\\\Users\\\\artke\\\\.vscode\\\\extensions\\\\andreweinand.mock-debug-0.19.0\\\\out\\\\mockDebug.js";

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

		getAndPrint(debugProtocolServer.setBreakpoints(new SetBreakpointsArguments().setSource(
				new Source().setPath("C:\\Users\\artke\\Desktop\\Debug\\dsp4e\\README.md").setName("Readme.md"))));

		getAndPrint(debugProtocolServer.configurationDone());

		getAndPrint(debugProtocolServer.threads());	
		
		Map<String, Object> launchArguments = new HashMap<>();
		launchArguments.put("type", "mock");
		launchArguments.put("request", "launch");
		launchArguments.put("name", "Mock Debug");
		launchArguments.put("program", "C:\\Users\\artke\\Desktop\\Debug\\dsp4e\\README.md");
		launchArguments.put("stopOnEntry", true);
		launchArguments.put("trace", true);
		launchArguments.put("noDebug", false);
		getAndPrint(debugProtocolServer.launch(Either.forLeft(launchArguments)));

		getAndPrint(debugProtocolServer.variables(new VariablesArguments().setVariablesReference(1000)));
		
		getAndPrint(debugProtocolServer.threads());	

		getAndPrint(debugProtocolServer.stackTrace(new StackTraceArguments().setThreadId(1).setStartFrame(0).setLevels(20)));	
		
		getAndPrint(debugProtocolServer.variables(new VariablesArguments().setVariablesReference(1001)));

		getAndPrint(debugProtocolServer.next(new NextArguments().setThreadId(1)));
		
		getAndPrint(debugProtocolServer.variables(new VariablesArguments().setVariablesReference(1003)));

		getAndPrint(debugProtocolServer.threads());	

		getAndPrint(debugProtocolServer.stackTrace(new StackTraceArguments().setThreadId(1).setStartFrame(0).setLevels(20)));	

		getAndPrint(debugProtocolServer.disconnect(new DisconnectArguments()));
		
		process.destroy();
		listening.cancel(true);
	}

	private static void getAndPrint(CompletableFuture<?> future) {
		try {
			System.out.println(future.get());
		} catch (InterruptedException | ExecutionException e) {
			String message = e.getMessage();
			String lines[] = message.split("\\r\\n");
			System.err.println(lines[0]);
		}
	}

}
