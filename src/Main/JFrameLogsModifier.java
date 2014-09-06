package Main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JFrameLogsModifier extends JFrame
{

	public JFrameLogsModifier(Session session)
	{
		super();
		this.session = session;

		init();
		apearrences();
		control();

		populateJCombBox();
		pack();
	}

	private void init()
	{
		this.btnAddTask = new JButton("Add New Task");
		this.btnRemoveTask = new JButton("Remove Task");
		this.jComboBoxTask = new JComboBox<String>();
		this.btnAddPeriod = new JButton("Add New Period");
		this.btnRemovePeriod = new JButton("Remove Period");
		this.jComboBoxTask = new JComboBox<String>();
		this.jComboBoxPeriod = new JComboBox<String>();
		this.vBoxMain = Box.createVerticalBox();
		this.hBoxMenuTask = Box.createHorizontalBox();
		this.hBoxMenuPeriod = Box.createHorizontalBox();

		this.bLayoutMain = new BorderLayout();

		this.jComboBoxPeriod.addItem("periods");
		this.jComboBoxPeriod.addItem("--------");
		this.jComboBoxTask.addItem("Tasks");
		this.jComboBoxTask.addItem("--------");
	}

	private void control()
	{

	}

	private void apearrences()
	{
		setTitle("Modifie Logs");

		JPanel jP = new JPanel();
		GridLayout gL = new GridLayout(2, 3);

		jP.setLayout(gL);

		jP.add(this.jComboBoxTask);
		jP.add(this.btnAddTask);
		jP.add(this.btnRemoveTask);
		jP.add(this.jComboBoxPeriod);
		jP.add(this.btnAddPeriod);
		jP.add(this.btnRemovePeriod);

		this.setLayout(new FlowLayout());
		this.add(jP);

		this.setVisible(true);

	}

	private void populateJCombBox()
	{
		String item = "";

		for (Task t : this.session.getTasks())
		{
			this.jComboBoxTask.addItem(t.getName());

			for (Period p : t.getPeriods())
			{
				item = t.getName() + " | " + p.getStartDate() + " - " + p.getEndDate() + " | " + p.getElapsedTime();
				this.jComboBoxPeriod.addItem(item);
			}
		}

	}

	private JButton btnRemoveTask;
	private JButton btnAddTask;
	private JButton btnRemovePeriod;
	private JButton btnAddPeriod;
	private JComboBox<String> jComboBoxTask;
	private JComboBox<String> jComboBoxPeriod;
	private Box hBoxMenuTask;
	private Box hBoxMenuPeriod;
	private Box vBoxMain;
	private BorderLayout bLayoutMain;
	private Session session;

	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
	{
		Session s = new Session(true);

		s.addTask("task_1", "task_1");
		s.addTask("task_2", "task_2");
		s.addTask("task_3", "task_3");
		s.addTask("task_4", "task_4");

		new JFrameLogsModifier(s);
	}
}
