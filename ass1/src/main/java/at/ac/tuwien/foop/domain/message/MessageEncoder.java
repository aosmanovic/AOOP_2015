package at.ac.tuwien.foop.domain.message;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageEncoder extends MessageToMessageEncoder<Object> {
	private ObjectMapper mapper = new ObjectMapper(); // TODO: inject

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg,
			List<Object> out) throws Exception {
		out.add(mapper.writeValueAsString(msg) + "\n");
	}
}
