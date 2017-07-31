package org.eclipse.dsp43.example.readme.debugmodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.dsp4j.DebugProtocol.InitializeRequest;
import org.eclipse.dsp4j.DebugProtocol.InitializeRequestArguments;
import org.eclipse.dsp4j.DebugProtocol.InitializedEvent;
import org.eclipse.dsp4j.DebugProtocol.ProtocolMessage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ReadmeDebugTarget extends MockDebugElement implements IDebugTarget {
	private static final String CONTENT_LENGTH = "Content-Length: ";

	private ILaunch launch;
	private IProcess process;

	private OutputStreamWriter writer;
	private BufferedReader reader;

	private Gson gson;

	public ReadmeDebugTarget(ILaunch launch, IProcess process, Reader reader, Writer writer) throws IOException {
		super(null);
		this.launch = launch;
		this.process = process;

		// give interpreter a chance to start
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		//this.writer = new BufferedWriter(writer);
		this.writer = (OutputStreamWriter) writer;
		this.reader = new BufferedReader(reader);
		GsonBuilder builder = new GsonBuilder();
		gson = builder.create();

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

		sendMessage(initialize);
		InitializedEvent initialized = recvMessage(InitializedEvent.class);

		// give interpreter a chance to start
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
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

	@Override
	public IDebugTarget getDebugTarget() {
		return this;
	}

	@Override
	public ILaunch getLaunch() {
		return launch;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canTerminate() {
		return true;
	}

	@Override
	public boolean isTerminated() {
		// TODO Auto-generated method stub	
		return process.isTerminated();
	}

	@Override
	public void terminate() throws DebugException {
		// TODO Auto-generated method stub
		System.out.println("Try to terminate stuff");
		process.terminate();
	}

	@Override
	public boolean canResume() {
		return false;
	}

	@Override
	public boolean canSuspend() {
		return false;
	}

	@Override
	public boolean isSuspended() {
		return false;
	}

	@Override
	public void resume() throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public void suspend() throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public void breakpointAdded(IBreakpoint breakpoint) {
		// TODO Auto-generated method stub

	}

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canDisconnect() {
		return false;
	}

	@Override
	public void disconnect() throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDisconnected() {
		return false;
	}

	@Override
	public boolean supportsStorageRetrieval() {
		return false;
	}

	@Override
	public IMemoryBlock getMemoryBlock(long startAddress, long length) throws DebugException {
		return null;
	}

	@Override
	public IProcess getProcess() {
		return process;
	}

	@Override
	public IThread[] getThreads() throws DebugException {
		return new IThread[] {};
	}

	@Override
	public boolean hasThreads() throws DebugException {
		return false;
	}

	@Override
	public String getName() throws DebugException {
		return "Readme Mock Debug";
	}

	@Override
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		return false;
	}

}
