package at.ac.tuwien.foop.client.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.domain.Game;
import at.ac.tuwien.foop.client.events.ConnectListener;
import at.ac.tuwien.foop.domain.message.MessageEncoder;

public class NettyClient implements Runnable {
	private static Logger log = LoggerFactory.getLogger(NettyClient.class);

	private final int MAX_RETRY = 5;
	private final int RETRY_TIME_MS = 4000;
	private final EventLoopGroup workerGroup = new NioEventLoopGroup();

	private CountDownLatch latch;
	private String host;
	private int port;

	private int retry = 0;
	private Channel channel;
	private CopyOnWriteArrayList<ConnectListener> connectListeners = new CopyOnWriteArrayList<>();

	private ClientHandler clientHandler;

	private Game game;

	public NettyClient(Game game, String host, int port) {
		this.host = host;
		this.port = port;
		this.game = game;
	}

	public void run() {
		log.info("start client");

		while (retry < MAX_RETRY) {
			latch = new CountDownLatch(1);
			Bootstrap bootstrap = new Bootstrap();
			clientHandler = new ClientHandler(game);
			bootstrap.group(workerGroup).channel(NioSocketChannel.class)
					.option(ChannelOption.SO_KEEPALIVE, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast(
									new LineBasedFrameDecoder(16384));
							ch.pipeline().addLast(
									new StringDecoder(CharsetUtil.UTF_8));
							ch.pipeline().addLast(
									new StringEncoder(CharsetUtil.UTF_8));
							ch.pipeline().addLast(new MessageEncoder());
							ch.pipeline().addLast(clientHandler);
						}
					});
			log.debug("try to connect to server");
			connect(bootstrap);

			// wait till connection process ends
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (channel != null) {
				try {
					log.debug("connected, and waiting for close signal...");
					clientHandler.addChannelActiveListener(() -> connectListeners.forEach(e -> e.onConnect(this)));
					channel.closeFuture().sync();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				log.debug("client channel closed!");
				break;
			}

			retry++;
			log.warn("could not connect, will retry ({}/{})!", retry, MAX_RETRY);

			try {
				Thread.sleep(RETRY_TIME_MS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if (retry == MAX_RETRY) {
			log.warn("could not connect to server, give up :(!");
			connectListeners.forEach(e -> e.onConnecitonFailure());
		}

		workerGroup.shutdownGracefully();
		log.info("client down");
	}

	private void connect(Bootstrap bootstrap) {
		log.debug("connect to {}:{}", host, port);
		ChannelFuture future = bootstrap.connect(host, port);
		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future)
					throws Exception {
				log.info("connection operation completed...");
				if (future.isSuccess()) {
					channel = future.channel();
				}
				latch.countDown();
			}
		});
	}
	
	public void disconnect() {
		if (channel == null) {
			return;
		}
		channel.close();
	}

	public void addConnectListener(ConnectListener l) {
		connectListeners.add(l);
	}

	public void removeConnectListener(ConnectListener l) {
		connectListeners.remove(l);
	}

	public ClientHandler getClientHandler() {
		return clientHandler;
	}
}
