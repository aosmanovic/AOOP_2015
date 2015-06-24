package at.ac.tuwien.foop.client.gui.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

public class FontStore {
	private static FontStore instance;

	private Font gameFont;

	private FontStore() {
		gameFont = loadFont("puppy.otf");
	}

	private Font loadFont(String path) {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, loadStream(path));
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()[0];
	}

	private InputStream loadStream(String path) {
		return Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(path);
	}

	public Font getGameFont() {
		return gameFont;
	}

	public static FontStore getInstance() {
		if (instance == null) {
			return new FontStore();
		}
		return instance;
	}
}
