package at.ac.tuwien.foop.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import at.ac.tuwien.foop.message.Message;
import at.ac.tuwien.foop.message.Message.Type;

public class ClientHandler extends ChannelHandlerAdapter {
	private static Logger log = LoggerFactory.getLogger(ClientHandler.class);

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("send ping");
		ctx.writeAndFlush(new Message(Type.ping));
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		String str = (String) msg;
		log.info(String.format("client received msg: %s", str));

		Message m = mapper.readValue(str, Message.class);

		if (m.type == Type.pong) {
			log.info("yay, got a pong!");
			// TODO: should be triggered by the UI
			ctx.channel().close();
		} else {
			log.warn("unknown message");
		}
	}
}
