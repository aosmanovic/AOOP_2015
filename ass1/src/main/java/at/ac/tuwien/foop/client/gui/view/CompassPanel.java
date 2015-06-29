package at.ac.tuwien.foop.client.gui.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.foop.client.gui.utils.ImageStore;
import at.ac.tuwien.foop.domain.Wind;

public class CompassPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(CompassPanel.class);

	private Wind wind;
	private ImageStore images;

	public CompassPanel(Wind wind) {
		super();

		log.debug("create compass panel");

		this.wind = wind;
		this.images = ImageStore.getInstance();
		// add(new JLabel(new ImageIcon(images.getCompass())));
		setPreferredSize(new Dimension(images.getCompass().getWidth(), images
				.getCompass().getWidth()));
	}

	public void wind(Wind w) {
		wind = w;
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		g.drawImage(images.getCompass(), 0, 0, null);
		g.drawImage(rotate(images.getCompassArrow(), wind.angle), 0, 0, null);
	}

	private BufferedImage rotate(BufferedImage image, double angle) {
		int w = image.getWidth(), h = image.getHeight();
		GraphicsConfiguration gc = getGraphicsConfiguration();
		BufferedImage result = gc.createCompatibleImage(w, h,
				Transparency.TRANSLUCENT);
		Graphics2D g = result.createGraphics();
		g.rotate(angle, w / 2, h / 2);
		g.drawRenderedImage(image, null);
		g.dispose();
		return result;
	}
}
