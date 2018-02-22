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
import com.davfx.ninio.core.Connected;
import com.davfx.ninio.core.Connecter;
import com.davfx.ninio.core.Connection;
import com.davfx.ninio.core.Listener;
import com.davfx.ninio.core.Listening;
import com.davfx.ninio.core.LockListening;
import com.davfx.ninio.core.Ninio;
import com.davfx.ninio.core.Nop;
import com.davfx.ninio.core.TcpSocketServer;
import com.davfx.ninio.core.UdpSocket;
import com.davfx.ninio.core.WaitClosedListening;
import com.davfx.ninio.core.WaitConnectedListening;
import com.davfx.ninio.util.Wait;
import com.github.itora.request.RegularSignedPowRequestSerializer;
import com.github.itora.request.SignedPowRequest;
import com.github.itora.request.SignedPowRequestSerializer;

public final class WiringTcpServer {

	private static final Logger LOGGER = LoggerFactory.getLogger(WiringTcpServer.class);

	private final Listener listener;
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	
	private final SignedPowRequestSerializer serializer = new RegularSignedPowRequestSerializer();
	
	private final Wait waitForClose = new Wait();
	private final int actualPortToListen;

	public WiringTcpServer(Ninio ninio, int portToListen, Consumer<SignedPowRequest> callback) {
		if (portToListen == 0) {
			int port;
			try {
				try (ServerSocket ss = new ServerSocket(0)) {
					port = ss.getLocalPort();
				}
			} catch (IOException ioe) {
				LOGGER.error("Server failed", ioe);
				waitForClose.run();
				listener = null;
				actualPortToListen = 0;
				return;
			}
			actualPortToListen = port;
		} else {
			actualPortToListen = portToListen;
		}
		
		LOGGER.debug("Opening server on port {}", actualPortToListen);
		
		Wait waitForOpen = new Wait();
		
		
		listener = ninio.create(TcpSocketServer.builder().bind(new Address(Address.ANY, actualPortToListen)));
		listener.listen(
				new Listening() {
					
					@Override
					public void connected(Address address) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void closed() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void failed(IOException e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public Connection connecting(Connected connecting) {
						// TODO Auto-generated method stub
						return null;
					}
				};ConnectedListening(serverWaitConnecting,
				new WaitClosedListening(serverWaitClosing,
				new LockListening(lock,
				new Listening() {
					@Override
					public void failed(IOException ioe) {
					}
					@Override
					public void connected(Address address) {
					}
					@Override
					public void closed() {
					}
					
					@Override
					public Connection connecting(final Connected connecting) {
						return new Connection() {
							@Override
							public void received(Address address, ByteBuffer buffer) {
								connecting.send(null, buffer, new Nop());
							}
							
							@Override
							public void failed(IOException ioe) {
								lock.fail(ioe);
							}
							@Override
							public void connected(Address address) {
								serverWaitClientConnecting.run();
							}
							@Override
							public void closed() {
								serverWaitClientClosing.run();
							}
						};
					}
				}))));
		
		connecter.connect(new Connection() {
			@Override
			public void failed(IOException ioe) {
				LOGGER.error("Udp server failed", ioe);
				waitForOpen.run();
				waitForClose.run();
			}
			@Override
			public void connected(Address address) {
				LOGGER.debug("Udp server ready");
				waitForOpen.run();
			}
			@Override
			public void closed() {
				LOGGER.debug("Udp server closed");
				waitForOpen.run();
				waitForClose.run();
			}
			
			@Override
			public void received(Address address, ByteBuffer buffer) {
				LOGGER.trace("Received {} bytes from {}", buffer.remaining(), address);
				callback.accept(serializer.deserialize(buffer));
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
	
	public void send(Address address, SignedPowRequest request) {
		if (connecter == null) {
			return;
		}
		connecter.send(address, serializer.serialize(request), new Nop());
	}
}
