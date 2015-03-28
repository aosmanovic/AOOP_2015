package at.ac.tuwien.foop.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class GameHandler extends MessageToMessageDecoder<String> {
	private static Logger log = LoggerFactory.getLogger(GameHandler.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, String msg,
			List<Object> out) throws Exception {
		log.info("msg was :" + msg);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		log.info("new client connected");
	}
}
