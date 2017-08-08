/*---------------------------------------------------------------------------------------------
 *  Copyright (c) Microsoft Corporation (and Jonah). All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 *--------------------------------------------------------------------------------------------*/

package org.eclipse.dsp4j;

import java.util.Arrays;
import java.util.Map;

/** Declaration module describing the VS Code debug protocol.
	Auto-generated from json schema. Do not edit manually.
*/
public class DebugProtocol {

	/** Base class of requests, responses, and events. */
	public static class ProtocolMessage {
		/** Sequence number. */
		public Integer seq;
		public ProtocolMessage setSeq(Integer seq) {
			this.seq = seq;
			return this;
		}

		/** One of 'request', 'response', or 'event'. */
		public String type;
		public ProtocolMessage setType(String type) {
			this.type = type;
			return this;
		}

		@Override
		public String toString() {
			return "ProtocolMessage ["
				+ "seq=" + seq
				+ ", type=" + type
			 + "]";
		}
	}

	/** A client or server-initiated request. */
	public static class Request extends ProtocolMessage {
		public String type;
		public Request setType(String type) {
			this.type = type;
			return this;
		}

		/** The command to execute. */
		public String command;
		public Request setCommand(String command) {
			this.command = command;
			return this;
		}

		/** Object containing arguments for the command. */
		public Object arguments;
		public Request setArguments(Object arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "Request ["
				+ "type=" + type
				+ ", command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Server-initiated event. */
	public static class Event extends ProtocolMessage {
		public String type;
		public Event setType(String type) {
			this.type = type;
			return this;
		}

		/** Type of event. */
		public String event;
		public Event setEvent(String event) {
			this.event = event;
			return this;
		}

		/** Event-specific information. */
		public Object body;
		public Event setBody(Object body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "Event ["
				+ "type=" + type
				+ ", event=" + event
				+ ", body=" + body
			 + "]";
		}
	}

	/** Response to a request. */
	public static class Response extends ProtocolMessage {
		public String type;
		public Response setType(String type) {
			this.type = type;
			return this;
		}

		/** Sequence number of the corresponding request. */
		public Integer request_seq;
		public Response setRequest_seq(Integer request_seq) {
			this.request_seq = request_seq;
			return this;
		}

		/** Outcome of the request. */
		public Boolean success;
		public Response setSuccess(Boolean success) {
			this.success = success;
			return this;
		}

		/** The command requested. */
		public String command;
		public Response setCommand(String command) {
			this.command = command;
			return this;
		}

		/** Contains error message if success == false. */
		public String message;
		public Response setMessage(String message) {
			this.message = message;
			return this;
		}

		/** Contains request result if success is true and optional error details if success is false. */
		public Object body;
		public Response setBody(Object body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "Response ["
				+ "type=" + type
				+ ", request_seq=" + request_seq
				+ ", success=" + success
				+ ", command=" + command
				+ ", message=" + message
				+ ", body=" + body
			 + "]";
		}
	}

	/** Event message for 'initialized' event type.
		This event indicates that the debug adapter is ready to accept configuration requests (e.g. SetBreakpointsRequest, SetExceptionBreakpointsRequest).
		A debug adapter is expected to send this event when it is ready to accept configuration requests (but not before the InitializeRequest has finished).
		The sequence of events/requests is as follows:
		- adapters sends InitializedEvent (after the InitializeRequest has returned)
		- frontend sends zero or more SetBreakpointsRequest
		- frontend sends one SetFunctionBreakpointsRequest
		- frontend sends a SetExceptionBreakpointsRequest if one or more exceptionBreakpointFilters have been defined (or if supportsConfigurationDoneRequest is not defined or false)
		- frontend sends other future configuration requests
		- frontend sends one ConfigurationDoneRequest to indicate the end of the configuration
	*/
	public static class InitializedEvent extends Event {
		public String event;
		public InitializedEvent setEvent(String event) {
			this.event = event;
			return this;
		}

		@Override
		public String toString() {
			return "InitializedEvent ["
				+ "event=" + event
			 + "]";
		}
	}

	/** Event message for 'stopped' event type.
		The event indicates that the execution of the debuggee has stopped due to some condition.
		This can be caused by a break point previously set, a stepping action has completed, by executing a debugger statement etc.
	*/
	public static class StoppedEvent extends Event {
		public String event;
		public StoppedEvent setEvent(String event) {
			this.event = event;
			return this;
		}

		public static class Body {
			/** The reason for the event (such as: 'step', 'breakpoint', 'exception', 'pause', 'entry').
				For backward compatibility this string is shown in the UI if the 'description' attribute is missing (but it must not be translated).
			*/
			public String reason;
			public Body setReason(String reason) {
				this.reason = reason;
				return this;
			}

			/** The full reason for the event, e.g. 'Paused on exception'. This string is shown in the UI as is. */
			public String description;
			public Body setDescription(String description) {
				this.description = description;
				return this;
			}

			/** The thread which was stopped. */
			public Integer threadId;
			public Body setThreadId(Integer threadId) {
				this.threadId = threadId;
				return this;
			}

			/** Additional information. E.g. if reason is 'exception', text contains the exception name. This string is shown in the UI. */
			public String text;
			public Body setText(String text) {
				this.text = text;
				return this;
			}

			/** If allThreadsStopped is true, a debug adapter can announce that all threads have stopped.
				*  The client should use this information to enable that all threads can be expanded to access their stacktraces.
				*  If the attribute is missing or false, only the thread with the given threadId can be expanded.
			*/
			public Boolean allThreadsStopped;
			public Body setAllThreadsStopped(Boolean allThreadsStopped) {
				this.allThreadsStopped = allThreadsStopped;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "reason=" + reason
					+ ", description=" + description
					+ ", threadId=" + threadId
					+ ", text=" + text
					+ ", allThreadsStopped=" + allThreadsStopped
				 + "]";
			}
		};

		public Body body;
		public StoppedEvent setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "StoppedEvent ["
				+ "event=" + event
				+ ", body=" + body
			 + "]";
		}
	}

	/** Event message for 'continued' event type.
		The event indicates that the execution of the debuggee has continued.
		Please note: a debug adapter is not expected to send this event in response to a request that implies that execution continues, e.g. 'launch' or 'continue'.
		It is only necessary to send a ContinuedEvent if there was no previous request that implied this.
	*/
	public static class ContinuedEvent extends Event {
		public String event;
		public ContinuedEvent setEvent(String event) {
			this.event = event;
			return this;
		}

		public static class Body {
			/** The thread which was continued. */
			public Integer threadId;
			public Body setThreadId(Integer threadId) {
				this.threadId = threadId;
				return this;
			}

			/** If allThreadsContinued is true, a debug adapter can announce that all threads have continued. */
			public Boolean allThreadsContinued;
			public Body setAllThreadsContinued(Boolean allThreadsContinued) {
				this.allThreadsContinued = allThreadsContinued;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "threadId=" + threadId
					+ ", allThreadsContinued=" + allThreadsContinued
				 + "]";
			}
		};

		public Body body;
		public ContinuedEvent setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "ContinuedEvent ["
				+ "event=" + event
				+ ", body=" + body
			 + "]";
		}
	}

	/** Event message for 'exited' event type.
		The event indicates that the debuggee has exited.
	*/
	public static class ExitedEvent extends Event {
		public String event;
		public ExitedEvent setEvent(String event) {
			this.event = event;
			return this;
		}

		public static class Body {
			/** The exit code returned from the debuggee. */
			public Integer exitCode;
			public Body setExitCode(Integer exitCode) {
				this.exitCode = exitCode;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "exitCode=" + exitCode
				 + "]";
			}
		};

