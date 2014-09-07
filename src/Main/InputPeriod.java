package Main;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
		this.jTxtStart = new JTextField("- Sun Sep 07 14:33:08 CEST 2014 -");
		this.jTxtEnd = new JTextField("- Sun Sep 07 14:53:08 CEST 2014 -");
		this.jTxtElapsedTime = new JTextField("Elapsed Time");

		populateJComboBox();
	}

	private void control()
	{
		this.btnAdd.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				String sDateStart = InputPeriod.this.jTxtStart.getText();
				String sDateEnd = InputPeriod.this.jTxtStart.getText();
				String taskSelected = (String) InputPeriod.this.jComboxBoxTasks.getSelectedItem();
				int elapsedTime = Integer.parseInt(InputPeriod.this.jTxtElapsedTime.getText());

				if (isValideInput(sDateStart) && isValideInput(sDateEnd))
				{
					System.out.println(stringToDate("Sun Sep 07 14:33:08 CEST 2014").getTime());

					Task task = InputPeriod.this.parent.getTask(taskSelected);
					task.addPeriod(stringToDate(sDateStart), stringToDate(sDateEnd), elapsedTime);

				}
				InputPeriod.this.setVisible(false);
			}
		});
	}

	private Calendar stringToDate(String s)
	{
		String time = s.split(" ")[3];
		String monthString = s.split(" ")[1];
		int month = 0;

		switch (monthString)
		{
		case "Jan":
			month = 1;
			break;
		case "Fev":
			month = 2;
			break;
		case "Mar":
			month = 3;
			break;
		case "Avr":
			month = 4;
			break;
		case "Mai":
			month = 5;
			break;
		case "Jun":
			month = 6;
			break;
		case "Jul":
			month = 7;
			break;
		case "Aug":
			month = 8;
			break;
		case "Sep":
			month = 9;
			break;
		case "Oct":
			month = 10;
			break;
		case "Nov":
			month = 11;
			break;
		case "Dec":
			month = 12;
			break;

		default:
			break;
		}

		int year = Integer.parseInt(s.split(" ")[5]);
		int day = Integer.parseInt(s.split(" ")[2]);
		int hours = Integer.parseInt(time.split(":")[0]);
		int minutes = Integer.parseInt(time.split(":")[1]);
		int seconds = Integer.parseInt(time.split(":")[2]);

		Calendar d = Calendar.getInstance();

		d.set(year, month - 1, day, hours, minutes, seconds);

		return d;
	}

	private void populateJComboBox()
	{
		for (Task t : this.parent.getTasks())
		{
			this.jComboxBoxTasks.addItem(t.getName());
		}
	}

	private void apearence()
	{
		JPanel jP = new JPanel(new FlowLayout());
		this.add(jP);

		// TODO put name in top of the txt field
		// this.jPanelTxtEdit.add(new JLabel("Start Date: "));
		this.jPanelTxtEdit.add(this.jTxtStart);
		// this.jPanelTxtEdit.add(new JLabel("End Date: "));
		this.jPanelTxtEdit.add(this.jTxtEnd);
		this.jPanelTxtEdit.add(this.jTxtElapsedTime);

		jP.add(this.jComboxBoxTasks);
		jP.add(this.jPanelTxtEdit);

		jP.add(this.btnAdd);
	}

	private boolean isValideInput(String s)
	{
		return Regex.isDateAndTime(s);
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
