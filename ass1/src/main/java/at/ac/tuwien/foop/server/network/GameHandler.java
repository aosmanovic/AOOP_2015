package at.ac.tuwien.foop.server.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.domain.Player;
import at.ac.tuwien.foop.domain.message.Message;
import at.ac.tuwien.foop.domain.message.Message.Type;
import at.ac.tuwien.foop.domain.message.client.JoinMessage;
import at.ac.tuwien.foop.domain.message.client.WindMessage;
import at.ac.tuwien.foop.domain.message.server.BoardMessage;
import at.ac.tuwien.foop.domain.message.server.GameOverMessage;
import at.ac.tuwien.foop.domain.message.server.JoinedMessage;
import at.ac.tuwien.foop.domain.message.server.NewPlayerMessage;
import at.ac.tuwien.foop.domain.message.server.RemovePlayerMessage;
import at.ac.tuwien.foop.domain.message.server.UnknownMessage;
import at.ac.tuwien.foop.domain.message.server.UpdateMessage;
import at.ac.tuwien.foop.server.domain.BoardString;
import at.ac.tuwien.foop.server.domain.Game;
import at.ac.tuwien.foop.server.event.GameEvent;
import at.ac.tuwien.foop.server.event.GameEventListener;
import at.ac.tuwien.foop.server.event.GameOverEvent;
import at.ac.tuwien.foop.server.event.NewPlayerEvent;
import at.ac.tuwien.foop.server.event.RemovePlayerEvent;
import at.ac.tuwien.foop.server.service.GameLogicService;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GameHandler extends ChannelHandlerAdapter implements
		GameEventListener {
	private static Logger log = LoggerFactory.getLogger(GameHandler.class);

	private ObjectMapper mapper = new ObjectMapper();
	private Game game;
	private Player player;
	private Channel channel;

	public GameHandler(Game game) {
		this.game = game;
		game.addGameEventListener(this);
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		String str = (String) msg;
		log.info(String.format("server received msg: %s", str));

		Message m = mapper.readValue(str, Message.class);

		if (m.type == Type.C_PING) {
			ctx.writeAndFlush(new Message(Type.S_PONG));
		} else if (m.type == Type.C_JOIN) {
			JoinMessage jm = mapper.readValue(str, JoinMessage.class);
			player = game.join(jm.name);
			if (player != null) {
				BoardString b = game.boardString();
				ctx.writeAndFlush(new BoardMessage(UUID.randomUUID(), b.board,
						b.width));
				ctx.writeAndFlush(new JoinedMessage(game.getPlayers()));
			} else {
				ctx.writeAndFlush(new Message(Type.S_ALREADY_FULL));
			}
		} else if (m.type == Type.C_LEAVE) {
			game.leave(player);
		} else if (m.type == Type.C_WIND) {
			game.sendGust(mapper.readValue(str, WindMessage.class).wind);
		} else if (m.type == Type.C_START) {
			game.start();
		} else if (m.type == Type.C_NEWLEVEL) {
			Game.setLevelCounter(Game.getLevelCounter() + 1);
			// TODO not accourding to domain model - just for testing purpose,
			// server should create a new game
			game.setBoard(new GameLogicService().loadBoard(GameLogicService
					.getBOARD_PATH()));

			BoardString b = game.boardString();
			ctx.writeAndFlush(new BoardMessage(UUID.randomUUID(), b.board,
					b.width));
			// TODO MOVEMENT FOR THIS NEW LEVEL
		} else {
			log.warn("unknown message");
			ctx.writeAndFlush(new UnknownMessage(m.type.toString()));
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		channel = ctx.channel();
		log.info("new client connected");
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
		ctx.channel().close();
	}

	@Override
	public void onUpdate(GameEvent e) {
		if (e.type == GameEvent.Type.START) {
			channel.writeAndFlush(new Message(Type.S_START));
		} else if (e.type == GameEvent.Type.UPDATE) {
			channel.writeAndFlush(new UpdateMessage(game.getPlayers(), game
					.wind()));
		}
	}

	@Override
	public void onUpdate(NewPlayerEvent e) {
		channel.writeAndFlush(new NewPlayerMessage(e.player));
	}

	@Override
	public void onUpdate(RemovePlayerEvent e) {
		channel.writeAndFlush(new RemovePlayerMessage(e.player));
	}

	@Override
	public void onUpdate(GameOverEvent e) {
		// TODO Auto-generated method stub
		channel.writeAndFlush(new GameOverMessage(e.player));
		BoardString b = game.boardString();
		channel.writeAndFlush(new BoardMessage(UUID.randomUUID(), b.board,
				b.width));
		// TODO: check why joined
		// channel.writeAndFlush(new Message(Type.S_JOINED));

	}

}
