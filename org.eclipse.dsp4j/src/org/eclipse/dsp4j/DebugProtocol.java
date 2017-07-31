/*---------------------------------------------------------------------------------------------
 *  Copyright (c) Microsoft Corporation (and Jonah). All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 *--------------------------------------------------------------------------------------------*/

package org.eclipse.dsp4j;

import java.util.Map;

/**
 * Declaration module describing the VS Code debug protocol. Auto-generated from
 * json schema. Do not edit manually.
 */
public class DebugProtocol {

	/** Base class of requests, responses, and events. */
	public static class ProtocolMessage {
		/** Sequence number. */
		public int seq;
		/** One of 'request', 'response', or 'event'. */
		public String type;
		
		@Override
		public String toString() {
			return "ProtocolMessage [seq=" + seq + ", type=" + type + "]";
		}
		
		
	}

	/** A client or server-initiated request. */
	public static class Request extends ProtocolMessage {
	}

	/** Server-initiated event. */
	public static class Event extends ProtocolMessage {
	}

	/** Response to a request. */
	public static class Response extends ProtocolMessage {
	}

	/**
	 * Event message for 'initialized' event type. This event indicates that the
	 * debug adapter is ready to accept configuration requests (e.g.
	 * SetBreakpointsRequest, SetExceptionBreakpointsRequest). A debug adapter is
	 * expected to send this event when it is ready to accept configuration requests
	 * (but not before the InitializeRequest has finished). The sequence of
	 * events/requests is as follows: - adapters sends InitializedEvent (after the
	 * InitializeRequest has returned) - frontend sends zero or more
	 * SetBreakpointsRequest - frontend sends one SetFunctionBreakpointsRequest -
	 * frontend sends a SetExceptionBreakpointsRequest if one or more
	 * exceptionBreakpointFilters have been defined (or if
	 * supportsConfigurationDoneRequest is not defined or false) - frontend sends
	 * other future configuration requests - frontend sends one
	 * ConfigurationDoneRequest to indicate the end of the configuration
	 */
	public static class InitializedEvent extends Event {
		public String event;
	}

	/**
	 * Event message for 'stopped' event type. The event indicates that the
	 * execution of the debuggee has stopped due to some condition. This can be
	 * caused by a break point previously set, a stepping action has completed, by
	 * executing a debugger statement etc.
	 */
	public static class StoppedEvent extends Event {
		public String event;

		public static class Body {
			/**
			 * The reason for the event (such as: 'step', 'breakpoint', 'exception',
			 * 'pause', 'entry'). For backward compatibility this string is shown in the UI
			 * if the 'description' attribute is missing (but it must not be translated).
			 */
			public String reason;
			/**
			 * The full reason for the event, e.g. 'Paused on exception'. This string is
			 * shown in the UI as is.
			 */
			public String description;
			/** The thread which was stopped. */
			public int threadId;
			/**
			 * Additional information. E.g. if reason is 'exception', text contains the
			 * exception name. This string is shown in the UI.
			 */
			public String text;
			/**
			 * If allThreadsStopped is true, a debug adapter can announce that all threads
			 * have stopped. The client should use this information to enable that all
			 * threads can be expanded to access their stacktraces. If the attribute is
			 * missing or false, only the thread with the given threadId can be expanded.
			 */
			public boolean allThreadsStopped;
		};

		public Body body;
	}

	/**
	 * Event message for 'continued' event type. The event indicates that the
	 * execution of the debuggee has continued. Please note: a debug adapter is not
	 * expected to send this event in response to a request that implies that
	 * execution continues, e.g. 'launch' or 'continue'. It is only necessary to
	 * send a ContinuedEvent if there was no previous request that implied this.
	 */
	public static class ContinuedEvent extends Event {
		public String event;

		public static class Body {
			/** The thread which was continued. */
			public int threadId;
			/**
			 * If allThreadsContinued is true, a debug adapter can announce that all threads
			 * have continued.
			 */
			public boolean allThreadsContinued;
		};

		public Body body;
	}

	/**
	 * Event message for 'exited' event type. The event indicates that the debuggee
	 * has exited.
	 */
	public static class ExitedEvent extends Event {
		public String event;

		public static class Body {
			/** The exit code returned from the debuggee. */
			public int exitCode;
		};

		public Body body;
	}

	/**
	 * Event message for 'terminated' event types. The event indicates that
	 * debugging of the debuggee has terminated.
	 */
	public static class TerminatedEvent extends Event {
		public String event;

		public static class Body {
			/**
			 * A debug adapter may set 'restart' to true (or to an arbitrary object) to
			 * request that the front end restarts the session. The value is not interpreted
			 * by the client and passed unmodified as an attribute '__restart' to the
			 * launchRequest.
			 */
			public Object restart;
		};

		public Body body;
	}

	/**
	 * Event message for 'thread' event type. The event indicates that a thread has
	 * started or exited.
	 */
	public static class ThreadEvent extends Event {
		public String event;

		public static class Body {
			/** The reason for the event (such as: 'started', 'exited'). */
			public String reason;
			/** The identifier of the thread. */
			public int threadId;
		};

		public Body body;
	}

	/**
	 * Event message for 'output' event type. The event indicates that the target
	 * has produced some output.
	 */
	public static class OutputEvent extends Event {
		public String event;

		public static class Body {
			/**
			 * The category of output (such as: 'console', 'stdout', 'stderr', 'telemetry').
			 * If not specified, 'console' is assumed.
			 */
			public String category;
			/** The output to report. */
			public String output;
			/**
			 * If an attribute 'variablesReference' exists and its value is > 0, the output
			 * contains objects which can be retrieved by passing variablesReference to the
			 * VariablesRequest.
			 */
			public int variablesReference;
			/**
			 * Optional data to report. For the 'telemetry' category the data will be sent
			 * to telemetry, for the other categories the data is shown in JSON format.
			 */
			public Object data;
		};

		public Body body;
	}

	/**
	 * Event message for 'breakpoint' event type. The event indicates that some
	 * information about a breakpoint has changed.
	 */
	public static class BreakpointEvent extends Event {
		public String event;

		public static class Body {
			/** The reason for the event (such as: 'changed', 'new'). */
			public String reason;
			/** The breakpoint. */
			public Breakpoint breakpoint;
		};

		public Body body;
	}

	/**
	 * Event message for 'module' event type. The event indicates that some
	 * information about a module has changed.
	 */
	public static class ModuleEvent extends Event {
		public String event;

		public static class Body {
			/** The reason for the event. */
			public String reason;
			/**
			 * The new, changed, or removed module. In case of 'removed' only the module id
			 * is used.
			 */
			public Module module;
		};

		public Body body;
	}

	/**
	 * Event message for 'process' event type. The event indicates that the debugger
	 * has begun debugging a new process. Either one that it has launched, or one
	 * that it has attached to.
	 */
	public static class ProcessEvent extends Event {
		public String event;

