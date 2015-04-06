package at.ac.tuwien.foop.server;

import io.netty.bootstrap.ServerBootstrap;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.message.MessageEncoder;
import at.ac.tuwien.foop.server.domain.Game;

public class NettyServer implements Runnable {
	private static Logger log = LoggerFactory.getLogger(NettyServer.class);

	private int port = 20150;

	public void run() {
		log.info("run server");
		final Game game = new Game();

		EventLoopGroup dispatcherGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
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
							ch.pipeline().addLast(
									new LineBasedFrameDecoder(2048));
							ch.pipeline().addLast(
									new StringDecoder(CharsetUtil.UTF_8));
							ch.pipeline().addLast(
									new StringEncoder(CharsetUtil.UTF_8));
							ch.pipeline().addLast(new MessageEncoder());
							ch.pipeline().addLast(new GameHandler(game));
						}
					});

			bootstrap.bind(port).sync().channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
			dispatcherGroup.shutdownGracefully();
		}
	}
}
