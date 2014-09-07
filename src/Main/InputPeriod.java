package Main;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

// TODO to finish

public class InputPeriod extends JFrame implements Serializable
{

	public InputPeriod(Session parent)
	{
		this.parent = parent;

		init();
		apearence();
		control();

		this.setVisible(true);
		this.pack();
	}

	private void init()
	{
		this.btnAdd = new JButton("Valider");
		this.jComboxBoxTasks = new JComboBox<String>();

		this.jPanelTxtEdit = new JPanel(new GridLayout(1, 2));
		this.jTxtStart = new JTextField("Start date");
		this.jTxtEnd = new JTextField("End date");
		this.jTxtElapsedTime = new JTextField("Elapsed Time");

		populateJComboBox();
	}

	private void control()
	{

	}

	private void populateJComboBox()
	{
		String item = "";

		for (Task t : this.parent.getTasks())
		{
			this.jComboxBoxTasks.addItem(t.getName());
		}
	}

	private void apearence()
	{
		JPanel jP = new JPanel(new FlowLayout());
		this.add(jP);

		this.jPanelTxtEdit.add(this.jTxtStart);
		this.jPanelTxtEdit.add(this.jTxtEnd);
		this.jPanelTxtEdit.add(this.jTxtElapsedTime);

		jP.add(this.jComboxBoxTasks);
		jP.add(this.jPanelTxtEdit);

		jP.add(this.btnAdd);

	}

	private JButton btnAdd;
	private JComboBox<String> jComboxBoxTasks;
	private JTextField jTxtStart;
	private JTextField jTxtEnd;
	private JTextField jTxtElapsedTime;
	private JPanel jPanelTxtEdit;
	private Session parent;

	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
	{
		Session s = new Session(true, new Manager());

		s.addTask("task_1", "task_1");
		s.addTask("task_2", "task_2");
		s.addTask("task_3", "task_3");
		s.addTask("task_4", "task_4");

		new InputPeriod(s);
	}

}