		public static class Body {
			/**
			 * The logical name of the process. This is usually the full path to process's
			 * executable file. Example: /home/example/myproj/program.js.
			 */
			public String name;
			/**
			 * The system process id of the debugged process. This property will be missing
			 * for non-system processes.
			 */
			public int systemProcessId;
			/**
			 * If true, the process is running on the same computer as the debug adapter.
			 */
			public boolean isLocalProcess;
			/**
			 * Describes how the debug engine started debugging this process. launch:
			 * Process was launched under the debugger. attach: Debugger attached to an
			 * existing process. attachForSuspendedLaunch: A project launcher component has
			 * launched a new process in a suspended state and then asked the debugger to
			 * attach.
			 */
			public String startMethod;
		};

		public Body body;
	}

	/**
	 * runInTerminal request; value of command field is 'runInTerminal'. With this
	 * request a debug adapter can run a command in a terminal.
	 */
	public static class RunInTerminalRequest extends Request {
		public String command;
		public RunInTerminalRequestArguments arguments;
	}

	/** Arguments for 'runInTerminal' request. */
	public static class RunInTerminalRequestArguments {
		/** What kind of terminal to launch. */
		public String kind;
		/** Optional title of the terminal. */
		public String title;
		/** Working directory of the command. */
		public String cwd;
		/** List of arguments. The first argument is the command to run. */
		public String[] args;
		/** Environment key-value pairs that are added to the default environment. */
		public Map<String, String> env;
	}

	/** Response to Initialize request. */
	public static class RunInTerminalResponse extends Response {
		public static class Body {
			/** The process ID. */
			public int processId;
		};

		public Body body;
	}

	/**
	 * On error that is whenever 'success' is false, the body can provide more
	 * details.
	 */
	public static class ErrorResponse extends Response {
		public static class Body {
			/** An optional, structured error message. */
			public Message error;
		};

		public Body body;
	}

	/** Initialize request; value of command field is 'initialize'. */
	public static class InitializeRequest extends Request {
		public String command;
		public InitializeRequestArguments arguments;
	}

	/** Arguments for 'initialize' request. */
	public static class InitializeRequestArguments {
		/** The ID of the (frontend) client using this adapter. */
		public String clientID;
		/** The ID of the debug adapter. */
		public String adapterID;
		/** If true all line numbers are 1-based (default). */
		public boolean linesStartAt1;
		/** If true all column numbers are 1-based (default). */
		public boolean columnsStartAt1;
		/**
		 * Determines in what format paths are specified. Possible values are 'path' or
		 * 'uri'. The default is 'path', which is the native format.
		 */
		public String pathFormat;
		/** Client supports the optional type attribute for variables. */
		public boolean supportsVariableType;
		/** Client supports the paging of variables. */
		public boolean supportsVariablePaging;
		/** Client supports the runInTerminal request. */
		public boolean supportsRunInTerminalRequest;
	}

	/** Response to 'initialize' request. */
	public static class InitializeResponse extends Response {
		/** The capabilities of this debug adapter. */
		public Capabilities body;
	}

	/**
	 * ConfigurationDone request; value of command field is 'configurationDone'. The
	 * client of the debug protocol must send this request at the end of the
	 * sequence of configuration requests (which was started by the
	 * InitializedEvent).
	 */
	public static class ConfigurationDoneRequest extends Request {
		public String command;
		public ConfigurationDoneArguments arguments;
	}

	/**
	 * Arguments for 'configurationDone' request. The configurationDone request has
	 * no standardized attributes.
	 */
	public static class ConfigurationDoneArguments {
	}

	/**
	 * Response to 'configurationDone' request. This is just an acknowledgement, so
	 * no body field is required.
	 */
	public static class ConfigurationDoneResponse extends Response {
	}

	/** Launch request; value of command field is 'launch'. */
	public static class LaunchRequest extends Request {
		public String command;
		public LaunchRequestArguments arguments;
	}

	/** Arguments for 'launch' request. */
	public static class LaunchRequestArguments {
		/**
		 * If noDebug is true the launch request should launch the program without
		 * enabling debugging.
		 */
		public boolean noDebug;
	}

	/**
	 * Response to 'launch' request. This is just an acknowledgement, so no body
	 * field is required.
	 */
	public static class LaunchResponse extends Response {
	}

	/** Attach request; value of command field is 'attach'. */
	public static class AttachRequest extends Request {
		public String command;
		public AttachRequestArguments arguments;
	}

	/**
	 * Arguments for 'attach' request. The attach request has no standardized
	 * attributes.
	 */
	public static class AttachRequestArguments {
	}

	/**
	 * Response to 'attach' request. This is just an acknowledgement, so no body
	 * field is required.
	 */
	public static class AttachResponse extends Response {
	}

	/**
	 * Restart request; value of command field is 'restart'. Restarts a debug
	 * session. If the capability 'supportsRestartRequest' is missing or has the
	 * value false, the client will implement 'restart' by terminating the debug
	 * adapter first and then launching it anew. A debug adapter can override this
	 * default behaviour by implementing a restart request and setting the
	 * capability 'supportsRestartRequest' to true.
	 */
	public static class RestartRequest extends Request {
		public String command;
		public RestartArguments arguments;
	}

	/**
	 * Arguments for 'restart' request. The restart request has no standardized
	 * attributes.
	 */
	public static class RestartArguments {
	}

	/**
	 * Response to 'restart' request. This is just an acknowledgement, so no body
	 * field is required.
	 */
	public static class RestartResponse extends Response {
	}

	/** Disconnect request; value of command field is 'disconnect'. */
	public static class DisconnectRequest extends Request {
		public String command;
		public DisconnectArguments arguments;
	}

	/** Arguments for 'disconnect' request. */
	public static class DisconnectArguments {
		/**
		 * Indicates whether the debuggee should be terminated when the debugger is
		 * disconnected. If unspecified, the debug adapter is free to do whatever it
		 * thinks is best. A client can only rely on this attribute being properly
		 * honored if a debug adapter returns true for the 'supportTerminateDebuggee'
		 * capability.
		 */
		public boolean terminateDebuggee;
	}

	/**
	 * Response to 'disconnect' request. This is just an acknowledgement, so no body
	 * field is required.
	 */
	public static class DisconnectResponse extends Response {
	}

	/**
	 * SetBreakpoints request; value of command field is 'setBreakpoints'. Sets
	 * multiple breakpoints for a single source and clears all previous breakpoints
	 * in that source. To clear all breakpoint for a source, specify an empty array.
	 * When a breakpoint is hit, a StoppedEvent (event type 'breakpoint') is
	 * generated.
	 */
	public static class SetBreakpointsRequest extends Request {
		public String command;
		public SetBreakpointsArguments arguments;
	}

