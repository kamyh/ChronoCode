package Main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AboutDialog(JFrame parent) {
		super(parent, "About Dialog", true);

		JButton btnClose = new JButton("Close");

		Box b = Box.createVerticalBox();
		b.add(Box.createVerticalStrut(25));
		b.add(Box.createGlue());
		b.add(Box.createHorizontalStrut(25));
		b.add(new JLabel("Chrono Code"));
		b.add(new JLabel("Powered by:"));
		b.add(new JLabel("Déruaz Vincent"));
		b.add(Box.createVerticalStrut(25));
		b.add(btnClose);
		b.add(Box.createVerticalStrut(25));
		b.add(Box.createGlue());

		getContentPane().add(b, "Center");

		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AboutDialog.this.dispatchEvent(new WindowEvent(
						AboutDialog.this, WindowEvent.WINDOW_CLOSING));
				;

			}
		});

		setSize(250, 150);
		softInMiddle(this);
		this.pack();
	}

	private void softInMiddle(AboutDialog aboutDialog) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		aboutDialog.setLocation(
				dim.width / 2 - aboutDialog.getSize().width / 2, dim.height / 2
						- aboutDialog.getSize().height / 2);
	}

	public static void main(String[] args) {
		JDialog f = new AboutDialog(new JFrame());
		f.setVisible(true);
	}
}