package org.eclipse.dsp4j;

import org.eclipse.dsp4j.DebugProtocol.OutputEvent;
import org.eclipse.dsp4j.DebugProtocol.StoppedEvent;
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

	@JsonNotification
	default void stopped(StoppedEvent.Body body) {
		System.out.println("stopped: " + body);
	}

}