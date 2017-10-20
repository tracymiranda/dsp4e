package org.eclipse.dsp4j.jsonrpc;

import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.json.adapters.CollectionTypeAdapterFactory;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapterFactory;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EnumTypeAdapterFactory;

import com.google.gson.GsonBuilder;

public class DebugMessageJsonHandler extends MessageJsonHandler {
	public DebugMessageJsonHandler(Map<String, JsonRpcMethod> supportedMethods) {
		super(supportedMethods);
	}

	public DebugMessageJsonHandler(Map<String, JsonRpcMethod> supportedMethods, Consumer<GsonBuilder> configureGson) {
		super(supportedMethods);
		// TODO support 0.3.0 by having configureGson not be ignored
	}

	public GsonBuilder getDefaultGsonBuilder() {
		return new GsonBuilder().registerTypeAdapterFactory(new CollectionTypeAdapterFactory())
				.registerTypeAdapterFactory(new EitherTypeAdapterFactory())
				.registerTypeAdapterFactory(new EnumTypeAdapterFactory())
				.registerTypeAdapterFactory(new DebugMessageTypeAdapterFactory(this));
	}

}
