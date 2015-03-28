package at.ac.tuwien.foop.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientHandler extends ChannelHandlerAdapter {
	private static Logger log = LoggerFactory.getLogger(ClientHandler.class);

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		log.info("register client");
		ctx.writeAndFlush("ping\n");
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		log.info("unregister client");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		log.info(String.format("received msg: %s", msg != null ? msg.toString() : ""));
	}
}