		public Body body;
		public ExitedEvent setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "ExitedEvent ["
				+ "event=" + event
				+ ", body=" + body
			 + "]";
		}
	}

	/** Event message for 'terminated' event types.
		The event indicates that debugging of the debuggee has terminated.
	*/
	public static class TerminatedEvent extends Event {
		public String event;
		public TerminatedEvent setEvent(String event) {
			this.event = event;
			return this;
		}

		public static class Body {
			/** A debug adapter may set 'restart' to true (or to an arbitrary object) to request that the front end restarts the session.
				The value is not interpreted by the client and passed unmodified as an attribute '__restart' to the launchRequest.
			*/
			public Object restart;
			public Body setRestart(Object restart) {
				this.restart = restart;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "restart=" + restart
				 + "]";
			}
		};

		public Body body;
		public TerminatedEvent setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "TerminatedEvent ["
				+ "event=" + event
				+ ", body=" + body
			 + "]";
		}
	}

	/** Event message for 'thread' event type.
		The event indicates that a thread has started or exited.
	*/
	public static class ThreadEvent extends Event {
		public String event;
		public ThreadEvent setEvent(String event) {
			this.event = event;
			return this;
		}

		public static class Body {
			/** The reason for the event (such as: 'started', 'exited'). */
			public String reason;
			public Body setReason(String reason) {
				this.reason = reason;
				return this;
			}

			/** The identifier of the thread. */
			public Integer threadId;
			public Body setThreadId(Integer threadId) {
				this.threadId = threadId;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "reason=" + reason
					+ ", threadId=" + threadId
				 + "]";
			}
		};

		public Body body;
		public ThreadEvent setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "ThreadEvent ["
				+ "event=" + event
				+ ", body=" + body
			 + "]";
		}
	}

	/** Event message for 'output' event type.
		The event indicates that the target has produced some output.
	*/
	public static class OutputEvent extends Event {
		public String event;
		public OutputEvent setEvent(String event) {
			this.event = event;
			return this;
		}

		public static class Body {
			/** The category of output (such as: 'console', 'stdout', 'stderr', 'telemetry'). If not specified, 'console' is assumed. */
			public String category;
			public Body setCategory(String category) {
				this.category = category;
				return this;
			}

			/** The output to report. */
			public String output;
			public Body setOutput(String output) {
				this.output = output;
				return this;
			}

			/** If an attribute 'variablesReference' exists and its value is > 0, the output contains objects which can be retrieved by passing variablesReference to the VariablesRequest. */
			public Integer variablesReference;
			public Body setVariablesReference(Integer variablesReference) {
				this.variablesReference = variablesReference;
				return this;
			}

			/** Optional data to report. For the 'telemetry' category the data will be sent to telemetry, for the other categories the data is shown in JSON format. */
			public Object data;
			public Body setData(Object data) {
				this.data = data;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "category=" + category
					+ ", output=" + output
					+ ", variablesReference=" + variablesReference
					+ ", data=" + data
				 + "]";
			}
		};

		public Body body;
		public OutputEvent setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "OutputEvent ["
				+ "event=" + event
				+ ", body=" + body
			 + "]";
		}
	}

	/** Event message for 'breakpoint' event type.
		The event indicates that some information about a breakpoint has changed.
	*/
	public static class BreakpointEvent extends Event {
		public String event;
		public BreakpointEvent setEvent(String event) {
			this.event = event;
			return this;
		}

		public static class Body {
			/** The reason for the event (such as: 'changed', 'new'). */
			public String reason;
			public Body setReason(String reason) {
				this.reason = reason;
				return this;
			}

			/** The breakpoint. */
			public Breakpoint breakpoint;
			public Body setBreakpoint(Breakpoint breakpoint) {
				this.breakpoint = breakpoint;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "reason=" + reason
					+ ", breakpoint=" + breakpoint
				 + "]";
			}
		};

		public Body body;
		public BreakpointEvent setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "BreakpointEvent ["
				+ "event=" + event
				+ ", body=" + body
			 + "]";
		}
	}

	/** Event message for 'module' event type.
		The event indicates that some information about a module has changed.
	*/
	public static class ModuleEvent extends Event {
		public String event;
		public ModuleEvent setEvent(String event) {
			this.event = event;
			return this;
		}

		public static class Body {
			/** The reason for the event. */
			public String reason;
			public Body setReason(String reason) {
				this.reason = reason;
				return this;
			}

			/** The new, changed, or removed module. In case of 'removed' only the module id is used. */
			public Module module;
			public Body setModule(Module module) {
				this.module = module;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "reason=" + reason
					+ ", module=" + module
				 + "]";
			}
		};

		public Body body;
		public ModuleEvent setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "ModuleEvent ["
				+ "event=" + event
				+ ", body=" + body
			 + "]";
		}
	}

	/** Event message for 'process' event type.
		The event indicates that the debugger has begun debugging a new process. Either one that it has launched, or one that it has attached to.
	*/
	public static class ProcessEvent extends Event {
		public String event;
		public ProcessEvent setEvent(String event) {
			this.event = event;
			return this;
		}

		public static class Body {
			/** The logical name of the process. This is usually the full path to process's executable file. Example: /home/example/myproj/program.js. */
			public String name;
			public Body setName(String name) {
				this.name = name;
				return this;
			}

			/** The system process id of the debugged process. This property will be missing for non-system processes. */
			public Integer systemProcessId;
			public Body setSystemProcessId(Integer systemProcessId) {
				this.systemProcessId = systemProcessId;
				return this;
			}

			/** If true, the process is running on the same computer as the debug adapter. */
			public Boolean isLocalProcess;
			public Body setIsLocalProcess(Boolean isLocalProcess) {
				this.isLocalProcess = isLocalProcess;
				return this;
			}

			/** Describes how the debug engine started debugging this process.
				launch: Process was launched under the debugger.
				attach: Debugger attached to an existing process.
				attachForSuspendedLaunch: A project launcher component has launched a new process in a suspended state and then asked the debugger to attach.
			*/
			public String startMethod;
			public Body setStartMethod(String startMethod) {
				this.startMethod = startMethod;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "name=" + name
					+ ", systemProcessId=" + systemProcessId
					+ ", isLocalProcess=" + isLocalProcess
					+ ", startMethod=" + startMethod
				 + "]";
			}
		};

		public Body body;
		public ProcessEvent setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "ProcessEvent ["
				+ "event=" + event
				+ ", body=" + body
			 + "]";
		}
	}

	/** runInTerminal request; value of command field is 'runInTerminal'.
		With this request a debug adapter can run a command in a terminal.
	*/
	public static class RunInTerminalRequest extends Request {
		public String command;
		public RunInTerminalRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public RunInTerminalRequestArguments arguments;
		public RunInTerminalRequest setArguments(RunInTerminalRequestArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "RunInTerminalRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'runInTerminal' request. */
	public static class RunInTerminalRequestArguments {
		/** What kind of terminal to launch. */
		public String kind;
		public RunInTerminalRequestArguments setKind(String kind) {
			this.kind = kind;
			return this;
		}

		/** Optional title of the terminal. */
		public String title;
		public RunInTerminalRequestArguments setTitle(String title) {
			this.title = title;
			return this;
		}

		/** Working directory of the command. */
		public String cwd;
		public RunInTerminalRequestArguments setCwd(String cwd) {
			this.cwd = cwd;
			return this;
		}

		/** List of arguments. The first argument is the command to run. */
		public String[] args;
		public RunInTerminalRequestArguments setArgs(String[] args) {
			this.args = args;
			return this;
		}

		/** Environment key-value pairs that are added to the default environment. */
		public Map<String, String> env;
		public RunInTerminalRequestArguments setEnv(Map<String, String> env) {
			this.env = env;
			return this;
		}

		@Override
		public String toString() {
			return "RunInTerminalRequestArguments ["
				+ "kind=" + kind
				+ ", title=" + title
				+ ", cwd=" + cwd
				+ ", args=" + Arrays.toString(args)
				+ ", env=" + env
			 + "]";
		}
	}

	/** Response to Initialize request. */
	public static class RunInTerminalResponse extends Response {
		public static class Body {
			/** The process ID. */
			public Integer processId;
			public Body setProcessId(Integer processId) {
				this.processId = processId;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "processId=" + processId
				 + "]";
			}
		};

		public Body body;
		public RunInTerminalResponse setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "RunInTerminalResponse ["
				+ "body=" + body
			 + "]";
		}
	}

	/** On error that is whenever 'success' is false, the body can provide more details. */
	public static class ErrorResponse extends Response {
		public static class Body {
			/** An optional, structured error message. */
			public Message error;
			public Body setError(Message error) {
				this.error = error;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "error=" + error
				 + "]";
			}
		};

		public Body body;
		public ErrorResponse setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "ErrorResponse ["
				+ "body=" + body
			 + "]";
		}
	}

	/** Initialize request; value of command field is 'initialize'. */
	public static class InitializeRequest extends Request {
		public String command;
		public InitializeRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public InitializeRequestArguments arguments;
		public InitializeRequest setArguments(InitializeRequestArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "InitializeRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'initialize' request. */
	public static class InitializeRequestArguments {
		/** The ID of the (frontend) client using this adapter. */
		public String clientID;
		public InitializeRequestArguments setClientID(String clientID) {
			this.clientID = clientID;
			return this;
		}

		/** The ID of the debug adapter. */
		public String adapterID;
		public InitializeRequestArguments setAdapterID(String adapterID) {
			this.adapterID = adapterID;
			return this;
		}

		/** If true all line numbers are 1-based (default). */
		public Boolean linesStartAt1;
		public InitializeRequestArguments setLinesStartAt1(Boolean linesStartAt1) {
			this.linesStartAt1 = linesStartAt1;
			return this;
		}

		/** If true all column numbers are 1-based (default). */
		public Boolean columnsStartAt1;
		public InitializeRequestArguments setColumnsStartAt1(Boolean columnsStartAt1) {
			this.columnsStartAt1 = columnsStartAt1;
			return this;
		}

		/** Determines in what format paths are specified. Possible values are 'path' or 'uri'. The default is 'path', which is the native format. */
		public String pathFormat;
		public InitializeRequestArguments setPathFormat(String pathFormat) {
			this.pathFormat = pathFormat;
			return this;
		}

		/** Client supports the optional type attribute for variables. */
		public Boolean supportsVariableType;
		public InitializeRequestArguments setSupportsVariableType(Boolean supportsVariableType) {
			this.supportsVariableType = supportsVariableType;
			return this;
		}

		/** Client supports the paging of variables. */
		public Boolean supportsVariablePaging;
		public InitializeRequestArguments setSupportsVariablePaging(Boolean supportsVariablePaging) {
			this.supportsVariablePaging = supportsVariablePaging;
			return this;
		}

		/** Client supports the runInTerminal request. */
		public Boolean supportsRunInTerminalRequest;
		public InitializeRequestArguments setSupportsRunInTerminalRequest(Boolean supportsRunInTerminalRequest) {
			this.supportsRunInTerminalRequest = supportsRunInTerminalRequest;
			return this;
		}

		@Override
		public String toString() {
			return "InitializeRequestArguments ["
				+ "clientID=" + clientID
				+ ", adapterID=" + adapterID
				+ ", linesStartAt1=" + linesStartAt1
				+ ", columnsStartAt1=" + columnsStartAt1
				+ ", pathFormat=" + pathFormat
				+ ", supportsVariableType=" + supportsVariableType
				+ ", supportsVariablePaging=" + supportsVariablePaging
				+ ", supportsRunInTerminalRequest=" + supportsRunInTerminalRequest
			 + "]";
		}
	}

	/** Response to 'initialize' request. */
	public static class InitializeResponse extends Response {
		/** The capabilities of this debug adapter. */
		public Capabilities body;
		public InitializeResponse setBody(Capabilities body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "InitializeResponse ["
				+ "body=" + body
			 + "]";
		}
	}

	/** ConfigurationDone request; value of command field is 'configurationDone'.
		The client of the debug protocol must send this request at the end of the sequence of configuration requests (which was started by the InitializedEvent).
	*/
	public static class ConfigurationDoneRequest extends Request {
		public String command;
		public ConfigurationDoneRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public ConfigurationDoneArguments arguments;
		public ConfigurationDoneRequest setArguments(ConfigurationDoneArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "ConfigurationDoneRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'configurationDone' request.
		The configurationDone request has no standardized attributes.
	*/
	public static class ConfigurationDoneArguments {
		@Override
		public String toString() {
			return "ConfigurationDoneArguments ["
			 + "]";
		}
	}

	/** Response to 'configurationDone' request. This is just an acknowledgement, so no body field is required. */
	public static class ConfigurationDoneResponse extends Response {
		@Override
		public String toString() {
			return "ConfigurationDoneResponse ["
			 + "]";
		}
	}

	/** Launch request; value of command field is 'launch'. */
	public static class LaunchRequest extends Request {
		public String command;
		public LaunchRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public LaunchRequestArguments arguments;
		public LaunchRequest setArguments(LaunchRequestArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "LaunchRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'launch' request. */
	public static class LaunchRequestArguments {
		/** If noDebug is true the launch request should launch the program without enabling debugging. */
		public Boolean noDebug;
		public LaunchRequestArguments setNoDebug(Boolean noDebug) {
			this.noDebug = noDebug;
			return this;
		}

		@Override
		public String toString() {
			return "LaunchRequestArguments ["
				+ "noDebug=" + noDebug
			 + "]";
		}
	}

	/** Response to 'launch' request. This is just an acknowledgement, so no body field is required. */
	public static class LaunchResponse extends Response {
		@Override
		public String toString() {
			return "LaunchResponse ["
			 + "]";
		}
	}

	/** Attach request; value of command field is 'attach'. */
	public static class AttachRequest extends Request {
		public String command;
		public AttachRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public AttachRequestArguments arguments;
		public AttachRequest setArguments(AttachRequestArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "AttachRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'attach' request.
		The attach request has no standardized attributes.
	*/
	public static class AttachRequestArguments {
		@Override
		public String toString() {
			return "AttachRequestArguments ["
			 + "]";
		}
	}

	/** Response to 'attach' request. This is just an acknowledgement, so no body field is required. */
	public static class AttachResponse extends Response {
		@Override
		public String toString() {
			return "AttachResponse ["
			 + "]";
		}
	}

	/** Restart request; value of command field is 'restart'.
		Restarts a debug session. If the capability 'supportsRestartRequest' is missing or has the value false,
		the client will implement 'restart' by terminating the debug adapter first and then launching it anew.
		A debug adapter can override this default behaviour by implementing a restart request
		and setting the capability 'supportsRestartRequest' to true.
	*/
	public static class RestartRequest extends Request {
		public String command;
		public RestartRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public RestartArguments arguments;
		public RestartRequest setArguments(RestartArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "RestartRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'restart' request.
		The restart request has no standardized attributes.
	*/
	public static class RestartArguments {
		@Override
		public String toString() {
			return "RestartArguments ["
			 + "]";
		}
	}

	/** Response to 'restart' request. This is just an acknowledgement, so no body field is required. */
	public static class RestartResponse extends Response {
		@Override
		public String toString() {
			return "RestartResponse ["
			 + "]";
		}
	}

	/** Disconnect request; value of command field is 'disconnect'. */
	public static class DisconnectRequest extends Request {
		public String command;
		public DisconnectRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public DisconnectArguments arguments;
		public DisconnectRequest setArguments(DisconnectArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "DisconnectRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'disconnect' request. */
	public static class DisconnectArguments {
		/** Indicates whether the debuggee should be terminated when the debugger is disconnected.
			If unspecified, the debug adapter is free to do whatever it thinks is best.
			A client can only rely on this attribute being properly honored if a debug adapter returns true for the 'supportTerminateDebuggee' capability.
		*/
		public Boolean terminateDebuggee;
		public DisconnectArguments setTerminateDebuggee(Boolean terminateDebuggee) {
			this.terminateDebuggee = terminateDebuggee;
			return this;
		}

		@Override
		public String toString() {
			return "DisconnectArguments ["
				+ "terminateDebuggee=" + terminateDebuggee
			 + "]";
		}
	}

	/** Response to 'disconnect' request. This is just an acknowledgement, so no body field is required. */
	public static class DisconnectResponse extends Response {
		@Override
		public String toString() {
			return "DisconnectResponse ["
			 + "]";
		}
	}

	/** SetBreakpoints request; value of command field is 'setBreakpoints'.
		Sets multiple breakpoints for a single source and clears all previous breakpoints in that source.
		To clear all breakpoint for a source, specify an empty array.
		When a breakpoint is hit, a StoppedEvent (event type 'breakpoint') is generated.
	*/
	public static class SetBreakpointsRequest extends Request {
		public String command;
		public SetBreakpointsRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public SetBreakpointsArguments arguments;
		public SetBreakpointsRequest setArguments(SetBreakpointsArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "SetBreakpointsRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'setBreakpoints' request. */
	public static class SetBreakpointsArguments {
		/** The source location of the breakpoints; either source.path or source.reference must be specified. */
		public Source source;
		public SetBreakpointsArguments setSource(Source source) {
			this.source = source;
			return this;
		}

		/** The code locations of the breakpoints. */
		public SourceBreakpoint[] breakpoints;
		public SetBreakpointsArguments setBreakpoints(SourceBreakpoint[] breakpoints) {
			this.breakpoints = breakpoints;
			return this;
		}

		/** Deprecated: The code locations of the breakpoints. */
		public Integer[] lines;
		public SetBreakpointsArguments setLines(Integer[] lines) {
			this.lines = lines;
			return this;
		}

		/** A value of true indicates that the underlying source has been modified which results in new breakpoint locations. */
		public Boolean sourceModified;
		public SetBreakpointsArguments setSourceModified(Boolean sourceModified) {
			this.sourceModified = sourceModified;
			return this;
		}

		@Override
		public String toString() {
			return "SetBreakpointsArguments ["
				+ "source=" + source
				+ ", breakpoints=" + Arrays.toString(breakpoints)
				+ ", lines=" + Arrays.toString(lines)
				+ ", sourceModified=" + sourceModified
			 + "]";
		}
	}

	/** Response to 'setBreakpoints' request.
		Returned is information about each breakpoint created by this request.
		This includes the actual code location and whether the breakpoint could be verified.
		The breakpoints returned are in the same order as the elements of the 'breakpoints'
		(or the deprecated 'lines') in the SetBreakpointsArguments.
	*/
	public static class SetBreakpointsResponse extends Response {
		public static class Body {
			/** Information about the breakpoints. The array elements are in the same order as the elements of the 'breakpoints' (or the deprecated 'lines') in the SetBreakpointsArguments. */
			public Breakpoint[] breakpoints;
			public Body setBreakpoints(Breakpoint[] breakpoints) {
				this.breakpoints = breakpoints;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "breakpoints=" + Arrays.toString(breakpoints)
				 + "]";
			}
		};

		public Body body;
		public SetBreakpointsResponse setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "SetBreakpointsResponse ["
				+ "body=" + body
			 + "]";
		}
	}

	/** SetFunctionBreakpoints request; value of command field is 'setFunctionBreakpoints'.
		Sets multiple function breakpoints and clears all previous function breakpoints.
		To clear all function breakpoint, specify an empty array.
		When a function breakpoint is hit, a StoppedEvent (event type 'function breakpoint') is generated.
	*/
	public static class SetFunctionBreakpointsRequest extends Request {
		public String command;
		public SetFunctionBreakpointsRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public SetFunctionBreakpointsArguments arguments;
		public SetFunctionBreakpointsRequest setArguments(SetFunctionBreakpointsArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "SetFunctionBreakpointsRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'setFunctionBreakpoints' request. */
	public static class SetFunctionBreakpointsArguments {
		/** The function names of the breakpoints. */
		public FunctionBreakpoint[] breakpoints;
		public SetFunctionBreakpointsArguments setBreakpoints(FunctionBreakpoint[] breakpoints) {
			this.breakpoints = breakpoints;
			return this;
		}

		@Override
		public String toString() {
			return "SetFunctionBreakpointsArguments ["
				+ "breakpoints=" + Arrays.toString(breakpoints)
			 + "]";
		}
	}

	/** Response to 'setFunctionBreakpoints' request.
		Returned is information about each breakpoint created by this request.
	*/
	public static class SetFunctionBreakpointsResponse extends Response {
		public static class Body {
			/** Information about the breakpoints. The array elements correspond to the elements of the 'breakpoints' array. */
			public Breakpoint[] breakpoints;
			public Body setBreakpoints(Breakpoint[] breakpoints) {
				this.breakpoints = breakpoints;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "breakpoints=" + Arrays.toString(breakpoints)
				 + "]";
			}
		};

		public Body body;
		public SetFunctionBreakpointsResponse setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "SetFunctionBreakpointsResponse ["
				+ "body=" + body
			 + "]";
		}
	}

	/** SetExceptionBreakpoints request; value of command field is 'setExceptionBreakpoints'.
		The request configures the debuggers response to thrown exceptions. If an exception is configured to break, a StoppedEvent is fired (event type 'exception').
	*/
	public static class SetExceptionBreakpointsRequest extends Request {
		public String command;
		public SetExceptionBreakpointsRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public SetExceptionBreakpointsArguments arguments;
		public SetExceptionBreakpointsRequest setArguments(SetExceptionBreakpointsArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "SetExceptionBreakpointsRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'setExceptionBreakpoints' request. */
	public static class SetExceptionBreakpointsArguments {
		/** IDs of checked exception options. The set of IDs is returned via the 'exceptionBreakpointFilters' capability. */
		public String[] filters;
		public SetExceptionBreakpointsArguments setFilters(String[] filters) {
			this.filters = filters;
			return this;
		}

		/** Configuration options for selected exceptions. */
		public ExceptionOptions[] exceptionOptions;
		public SetExceptionBreakpointsArguments setExceptionOptions(ExceptionOptions[] exceptionOptions) {
			this.exceptionOptions = exceptionOptions;
			return this;
		}

		@Override
		public String toString() {
			return "SetExceptionBreakpointsArguments ["
				+ "filters=" + Arrays.toString(filters)
				+ ", exceptionOptions=" + Arrays.toString(exceptionOptions)
			 + "]";
		}
	}

	/** Response to 'setExceptionBreakpoints' request. This is just an acknowledgement, so no body field is required. */
	public static class SetExceptionBreakpointsResponse extends Response {
		@Override
		public String toString() {
			return "SetExceptionBreakpointsResponse ["
			 + "]";
		}
	}

	/** Continue request; value of command field is 'continue'.
		The request starts the debuggee to run again.
	*/
	public static class ContinueRequest extends Request {
		public String command;
		public ContinueRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public ContinueArguments arguments;
		public ContinueRequest setArguments(ContinueArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "ContinueRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'continue' request. */
	public static class ContinueArguments {
		/** Continue execution for the specified thread (if possible). If the backend cannot continue on a single thread but will continue on all threads, it should set the allThreadsContinued attribute in the response to true. */
		public Integer threadId;
		public ContinueArguments setThreadId(Integer threadId) {
			this.threadId = threadId;
			return this;
		}

		@Override
		public String toString() {
			return "ContinueArguments ["
				+ "threadId=" + threadId
			 + "]";
		}
	}

	/** Response to 'continue' request. */
	public static class ContinueResponse extends Response {
		public static class Body {
			/** If true, the continue request has ignored the specified thread and continued all threads instead. If this attribute is missing a value of 'true' is assumed for backward compatibility. */
			public Boolean allThreadsContinued;
			public Body setAllThreadsContinued(Boolean allThreadsContinued) {
				this.allThreadsContinued = allThreadsContinued;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "allThreadsContinued=" + allThreadsContinued
				 + "]";
			}
		};

		public Body body;
		public ContinueResponse setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "ContinueResponse ["
				+ "body=" + body
			 + "]";
		}
	}

	/** Next request; value of command field is 'next'.
		The request starts the debuggee to run again for one step.
		The debug adapter first sends the NextResponse and then a StoppedEvent (event type 'step') after the step has completed.
	*/
	public static class NextRequest extends Request {
		public String command;
		public NextRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public NextArguments arguments;
		public NextRequest setArguments(NextArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "NextRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'next' request. */
	public static class NextArguments {
		/** Execute 'next' for this thread. */
		public Integer threadId;
		public NextArguments setThreadId(Integer threadId) {
			this.threadId = threadId;
			return this;
		}

		@Override
		public String toString() {
			return "NextArguments ["
				+ "threadId=" + threadId
			 + "]";
		}
	}

	/** Response to 'next' request. This is just an acknowledgement, so no body field is required. */
	public static class NextResponse extends Response {
		@Override
		public String toString() {
			return "NextResponse ["
			 + "]";
		}
	}

	/** StepIn request; value of command field is 'stepIn'.
		The request starts the debuggee to step into a function/method if possible.
		If it cannot step into a target, 'stepIn' behaves like 'next'.
		The debug adapter first sends the StepInResponse and then a StoppedEvent (event type 'step') after the step has completed.
		If there are multiple function/method calls (or other targets) on the source line,
		the optional argument 'targetId' can be used to control into which target the 'stepIn' should occur.
		The list of possible targets for a given source line can be retrieved via the 'stepInTargets' request.
	*/
	public static class StepInRequest extends Request {
		public String command;
		public StepInRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public StepInArguments arguments;
		public StepInRequest setArguments(StepInArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "StepInRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'stepIn' request. */
	public static class StepInArguments {
		/** Execute 'stepIn' for this thread. */
		public Integer threadId;
		public StepInArguments setThreadId(Integer threadId) {
			this.threadId = threadId;
			return this;
		}

		/** Optional id of the target to step into. */
		public Integer targetId;
		public StepInArguments setTargetId(Integer targetId) {
			this.targetId = targetId;
			return this;
		}

		@Override
		public String toString() {
			return "StepInArguments ["
				+ "threadId=" + threadId
				+ ", targetId=" + targetId
			 + "]";
		}
	}

	/** Response to 'stepIn' request. This is just an acknowledgement, so no body field is required. */
	public static class StepInResponse extends Response {
		@Override
		public String toString() {
			return "StepInResponse ["
			 + "]";
		}
	}

	/** StepOut request; value of command field is 'stepOut'.
		The request starts the debuggee to run again for one step.
		The debug adapter first sends the StepOutResponse and then a StoppedEvent (event type 'step') after the step has completed.
	*/
	public static class StepOutRequest extends Request {
		public String command;
		public StepOutRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public StepOutArguments arguments;
		public StepOutRequest setArguments(StepOutArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "StepOutRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'stepOut' request. */
	public static class StepOutArguments {
		/** Execute 'stepOut' for this thread. */
		public Integer threadId;
		public StepOutArguments setThreadId(Integer threadId) {
			this.threadId = threadId;
			return this;
		}

		@Override
		public String toString() {
			return "StepOutArguments ["
				+ "threadId=" + threadId
			 + "]";
		}
	}

	/** Response to 'stepOut' request. This is just an acknowledgement, so no body field is required. */
	public static class StepOutResponse extends Response {
		@Override
		public String toString() {
			return "StepOutResponse ["
			 + "]";
		}
	}

	/** StepBack request; value of command field is 'stepBack'.
		The request starts the debuggee to run one step backwards.
		The debug adapter first sends the StepBackResponse and then a StoppedEvent (event type 'step') after the step has completed. Clients should only call this request if the capability supportsStepBack is true.
	*/
	public static class StepBackRequest extends Request {
		public String command;
		public StepBackRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public StepBackArguments arguments;
		public StepBackRequest setArguments(StepBackArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "StepBackRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'stepBack' request. */
	public static class StepBackArguments {
		/** Exceute 'stepBack' for this thread. */
		public Integer threadId;
		public StepBackArguments setThreadId(Integer threadId) {
			this.threadId = threadId;
			return this;
		}

		@Override
		public String toString() {
			return "StepBackArguments ["
				+ "threadId=" + threadId
			 + "]";
		}
	}

	/** Response to 'stepBack' request. This is just an acknowledgement, so no body field is required. */
	public static class StepBackResponse extends Response {
		@Override
		public String toString() {
			return "StepBackResponse ["
			 + "]";
		}
	}

	/** ReverseContinue request; value of command field is 'reverseContinue'.
		The request starts the debuggee to run backward. Clients should only call this request if the capability supportsStepBack is true.
	*/
	public static class ReverseContinueRequest extends Request {
		public String command;
		public ReverseContinueRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public ReverseContinueArguments arguments;
		public ReverseContinueRequest setArguments(ReverseContinueArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "ReverseContinueRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'reverseContinue' request. */
	public static class ReverseContinueArguments {
		/** Exceute 'reverseContinue' for this thread. */
		public Integer threadId;
		public ReverseContinueArguments setThreadId(Integer threadId) {
			this.threadId = threadId;
			return this;
		}

		@Override
		public String toString() {
			return "ReverseContinueArguments ["
				+ "threadId=" + threadId
			 + "]";
		}
	}

	/** Response to 'reverseContinue' request. This is just an acknowledgement, so no body field is required. */
	public static class ReverseContinueResponse extends Response {
		@Override
		public String toString() {
			return "ReverseContinueResponse ["
			 + "]";
		}
	}

	/** RestartFrame request; value of command field is 'restartFrame'.
		The request restarts execution of the specified stackframe.
		The debug adapter first sends the RestartFrameResponse and then a StoppedEvent (event type 'restart') after the restart has completed.
	*/
	public static class RestartFrameRequest extends Request {
		public String command;
		public RestartFrameRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public RestartFrameArguments arguments;
		public RestartFrameRequest setArguments(RestartFrameArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "RestartFrameRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'restartFrame' request. */
	public static class RestartFrameArguments {
		/** Restart this stackframe. */
		public Integer frameId;
		public RestartFrameArguments setFrameId(Integer frameId) {
			this.frameId = frameId;
			return this;
		}

		@Override
		public String toString() {
			return "RestartFrameArguments ["
				+ "frameId=" + frameId
			 + "]";
		}
	}

	/** Response to 'restartFrame' request. This is just an acknowledgement, so no body field is required. */
	public static class RestartFrameResponse extends Response {
		@Override
		public String toString() {
			return "RestartFrameResponse ["
			 + "]";
		}
	}

	/** Goto request; value of command field is 'goto'.
		The request sets the location where the debuggee will continue to run.
		This makes it possible to skip the execution of code or to executed code again.
		The code between the current location and the goto target is not executed but skipped.
		The debug adapter first sends the GotoResponse and then a StoppedEvent (event type 'goto').
	*/
	public static class GotoRequest extends Request {
		public String command;
		public GotoRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public GotoArguments arguments;
		public GotoRequest setArguments(GotoArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "GotoRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'goto' request. */
	public static class GotoArguments {
		/** Set the goto target for this thread. */
		public Integer threadId;
		public GotoArguments setThreadId(Integer threadId) {
			this.threadId = threadId;
			return this;
		}

		/** The location where the debuggee will continue to run. */
		public Integer targetId;
		public GotoArguments setTargetId(Integer targetId) {
			this.targetId = targetId;
			return this;
		}

		@Override
		public String toString() {
			return "GotoArguments ["
				+ "threadId=" + threadId
				+ ", targetId=" + targetId
			 + "]";
		}
	}

	/** Response to 'goto' request. This is just an acknowledgement, so no body field is required. */
	public static class GotoResponse extends Response {
		@Override
		public String toString() {
			return "GotoResponse ["
			 + "]";
		}
	}

	/** Pause request; value of command field is 'pause'.
		The request suspenses the debuggee.
		The debug adapter first sends the PauseResponse and then a StoppedEvent (event type 'pause') after the thread has been paused successfully.
	*/
	public static class PauseRequest extends Request {
		public String command;
		public PauseRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public PauseArguments arguments;
		public PauseRequest setArguments(PauseArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "PauseRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'pause' request. */
	public static class PauseArguments {
		/** Pause execution for this thread. */
		public Integer threadId;
		public PauseArguments setThreadId(Integer threadId) {
			this.threadId = threadId;
			return this;
		}

		@Override
		public String toString() {
			return "PauseArguments ["
				+ "threadId=" + threadId
			 + "]";
		}
	}

	/** Response to 'pause' request. This is just an acknowledgement, so no body field is required. */
	public static class PauseResponse extends Response {
		@Override
		public String toString() {
			return "PauseResponse ["
			 + "]";
		}
	}

	/** StackTrace request; value of command field is 'stackTrace'. The request returns a stacktrace from the current execution state. */
	public static class StackTraceRequest extends Request {
		public String command;
		public StackTraceRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public StackTraceArguments arguments;
		public StackTraceRequest setArguments(StackTraceArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "StackTraceRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'stackTrace' request. */
	public static class StackTraceArguments {
		/** Retrieve the stacktrace for this thread. */
		public Integer threadId;
		public StackTraceArguments setThreadId(Integer threadId) {
			this.threadId = threadId;
			return this;
		}

		/** The index of the first frame to return; if omitted frames start at 0. */
		public Integer startFrame;
		public StackTraceArguments setStartFrame(Integer startFrame) {
			this.startFrame = startFrame;
			return this;
		}

		/** The maximum number of frames to return. If levels is not specified or 0, all frames are returned. */
		public Integer levels;
		public StackTraceArguments setLevels(Integer levels) {
			this.levels = levels;
			return this;
		}

		/** Specifies details on how to format the stack frames. */
		public StackFrameFormat format;
		public StackTraceArguments setFormat(StackFrameFormat format) {
			this.format = format;
			return this;
		}

		@Override
		public String toString() {
			return "StackTraceArguments ["
				+ "threadId=" + threadId
				+ ", startFrame=" + startFrame
				+ ", levels=" + levels
				+ ", format=" + format
			 + "]";
		}
	}

	/** Response to 'stackTrace' request. */
	public static class StackTraceResponse extends Response {
		public static class Body {
			/** The frames of the stackframe. If the array has length zero, there are no stackframes available.
				This means that there is no location information available.
			*/
			public StackFrame[] stackFrames;
			public Body setStackFrames(StackFrame[] stackFrames) {
				this.stackFrames = stackFrames;
				return this;
			}

			/** The total number of frames available. */
			public Integer totalFrames;
			public Body setTotalFrames(Integer totalFrames) {
				this.totalFrames = totalFrames;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "stackFrames=" + Arrays.toString(stackFrames)
					+ ", totalFrames=" + totalFrames
				 + "]";
			}
		};

		public Body body;
		public StackTraceResponse setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "StackTraceResponse ["
				+ "body=" + body
			 + "]";
		}
	}

	/** Scopes request; value of command field is 'scopes'.
		The request returns the variable scopes for a given stackframe ID.
	*/
	public static class ScopesRequest extends Request {
		public String command;
		public ScopesRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public ScopesArguments arguments;
		public ScopesRequest setArguments(ScopesArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "ScopesRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'scopes' request. */
	public static class ScopesArguments {
		/** Retrieve the scopes for this stackframe. */
		public Integer frameId;
		public ScopesArguments setFrameId(Integer frameId) {
			this.frameId = frameId;
			return this;
		}

		@Override
		public String toString() {
			return "ScopesArguments ["
				+ "frameId=" + frameId
			 + "]";
		}
	}

	/** Response to 'scopes' request. */
	public static class ScopesResponse extends Response {
		public static class Body {
			/** The scopes of the stackframe. If the array has length zero, there are no scopes available. */
			public Scope[] scopes;
			public Body setScopes(Scope[] scopes) {
				this.scopes = scopes;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "scopes=" + Arrays.toString(scopes)
				 + "]";
			}
		};

		public Body body;
		public ScopesResponse setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "ScopesResponse ["
				+ "body=" + body
			 + "]";
		}
	}

	/** Variables request; value of command field is 'variables'.
		Retrieves all child variables for the given variable reference.
		An optional filter can be used to limit the fetched children to either named or indexed children.
	*/
	public static class VariablesRequest extends Request {
		public String command;
		public VariablesRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public VariablesArguments arguments;
		public VariablesRequest setArguments(VariablesArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "VariablesRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'variables' request. */
	public static class VariablesArguments {
		/** The Variable reference. */
		public Integer variablesReference;
		public VariablesArguments setVariablesReference(Integer variablesReference) {
			this.variablesReference = variablesReference;
			return this;
		}

		/** Optional filter to limit the child variables to either named or indexed. If ommited, both types are fetched. */
		public String filter;
		public VariablesArguments setFilter(String filter) {
			this.filter = filter;
			return this;
		}

		/** The index of the first variable to return; if omitted children start at 0. */
		public Integer start;
		public VariablesArguments setStart(Integer start) {
			this.start = start;
			return this;
		}

		/** The number of variables to return. If count is missing or 0, all variables are returned. */
		public Integer count;
		public VariablesArguments setCount(Integer count) {
			this.count = count;
			return this;
		}

		/** Specifies details on how to format the Variable values. */
		public ValueFormat format;
		public VariablesArguments setFormat(ValueFormat format) {
			this.format = format;
			return this;
		}

		@Override
		public String toString() {
			return "VariablesArguments ["
				+ "variablesReference=" + variablesReference
				+ ", filter=" + filter
				+ ", start=" + start
				+ ", count=" + count
				+ ", format=" + format
			 + "]";
		}
	}

	/** Response to 'variables' request. */
	public static class VariablesResponse extends Response {
		public static class Body {
			/** All (or a range) of variables for the given variable reference. */
			public Variable[] variables;
			public Body setVariables(Variable[] variables) {
				this.variables = variables;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "variables=" + Arrays.toString(variables)
				 + "]";
			}
		};

		public Body body;
		public VariablesResponse setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "VariablesResponse ["
				+ "body=" + body
			 + "]";
		}
	}

	/** setVariable request; value of command field is 'setVariable'.
		Set the variable with the given name in the variable container to a new value.
	*/
	public static class SetVariableRequest extends Request {
		public String command;
		public SetVariableRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public SetVariableArguments arguments;
		public SetVariableRequest setArguments(SetVariableArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "SetVariableRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'setVariable' request. */
	public static class SetVariableArguments {
		/** The reference of the variable container. */
		public Integer variablesReference;
		public SetVariableArguments setVariablesReference(Integer variablesReference) {
			this.variablesReference = variablesReference;
			return this;
		}

		/** The name of the variable. */
		public String name;
		public SetVariableArguments setName(String name) {
			this.name = name;
			return this;
		}

		/** The value of the variable. */
		public String value;
		public SetVariableArguments setValue(String value) {
			this.value = value;
			return this;
		}

		/** Specifies details on how to format the response value. */
		public ValueFormat format;
		public SetVariableArguments setFormat(ValueFormat format) {
			this.format = format;
			return this;
		}

		@Override
		public String toString() {
			return "SetVariableArguments ["
				+ "variablesReference=" + variablesReference
				+ ", name=" + name
				+ ", value=" + value
				+ ", format=" + format
			 + "]";
		}
	}

	/** Response to 'setVariable' request. */
	public static class SetVariableResponse extends Response {
		public static class Body {
			/** The new value of the variable. */
			public String value;
			public Body setValue(String value) {
				this.value = value;
				return this;
			}

			/** The type of the new value. Typically shown in the UI when hovering over the value. */
			public String type;
			public Body setType(String type) {
				this.type = type;
				return this;
			}

			/** If variablesReference is > 0, the new value is structured and its children can be retrieved by passing variablesReference to the VariablesRequest. */
			public Integer variablesReference;
			public Body setVariablesReference(Integer variablesReference) {
				this.variablesReference = variablesReference;
				return this;
			}

			/** The number of named child variables.
				The client can use this optional information to present the variables in a paged UI and fetch them in chunks.
			*/
			public Integer namedVariables;
			public Body setNamedVariables(Integer namedVariables) {
				this.namedVariables = namedVariables;
				return this;
			}

			/** The number of indexed child variables.
				The client can use this optional information to present the variables in a paged UI and fetch them in chunks.
			*/
			public Integer indexedVariables;
			public Body setIndexedVariables(Integer indexedVariables) {
				this.indexedVariables = indexedVariables;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "value=" + value
					+ ", type=" + type
					+ ", variablesReference=" + variablesReference
					+ ", namedVariables=" + namedVariables
					+ ", indexedVariables=" + indexedVariables
				 + "]";
			}
		};

		public Body body;
		public SetVariableResponse setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "SetVariableResponse ["
				+ "body=" + body
			 + "]";
		}
	}

	/** Source request; value of command field is 'source'.
		The request retrieves the source code for a given source reference.
	*/
	public static class SourceRequest extends Request {
		public String command;
		public SourceRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public SourceArguments arguments;
		public SourceRequest setArguments(SourceArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "SourceRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'source' request. */
	public static class SourceArguments {
		/** Specifies the source content to load. Either source.path or source.sourceReference must be specified. */
		public Source source;
		public SourceArguments setSource(Source source) {
			this.source = source;
			return this;
		}

		/** The reference to the source. This is the same as source.sourceReference. This is provided for backward compatibility since old backends do not understand the 'source' attribute. */
		public Integer sourceReference;
		public SourceArguments setSourceReference(Integer sourceReference) {
			this.sourceReference = sourceReference;
			return this;
		}

		@Override
		public String toString() {
			return "SourceArguments ["
				+ "source=" + source
				+ ", sourceReference=" + sourceReference
			 + "]";
		}
	}

	/** Response to 'source' request. */
	public static class SourceResponse extends Response {
		public static class Body {
			/** Content of the source reference. */
			public String content;
			public Body setContent(String content) {
				this.content = content;
				return this;
			}

			/** Optional content type (mime type) of the source. */
			public String mimeType;
			public Body setMimeType(String mimeType) {
				this.mimeType = mimeType;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "content=" + content
					+ ", mimeType=" + mimeType
				 + "]";
			}
		};

		public Body body;
		public SourceResponse setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "SourceResponse ["
				+ "body=" + body
			 + "]";
		}
	}

	/** Thread request; value of command field is 'threads'.
		The request retrieves a list of all threads.
	*/
	public static class ThreadsRequest extends Request {
		public String command;
		public ThreadsRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		@Override
		public String toString() {
			return "ThreadsRequest ["
				+ "command=" + command
			 + "]";
		}
	}

	/** Response to 'threads' request. */
	public static class ThreadsResponse extends Response {
		public static class Body {
			/** All threads. */
			public Thread[] threads;
			public Body setThreads(Thread[] threads) {
				this.threads = threads;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "threads=" + Arrays.toString(threads)
				 + "]";
			}
		};

		public Body body;
		public ThreadsResponse setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "ThreadsResponse ["
				+ "body=" + body
			 + "]";
		}
	}

	/** Modules can be retrieved from the debug adapter with the ModulesRequest which can either return all modules or a range of modules to support paging. */
	public static class ModulesRequest extends Request {
		public String command;
		public ModulesRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public ModulesArguments arguments;
		public ModulesRequest setArguments(ModulesArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "ModulesRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'modules' request. */
	public static class ModulesArguments {
		/** The index of the first module to return; if omitted modules start at 0. */
		public Integer startModule;
		public ModulesArguments setStartModule(Integer startModule) {
			this.startModule = startModule;
			return this;
		}

		/** The number of modules to return. If moduleCount is not specified or 0, all modules are returned. */
		public Integer moduleCount;
		public ModulesArguments setModuleCount(Integer moduleCount) {
			this.moduleCount = moduleCount;
			return this;
		}

		@Override
		public String toString() {
			return "ModulesArguments ["
				+ "startModule=" + startModule
				+ ", moduleCount=" + moduleCount
			 + "]";
		}
	}

	/** Response to 'modules' request. */
	public static class ModulesResponse extends Response {
		public static class Body {
			/** All modules or range of modules. */
			public Module[] modules;
			public Body setModules(Module[] modules) {
				this.modules = modules;
				return this;
			}

			/** The total number of modules available. */
			public Integer totalModules;
			public Body setTotalModules(Integer totalModules) {
				this.totalModules = totalModules;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "modules=" + Arrays.toString(modules)
					+ ", totalModules=" + totalModules
				 + "]";
			}
		};

		public Body body;
		public ModulesResponse setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "ModulesResponse ["
				+ "body=" + body
			 + "]";
		}
	}

	/** Evaluate request; value of command field is 'evaluate'.
		Evaluates the given expression in the context of the top most stack frame.
		The expression has access to any variables and arguments that are in scope.
	*/
	public static class EvaluateRequest extends Request {
		public String command;
		public EvaluateRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public EvaluateArguments arguments;
		public EvaluateRequest setArguments(EvaluateArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "EvaluateRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'evaluate' request. */
	public static class EvaluateArguments {
		/** The expression to evaluate. */
		public String expression;
		public EvaluateArguments setExpression(String expression) {
			this.expression = expression;
			return this;
		}

		/** Evaluate the expression in the scope of this stack frame. If not specified, the expression is evaluated in the global scope. */
		public Integer frameId;
		public EvaluateArguments setFrameId(Integer frameId) {
			this.frameId = frameId;
			return this;
		}

		/** The context in which the evaluate request is run. Possible values are 'watch' if evaluate is run in a watch, 'repl' if run from the REPL console, or 'hover' if run from a data hover. */
		public String context;
		public EvaluateArguments setContext(String context) {
			this.context = context;
			return this;
		}

		/** Specifies details on how to format the Evaluate result. */
		public ValueFormat format;
		public EvaluateArguments setFormat(ValueFormat format) {
			this.format = format;
			return this;
		}

		@Override
		public String toString() {
			return "EvaluateArguments ["
				+ "expression=" + expression
				+ ", frameId=" + frameId
				+ ", context=" + context
				+ ", format=" + format
			 + "]";
		}
	}

	/** Response to 'evaluate' request. */
	public static class EvaluateResponse extends Response {
		public static class Body {
			/** The result of the evaluate request. */
			public String result;
			public Body setResult(String result) {
				this.result = result;
				return this;
			}

			/** The optional type of the evaluate result. */
			public String type;
			public Body setType(String type) {
				this.type = type;
				return this;
			}

			/** If variablesReference is > 0, the evaluate result is structured and its children can be retrieved by passing variablesReference to the VariablesRequest. */
			public Integer variablesReference;
			public Body setVariablesReference(Integer variablesReference) {
				this.variablesReference = variablesReference;
				return this;
			}

			/** The number of named child variables.
				The client can use this optional information to present the variables in a paged UI and fetch them in chunks.
			*/
			public Integer namedVariables;
			public Body setNamedVariables(Integer namedVariables) {
				this.namedVariables = namedVariables;
				return this;
			}

			/** The number of indexed child variables.
				The client can use this optional information to present the variables in a paged UI and fetch them in chunks.
			*/
			public Integer indexedVariables;
			public Body setIndexedVariables(Integer indexedVariables) {
				this.indexedVariables = indexedVariables;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "result=" + result
					+ ", type=" + type
					+ ", variablesReference=" + variablesReference
					+ ", namedVariables=" + namedVariables
					+ ", indexedVariables=" + indexedVariables
				 + "]";
			}
		};

		public Body body;
		public EvaluateResponse setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "EvaluateResponse ["
				+ "body=" + body
			 + "]";
		}
	}

	/** StepInTargets request; value of command field is 'stepInTargets'.
		This request retrieves the possible stepIn targets for the specified stack frame.
		These targets can be used in the 'stepIn' request.
		The StepInTargets may only be called if the 'supportsStepInTargetsRequest' capability exists and is true.
	*/
	public static class StepInTargetsRequest extends Request {
		public String command;
		public StepInTargetsRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public StepInTargetsArguments arguments;
		public StepInTargetsRequest setArguments(StepInTargetsArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "StepInTargetsRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'stepInTargets' request. */
	public static class StepInTargetsArguments {
		/** The stack frame for which to retrieve the possible stepIn targets. */
		public Integer frameId;
		public StepInTargetsArguments setFrameId(Integer frameId) {
			this.frameId = frameId;
			return this;
		}

		@Override
		public String toString() {
			return "StepInTargetsArguments ["
				+ "frameId=" + frameId
			 + "]";
		}
	}

	/** Response to 'stepInTargets' request. */
	public static class StepInTargetsResponse extends Response {
		public static class Body {
			/** The possible stepIn targets of the specified source location. */
			public StepInTarget[] targets;
			public Body setTargets(StepInTarget[] targets) {
				this.targets = targets;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "targets=" + Arrays.toString(targets)
				 + "]";
			}
		};

		public Body body;
		public StepInTargetsResponse setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "StepInTargetsResponse ["
				+ "body=" + body
			 + "]";
		}
	}

	/** GotoTargets request; value of command field is 'gotoTargets'.
		This request retrieves the possible goto targets for the specified source location.
		These targets can be used in the 'goto' request.
		The GotoTargets request may only be called if the 'supportsGotoTargetsRequest' capability exists and is true.
	*/
	public static class GotoTargetsRequest extends Request {
		public String command;
		public GotoTargetsRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public GotoTargetsArguments arguments;
		public GotoTargetsRequest setArguments(GotoTargetsArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "GotoTargetsRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'gotoTargets' request. */
	public static class GotoTargetsArguments {
		/** The source location for which the goto targets are determined. */
		public Source source;
		public GotoTargetsArguments setSource(Source source) {
			this.source = source;
			return this;
		}

		/** The line location for which the goto targets are determined. */
		public Integer line;
		public GotoTargetsArguments setLine(Integer line) {
			this.line = line;
			return this;
		}

		/** An optional column location for which the goto targets are determined. */
		public Integer column;
		public GotoTargetsArguments setColumn(Integer column) {
			this.column = column;
			return this;
		}

		@Override
		public String toString() {
			return "GotoTargetsArguments ["
				+ "source=" + source
				+ ", line=" + line
				+ ", column=" + column
			 + "]";
		}
	}

	/** Response to 'gotoTargets' request. */
	public static class GotoTargetsResponse extends Response {
		public static class Body {
			/** The possible goto targets of the specified location. */
			public GotoTarget[] targets;
			public Body setTargets(GotoTarget[] targets) {
				this.targets = targets;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "targets=" + Arrays.toString(targets)
				 + "]";
			}
		};

		public Body body;
		public GotoTargetsResponse setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "GotoTargetsResponse ["
				+ "body=" + body
			 + "]";
		}
	}

	/** CompletionsRequest request; value of command field is 'completions'.
		Returns a list of possible completions for a given caret position and text.
		The CompletionsRequest may only be called if the 'supportsCompletionsRequest' capability exists and is true.
	*/
	public static class CompletionsRequest extends Request {
		public String command;
		public CompletionsRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public CompletionsArguments arguments;
		public CompletionsRequest setArguments(CompletionsArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "CompletionsRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'completions' request. */
	public static class CompletionsArguments {
		/** Returns completions in the scope of this stack frame. If not specified, the completions are returned for the global scope. */
		public Integer frameId;
		public CompletionsArguments setFrameId(Integer frameId) {
			this.frameId = frameId;
			return this;
		}

		/** One or more source lines. Typically this is the text a user has typed into the debug console before he asked for completion. */
		public String text;
		public CompletionsArguments setText(String text) {
			this.text = text;
			return this;
		}

		/** The character position for which to determine the completion proposals. */
		public Integer column;
		public CompletionsArguments setColumn(Integer column) {
			this.column = column;
			return this;
		}

		/** An optional line for which to determine the completion proposals. If missing the first line of the text is assumed. */
		public Integer line;
		public CompletionsArguments setLine(Integer line) {
			this.line = line;
			return this;
		}

		@Override
		public String toString() {
			return "CompletionsArguments ["
				+ "frameId=" + frameId
				+ ", text=" + text
				+ ", column=" + column
				+ ", line=" + line
			 + "]";
		}
	}

	/** Response to 'completions' request. */
	public static class CompletionsResponse extends Response {
		public static class Body {
			/** The possible completions for . */
			public CompletionItem[] targets;
			public Body setTargets(CompletionItem[] targets) {
				this.targets = targets;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "targets=" + Arrays.toString(targets)
				 + "]";
			}
		};

		public Body body;
		public CompletionsResponse setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "CompletionsResponse ["
				+ "body=" + body
			 + "]";
		}
	}

	/** ExceptionInfoRequest request; value of command field is 'exceptionInfo'.
		Retrieves the details of the exception that caused the StoppedEvent to be raised.
	*/
	public static class ExceptionInfoRequest extends Request {
		public String command;
		public ExceptionInfoRequest setCommand(String command) {
			this.command = command;
			return this;
		}

		public ExceptionInfoArguments arguments;
		public ExceptionInfoRequest setArguments(ExceptionInfoArguments arguments) {
			this.arguments = arguments;
			return this;
		}

		@Override
		public String toString() {
			return "ExceptionInfoRequest ["
				+ "command=" + command
				+ ", arguments=" + arguments
			 + "]";
		}
	}

	/** Arguments for 'exceptionInfo' request. */
	public static class ExceptionInfoArguments {
		/** Thread for which exception information should be retrieved. */
		public Integer threadId;
		public ExceptionInfoArguments setThreadId(Integer threadId) {
			this.threadId = threadId;
			return this;
		}

		@Override
		public String toString() {
			return "ExceptionInfoArguments ["
				+ "threadId=" + threadId
			 + "]";
		}
	}

	/** Response to 'exceptionInfo' request. */
	public static class ExceptionInfoResponse extends Response {
		public static class Body {
			/** ID of the exception that was thrown. */
			public String exceptionId;
			public Body setExceptionId(String exceptionId) {
				this.exceptionId = exceptionId;
				return this;
			}

			/** Descriptive text for the exception provided by the debug adapter. */
			public String description;
			public Body setDescription(String description) {
				this.description = description;
				return this;
			}

			/** Mode that caused the exception notification to be raised. */
			public ExceptionBreakMode breakMode;
			public Body setBreakMode(ExceptionBreakMode breakMode) {
				this.breakMode = breakMode;
				return this;
			}

			/** Detailed information about the exception. */
			public ExceptionDetails details;
			public Body setDetails(ExceptionDetails details) {
				this.details = details;
				return this;
			}

			@Override
			public String toString() {
				return "Body ["
					+ "exceptionId=" + exceptionId
					+ ", description=" + description
					+ ", breakMode=" + breakMode
					+ ", details=" + details
				 + "]";
			}
		};

		public Body body;
		public ExceptionInfoResponse setBody(Body body) {
			this.body = body;
			return this;
		}

		@Override
		public String toString() {
			return "ExceptionInfoResponse ["
				+ "body=" + body
			 + "]";
		}
	}

	/** Information about the capabilities of a debug adapter. */
	public static class Capabilities {
		/** The debug adapter supports the configurationDoneRequest. */
		public Boolean supportsConfigurationDoneRequest;
		public Capabilities setSupportsConfigurationDoneRequest(Boolean supportsConfigurationDoneRequest) {
			this.supportsConfigurationDoneRequest = supportsConfigurationDoneRequest;
			return this;
		}

		/** The debug adapter supports function breakpoints. */
		public Boolean supportsFunctionBreakpoints;
		public Capabilities setSupportsFunctionBreakpoints(Boolean supportsFunctionBreakpoints) {
			this.supportsFunctionBreakpoints = supportsFunctionBreakpoints;
			return this;
		}

		/** The debug adapter supports conditional breakpoints. */
		public Boolean supportsConditionalBreakpoints;
		public Capabilities setSupportsConditionalBreakpoints(Boolean supportsConditionalBreakpoints) {
			this.supportsConditionalBreakpoints = supportsConditionalBreakpoints;
			return this;
		}

		/** The debug adapter supports breakpoints that break execution after a specified number of hits. */
		public Boolean supportsHitConditionalBreakpoints;
		public Capabilities setSupportsHitConditionalBreakpoints(Boolean supportsHitConditionalBreakpoints) {
			this.supportsHitConditionalBreakpoints = supportsHitConditionalBreakpoints;
			return this;
		}

		/** The debug adapter supports a (side effect free) evaluate request for data hovers. */
		public Boolean supportsEvaluateForHovers;
		public Capabilities setSupportsEvaluateForHovers(Boolean supportsEvaluateForHovers) {
			this.supportsEvaluateForHovers = supportsEvaluateForHovers;
			return this;
		}

		/** Available filters or options for the setExceptionBreakpoints request. */
		public ExceptionBreakpointsFilter[] exceptionBreakpointFilters;
		public Capabilities setExceptionBreakpointFilters(ExceptionBreakpointsFilter[] exceptionBreakpointFilters) {
			this.exceptionBreakpointFilters = exceptionBreakpointFilters;
			return this;
		}

		/** The debug adapter supports stepping back via the stepBack and reverseContinue requests. */
		public Boolean supportsStepBack;
		public Capabilities setSupportsStepBack(Boolean supportsStepBack) {
			this.supportsStepBack = supportsStepBack;
			return this;
		}

		/** The debug adapter supports setting a variable to a value. */
		public Boolean supportsSetVariable;
		public Capabilities setSupportsSetVariable(Boolean supportsSetVariable) {
			this.supportsSetVariable = supportsSetVariable;
			return this;
		}

		/** The debug adapter supports restarting a frame. */
		public Boolean supportsRestartFrame;
		public Capabilities setSupportsRestartFrame(Boolean supportsRestartFrame) {
			this.supportsRestartFrame = supportsRestartFrame;
			return this;
		}

		/** The debug adapter supports the gotoTargetsRequest. */
		public Boolean supportsGotoTargetsRequest;
		public Capabilities setSupportsGotoTargetsRequest(Boolean supportsGotoTargetsRequest) {
			this.supportsGotoTargetsRequest = supportsGotoTargetsRequest;
			return this;
		}

		/** The debug adapter supports the stepInTargetsRequest. */
		public Boolean supportsStepInTargetsRequest;
		public Capabilities setSupportsStepInTargetsRequest(Boolean supportsStepInTargetsRequest) {
			this.supportsStepInTargetsRequest = supportsStepInTargetsRequest;
			return this;
		}

		/** The debug adapter supports the completionsRequest. */
		public Boolean supportsCompletionsRequest;
		public Capabilities setSupportsCompletionsRequest(Boolean supportsCompletionsRequest) {
			this.supportsCompletionsRequest = supportsCompletionsRequest;
			return this;
		}

		/** The debug adapter supports the modules request. */
		public Boolean supportsModulesRequest;
		public Capabilities setSupportsModulesRequest(Boolean supportsModulesRequest) {
			this.supportsModulesRequest = supportsModulesRequest;
			return this;
		}

		/** The set of additional module information exposed by the debug adapter. */
		public ColumnDescriptor[] additionalModuleColumns;
		public Capabilities setAdditionalModuleColumns(ColumnDescriptor[] additionalModuleColumns) {
			this.additionalModuleColumns = additionalModuleColumns;
			return this;
		}

		/** Checksum algorithms supported by the debug adapter. */
		public ChecksumAlgorithm[] supportedChecksumAlgorithms;
		public Capabilities setSupportedChecksumAlgorithms(ChecksumAlgorithm[] supportedChecksumAlgorithms) {
			this.supportedChecksumAlgorithms = supportedChecksumAlgorithms;
			return this;
		}

		/** The debug adapter supports the RestartRequest. In this case a client should not implement 'restart' by terminating and relaunching the adapter but by calling the RestartRequest. */
		public Boolean supportsRestartRequest;
		public Capabilities setSupportsRestartRequest(Boolean supportsRestartRequest) {
			this.supportsRestartRequest = supportsRestartRequest;
			return this;
		}

		/** The debug adapter supports 'exceptionOptions' on the setExceptionBreakpoints request. */
		public Boolean supportsExceptionOptions;
		public Capabilities setSupportsExceptionOptions(Boolean supportsExceptionOptions) {
			this.supportsExceptionOptions = supportsExceptionOptions;
			return this;
		}

		/** The debug adapter supports a 'format' attribute on the stackTraceRequest, variablesRequest, and evaluateRequest. */
		public Boolean supportsValueFormattingOptions;
		public Capabilities setSupportsValueFormattingOptions(Boolean supportsValueFormattingOptions) {
			this.supportsValueFormattingOptions = supportsValueFormattingOptions;
			return this;
		}

		/** The debug adapter supports the exceptionInfo request. */
		public Boolean supportsExceptionInfoRequest;
		public Capabilities setSupportsExceptionInfoRequest(Boolean supportsExceptionInfoRequest) {
			this.supportsExceptionInfoRequest = supportsExceptionInfoRequest;
			return this;
		}

		/** The debug adapter supports the 'terminateDebuggee' attribute on the 'disconnect' request. */
		public Boolean supportTerminateDebuggee;
		public Capabilities setSupportTerminateDebuggee(Boolean supportTerminateDebuggee) {
			this.supportTerminateDebuggee = supportTerminateDebuggee;
			return this;
		}

		/** The debug adapter supports the delayed loading of parts of the stack, which requires that both the 'startFrame' and 'levels' arguments and the 'totalFrames' result of the 'StackTrace' request are supported. */
		public Boolean supportsDelayedStackTraceLoading;
		public Capabilities setSupportsDelayedStackTraceLoading(Boolean supportsDelayedStackTraceLoading) {
			this.supportsDelayedStackTraceLoading = supportsDelayedStackTraceLoading;
			return this;
		}

		@Override
		public String toString() {
			return "Capabilities ["
				+ "supportsConfigurationDoneRequest=" + supportsConfigurationDoneRequest
				+ ", supportsFunctionBreakpoints=" + supportsFunctionBreakpoints
				+ ", supportsConditionalBreakpoints=" + supportsConditionalBreakpoints
				+ ", supportsHitConditionalBreakpoints=" + supportsHitConditionalBreakpoints
				+ ", supportsEvaluateForHovers=" + supportsEvaluateForHovers
				+ ", exceptionBreakpointFilters=" + Arrays.toString(exceptionBreakpointFilters)
				+ ", supportsStepBack=" + supportsStepBack
				+ ", supportsSetVariable=" + supportsSetVariable
				+ ", supportsRestartFrame=" + supportsRestartFrame
				+ ", supportsGotoTargetsRequest=" + supportsGotoTargetsRequest
				+ ", supportsStepInTargetsRequest=" + supportsStepInTargetsRequest
				+ ", supportsCompletionsRequest=" + supportsCompletionsRequest
				+ ", supportsModulesRequest=" + supportsModulesRequest
				+ ", additionalModuleColumns=" + Arrays.toString(additionalModuleColumns)
				+ ", supportedChecksumAlgorithms=" + Arrays.toString(supportedChecksumAlgorithms)
				+ ", supportsRestartRequest=" + supportsRestartRequest
				+ ", supportsExceptionOptions=" + supportsExceptionOptions
				+ ", supportsValueFormattingOptions=" + supportsValueFormattingOptions
				+ ", supportsExceptionInfoRequest=" + supportsExceptionInfoRequest
				+ ", supportTerminateDebuggee=" + supportTerminateDebuggee
				+ ", supportsDelayedStackTraceLoading=" + supportsDelayedStackTraceLoading
			 + "]";
		}
	}

	/** An ExceptionBreakpointsFilter is shown in the UI as an option for configuring how exceptions are dealt with. */
	public static class ExceptionBreakpointsFilter {
		/** The internal ID of the filter. This value is passed to the setExceptionBreakpoints request. */
		public String filter;
		public ExceptionBreakpointsFilter setFilter(String filter) {
			this.filter = filter;
			return this;
		}

		/** The name of the filter. This will be shown in the UI. */
		public String label;
		public ExceptionBreakpointsFilter setLabel(String label) {
			this.label = label;
			return this;
		}

		/** Initial value of the filter. If not specified a value 'false' is assumed. */
		public Boolean default_;
		public ExceptionBreakpointsFilter setDefault_(Boolean default_) {
			this.default_ = default_;
			return this;
		}

		@Override
		public String toString() {
			return "ExceptionBreakpointsFilter ["
				+ "filter=" + filter
				+ ", label=" + label
				+ ", default=" + default_
			 + "]";
		}
	}

	/** A structured message object. Used to return errors from requests. */
	public static class Message {
		/** Unique identifier for the message. */
		public Integer id;
		public Message setId(Integer id) {
			this.id = id;
			return this;
		}

		/** A format string for the message. Embedded variables have the form '{name}'.
			If variable name starts with an underscore character, the variable does not contain user data (PII) and can be safely used for telemetry purposes.
		*/
		public String format;
		public Message setFormat(String format) {
			this.format = format;
			return this;
		}

		/** An object used as a dictionary for looking up the variables in the format string. */
		public Map<String, String> variables;
		public Message setVariables(Map<String, String> variables) {
			this.variables = variables;
			return this;
		}

		/** If true send to telemetry. */
		public Boolean sendTelemetry;
		public Message setSendTelemetry(Boolean sendTelemetry) {
			this.sendTelemetry = sendTelemetry;
			return this;
		}

		/** If true show user. */
		public Boolean showUser;
		public Message setShowUser(Boolean showUser) {
			this.showUser = showUser;
			return this;
		}

		/** An optional url where additional information about this message can be found. */
		public String url;
		public Message setUrl(String url) {
			this.url = url;
			return this;
		}

		/** An optional label that is presented to the user as the UI for opening the url. */
		public String urlLabel;
		public Message setUrlLabel(String urlLabel) {
			this.urlLabel = urlLabel;
			return this;
		}

		@Override
		public String toString() {
			return "Message ["
				+ "id=" + id
				+ ", format=" + format
				+ ", variables=" + variables
				+ ", sendTelemetry=" + sendTelemetry
				+ ", showUser=" + showUser
				+ ", url=" + url
				+ ", urlLabel=" + urlLabel
			 + "]";
		}
	}

	/** A Module object represents a row in the modules view.
		Two attributes are mandatory: an id identifies a module in the modules view and is used in a ModuleEvent for identifying a module for adding, updating or deleting.
		The name is used to minimally render the module in the UI.
		
		Additional attributes can be added to the module. They will show up in the module View if they have a corresponding ColumnDescriptor.
		
		To avoid an unnecessary proliferation of additional attributes with similar semantics but different names
		we recommend to re-use attributes from the 'recommended' list below first, and only introduce new attributes if nothing appropriate could be found.
	*/
	public static class Module {
		/** Unique identifier for the module. */
		public /* type one of integer | string */ Object id;
		public Module setId(/* type one of integer | string */ Object id) {
			this.id = id;
			return this;
		}

		/** A name of the module. */
		public String name;
		public Module setName(String name) {
			this.name = name;
			return this;
		}

		/** optional but recommended attributes.
			always try to use these first before introducing additional attributes.
			
			Logical full path to the module. The exact definition is implementation defined, but usually this would be a full path to the on-disk file for the module.
		*/
		public String path;
		public Module setPath(String path) {
			this.path = path;
			return this;
		}

		/** True if the module is optimized. */
		public Boolean isOptimized;
		public Module setIsOptimized(Boolean isOptimized) {
			this.isOptimized = isOptimized;
			return this;
		}

		/** True if the module is considered 'user code' by a debugger that supports 'Just My Code'. */
		public Boolean isUserCode;
		public Module setIsUserCode(Boolean isUserCode) {
			this.isUserCode = isUserCode;
			return this;
		}

		/** Version of Module. */
		public String version;
		public Module setVersion(String version) {
			this.version = version;
			return this;
		}

		/** User understandable description of if symbols were found for the module (ex: 'Symbols Loaded', 'Symbols not found', etc. */
		public String symbolStatus;
		public Module setSymbolStatus(String symbolStatus) {
			this.symbolStatus = symbolStatus;
			return this;
		}

		/** Logical full path to the symbol file. The exact definition is implementation defined. */
		public String symbolFilePath;
		public Module setSymbolFilePath(String symbolFilePath) {
			this.symbolFilePath = symbolFilePath;
			return this;
		}

		/** Module created or modified. */
		public String dateTimeStamp;
		public Module setDateTimeStamp(String dateTimeStamp) {
			this.dateTimeStamp = dateTimeStamp;
			return this;
		}

		/** Address range covered by this module. */
		public String addressRange;
		public Module setAddressRange(String addressRange) {
			this.addressRange = addressRange;
			return this;
		}

		@Override
		public String toString() {
			return "Module ["
				+ "id=" + id
				+ ", name=" + name
				+ ", path=" + path
				+ ", isOptimized=" + isOptimized
				+ ", isUserCode=" + isUserCode
				+ ", version=" + version
				+ ", symbolStatus=" + symbolStatus
				+ ", symbolFilePath=" + symbolFilePath
				+ ", dateTimeStamp=" + dateTimeStamp
				+ ", addressRange=" + addressRange
			 + "]";
		}
	}

	/** A ColumnDescriptor specifies what module attribute to show in a column of the ModulesView, how to format it, and what the column's label should be.
		It is only used if the underlying UI actually supports this level of customization.
	*/
	public static class ColumnDescriptor {
		/** Name of the attribute rendered in this column. */
		public String attributeName;
		public ColumnDescriptor setAttributeName(String attributeName) {
			this.attributeName = attributeName;
			return this;
		}

		/** Header UI label of column. */
		public String label;
		public ColumnDescriptor setLabel(String label) {
			this.label = label;
			return this;
		}

		/** Format to use for the rendered values in this column. TBD how the format strings looks like. */
		public String format;
		public ColumnDescriptor setFormat(String format) {
			this.format = format;
			return this;
		}

		/** Datatype of values in this column.  Defaults to 'string' if not specified. */
		public String type;
		public ColumnDescriptor setType(String type) {
			this.type = type;
			return this;
		}

		/** Width of this column in characters (hint only). */
		public Integer width;
		public ColumnDescriptor setWidth(Integer width) {
			this.width = width;
			return this;
		}

		@Override
		public String toString() {
			return "ColumnDescriptor ["
				+ "attributeName=" + attributeName
				+ ", label=" + label
				+ ", format=" + format
				+ ", type=" + type
				+ ", width=" + width
			 + "]";
		}
	}

	/** The ModulesViewDescriptor is the container for all declarative configuration options of a ModuleView.
		For now it only specifies the columns to be shown in the modules view.
	*/
	public static class ModulesViewDescriptor {
		public ColumnDescriptor[] columns;
		public ModulesViewDescriptor setColumns(ColumnDescriptor[] columns) {
			this.columns = columns;
			return this;
		}

		@Override
		public String toString() {
			return "ModulesViewDescriptor ["
				+ "columns=" + Arrays.toString(columns)
			 + "]";
		}
	}

	/** A Thread */
	public static class Thread {
		/** Unique identifier for the thread. */
		public Integer id;
		public Thread setId(Integer id) {
			this.id = id;
			return this;
		}

		/** A name of the thread. */
		public String name;
		public Thread setName(String name) {
			this.name = name;
			return this;
		}

		@Override
		public String toString() {
			return "Thread ["
				+ "id=" + id
				+ ", name=" + name
			 + "]";
		}
	}

	/** A Source is a descriptor for source code. It is returned from the debug adapter as part of a StackFrame and it is used by clients when specifying breakpoints. */
	public static class Source {
		/** The short name of the source. Every source returned from the debug adapter has a name. When sending a source to the debug adapter this name is optional. */
		public String name;
		public Source setName(String name) {
			this.name = name;
			return this;
		}

		/** The path of the source to be shown in the UI. It is only used to locate and load the content of the source if no sourceReference is specified (or its vaule is 0). */
		public String path;
		public Source setPath(String path) {
			this.path = path;
			return this;
		}

		/** If sourceReference > 0 the contents of the source must be retrieved through the SourceRequest (even if a path is specified). A sourceReference is only valid for a session, so it must not be used to persist a source. */
		public Integer sourceReference;
		public Source setSourceReference(Integer sourceReference) {
			this.sourceReference = sourceReference;
			return this;
		}

		/** An optional hint for how to present the source in the UI. A value of 'deemphasize' can be used to indicate that the source is not available or that it is skipped on stepping. */
		public String presentationHint;
		public Source setPresentationHint(String presentationHint) {
			this.presentationHint = presentationHint;
			return this;
		}

		/** The (optional) origin of this source: possible values 'internal module', 'inlined content from source map', etc. */
		public String origin;
		public Source setOrigin(String origin) {
			this.origin = origin;
			return this;
		}

		/** Optional data that a debug adapter might want to loop through the client. The client should leave the data intact and persist it across sessions. The client should not interpret the data. */
		public Object adapterData;
		public Source setAdapterData(Object adapterData) {
			this.adapterData = adapterData;
			return this;
		}

		/** The checksums associated with this file. */
		public Checksum[] checksums;
		public Source setChecksums(Checksum[] checksums) {
			this.checksums = checksums;
			return this;
		}

		@Override
		public String toString() {
			return "Source ["
				+ "name=" + name
				+ ", path=" + path
				+ ", sourceReference=" + sourceReference
				+ ", presentationHint=" + presentationHint
				+ ", origin=" + origin
				+ ", adapterData=" + adapterData
				+ ", checksums=" + Arrays.toString(checksums)
			 + "]";
		}
	}

	/** A Stackframe contains the source location. */
	public static class StackFrame {
		/** An identifier for the stack frame. It must be unique across all threads. This id can be used to retrieve the scopes of the frame with the 'scopesRequest' or to restart the execution of a stackframe. */
		public Integer id;
		public StackFrame setId(Integer id) {
			this.id = id;
			return this;
		}

		/** The name of the stack frame, typically a method name. */
		public String name;
		public StackFrame setName(String name) {
			this.name = name;
			return this;
		}

		/** The optional source of the frame. */
		public Source source;
		public StackFrame setSource(Source source) {
			this.source = source;
			return this;
		}

		/** The line within the file of the frame. If source is null or doesn't exist, line is 0 and must be ignored. */
		public Integer line;
		public StackFrame setLine(Integer line) {
			this.line = line;
			return this;
		}

		/** The column within the line. If source is null or doesn't exist, column is 0 and must be ignored. */
		public Integer column;
		public StackFrame setColumn(Integer column) {
			this.column = column;
			return this;
		}

		/** An optional end line of the range covered by the stack frame. */
		public Integer endLine;
		public StackFrame setEndLine(Integer endLine) {
			this.endLine = endLine;
			return this;
		}

		/** An optional end column of the range covered by the stack frame. */
		public Integer endColumn;
		public StackFrame setEndColumn(Integer endColumn) {
			this.endColumn = endColumn;
			return this;
		}

		/** The module associated with this frame, if any. */
		public /* type one of integer | string */ Object moduleId;
		public StackFrame setModuleId(/* type one of integer | string */ Object moduleId) {
			this.moduleId = moduleId;
			return this;
		}

		/** An optional hint for how to present this frame in the UI. A value of 'label' can be used to indicate that the frame is an artificial frame that is used as a visual label or separator. A value of 'subtle' can be used to change the appearance of a frame in a 'subtle' way. */
		public String presentationHint;
		public StackFrame setPresentationHint(String presentationHint) {
			this.presentationHint = presentationHint;
			return this;
		}

		@Override
		public String toString() {
			return "StackFrame ["
				+ "id=" + id
				+ ", name=" + name
				+ ", source=" + source
				+ ", line=" + line
				+ ", column=" + column
				+ ", endLine=" + endLine
				+ ", endColumn=" + endColumn
				+ ", moduleId=" + moduleId
				+ ", presentationHint=" + presentationHint
			 + "]";
		}
	}

	/** A Scope is a named container for variables. Optionally a scope can map to a source or a range within a source. */
	public static class Scope {
		/** Name of the scope such as 'Arguments', 'Locals'. */
		public String name;
		public Scope setName(String name) {
			this.name = name;
			return this;
		}

		/** The variables of this scope can be retrieved by passing the value of variablesReference to the VariablesRequest. */
		public Integer variablesReference;
		public Scope setVariablesReference(Integer variablesReference) {
			this.variablesReference = variablesReference;
			return this;
		}

		/** The number of named variables in this scope.
			The client can use this optional information to present the variables in a paged UI and fetch them in chunks.
		*/
		public Integer namedVariables;
		public Scope setNamedVariables(Integer namedVariables) {
			this.namedVariables = namedVariables;
			return this;
		}

		/** The number of indexed variables in this scope.
			The client can use this optional information to present the variables in a paged UI and fetch them in chunks.
		*/
		public Integer indexedVariables;
		public Scope setIndexedVariables(Integer indexedVariables) {
			this.indexedVariables = indexedVariables;
			return this;
		}

		/** If true, the number of variables in this scope is large or expensive to retrieve. */
		public Boolean expensive;
		public Scope setExpensive(Boolean expensive) {
			this.expensive = expensive;
			return this;
		}

		/** Optional source for this scope. */
		public Source source;
		public Scope setSource(Source source) {
			this.source = source;
			return this;
		}

		/** Optional start line of the range covered by this scope. */
		public Integer line;
		public Scope setLine(Integer line) {
			this.line = line;
			return this;
		}

		/** Optional start column of the range covered by this scope. */
		public Integer column;
		public Scope setColumn(Integer column) {
			this.column = column;
			return this;
		}

		/** Optional end line of the range covered by this scope. */
		public Integer endLine;
		public Scope setEndLine(Integer endLine) {
			this.endLine = endLine;
			return this;
		}

		/** Optional end column of the range covered by this scope. */
		public Integer endColumn;
		public Scope setEndColumn(Integer endColumn) {
			this.endColumn = endColumn;
			return this;
		}

		@Override
		public String toString() {
			return "Scope ["
				+ "name=" + name
				+ ", variablesReference=" + variablesReference
				+ ", namedVariables=" + namedVariables
				+ ", indexedVariables=" + indexedVariables
				+ ", expensive=" + expensive
				+ ", source=" + source
				+ ", line=" + line
				+ ", column=" + column
				+ ", endLine=" + endLine
				+ ", endColumn=" + endColumn
			 + "]";
		}
	}

	/** A Variable is a name/value pair.
		Optionally a variable can have a 'type' that is shown if space permits or when hovering over the variable's name.
		An optional 'kind' is used to render additional properties of the variable, e.g. different icons can be used to indicate that a variable is public or private.
		If the value is structured (has children), a handle is provided to retrieve the children with the VariablesRequest.
		If the number of named or indexed children is large, the numbers should be returned via the optional 'namedVariables' and 'indexedVariables' attributes.
		The client can use this optional information to present the children in a paged UI and fetch them in chunks.
	*/
	public static class Variable {
		/** The variable's name. */
		public String name;
		public Variable setName(String name) {
			this.name = name;
			return this;
		}

		/** The variable's value. This can be a multi-line text, e.g. for a function the body of a function. */
		public String value;
		public Variable setValue(String value) {
			this.value = value;
			return this;
		}

		/** The type of the variable's value. Typically shown in the UI when hovering over the value. */
		public String type;
		public Variable setType(String type) {
			this.type = type;
			return this;
		}

		/** Properties of a variable that can be used to determine how to render the variable in the UI. Format of the string value: TBD. */
		public String kind;
		public Variable setKind(String kind) {
			this.kind = kind;
			return this;
		}

		/** Optional evaluatable name of this variable which can be passed to the 'EvaluateRequest' to fetch the variable's value. */
		public String evaluateName;
		public Variable setEvaluateName(String evaluateName) {
			this.evaluateName = evaluateName;
			return this;
		}

		/** If variablesReference is > 0, the variable is structured and its children can be retrieved by passing variablesReference to the VariablesRequest. */
		public Integer variablesReference;
		public Variable setVariablesReference(Integer variablesReference) {
			this.variablesReference = variablesReference;
			return this;
		}

		/** The number of named child variables.
			The client can use this optional information to present the children in a paged UI and fetch them in chunks.
		*/
		public Integer namedVariables;
		public Variable setNamedVariables(Integer namedVariables) {
			this.namedVariables = namedVariables;
			return this;
		}

		/** The number of indexed child variables.
			The client can use this optional information to present the children in a paged UI and fetch them in chunks.
		*/
		public Integer indexedVariables;
		public Variable setIndexedVariables(Integer indexedVariables) {
			this.indexedVariables = indexedVariables;
			return this;
		}

		@Override
		public String toString() {
			return "Variable ["
				+ "name=" + name
				+ ", value=" + value
				+ ", type=" + type
				+ ", kind=" + kind
				+ ", evaluateName=" + evaluateName
				+ ", variablesReference=" + variablesReference
				+ ", namedVariables=" + namedVariables
				+ ", indexedVariables=" + indexedVariables
			 + "]";
		}
	}

	/** Properties of a breakpoint passed to the setBreakpoints request. */
	public static class SourceBreakpoint {
		/** The source line of the breakpoint. */
		public Integer line;
		public SourceBreakpoint setLine(Integer line) {
			this.line = line;
			return this;
		}

		/** An optional source column of the breakpoint. */
		public Integer column;
		public SourceBreakpoint setColumn(Integer column) {
			this.column = column;
			return this;
		}

		/** An optional expression for conditional breakpoints. */
		public String condition;
		public SourceBreakpoint setCondition(String condition) {
			this.condition = condition;
			return this;
		}

		/** An optional expression that controls how many hits of the breakpoint are ignored. The backend is expected to interpret the expression as needed. */
		public String hitCondition;
		public SourceBreakpoint setHitCondition(String hitCondition) {
			this.hitCondition = hitCondition;
			return this;
		}

		@Override
		public String toString() {
			return "SourceBreakpoint ["
				+ "line=" + line
				+ ", column=" + column
				+ ", condition=" + condition
				+ ", hitCondition=" + hitCondition
			 + "]";
		}
	}

	/** Properties of a breakpoint passed to the setFunctionBreakpoints request. */
	public static class FunctionBreakpoint {
		/** The name of the function. */
		public String name;
		public FunctionBreakpoint setName(String name) {
			this.name = name;
			return this;
		}

		/** An optional expression for conditional breakpoints. */
		public String condition;
		public FunctionBreakpoint setCondition(String condition) {
			this.condition = condition;
			return this;
		}

		/** An optional expression that controls how many hits of the breakpoint are ignored. The backend is expected to interpret the expression as needed. */
		public String hitCondition;
		public FunctionBreakpoint setHitCondition(String hitCondition) {
			this.hitCondition = hitCondition;
			return this;
		}

		@Override
		public String toString() {
			return "FunctionBreakpoint ["
				+ "name=" + name
				+ ", condition=" + condition
				+ ", hitCondition=" + hitCondition
			 + "]";
		}
	}

	/** Information about a Breakpoint created in setBreakpoints or setFunctionBreakpoints. */
	public static class Breakpoint {
		/** An optional unique identifier for the breakpoint. */
		public Integer id;
		public Breakpoint setId(Integer id) {
			this.id = id;
			return this;
		}

		/** If true breakpoint could be set (but not necessarily at the desired location). */
		public Boolean verified;
		public Breakpoint setVerified(Boolean verified) {
			this.verified = verified;
			return this;
		}

		/** An optional message about the state of the breakpoint. This is shown to the user and can be used to explain why a breakpoint could not be verified. */
		public String message;
		public Breakpoint setMessage(String message) {
			this.message = message;
			return this;
		}

		/** The source where the breakpoint is located. */
		public Source source;
		public Breakpoint setSource(Source source) {
			this.source = source;
			return this;
		}

		/** The start line of the actual range covered by the breakpoint. */
		public Integer line;
		public Breakpoint setLine(Integer line) {
			this.line = line;
			return this;
		}

		/** An optional start column of the actual range covered by the breakpoint. */
		public Integer column;
		public Breakpoint setColumn(Integer column) {
			this.column = column;
			return this;
		}

		/** An optional end line of the actual range covered by the breakpoint. */
		public Integer endLine;
		public Breakpoint setEndLine(Integer endLine) {
			this.endLine = endLine;
			return this;
		}

		/** An optional end column of the actual range covered by the breakpoint. If no end line is given, then the end column is assumed to be in the start line. */
		public Integer endColumn;
		public Breakpoint setEndColumn(Integer endColumn) {
			this.endColumn = endColumn;
			return this;
		}

		@Override
		public String toString() {
			return "Breakpoint ["
				+ "id=" + id
				+ ", verified=" + verified
				+ ", message=" + message
				+ ", source=" + source
				+ ", line=" + line
				+ ", column=" + column
				+ ", endLine=" + endLine
				+ ", endColumn=" + endColumn
			 + "]";
		}
	}

	/** A StepInTarget can be used in the 'stepIn' request and determines into which single target the stepIn request should step. */
	public static class StepInTarget {
		/** Unique identifier for a stepIn target. */
		public Integer id;
		public StepInTarget setId(Integer id) {
			this.id = id;
			return this;
		}

		/** The name of the stepIn target (shown in the UI). */
		public String label;
		public StepInTarget setLabel(String label) {
			this.label = label;
			return this;
		}

		@Override
		public String toString() {
			return "StepInTarget ["
				+ "id=" + id
				+ ", label=" + label
			 + "]";
		}
	}

	/** A GotoTarget describes a code location that can be used as a target in the 'goto' request.
		The possible goto targets can be determined via the 'gotoTargets' request.
	*/
	public static class GotoTarget {
		/** Unique identifier for a goto target. This is used in the goto request. */
		public Integer id;
		public GotoTarget setId(Integer id) {
			this.id = id;
			return this;
		}

		/** The name of the goto target (shown in the UI). */
		public String label;
		public GotoTarget setLabel(String label) {
			this.label = label;
			return this;
		}

		/** The line of the goto target. */
		public Integer line;
		public GotoTarget setLine(Integer line) {
			this.line = line;
			return this;
		}

		/** An optional column of the goto target. */
		public Integer column;
		public GotoTarget setColumn(Integer column) {
			this.column = column;
			return this;
		}

		/** An optional end line of the range covered by the goto target. */
		public Integer endLine;
		public GotoTarget setEndLine(Integer endLine) {
			this.endLine = endLine;
			return this;
		}

		/** An optional end column of the range covered by the goto target. */
		public Integer endColumn;
		public GotoTarget setEndColumn(Integer endColumn) {
			this.endColumn = endColumn;
			return this;
		}

		@Override
		public String toString() {
			return "GotoTarget ["
				+ "id=" + id
				+ ", label=" + label
				+ ", line=" + line
				+ ", column=" + column
				+ ", endLine=" + endLine
				+ ", endColumn=" + endColumn
			 + "]";
		}
	}

	/** CompletionItems are the suggestions returned from the CompletionsRequest. */
	public static class CompletionItem {
		/** The label of this completion item. By default this is also the text that is inserted when selecting this completion. */
		public String label;
		public CompletionItem setLabel(String label) {
			this.label = label;
			return this;
		}

		/** If text is not falsy then it is inserted instead of the label. */
		public String text;
		public CompletionItem setText(String text) {
			this.text = text;
			return this;
		}

		/** The item's type. Typically the client uses this information to render the item in the UI with an icon. */
		public CompletionItemType type;
		public CompletionItem setType(CompletionItemType type) {
			this.type = type;
			return this;
		}

		/** This value determines the location (in the CompletionsRequest's 'text' attribute) where the completion text is added.
			If missing the text is added at the location specified by the CompletionsRequest's 'column' attribute.
		*/
		public Integer start;
		public CompletionItem setStart(Integer start) {
			this.start = start;
			return this;
		}

		/** This value determines how many characters are overwritten by the completion text.
			If missing the value 0 is assumed which results in the completion text being inserted.
		*/
		public Integer length;
		public CompletionItem setLength(Integer length) {
			this.length = length;
			return this;
		}

		@Override
		public String toString() {
			return "CompletionItem ["
				+ "label=" + label
				+ ", text=" + text
				+ ", type=" + type
				+ ", start=" + start
				+ ", length=" + length
			 + "]";
		}
	}

	/** Some predefined types for the CompletionItem. Please note that not all clients have specific icons for all of them. */
	enum CompletionItemType { method, function, constructor, field, variable, class_, interface_, module, property, unit, value, enum_, keyword, snippet, text, color, file, reference, customcolor }

	/** Names of checksum algorithms that may be supported by a debug adapter. */
	enum ChecksumAlgorithm { MD5, SHA1, SHA256, timestamp }

	/** The checksum of an item calculated by the specified algorithm. */
	public static class Checksum {
		/** The algorithm used to calculate this checksum. */
		public ChecksumAlgorithm algorithm;
		public Checksum setAlgorithm(ChecksumAlgorithm algorithm) {
			this.algorithm = algorithm;
			return this;
		}

		/** Value of the checksum. */
		public String checksum;
		public Checksum setChecksum(String checksum) {
			this.checksum = checksum;
			return this;
		}

		@Override
		public String toString() {
			return "Checksum ["
				+ "algorithm=" + algorithm
				+ ", checksum=" + checksum
			 + "]";
		}
	}

	/** Provides formatting information for a value. */
	public static class ValueFormat {
		/** Display the value in hex. */
		public Boolean hex;
		public ValueFormat setHex(Boolean hex) {
			this.hex = hex;
			return this;
		}

		@Override
		public String toString() {
			return "ValueFormat ["
				+ "hex=" + hex
			 + "]";
		}
	}

	/** Provides formatting information for a stack frame. */
	public static class StackFrameFormat extends ValueFormat {
		/** Displays parameters for the stack frame. */
		public Boolean parameters;
		public StackFrameFormat setParameters(Boolean parameters) {
			this.parameters = parameters;
			return this;
		}

		/** Displays the types of parameters for the stack frame. */
		public Boolean parameterTypes;
		public StackFrameFormat setParameterTypes(Boolean parameterTypes) {
			this.parameterTypes = parameterTypes;
			return this;
		}

		/** Displays the names of parameters for the stack frame. */
		public Boolean parameterNames;
		public StackFrameFormat setParameterNames(Boolean parameterNames) {
			this.parameterNames = parameterNames;
			return this;
		}

		/** Displays the values of parameters for the stack frame. */
		public Boolean parameterValues;
		public StackFrameFormat setParameterValues(Boolean parameterValues) {
			this.parameterValues = parameterValues;
			return this;
		}

		/** Displays the line number of the stack frame. */
		public Boolean line;
		public StackFrameFormat setLine(Boolean line) {
			this.line = line;
			return this;
		}

		/** Displays the module of the stack frame. */
		public Boolean module;
		public StackFrameFormat setModule(Boolean module) {
			this.module = module;
			return this;
		}

		/** Includes all stack frames, including those the debug adapter might otherwise hide. */
		public Boolean includeAll;
		public StackFrameFormat setIncludeAll(Boolean includeAll) {
			this.includeAll = includeAll;
			return this;
		}

		@Override
		public String toString() {
			return "StackFrameFormat ["
				+ "parameters=" + parameters
				+ ", parameterTypes=" + parameterTypes
				+ ", parameterNames=" + parameterNames
				+ ", parameterValues=" + parameterValues
				+ ", line=" + line
				+ ", module=" + module
				+ ", includeAll=" + includeAll
			 + "]";
		}
	}

	/** An ExceptionOptions assigns configuration options to a set of exceptions. */
	public static class ExceptionOptions {
		/** A path that selects a single or multiple exceptions in a tree. If 'path' is missing, the whole tree is selected. By convention the first segment of the path is a category that is used to group exceptions in the UI. */
		public ExceptionPathSegment[] path;
		public ExceptionOptions setPath(ExceptionPathSegment[] path) {
			this.path = path;
			return this;
		}

		/** Condition when a thrown exception should result in a break. */
		public ExceptionBreakMode breakMode;
		public ExceptionOptions setBreakMode(ExceptionBreakMode breakMode) {
			this.breakMode = breakMode;
			return this;
		}

		@Override
		public String toString() {
			return "ExceptionOptions ["
				+ "path=" + Arrays.toString(path)
				+ ", breakMode=" + breakMode
			 + "]";
		}
	}

	/** This enumeration defines all possible conditions when a thrown exception should result in a break.
		never: never breaks,
		always: always breaks,
		unhandled: breaks when excpetion unhandled,
		userUnhandled: breaks if the exception is not handled by user code.
	*/
	enum ExceptionBreakMode { never, always, unhandled, userUnhandled }

	/** An ExceptionPathSegment represents a segment in a path that is used to match leafs or nodes in a tree of exceptions. If a segment consists of more than one name, it matches the names provided if 'negate' is false or missing or it matches anything except the names provided if 'negate' is true. */
	public static class ExceptionPathSegment {
		/** If false or missing this segment matches the names provided, otherwise it matches anything except the names provided. */
		public Boolean negate;
		public ExceptionPathSegment setNegate(Boolean negate) {
			this.negate = negate;
			return this;
		}

		/** Depending on the value of 'negate' the names that should match or not match. */
		public String[] names;
		public ExceptionPathSegment setNames(String[] names) {
			this.names = names;
			return this;
		}

		@Override
		public String toString() {
			return "ExceptionPathSegment ["
				+ "negate=" + negate
				+ ", names=" + Arrays.toString(names)
			 + "]";
		}
	}

	/** Detailed information about an exception that has occurred. */
	public static class ExceptionDetails {
		/** Message contained in the exception. */
		public String message;
		public ExceptionDetails setMessage(String message) {
			this.message = message;
			return this;
		}

		/** Short type name of the exception object. */
		public String typeName;
		public ExceptionDetails setTypeName(String typeName) {
			this.typeName = typeName;
			return this;
		}

		/** Fully-qualified type name of the exception object. */
		public String fullTypeName;
		public ExceptionDetails setFullTypeName(String fullTypeName) {
			this.fullTypeName = fullTypeName;
			return this;
		}

		/** Optional expression that can be evaluated in the current scope to obtain the exception object. */
		public String evaluateName;
		public ExceptionDetails setEvaluateName(String evaluateName) {
			this.evaluateName = evaluateName;
			return this;
		}

		/** Stack trace at the time the exception was thrown. */
		public String stackTrace;
		public ExceptionDetails setStackTrace(String stackTrace) {
			this.stackTrace = stackTrace;
			return this;
		}

		/** Details of the exception contained by this exception, if any. */
		public ExceptionDetails[] innerException;
		public ExceptionDetails setInnerException(ExceptionDetails[] innerException) {
			this.innerException = innerException;
			return this;
		}

		@Override
		public String toString() {
			return "ExceptionDetails ["
				+ "message=" + message
				+ ", typeName=" + typeName
				+ ", fullTypeName=" + fullTypeName
				+ ", evaluateName=" + evaluateName
				+ ", stackTrace=" + stackTrace
				+ ", innerException=" + Arrays.toString(innerException)
			 + "]";
		}
	}
}