	/** Arguments for 'setBreakpoints' request. */
	public static class SetBreakpointsArguments {
		/**
		 * The source location of the breakpoints; either source.path or
		 * source.reference must be specified.
		 */
		public Source source;
		/** The code locations of the breakpoints. */
		public SourceBreakpoint[] breakpoints;
		/** Deprecated: The code locations of the breakpoints. */
		public int[] lines;
		/**
		 * A value of true indicates that the underlying source has been modified which
		 * results in new breakpoint locations.
		 */
		public boolean sourceModified;
	}

	/**
	 * Response to 'setBreakpoints' request. Returned is information about each
	 * breakpoint created by this request. This includes the actual code location
	 * and whether the breakpoint could be verified. The breakpoints returned are in
	 * the same order as the elements of the 'breakpoints' (or the deprecated
	 * 'lines') in the SetBreakpointsArguments.
	 */
	public static class SetBreakpointsResponse extends Response {
		public static class Body {
			/**
			 * Information about the breakpoints. The array elements are in the same order
			 * as the elements of the 'breakpoints' (or the deprecated 'lines') in the
			 * SetBreakpointsArguments.
			 */
			public Breakpoint[] breakpoints;
		};

		public Body body;
	}

	/**
	 * SetFunctionBreakpoints request; value of command field is
	 * 'setFunctionBreakpoints'. Sets multiple function breakpoints and clears all
	 * previous function breakpoints. To clear all function breakpoint, specify an
	 * empty array. When a function breakpoint is hit, a StoppedEvent (event type
	 * 'function breakpoint') is generated.
	 */
	public static class SetFunctionBreakpointsRequest extends Request {
		public String command;
		public SetFunctionBreakpointsArguments arguments;
	}

	/** Arguments for 'setFunctionBreakpoints' request. */
	public static class SetFunctionBreakpointsArguments {
		/** The function names of the breakpoints. */
		public FunctionBreakpoint[] breakpoints;
	}

	/**
	 * Response to 'setFunctionBreakpoints' request. Returned is information about
	 * each breakpoint created by this request.
	 */
	public static class SetFunctionBreakpointsResponse extends Response {
		public static class Body {
			/**
			 * Information about the breakpoints. The array elements correspond to the
			 * elements of the 'breakpoints' array.
			 */
			public Breakpoint[] breakpoints;
		};

		public Body body;
	}

	/**
	 * SetExceptionBreakpoints request; value of command field is
	 * 'setExceptionBreakpoints'. The request configures the debuggers response to
	 * thrown exceptions. If an exception is configured to break, a StoppedEvent is
	 * fired (event type 'exception').
	 */
	public static class SetExceptionBreakpointsRequest extends Request {
		public String command;
		public SetExceptionBreakpointsArguments arguments;
	}

	/** Arguments for 'setExceptionBreakpoints' request. */
	public static class SetExceptionBreakpointsArguments {
		/**
		 * IDs of checked exception options. The set of IDs is returned via the
		 * 'exceptionBreakpointFilters' capability.
		 */
		public String[] filters;
		/** Configuration options for selected exceptions. */
		public ExceptionOptions[] exceptionOptions;
	}

	/**
	 * Response to 'setExceptionBreakpoints' request. This is just an
	 * acknowledgement, so no body field is required.
	 */
	public static class SetExceptionBreakpointsResponse extends Response {
	}

	/**
	 * Continue request; value of command field is 'continue'. The request starts
	 * the debuggee to run again.
	 */
	public static class ContinueRequest extends Request {
		public String command;
		public ContinueArguments arguments;
	}

	/** Arguments for 'continue' request. */
	public static class ContinueArguments {
		/**
		 * Continue execution for the specified thread (if possible). If the backend
		 * cannot continue on a single thread but will continue on all threads, it
		 * should set the allThreadsContinued attribute in the response to true.
		 */
		public int threadId;
	}

	/** Response to 'continue' request. */
	public static class ContinueResponse extends Response {
		public static class Body {
			/**
			 * If true, the continue request has ignored the specified thread and continued
			 * all threads instead. If this attribute is missing a value of 'true' is
			 * assumed for backward compatibility.
			 */
			public boolean allThreadsContinued;
		};

		public Body body;
	}

	/**
	 * Next request; value of command field is 'next'. The request starts the
	 * debuggee to run again for one step. The debug adapter first sends the
	 * NextResponse and then a StoppedEvent (event type 'step') after the step has
	 * completed.
	 */
	public static class NextRequest extends Request {
		public String command;
		public NextArguments arguments;
	}

	/** Arguments for 'next' request. */
	public static class NextArguments {
		/** Execute 'next' for this thread. */
		public int threadId;
	}

	/**
	 * Response to 'next' request. This is just an acknowledgement, so no body field
	 * is required.
	 */
	public static class NextResponse extends Response {
	}

	/**
	 * StepIn request; value of command field is 'stepIn'. The request starts the
	 * debuggee to step into a function/method if possible. If it cannot step into a
	 * target, 'stepIn' behaves like 'next'. The debug adapter first sends the
	 * StepInResponse and then a StoppedEvent (event type 'step') after the step has
	 * completed. If there are multiple function/method calls (or other targets) on
	 * the source line, the optional argument 'targetId' can be used to control into
	 * which target the 'stepIn' should occur. The list of possible targets for a
	 * given source line can be retrieved via the 'stepInTargets' request.
	 */
	public static class StepInRequest extends Request {
		public String command;
		public StepInArguments arguments;
	}

	/** Arguments for 'stepIn' request. */
	public static class StepInArguments {
		/** Execute 'stepIn' for this thread. */
		public int threadId;
		/** Optional id of the target to step into. */
		public int targetId;
	}

	/**
	 * Response to 'stepIn' request. This is just an acknowledgement, so no body
	 * field is required.
	 */
	public static class StepInResponse extends Response {
	}

	/**
	 * StepOut request; value of command field is 'stepOut'. The request starts the
	 * debuggee to run again for one step. The debug adapter first sends the
	 * StepOutResponse and then a StoppedEvent (event type 'step') after the step
	 * has completed.
	 */
	public static class StepOutRequest extends Request {
		public String command;
		public StepOutArguments arguments;
	}

	/** Arguments for 'stepOut' request. */
	public static class StepOutArguments {
		/** Execute 'stepOut' for this thread. */
		public int threadId;
	}

	/**
	 * Response to 'stepOut' request. This is just an acknowledgement, so no body
	 * field is required.
	 */
	public static class StepOutResponse extends Response {
	}

	/**
	 * StepBack request; value of command field is 'stepBack'. The request starts
	 * the debuggee to run one step backwards. The debug adapter first sends the
	 * StepBackResponse and then a StoppedEvent (event type 'step') after the step
	 * has completed. Clients should only call this request if the capability
	 * supportsStepBack is true.
	 */
	public static class StepBackRequest extends Request {
		public String command;
		public StepBackArguments arguments;
	}

	/** Arguments for 'stepBack' request. */
	public static class StepBackArguments {
		/** Exceute 'stepBack' for this thread. */
		public int threadId;
	}

