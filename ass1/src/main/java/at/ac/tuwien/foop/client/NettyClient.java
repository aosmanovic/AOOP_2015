package at.ac.tuwien.foop.client;

import io.netty.bootstrap.Bootstrap;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.model.Game;
import at.ac.tuwien.foop.message.MessageEncoder;

public class NettyClient implements Runnable {
	private static Logger log = LoggerFactory.getLogger(NettyClient.class);

	private String host = "localhost";
	private int port = 20150;
	private Game game;
	
	public NettyClient(Game game) {
		this.game = game;
	}

	public void run() {
		log.info("start client");
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(workerGroup).channel(NioSocketChannel.class)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch)
							throws Exception {
						ch.pipeline().addLast(
								new LineBasedFrameDecoder(256));
						ch.pipeline().addLast(
								new StringDecoder(CharsetUtil.UTF_8));
						ch.pipeline().addLast(
								new StringEncoder(CharsetUtil.UTF_8));
						ch.pipeline().addLast(new MessageEncoder());
						ch.pipeline().addLast(new ClientHandler(game));
					}
				});
		try {
			bootstrap.connect(host, port).sync().channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}
