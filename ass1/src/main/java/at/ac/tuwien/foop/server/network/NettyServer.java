package at.ac.tuwien.foop.server.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.domain.message.MessageEncoder;
import at.ac.tuwien.foop.server.domain.Game;
import at.ac.tuwien.foop.server.event.ServerReadyListener;

public class NettyServer implements Runnable {
	private static Logger log = LoggerFactory.getLogger(NettyServer.class);

	private final EventLoopGroup dispatcherGroup = new NioEventLoopGroup();
	private final EventLoopGroup workerGroup = new NioEventLoopGroup();

	private int port = 20150;
	private Game game;
	private Channel channel;

	private CopyOnWriteArrayList<ServerReadyListener> connectListeners = new CopyOnWriteArrayList<>();
	private CountDownLatch channelLatch = new CountDownLatch(1);

	public NettyServer(Game game) {
		this.game = game;
	}

	public void run() {
		log.info("run server");

		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(dispatcherGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							// board size up to 126 width ;)
							ch.pipeline().addLast(
									new LineBasedFrameDecoder(16384));
							ch.pipeline().addLast(
									new StringDecoder(CharsetUtil.UTF_8));
							ch.pipeline().addLast(
									new StringEncoder(CharsetUtil.UTF_8));
							ch.pipeline().addLast(new MessageEncoder());
							ch.pipeline().addLast(new GameHandler(game));
						}
					});

			log.debug("waiting for all channels to be closed!?");
			ChannelFuture bind = bootstrap.bind(port);
			bind.addListener(new ChannelFutureListener() {

				@Override
				public void operationComplete(ChannelFuture future)
						throws Exception {
					if (!future.isSuccess()) {
						System.out
								.println("***************no sux!******************");
						connectListeners.forEach(e -> e.onFailure());
					} else {
						channel = future.channel();
						connectListeners.forEach(e -> e.onReady());
					}
					channelLatch.countDown();
				}
			});

			try {
				channelLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (channel != null) {
				channel.closeFuture().sync();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			shutDown();
		}
	}

	public void shutDown() {
		log.debug("shut down server!");
		workerGroup.shutdownGracefully();
		dispatcherGroup.shutdownGracefully();
	}

	public void addConnectListener(ServerReadyListener l) {
		connectListeners.add(l);
	}

	public void removeConnectListener(ServerReadyListener l) {
		connectListeners.remove(l);
	}
}