	/**
	 * Response to 'stepBack' request. This is just an acknowledgement, so no body
	 * field is required.
	 */
	public static class StepBackResponse extends Response {
	}

	/**
	 * ReverseContinue request; value of command field is 'reverseContinue'. The
	 * request starts the debuggee to run backward. Clients should only call this
	 * request if the capability supportsStepBack is true.
	 */
	public static class ReverseContinueRequest extends Request {
		public String command;
		public ReverseContinueArguments arguments;
	}

	/** Arguments for 'reverseContinue' request. */
	public static class ReverseContinueArguments {
		/** Exceute 'reverseContinue' for this thread. */
		public int threadId;
	}

	/**
	 * Response to 'reverseContinue' request. This is just an acknowledgement, so no
	 * body field is required.
	 */
	public static class ReverseContinueResponse extends Response {
	}

	/**
	 * RestartFrame request; value of command field is 'restartFrame'. The request
	 * restarts execution of the specified stackframe. The debug adapter first sends
	 * the RestartFrameResponse and then a StoppedEvent (event type 'restart') after
	 * the restart has completed.
	 */
	public static class RestartFrameRequest extends Request {
		public String command;
		public RestartFrameArguments arguments;
	}

	/** Arguments for 'restartFrame' request. */
	public static class RestartFrameArguments {
		/** Restart this stackframe. */
		public int frameId;
	}

	/**
	 * Response to 'restartFrame' request. This is just an acknowledgement, so no
	 * body field is required.
	 */
	public static class RestartFrameResponse extends Response {
	}

	/**
	 * Goto request; value of command field is 'goto'. The request sets the location
	 * where the debuggee will continue to run. This makes it possible to skip the
	 * execution of code or to executed code again. The code between the current
	 * location and the goto target is not executed but skipped. The debug adapter
	 * first sends the GotoResponse and then a StoppedEvent (event type 'goto').
	 */
	public static class GotoRequest extends Request {
		public String command;
		public GotoArguments arguments;
	}

	/** Arguments for 'goto' request. */
	public static class GotoArguments {
		/** Set the goto target for this thread. */
		public int threadId;
		/** The location where the debuggee will continue to run. */
		public int targetId;
	}

	/**
	 * Response to 'goto' request. This is just an acknowledgement, so no body field
	 * is required.
	 */
	public static class GotoResponse extends Response {
	}

	/**
	 * Pause request; value of command field is 'pause'. The request suspenses the
	 * debuggee. The debug adapter first sends the PauseResponse and then a
	 * StoppedEvent (event type 'pause') after the thread has been paused
	 * successfully.
	 */
	public static class PauseRequest extends Request {
		public String command;
		public PauseArguments arguments;
	}

	/** Arguments for 'pause' request. */
	public static class PauseArguments {
		/** Pause execution for this thread. */
		public int threadId;
	}

	/**
	 * Response to 'pause' request. This is just an acknowledgement, so no body
	 * field is required.
	 */
	public static class PauseResponse extends Response {
	}

	/**
	 * StackTrace request; value of command field is 'stackTrace'. The request
	 * returns a stacktrace from the current execution state.
	 */
	public static class StackTraceRequest extends Request {
		public String command;
		public StackTraceArguments arguments;
	}

	/** Arguments for 'stackTrace' request. */
	public static class StackTraceArguments {
		/** Retrieve the stacktrace for this thread. */
		public int threadId;
		/** The index of the first frame to return; if omitted frames start at 0. */
		public int startFrame;
		/**
		 * The maximum number of frames to return. If levels is not specified or 0, all
		 * frames are returned.
		 */
		public int levels;
		/** Specifies details on how to format the stack frames. */
		public StackFrameFormat format;
	}

	/** Response to 'stackTrace' request. */
	public static class StackTraceResponse extends Response {
		public static class Body {
			/**
			 * The frames of the stackframe. If the array has length zero, there are no
			 * stackframes available. This means that there is no location information
			 * available.
			 */
			public StackFrame[] stackFrames;
			/** The total number of frames available. */
			public int totalFrames;
		};

		public Body body;
	}

	/**
	 * Scopes request; value of command field is 'scopes'. The request returns the
	 * variable scopes for a given stackframe ID.
	 */
	public static class ScopesRequest extends Request {
		public String command;
		public ScopesArguments arguments;
	}

	/** Arguments for 'scopes' request. */
	public static class ScopesArguments {
		/** Retrieve the scopes for this stackframe. */
		public int frameId;
	}

	/** Response to 'scopes' request. */
	public static class ScopesResponse extends Response {
		public static class Body {
			/**
			 * The scopes of the stackframe. If the array has length zero, there are no
			 * scopes available.
			 */
			public Scope[] scopes;
		};

		public Body body;
	}

	/**
	 * Variables request; value of command field is 'variables'. Retrieves all child
	 * variables for the given variable reference. An optional filter can be used to
	 * limit the fetched children to either named or indexed children.
	 */
	public static class VariablesRequest extends Request {
		public String command;
		public VariablesArguments arguments;
	}

	/** Arguments for 'variables' request. */
	public static class VariablesArguments {
		/** The Variable reference. */
		public int variablesReference;
		/**
		 * Optional filter to limit the child variables to either named or indexed. If
		 * ommited, both types are fetched.
		 */
		public String filter;
		/**
		 * The index of the first variable to return; if omitted children start at 0.
		 */
		public int start;
		/**
		 * The number of variables to return. If count is missing or 0, all variables
		 * are returned.
		 */
		public int count;
		/** Specifies details on how to format the Variable values. */
		public ValueFormat format;
	}

	/** Response to 'variables' request. */
	public static class VariablesResponse extends Response {
		public static class Body {
			/** All (or a range) of variables for the given variable reference. */
			public Variable[] variables;
		};

		public Body body;
	}

	/**
	 * setVariable request; value of command field is 'setVariable'. Set the
	 * variable with the given name in the variable container to a new value.
	 */
	public static class SetVariableRequest extends Request {
		public String command;
		public SetVariableArguments arguments;
	}

	/** Arguments for 'setVariable' request. */
	public static class SetVariableArguments {
		/** The reference of the variable container. */
		public int variablesReference;
		/** The name of the variable. */
		public String name;
		/** The value of the variable. */
		public String value;
		/** Specifies details on how to format the response value. */
		public ValueFormat format;
	}

	/** Response to 'setVariable' request. */
	public static class SetVariableResponse extends Response {
		public static class Body {
			/** The new value of the variable. */
			public String value;
			/**
			 * The type of the new value. Typically shown in the UI when hovering over the
			 * value.
			 */
			public String type;
			/**
			 * If variablesReference is > 0, the new value is structured and its children
			 * can be retrieved by passing variablesReference to the VariablesRequest.
			 */
			public int variablesReference;
			/**
			 * The number of named child variables. The client can use this optional
			 * information to present the variables in a paged UI and fetch them in chunks.
			 */
			public int namedVariables;
			/**
			 * The number of indexed child variables. The client can use this optional
			 * information to present the variables in a paged UI and fetch them in chunks.
			 */
			public int indexedVariables;
		};

