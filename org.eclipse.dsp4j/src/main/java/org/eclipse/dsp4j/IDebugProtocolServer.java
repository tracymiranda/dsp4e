package org.eclipse.dsp4j;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.eclipse.dsp4j.DebugProtocol.Capabilities;
import org.eclipse.dsp4j.DebugProtocol.ContinueArguments;
import org.eclipse.dsp4j.DebugProtocol.ContinueResponse;
import org.eclipse.dsp4j.DebugProtocol.DisconnectArguments;
import org.eclipse.dsp4j.DebugProtocol.DisconnectResponse;
import org.eclipse.dsp4j.DebugProtocol.InitializeRequestArguments;
import org.eclipse.dsp4j.DebugProtocol.LaunchRequestArguments;
import org.eclipse.dsp4j.DebugProtocol.NextArguments;
import org.eclipse.dsp4j.DebugProtocol.ScopesArguments;
import org.eclipse.dsp4j.DebugProtocol.ScopesResponse;
import org.eclipse.dsp4j.DebugProtocol.SetBreakpointsArguments;
import org.eclipse.dsp4j.DebugProtocol.SetBreakpointsResponse;
import org.eclipse.dsp4j.DebugProtocol.SetFunctionBreakpointsArguments;
import org.eclipse.dsp4j.DebugProtocol.SetFunctionBreakpointsResponse;
import org.eclipse.dsp4j.DebugProtocol.StackTraceArguments;
import org.eclipse.dsp4j.DebugProtocol.StackTraceResponse;
import org.eclipse.dsp4j.DebugProtocol.StepInArguments;
import org.eclipse.dsp4j.DebugProtocol.StepOutArguments;
import org.eclipse.dsp4j.DebugProtocol.ThreadsResponse;
import org.eclipse.dsp4j.DebugProtocol.VariablesArguments;
import org.eclipse.dsp4j.DebugProtocol.VariablesResponse;
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
	CompletableFuture<SetFunctionBreakpointsResponse.Body> setFunctionBreakpoints(
			SetFunctionBreakpointsArguments setFunctionBreakpointsArgs);

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
	CompletableFuture<Void> stepIn(StepInArguments stepInArguments);

	@JsonRequest
	CompletableFuture<Void> stepOut(StepOutArguments stepOutArguments);

	@JsonRequest(value = "continue")
	CompletableFuture<ContinueResponse.Body> continue_(ContinueArguments arguments);

	@JsonRequest
	CompletableFuture<StackTraceResponse.Body> stackTrace(StackTraceArguments stackTraceArguments);

	@JsonRequest
	CompletableFuture<ScopesResponse.Body> scopes(ScopesArguments scopesArguments);
}
