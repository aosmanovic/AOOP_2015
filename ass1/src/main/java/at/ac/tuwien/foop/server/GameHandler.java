package at.ac.tuwien.foop.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameHandler extends ChannelHandlerAdapter {
	private static Logger log = LoggerFactory.getLogger(GameHandler.class);

	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		String str = (String) msg;
		log.info(String.format("server received msg: %s", str));

		if (str.equals("ping")) {
			ctx.writeAndFlush("pong\n");
		}
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		log.info("new client connected");
	}
}
