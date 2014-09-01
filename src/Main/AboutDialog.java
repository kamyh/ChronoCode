package Main;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutDialog extends JDialog {
	public AboutDialog(JFrame parent) {
		super(parent, "About Dialog", true);

		Box b = Box.createVerticalBox();
		b.add(Box.createGlue());
		b.add(Box.createHorizontalStrut(25));
		b.add(new JLabel("Chrono Code"));
		b.add(new JLabel("Powered by:"));
		b.add(new JLabel("Déruaz Vincent"));
		b.add(Box.createGlue());

		getContentPane().add(b, "Center");

		setSize(250, 150);
		this.pack();
	}

	public static void main(String[] args) {
		JDialog f = new AboutDialog(new JFrame());
		f.show();
	}
}