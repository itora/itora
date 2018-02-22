package com.github.itora.server.udp;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.davfx.ninio.core.Address;
import com.davfx.ninio.core.Connecter;
import com.davfx.ninio.core.Connection;
import com.davfx.ninio.core.Ninio;
import com.davfx.ninio.core.Nop;
import com.davfx.ninio.core.UdpSocket;
import com.davfx.ninio.util.Wait;
import com.github.itora.crypto.Signed;
import com.github.itora.pow.Powed;
import com.github.itora.request.RegularSignedPowRequestSerializer;
import com.github.itora.request.Request;
import com.github.itora.request.SignedPowRequestSerializer;
import com.github.itora.util.ProducingByteArray;

public final class UdpServer {

	private static final Logger LOGGER = LoggerFactory.getLogger(UdpServer.class);

	private final Connecter connecter;
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	
	private final SignedPowRequestSerializer serializer = new RegularSignedPowRequestSerializer();
	
	private final Wait waitForClose = new Wait();
	private final int actualPortToListen;

	public UdpServer(Ninio ninio, int portToListen, Consumer<Signed<Powed<Request>>> callback) {
		if (portToListen == 0) {
			int port;
			try {
				try (ServerSocket ss = new ServerSocket(0)) {
					port = ss.getLocalPort();
				}
			} catch (IOException ioe) {
				LOGGER.error("Server failed", ioe);
				waitForClose.run();
				connecter = null;
				actualPortToListen = 0;
				return;
			}
			actualPortToListen = port;
		} else {
			actualPortToListen = portToListen;
		}
		
		LOGGER.debug("Opening server on port {}", actualPortToListen);
		
		Wait waitForOpen = new Wait();
		
		connecter = ninio.create(UdpSocket.builder().bind(new Address(Address.ANY, actualPortToListen)));
		connecter.connect(new Connection() {
			@Override
			public void failed(IOException ioe) {
				LOGGER.error("Server failed", ioe);
				waitForOpen.run();
				waitForClose.run();
			}
			@Override
			public void connected(Address address) {
				LOGGER.debug("Server ready");
				waitForOpen.run();
			}
			@Override
			public void closed() {
				LOGGER.debug("Server closed");
				waitForOpen.run();
				waitForClose.run();
			}
			
			@Override
			public void received(Address address, ByteBuffer buffer) {
				LOGGER.trace("Received {} bytes from {}", buffer.remaining(), address);
				callback.accept(serializer.deserialize(new ProducingByteArray().produceBytes(buffer.array(), buffer.position(), buffer.remaining()).finish()));
			}
		});

		waitForOpen.waitFor();
	}
	
	public void close() {
		if (connecter != null) {
			connecter.close();
		}
		waitForClose.waitFor();
		executor.shutdown();
	}
	
	public int port() {
		return actualPortToListen;
	}
	
	public void send(Address address, Signed<Powed<Request>> request) {
		if (connecter == null) {
			return;
		}
		connecter.send(address, ByteBuffer.wrap(serializer.serialize(request).flattened()), new Nop());
	}
}
