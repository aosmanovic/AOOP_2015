package at.ac.tuwien.foop.client.gui.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import at.ac.tuwien.foop.client.gui.utils.FieldImages;
import at.ac.tuwien.foop.domain.Wind;

public class CompassPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Wind wind;
	private FieldImages images;

	public CompassPanel(Wind wind, FieldImages images) {
		super();
		this.wind = wind;
		this.images = images;
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

	// TODO: remove when tested
	public static void main(String args[]) {
		JFrame frame = new JFrame("test");
		CompassPanel panel = new CompassPanel(Wind.fromCoordinates(1.5, 3),
				new FieldImages());
		frame.add(panel);
		frame.setBounds(10, 10, 300, 300);
		frame.setVisible(true);
	}
}