		public Body body;
	}

	/**
	 * Source request; value of command field is 'source'. The request retrieves the
	 * source code for a given source reference.
	 */
	public static class SourceRequest extends Request {
		public String command;
		public SourceArguments arguments;
	}

	/** Arguments for 'source' request. */
	public static class SourceArguments {
		/**
		 * Specifies the source content to load. Either source.path or
		 * source.sourceReference must be specified.
		 */
		public Source source;
		/**
		 * The reference to the source. This is the same as source.sourceReference. This
		 * is provided for backward compatibility since old backends do not understand
		 * the 'source' attribute.
		 */
		public int sourceReference;
	}

	/** Response to 'source' request. */
	public static class SourceResponse extends Response {
		public static class Body {
			/** Content of the source reference. */
			public String content;
			/** Optional content type (mime type) of the source. */
			public String mimeType;
		};

		public Body body;
	}

	/**
	 * Thread request; value of command field is 'threads'. The request retrieves a
	 * list of all threads.
	 */
	public static class ThreadsRequest extends Request {
		public String command;
	}

	/** Response to 'threads' request. */
	public static class ThreadsResponse extends Response {
		public static class Body {
			/** All threads. */
			public Thread[] threads;
		};

		public Body body;
	}

	/**
	 * Modules can be retrieved from the debug adapter with the ModulesRequest which
	 * can either return all modules or a range of modules to support paging.
	 */
	public static class ModulesRequest extends Request {
		public String command;
		public ModulesArguments arguments;
	}

	/** Arguments for 'modules' request. */
	public static class ModulesArguments {
		/** The index of the first module to return; if omitted modules start at 0. */
		public int startModule;
		/**
		 * The number of modules to return. If moduleCount is not specified or 0, all
		 * modules are returned.
		 */
		public int moduleCount;
	}

	/** Response to 'modules' request. */
	public static class ModulesResponse extends Response {
		public static class Body {
			/** All modules or range of modules. */
			public Module[] modules;
			/** The total number of modules available. */
			public int totalModules;
		};

		public Body body;
	}

	/**
	 * Evaluate request; value of command field is 'evaluate'. Evaluates the given
	 * expression in the context of the top most stack frame. The expression has
	 * access to any variables and arguments that are in scope.
	 */
	public static class EvaluateRequest extends Request {
		public String command;
		public EvaluateArguments arguments;
	}

	/** Arguments for 'evaluate' request. */
	public static class EvaluateArguments {
		/** The expression to evaluate. */
		public String expression;
		/**
		 * Evaluate the expression in the scope of this stack frame. If not specified,
		 * the expression is evaluated in the global scope.
		 */
		public int frameId;
		/**
		 * The context in which the evaluate request is run. Possible values are 'watch'
		 * if evaluate is run in a watch, 'repl' if run from the REPL console, or
		 * 'hover' if run from a data hover.
		 */
		public String context;
		/** Specifies details on how to format the Evaluate result. */
		public ValueFormat format;
	}

	/** Response to 'evaluate' request. */
	public static class EvaluateResponse extends Response {
		public static class Body {
			/** The result of the evaluate request. */
			public String result;
			/** The optional type of the evaluate result. */
			public String type;
			/**
			 * If variablesReference is > 0, the evaluate result is structured and its
			 * children can be retrieved by passing variablesReference to the
			 * VariablesRequest.
			 */
			public int variablesReference;
			/**
			 * The number of named child variables. The client can use this optional
			 * information to present the variables in a paged UI and fetch them in chunks.
			 */
			public int namedVariables;
			/**
			 * The number of indexed child variables. The client can use this optional
			 * information to present the variables in a paged UI and fetch them in chunks.
			 */
			public int indexedVariables;
		};

		public Body body;
	}

	/**
	 * StepInTargets request; value of command field is 'stepInTargets'. This
	 * request retrieves the possible stepIn targets for the specified stack frame.
	 * These targets can be used in the 'stepIn' request. The StepInTargets may only
	 * be called if the 'supportsStepInTargetsRequest' capability exists and is
	 * true.
	 */
	public static class StepInTargetsRequest extends Request {
		public String command;
		public StepInTargetsArguments arguments;
	}

	/** Arguments for 'stepInTargets' request. */
	public static class StepInTargetsArguments {
		/** The stack frame for which to retrieve the possible stepIn targets. */
		public int frameId;
	}

	/** Response to 'stepInTargets' request. */
	public static class StepInTargetsResponse extends Response {
		public static class Body {
			/** The possible stepIn targets of the specified source location. */
			public StepInTarget[] targets;
		};

		public Body body;
	}

	/**
	 * GotoTargets request; value of command field is 'gotoTargets'. This request
	 * retrieves the possible goto targets for the specified source location. These
	 * targets can be used in the 'goto' request. The GotoTargets request may only
	 * be called if the 'supportsGotoTargetsRequest' capability exists and is true.
	 */
	public static class GotoTargetsRequest extends Request {
		public String command;
		public GotoTargetsArguments arguments;
	}

	/** Arguments for 'gotoTargets' request. */
	public static class GotoTargetsArguments {
		/** The source location for which the goto targets are determined. */
		public Source source;
		/** The line location for which the goto targets are determined. */
		public int line;
		/** An optional column location for which the goto targets are determined. */
		public int column;
	}

	/** Response to 'gotoTargets' request. */
	public static class GotoTargetsResponse extends Response {
		public static class Body {
			/** The possible goto targets of the specified location. */
			public GotoTarget[] targets;
		};

		public Body body;
	}

	/**
	 * CompletionsRequest request; value of command field is 'completions'. Returns
	 * a list of possible completions for a given caret position and text. The
	 * CompletionsRequest may only be called if the 'supportsCompletionsRequest'
	 * capability exists and is true.
	 */
	public static class CompletionsRequest extends Request {
		public String command;
		public CompletionsArguments arguments;
	}

	/** Arguments for 'completions' request. */
	public static class CompletionsArguments {
		/**
		 * Returns completions in the scope of this stack frame. If not specified, the
		 * completions are returned for the global scope.
		 */
		public int frameId;
		/**
		 * One or more source lines. Typically this is the text a user has typed into
		 * the debug console before he asked for completion.
		 */
		public String text;
		/** The character position for which to determine the completion proposals. */
		public int column;
		/**
		 * An optional line for which to determine the completion proposals. If missing
		 * the first line of the text is assumed.
		 */
		public int line;
	}

	/** Response to 'completions' request. */
	public static class CompletionsResponse extends Response {
		public static class Body {
			/** The possible completions for . */
			public CompletionItem[] targets;
		};

