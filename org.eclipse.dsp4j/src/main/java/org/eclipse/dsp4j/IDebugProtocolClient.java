package org.eclipse.dsp4j;

import org.eclipse.dsp4j.DebugProtocol.ContinuedEvent;
import org.eclipse.dsp4j.DebugProtocol.OutputEvent;
import org.eclipse.dsp4j.DebugProtocol.StoppedEvent;
import org.eclipse.dsp4j.DebugProtocol.TerminatedEvent;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;

public interface IDebugProtocolClient {

	@JsonNotification
	default void initialized() {
		System.out.println("initialized");
	};

	@JsonNotification
	default void output(OutputEvent.Body body) {
		System.out.println("output body" + body.output);
	}

	/**
	 * Called by the debug adapter when a stopped event is issued.
	 * 
	 * @param body
	 *            contains the reason for the stopped event
	 */
	@JsonNotification
	default void stopped(StoppedEvent.Body body) {
		System.out.println("stopped: " + body);
	}
	
	/**
	 * Called by the debug adapter when a continued event is issued.
	 * 
	 * @param body
	 *            contains the thread id of the continued thread
	 */
	@JsonNotification
	default void continued(ContinuedEvent.Body body) {
		System.out.println("continued: " + body);
	}

	/**
	 * Called by the debug adapter when a terminate event is issued.
	 * 
	 * @param body
	 *            contains the restart flag
	 */
	@JsonNotification
	default void terminated(TerminatedEvent.Body body) {
		System.out.println("terminated: " + body);
	}

}