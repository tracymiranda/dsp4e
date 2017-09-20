package org.eclipse.dsp4j;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.eclipse.dsp4j.DebugProtocol.ContinueArguments;
import org.eclipse.dsp4j.DebugProtocol.InitializeRequestArguments;
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
	private static final String NODE_DEBUG_CMD = "/scratch/node/node-v6.11.0-linux-x64/bin/node";
	private static final String MOCK_DEBUG_ARG = "/home/jonah/.vscode/extensions/andreweinand.mock-debug-0.20.0/out/mockDebug.js";
	private static final String NATIVE_DEBUG_ARG = "/home/jonah/.vscode/extensions/webfreak.debug-0.21.2/out/src/gdb.js";
	private static final String CWD = "/scratch/debug/examples/nativedebug";
	private static final String README_MD = "/scratch/debug/examples/mockdebug/readme.md";

	public static class DebugProtocolClient implements IDebugProtocolClient {
	}

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		new TestUsingIntefaces().test();
	}

	@Test
	public void test() throws IOException, InterruptedException, ExecutionException {
		//
		// ProcessBuilder processBuilder = new ProcessBuilder(NODE_DEBUG_CMD,
		// MOCK_DEBUG_ARG);
		// Process process = processBuilder.start();
		// DebugLauncher<IDebugProtocolServer> debugProtocolLauncher =
		// DebugLauncher.createLauncher(
		// new DebugProtocolClient(), IDebugProtocolServer.class,
		// process.getInputStream(),
		// process.getOutputStream());

		Socket socket = new Socket("127.0.0.1", 4711);
		DebugLauncher<IDebugProtocolServer> debugProtocolLauncher = DebugLauncher.createLauncher(
				new DebugProtocolClient(), IDebugProtocolServer.class, socket.getInputStream(),
				socket.getOutputStream());

		Future<?> listening = debugProtocolLauncher.startListening();
		IDebugProtocolServer debugProtocolServer = debugProtocolLauncher.getRemoteProxy();

		getAndPrint(debugProtocolServer.initialize(
				new InitializeRequestArguments().setClientID("test").setAdapterID("mock").setPathFormat("path")));

		getAndPrint(debugProtocolServer.setBreakpoints(new SetBreakpointsArguments()
				.setSource(new Source().setPath("/scratch/debug/examples/mockdebug/readme.md").setName("readme.md"))
				.setLines(new Integer[] { 3 })
				.setBreakpoints(
						new DebugProtocol.SourceBreakpoint[] { new DebugProtocol.SourceBreakpoint().setLine(3) })
				.setSourceModified(false)));

		// getAndPrint(debugProtocolServer.threads());

		Map<String, Object> launchArguments = new HashMap<>();
		launchArguments.put("type", "mock");
		launchArguments.put("request", "launch");
		launchArguments.put("name", "Mock Debug");
		launchArguments.put("program", README_MD);
		launchArguments.put("stopOnEntry", true);
		launchArguments.put("trace", false);
		launchArguments.put("noDebug", false);
		launchArguments.put("debugServer", 4711);
		getAndPrint(debugProtocolServer.launch(Either.forLeft(launchArguments)));

		getAndPrint(debugProtocolServer.configurationDone());

		// getAndPrint(debugProtocolServer.variables(new
		// VariablesArguments().setVariablesReference(1000)));
		//
		// getAndPrint(debugProtocolServer.threads());
		//
		// getAndPrint(debugProtocolServer.stackTrace(new
		// StackTraceArguments().setThreadId(1).setStartFrame(0).setLevels(20)));
		//
		// getAndPrint(debugProtocolServer.variables(new
		// VariablesArguments().setVariablesReference(1001)));

		getAndPrint(debugProtocolServer.continue_(new ContinueArguments().setThreadId(1)));
		// getAndPrint(debugProtocolServer.next(new NextArguments().setThreadId(1)));

		getAndPrint(debugProtocolServer.variables(new VariablesArguments().setVariablesReference(1003)));

		getAndPrint(debugProtocolServer.threads());

		getAndPrint(debugProtocolServer
				.stackTrace(new StackTraceArguments().setThreadId(1).setStartFrame(0).setLevels(20)));

		// getAndPrint(debugProtocolServer.disconnect(new DisconnectArguments()));

		// process.destroy();
		socket.close();
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