		public Body body;
	}

	/**
	 * ExceptionInfoRequest request; value of command field is 'exceptionInfo'.
	 * Retrieves the details of the exception that caused the StoppedEvent to be
	 * raised.
	 */
	public static class ExceptionInfoRequest extends Request {
		public String command;
		public ExceptionInfoArguments arguments;
	}

	/** Arguments for 'exceptionInfo' request. */
	public static class ExceptionInfoArguments {
		/** Thread for which exception information should be retrieved. */
		public int threadId;
	}

	/** Response to 'exceptionInfo' request. */
	public static class ExceptionInfoResponse extends Response {
		public static class Body {
			/** ID of the exception that was thrown. */
			public String exceptionId;
			/** Descriptive text for the exception provided by the debug adapter. */
			public String description;
			/** Mode that caused the exception notification to be raised. */
			public ExceptionBreakMode breakMode;
			/** Detailed information about the exception. */
			public ExceptionDetails details;
		};

		public Body body;
	}

	/** Information about the capabilities of a debug adapter. */
	public static class Capabilities {
		/** The debug adapter supports the configurationDoneRequest. */
		public boolean supportsConfigurationDoneRequest;
		/** The debug adapter supports function breakpoints. */
		public boolean supportsFunctionBreakpoints;
		/** The debug adapter supports conditional breakpoints. */
		public boolean supportsConditionalBreakpoints;
		/**
		 * The debug adapter supports breakpoints that break execution after a specified
		 * number of hits.
		 */
		public boolean supportsHitConditionalBreakpoints;
		/**
		 * The debug adapter supports a (side effect free) evaluate request for data
		 * hovers.
		 */
		public boolean supportsEvaluateForHovers;
		/** Available filters or options for the setExceptionBreakpoints request. */
		public ExceptionBreakpointsFilter[] exceptionBreakpointFilters;
		/**
		 * The debug adapter supports stepping back via the stepBack and reverseContinue
		 * requests.
		 */
		public boolean supportsStepBack;
		/** The debug adapter supports setting a variable to a value. */
		public boolean supportsSetVariable;
		/** The debug adapter supports restarting a frame. */
		public boolean supportsRestartFrame;
		/** The debug adapter supports the gotoTargetsRequest. */
		public boolean supportsGotoTargetsRequest;
		/** The debug adapter supports the stepInTargetsRequest. */
		public boolean supportsStepInTargetsRequest;
		/** The debug adapter supports the completionsRequest. */
		public boolean supportsCompletionsRequest;
		/** The debug adapter supports the modules request. */
		public boolean supportsModulesRequest;
		/** The set of additional module information exposed by the debug adapter. */
		public ColumnDescriptor[] additionalModuleColumns;
		/** Checksum algorithms supported by the debug adapter. */
		public ChecksumAlgorithm[] supportedChecksumAlgorithms;
		/**
		 * The debug adapter supports the RestartRequest. In this case a client should
		 * not implement 'restart' by terminating and relaunching the adapter but by
		 * calling the RestartRequest.
		 */
		public boolean supportsRestartRequest;
		/**
		 * The debug adapter supports 'exceptionOptions' on the setExceptionBreakpoints
		 * request.
		 */
		public boolean supportsExceptionOptions;
		/**
		 * The debug adapter supports a 'format' attribute on the stackTraceRequest,
		 * variablesRequest, and evaluateRequest.
		 */
		public boolean supportsValueFormattingOptions;
		/** The debug adapter supports the exceptionInfo request. */
		public boolean supportsExceptionInfoRequest;
		/**
		 * The debug adapter supports the 'terminateDebuggee' attribute on the
		 * 'disconnect' request.
		 */
		public boolean supportTerminateDebuggee;
		/**
		 * The debug adapter supports the delayed loading of parts of the stack, which
		 * requires that both the 'startFrame' and 'levels' arguments and the
		 * 'totalFrames' result of the 'StackTrace' request are supported.
		 */
		public boolean supportsDelayedStackTraceLoading;
	}

	/**
	 * An ExceptionBreakpointsFilter is shown in the UI as an option for configuring
	 * how exceptions are dealt with.
	 */
	public static class ExceptionBreakpointsFilter {
		/**
		 * The internal ID of the filter. This value is passed to the
		 * setExceptionBreakpoints request.
		 */
		public String filter;
		/** The name of the filter. This will be shown in the UI. */
		public String label;
		/** Initial value of the filter. If not specified a value 'false' is assumed. */
		public boolean default_;
	}

	/** A structured message object. Used to return errors from requests. */
	public static class Message {
		/** Unique identifier for the message. */
		public int id;
		/**
		 * A format string for the message. Embedded variables have the form '{name}'.
		 * If variable name starts with an underscore character, the variable does not
		 * contain user data (PII) and can be safely used for telemetry purposes.
		 */
		public String format;
		/**
		 * An object used as a dictionary for looking up the variables in the format
		 * string.
		 */
		public Map<String, String> variables;
		/** If true send to telemetry. */
		public boolean sendTelemetry;
		/** If true show user. */
		public boolean showUser;
		/**
		 * An optional url where additional information about this message can be found.
		 */
		public String url;
		/**
		 * An optional label that is presented to the user as the UI for opening the
		 * url.
		 */
		public String urlLabel;
	}

	/**
	 * A Module object represents a row in the modules view. Two attributes are
	 * mandatory: an id identifies a module in the modules view and is used in a
	 * ModuleEvent for identifying a module for adding, updating or deleting. The
	 * name is used to minimally render the module in the UI.
	 *
	 * Additional attributes can be added to the module. They will show up in the
	 * module View if they have a corresponding ColumnDescriptor.
	 *
	 * To avoid an unnecessary proliferation of additional attributes with similar
	 * semantics but different names we recommend to re-use attributes from the
	 * 'recommended' list below first, and only introduce new attributes if nothing
	 * appropriate could be found.
	 */
	public static class Module {
		/** Unique identifier for the module. */
		public /* type one of integer | string */ Object id;
		/** A name of the module. */
		public String name;
		/**
		 * optional but recommended attributes. always try to use these first before
		 * introducing additional attributes.
		 *
		 * Logical full path to the module. The exact definition is implementation
		 * defined, but usually this would be a full path to the on-disk file for the
		 * module.
		 */
		public String path;
		/** True if the module is optimized. */
		public boolean isOptimized;
		/**
		 * True if the module is considered 'user code' by a debugger that supports
		 * 'Just My Code'.
		 */
		public boolean isUserCode;
		/** Version of Module. */
		public String version;
		/**
		 * User understandable description of if symbols were found for the module (ex:
		 * 'Symbols Loaded', 'Symbols not found', etc.
		 */
		public String symbolStatus;
		/**
		 * Logical full path to the symbol file. The exact definition is implementation
		 * defined.
		 */
		public String symbolFilePath;
		/** Module created or modified. */
		public String dateTimeStamp;
		/** Address range covered by this module. */
		public String addressRange;
	}

