package Main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
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

		this.jComboBoxPeriod.addItem("periods");
		this.jComboBoxPeriod.addItem("--------");
		this.jComboBoxTask.addItem("Tasks");
		this.jComboBoxTask.addItem("--------");
	}

	private void resetJComboBox()
	{

		jComboBoxPeriod.setModel(new DefaultComboBoxModel());
		jComboBoxTask.setModel(new DefaultComboBoxModel());

		this.jComboBoxPeriod.addItem("periods");
		this.jComboBoxPeriod.addItem("--------");
		this.jComboBoxTask.addItem("Tasks");
		this.jComboBoxTask.addItem("--------");

		this.populateJCombBox();
	}

	private void control()
	{
		this.btnAddTask.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub

			}
		});

		this.btnRemoveTask.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				String taskNameSelected = (String) jComboBoxTask.getSelectedItem();

				session.getParent().getLineInterfaceItem().remove(taskNameSelected);

				session.removeTask(taskNameSelected);

				resetJComboBox();

				session.getParent().resetItems();
				session.getParent().pack();
			}
		});

		this.btnAddPeriod.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				new InputPeriod(JFrameLogsModifier.this.session);
			}
		});

		this.btnRemovePeriod.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				String selectedItemTask = (String) jComboBoxPeriod.getSelectedItem();

				if (jComboBoxPeriod.getSelectedIndex() != 0 && jComboBoxPeriod.getSelectedIndex() != 1)
				{
					String taskName = selectedItemTask.split(" | ")[0];

					System.out.println(taskName);

					String start = selectedItemTask.split(" | ")[2] + " " + selectedItemTask.split(" | ")[3] + " " + selectedItemTask.split(" | ")[4] + " " + selectedItemTask.split(" | ")[5] + " "
							+ selectedItemTask.split(" | ")[6] + " " + selectedItemTask.split(" | ")[7];

					System.out.println(start);

					String end = selectedItemTask.split(" | ")[9] + " " + selectedItemTask.split(" | ")[10] + " " + selectedItemTask.split(" | ")[11] + " " + selectedItemTask.split(" | ")[12] + " "
							+ selectedItemTask.split(" | ")[13] + " " + selectedItemTask.split(" | ")[14];

					System.out.println(end);

					Task t = session.getTask(taskName);

					t.removePeriodFromTo(start, end);

					resetJComboBox();
				}
			}
		});
	}

	private void apearrences()
	{
		setTitle("Modifie Logs");

		JPanel jP = new JPanel();
		GridLayout gL = new GridLayout(2, 3);

		JPanel gPJComboBox = new JPanel(new GridLayout(2, 1));
		JPanel gPButton = new JPanel(new GridLayout(2, 2));
		JPanel gPAll = new JPanel(new GridLayout(1, 2));

		gPAll.add(gPJComboBox);
		gPAll.add(gPButton);

		jP.setLayout(gL);

		gPJComboBox.add(this.jComboBoxTask);
		gPButton.add(this.btnRemoveTask);
		gPButton.add(this.btnAddTask);
		gPJComboBox.add(this.jComboBoxPeriod);
		gPButton.add(this.btnRemovePeriod);
		gPButton.add(this.btnAddPeriod);

		this.setLayout(new FlowLayout());
		this.add(gPAll);

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
	private Session session;

	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
	{
		Session s = new Session(true, new Manager());

		s.addTask("task_1", "task_1");
		s.addTask("task_2", "task_2");
		s.addTask("task_3", "task_3");
		s.addTask("task_4", "task_4");

		new JFrameLogsModifier(s);
	}
}
