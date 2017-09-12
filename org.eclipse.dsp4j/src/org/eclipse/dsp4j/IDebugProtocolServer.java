package org.eclipse.dsp4j;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.eclipse.dsp4j.DebugProtocol.Capabilities;
import org.eclipse.dsp4j.DebugProtocol.DisconnectArguments;
import org.eclipse.dsp4j.DebugProtocol.DisconnectResponse;
import org.eclipse.dsp4j.DebugProtocol.InitializeRequestArguments;
import org.eclipse.dsp4j.DebugProtocol.LaunchRequestArguments;
import org.eclipse.dsp4j.DebugProtocol.NextArguments;
import org.eclipse.dsp4j.DebugProtocol.SetBreakpointsArguments;
import org.eclipse.dsp4j.DebugProtocol.SetBreakpointsResponse;
import org.eclipse.dsp4j.DebugProtocol.StackTraceArguments;
import org.eclipse.dsp4j.DebugProtocol.StackTraceResponse;
import org.eclipse.dsp4j.DebugProtocol.ThreadsResponse;
import org.eclipse.dsp4j.DebugProtocol.VariablesArguments;
import org.eclipse.dsp4j.DebugProtocol.VariablesResponse;
import org.eclipse.dsp4j.DebugProtocol.VariablesResponse.Body;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;

public interface IDebugProtocolServer {
	@JsonRequest
	CompletableFuture<Capabilities> initialize(InitializeRequestArguments param);

	@JsonRequest
	CompletableFuture<DisconnectResponse> disconnect(DisconnectArguments disconnectArguments);
	
	@JsonRequest
	CompletableFuture<SetBreakpointsResponse.Body> setBreakpoints(SetBreakpointsArguments setBreakpointsArgs);

	@JsonRequest
	CompletableFuture<Void> launch(Either<Map<String, Object>, LaunchRequestArguments> launchArguments);

	@JsonRequest
	CompletableFuture<Void> configurationDone();

	@JsonRequest
	CompletableFuture<ThreadsResponse.Body> threads();
	
	@JsonRequest
	CompletableFuture<VariablesResponse.Body> variables(VariablesArguments variablesArguments);
	
	@JsonRequest
	CompletableFuture<Void> next(NextArguments nextArguments);
	
	@JsonRequest
	CompletableFuture<StackTraceResponse.Body> stackTrace(StackTraceArguments stackTraceArguments);
}