	/**
	 * A ColumnDescriptor specifies what module attribute to show in a column of the
	 * ModulesView, how to format it, and what the column's label should be. It is
	 * only used if the underlying UI actually supports this level of customization.
	 */
	public static class ColumnDescriptor {
		/** Name of the attribute rendered in this column. */
		public String attributeName;
		/** Header UI label of column. */
		public String label;
		/**
		 * Format to use for the rendered values in this column. TBD how the format
		 * strings looks like.
		 */
		public String format;
		/** Datatype of values in this column. Defaults to 'string' if not specified. */
		public String type;
		/** Width of this column in characters (hint only). */
		public int width;
	}

	/**
	 * The ModulesViewDescriptor is the container for all declarative configuration
	 * options of a ModuleView. For now it only specifies the columns to be shown in
	 * the modules view.
	 */
	public static class ModulesViewDescriptor {
		public ColumnDescriptor[] columns;
	}

	/** A Thread */
	public static class Thread {
		/** Unique identifier for the thread. */
		public int id;
		/** A name of the thread. */
		public String name;
	}

	/**
	 * A Source is a descriptor for source code. It is returned from the debug
	 * adapter as part of a StackFrame and it is used by clients when specifying
	 * breakpoints.
	 */
	public static class Source {
		/**
		 * The short name of the source. Every source returned from the debug adapter
		 * has a name. When sending a source to the debug adapter this name is optional.
		 */
		public String name;
		/**
		 * The path of the source to be shown in the UI. It is only used to locate and
		 * load the content of the source if no sourceReference is specified (or its
		 * vaule is 0).
		 */
		public String path;
		/**
		 * If sourceReference > 0 the contents of the source must be retrieved through
		 * the SourceRequest (even if a path is specified). A sourceReference is only
		 * valid for a session, so it must not be used to persist a source.
		 */
		public int sourceReference;
		/**
		 * An optional hint for how to present the source in the UI. A value of
		 * 'deemphasize' can be used to indicate that the source is not available or
		 * that it is skipped on stepping.
		 */
		public String presentationHint;
		/**
		 * The (optional) origin of this source: possible values 'internal module',
		 * 'inlined content from source map', etc.
		 */
		public String origin;
		/**
		 * Optional data that a debug adapter might want to loop through the client. The
		 * client should leave the data intact and persist it across sessions. The
		 * client should not interpret the data.
		 */
		public Object adapterData;
		/** The checksums associated with this file. */
		public Checksum[] checksums;
	}

	/** A Stackframe contains the source location. */
	public static class StackFrame {
		/**
		 * An identifier for the stack frame. It must be unique across all threads. This
		 * id can be used to retrieve the scopes of the frame with the 'scopesRequest'
		 * or to restart the execution of a stackframe.
		 */
		public int id;
		/** The name of the stack frame, typically a method name. */
		public String name;
		/** The optional source of the frame. */
		public Source source;
		/**
		 * The line within the file of the frame. If source is null or doesn't exist,
		 * line is 0 and must be ignored.
		 */
		public int line;
		/**
		 * The column within the line. If source is null or doesn't exist, column is 0
		 * and must be ignored.
		 */
		public int column;
		/** An optional end line of the range covered by the stack frame. */
		public int endLine;
		/** An optional end column of the range covered by the stack frame. */
		public int endColumn;
		/** The module associated with this frame, if any. */
		public /* type one of integer | string */ Object moduleId;
		/**
		 * An optional hint for how to present this frame in the UI. A value of 'label'
		 * can be used to indicate that the frame is an artificial frame that is used as
		 * a visual label or separator. A value of 'subtle' can be used to change the
		 * appearance of a frame in a 'subtle' way.
		 */
		public String presentationHint;
	}

	/**
	 * A Scope is a named container for variables. Optionally a scope can map to a
	 * source or a range within a source.
	 */
	public static class Scope {
		/** Name of the scope such as 'Arguments', 'Locals'. */
		public String name;
		/**
		 * The variables of this scope can be retrieved by passing the value of
		 * variablesReference to the VariablesRequest.
		 */
		public int variablesReference;
		/**
		 * The number of named variables in this scope. The client can use this optional
		 * information to present the variables in a paged UI and fetch them in chunks.
		 */
		public int namedVariables;
		/**
		 * The number of indexed variables in this scope. The client can use this
		 * optional information to present the variables in a paged UI and fetch them in
		 * chunks.
		 */
		public int indexedVariables;
		/**
		 * If true, the number of variables in this scope is large or expensive to
		 * retrieve.
		 */
		public boolean expensive;
		/** Optional source for this scope. */
		public Source source;
		/** Optional start line of the range covered by this scope. */
		public int line;
		/** Optional start column of the range covered by this scope. */
		public int column;
		/** Optional end line of the range covered by this scope. */
		public int endLine;
		/** Optional end column of the range covered by this scope. */
		public int endColumn;
	}

	/**
	 * A Variable is a name/value pair. Optionally a variable can have a 'type' that
	 * is shown if space permits or when hovering over the variable's name. An
	 * optional 'kind' is used to render additional properties of the variable, e.g.
	 * different icons can be used to indicate that a variable is public or private.
	 * If the value is structured (has children), a handle is provided to retrieve
	 * the children with the VariablesRequest. If the number of named or indexed
	 * children is large, the numbers should be returned via the optional
	 * 'namedVariables' and 'indexedVariables' attributes. The client can use this
	 * optional information to present the children in a paged UI and fetch them in
	 * chunks.
	 */
	public static class Variable {
		/** The variable's name. */
		public String name;
		/**
		 * The variable's value. This can be a multi-line text, e.g. for a function the
		 * body of a function.
		 */
		public String value;
		/**
		 * The type of the variable's value. Typically shown in the UI when hovering
		 * over the value.
		 */
		public String type;
		/**
		 * Properties of a variable that can be used to determine how to render the
		 * variable in the UI. Format of the string value: TBD.
		 */
		public String kind;
		/**
		 * Optional evaluatable name of this variable which can be passed to the
		 * 'EvaluateRequest' to fetch the variable's value.
		 */
		public String evaluateName;
		/**
		 * If variablesReference is > 0, the variable is structured and its children can
		 * be retrieved by passing variablesReference to the VariablesRequest.
		 */
		public int variablesReference;
		/**
		 * The number of named child variables. The client can use this optional
		 * information to present the children in a paged UI and fetch them in chunks.
		 */
		public int namedVariables;
		/**
		 * The number of indexed child variables. The client can use this optional
		 * information to present the children in a paged UI and fetch them in chunks.
		 */
		public int indexedVariables;
	}

