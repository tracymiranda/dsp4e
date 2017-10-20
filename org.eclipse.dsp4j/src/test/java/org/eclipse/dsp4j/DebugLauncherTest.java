package org.eclipse.dsp4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.dsp4j.jsonrpc.DebugLauncher;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class DebugLauncherTest {

	private static final long TIMEOUT = 2000;

	static class Param {
		Param() {
		}
		Param(String message) {
			this.message = message;
		}
		public String message;
	}

	static interface A {
		@JsonNotification public void say(Param p);
	}

	@Test public void testDone() throws Exception {
		A a = new A() {
			@Override
			public void say(Param p) {
			}
		};
		DebugLauncher<A> launcher = DebugLauncher.createLauncher(a, A.class, new ByteArrayInputStream("".getBytes()), new ByteArrayOutputStream());
		Future<?> startListening = launcher.startListening();
		startListening.get(TIMEOUT, TimeUnit.MILLISECONDS);
		Assert.assertTrue(startListening.isDone());
		Assert.assertFalse(startListening.isCancelled());
	}

	@Test public void testCanceled() throws Exception {
		A a = new A() {
			@Override
			public void say(Param p) {
			}
		};
		DebugLauncher<A> launcher = DebugLauncher.createLauncher(a, A.class, new InputStream() {
			@Override
			public int read() throws IOException {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				return '\n';
			}
		}, new ByteArrayOutputStream());
		Future<?> startListening = launcher.startListening();
		startListening.cancel(true);
		Assert.assertTrue(startListening.isDone());
		Assert.assertTrue(startListening.isCancelled());
	}

	@Ignore("Not support in 0.2.0 of jsonrpc")
	@Test public void testCustomGson() throws Exception {
		A a = new A() {
			@Override
			public void say(Param p) {
			}
		};
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		TypeAdapter<Param> typeAdapter = new TypeAdapter<Param>() {
			@Override
			public void write(JsonWriter out, Param value) throws IOException {
				out.beginObject();
				out.name("message");
				out.value("bar");
				out.endObject();
			}
			@Override
			public Param read(JsonReader in) throws IOException {
				return null;
			}
		};
		DebugLauncher<A> launcher = DebugLauncher.createIoLauncher(a, A.class, new ByteArrayInputStream("".getBytes()), out,
				Executors.newCachedThreadPool(), c -> c,
				gsonBuilder -> {gsonBuilder.registerTypeAdapter(Param.class, typeAdapter);});
		A remoteProxy = launcher.getRemoteProxy();

		remoteProxy.say(new Param("foo"));
		Assert.assertEquals("Content-Length: 57\r\n\r\n" +
				"{\"type\":\"event\",\"command\":\"say\",\"body\":{\"message\":\"bar\"}}",
				out.toString());
	}

}