	/** Properties of a breakpoint passed to the setBreakpoints request. */
	public static class SourceBreakpoint {
		/** The source line of the breakpoint. */
		public int line;
		/** An optional source column of the breakpoint. */
		public int column;
		/** An optional expression for conditional breakpoints. */
		public String condition;
		/**
		 * An optional expression that controls how many hits of the breakpoint are
		 * ignored. The backend is expected to interpret the expression as needed.
		 */
		public String hitCondition;
	}

	/** Properties of a breakpoint passed to the setFunctionBreakpoints request. */
	public static class FunctionBreakpoint {
		/** The name of the function. */
		public String name;
		/** An optional expression for conditional breakpoints. */
		public String condition;
		/**
		 * An optional expression that controls how many hits of the breakpoint are
		 * ignored. The backend is expected to interpret the expression as needed.
		 */
		public String hitCondition;
	}

	/**
	 * Information about a Breakpoint created in setBreakpoints or
	 * setFunctionBreakpoints.
	 */
	public static class Breakpoint {
		/** An optional unique identifier for the breakpoint. */
		public int id;
		/**
		 * If true breakpoint could be set (but not necessarily at the desired
		 * location).
		 */
		public boolean verified;
		/**
		 * An optional message about the state of the breakpoint. This is shown to the
		 * user and can be used to explain why a breakpoint could not be verified.
		 */
		public String message;
		/** The source where the breakpoint is located. */
		public Source source;
		/** The start line of the actual range covered by the breakpoint. */
		public int line;
		/** An optional start column of the actual range covered by the breakpoint. */
		public int column;
		/** An optional end line of the actual range covered by the breakpoint. */
		public int endLine;
		/**
		 * An optional end column of the actual range covered by the breakpoint. If no
		 * end line is given, then the end column is assumed to be in the start line.
		 */
		public int endColumn;
	}

	/**
	 * A StepInTarget can be used in the 'stepIn' request and determines into which
	 * single target the stepIn request should step.
	 */
	public static class StepInTarget {
		/** Unique identifier for a stepIn target. */
		public int id;
		/** The name of the stepIn target (shown in the UI). */
		public String label;
	}

	/**
	 * A GotoTarget describes a code location that can be used as a target in the
	 * 'goto' request. The possible goto targets can be determined via the
	 * 'gotoTargets' request.
	 */
	public static class GotoTarget {
		/** Unique identifier for a goto target. This is used in the goto request. */
		public int id;
		/** The name of the goto target (shown in the UI). */
		public String label;
		/** The line of the goto target. */
		public int line;
		/** An optional column of the goto target. */
		public int column;
		/** An optional end line of the range covered by the goto target. */
		public int endLine;
		/** An optional end column of the range covered by the goto target. */
		public int endColumn;
	}

	/** CompletionItems are the suggestions returned from the CompletionsRequest. */
	public static class CompletionItem {
		/**
		 * The label of this completion item. By default this is also the text that is
		 * inserted when selecting this completion.
		 */
		public String label;
		/** If text is not falsy then it is inserted instead of the label. */
		public String text;
		/**
		 * The item's type. Typically the client uses this information to render the
		 * item in the UI with an icon.
		 */
		public CompletionItemType type;
		/**
		 * This value determines the location (in the CompletionsRequest's 'text'
		 * attribute) where the completion text is added. If missing the text is added
		 * at the location specified by the CompletionsRequest's 'column' attribute.
		 */
		public int start;
		/**
		 * This value determines how many characters are overwritten by the completion
		 * text. If missing the value 0 is assumed which results in the completion text
		 * being inserted.
		 */
		public int length;
	}

	/**
	 * Some predefined types for the CompletionItem. Please note that not all
	 * clients have specific icons for all of them.
	 */
	enum CompletionItemType {
		method, function, constructor, field, variable, class_, interface_, module, property, unit, value, enum_, keyword, snippet, text, color, file, reference, customcolor
	}

	/** Names of checksum algorithms that may be supported by a debug adapter. */
	enum ChecksumAlgorithm {
		MD5, SHA1, SHA256, timestamp
	}

	/** The checksum of an item calculated by the specified algorithm. */
	public static class Checksum {
		/** The algorithm used to calculate this checksum. */
		public ChecksumAlgorithm algorithm;
		/** Value of the checksum. */
		public String checksum;
	}

	/** Provides formatting information for a value. */
	public static class ValueFormat {
		/** Display the value in hex. */
		public boolean hex;
	}

	/** Provides formatting information for a stack frame. */
	public static class StackFrameFormat extends ValueFormat {
		/** Displays parameters for the stack frame. */
		public boolean parameters;
		/** Displays the types of parameters for the stack frame. */
		public boolean parameterTypes;
		/** Displays the names of parameters for the stack frame. */
		public boolean parameterNames;
		/** Displays the values of parameters for the stack frame. */
		public boolean parameterValues;
		/** Displays the line number of the stack frame. */
		public boolean line;
		/** Displays the module of the stack frame. */
		public boolean module;
		/**
		 * Includes all stack frames, including those the debug adapter might otherwise
		 * hide.
		 */
		public boolean includeAll;
	}

	/** An ExceptionOptions assigns configuration options to a set of exceptions. */
	public static class ExceptionOptions {
		/**
		 * A path that selects a single or multiple exceptions in a tree. If 'path' is
		 * missing, the whole tree is selected. By convention the first segment of the
		 * path is a category that is used to group exceptions in the UI.
		 */
		public ExceptionPathSegment[] path;
		/** Condition when a thrown exception should result in a break. */
		public ExceptionBreakMode breakMode;
	}

	/**
	 * This enumeration defines all possible conditions when a thrown exception
	 * should result in a break. never: never breaks, always: always breaks,
	 * unhandled: breaks when excpetion unhandled, userUnhandled: breaks if the
	 * exception is not handled by user code.
	 */
	enum ExceptionBreakMode {
		never, always, unhandled, userUnhandled
	}

	/**
	 * An ExceptionPathSegment represents a segment in a path that is used to match
	 * leafs or nodes in a tree of exceptions. If a segment consists of more than
	 * one name, it matches the names provided if 'negate' is false or missing or it
	 * matches anything except the names provided if 'negate' is true.
	 */
	public static class ExceptionPathSegment {
		/**
		 * If false or missing this segment matches the names provided, otherwise it
		 * matches anything except the names provided.
		 */
		public boolean negate;
		/**
		 * Depending on the value of 'negate' the names that should match or not match.
		 */
		public String[] names;
	}

	/** Detailed information about an exception that has occurred. */
	public static class ExceptionDetails {
		/** Message contained in the exception. */
		public String message;
		/** Short type name of the exception object. */
		public String typeName;
		/** Fully-qualified type name of the exception object. */
		public String fullTypeName;
		/**
		 * Optional expression that can be evaluated in the current scope to obtain the
		 * exception object.
		 */
		public String evaluateName;
		/** Stack trace at the time the exception was thrown. */
		public String stackTrace;
		/** Details of the exception contained by this exception, if any. */
		public ExceptionDetails[] innerException;
	}
}
