package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.PointerByReference;

//TODO debug activation blcklist

public class Manager extends JFrame implements Serializable {

	public Manager() {
		super("Chrono Code");
		apperence();
		control();

		this.isStart = false;
		init();
		start();
		this.pack();
	}

	private void control() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// menuBar
		this.menuBar = new JMenuBar();
		this.setJMenuBar(this.menuBar);
		// Define and add two drop down menu to the menubar
		JMenu fileMenu = new JMenu("File");
		JMenu actionMenu = new JMenu("Actions");
		JMenu OptionsMenu = new JMenu("Options");
		menuBar.add(fileMenu);
		menuBar.add(actionMenu);
		menuBar.add(OptionsMenu);
		JMenuItem newAction = new JMenuItem("New");
		JMenuItem openAction = new JMenuItem("Open");
		JMenuItem saveAction = new JMenuItem("Save");
		JMenuItem startPauseAction = new JMenuItem("Start/Pause");
		JMenuItem toTXTAction = new JMenuItem("logToTxt");
		JMenuItem toTXTDetailsAction = new JMenuItem("logToTxt details");
		JMenuItem stopAction = new JMenuItem("stop");
		BlacklistMenuChkBox = new JCheckBoxMenuItem("Blacklist");
		BlacklistMenuChkBox.setSelected(true);
		fileMenu.add(newAction);
		fileMenu.add(openAction);
		fileMenu.add(saveAction);
		actionMenu.add(startPauseAction);
		actionMenu.add(toTXTAction);
		actionMenu.add(toTXTDetailsAction);
		actionMenu.add(stopAction);
		OptionsMenu.add(BlacklistMenuChkBox);

		// TODO attributs action to menus
		newAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("You have clicked on the new action");
			}
		});

		BlacklistMenuChkBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				session.resetProcessList(BlacklistMenuChkBox.isSelected());
				jComboBoxListProcess.removeAll();
				jComboBoxListProcess = new JComboBox<>();

				populateJComboBoxListProcess();
				jComboBoxListProcess.revalidate();

			}
		});

		stopAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO PROPERLY STOP

			}
		});

		toTXTDetailsAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				printLogsToTxtDetails();

			}
		});

		toTXTAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				printLogsToTxt();

			}
		});

		startPauseAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isStart && currentTask != null) {
					currentTask.getLastPeriod().setEndDate();
				}
				isStart = !isStart;

			}
		});

		saveAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");
				fileChooser.setCurrentDirectory(new File("./"));
				fileChooser.setSelectedFile(new File("save.chco"));
				fileChooser.setFileFilter(new FileNameExtensionFilter(
						"ChronoCode file", "chco"));

				int userSelection = fileChooser.showSaveDialog(Manager.this);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File fileToSave = fileChooser.getSelectedFile();
					save(fileToSave.getAbsolutePath());
				}

			}
		});

		openAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("./"));
				chooser.setSelectedFile(new File("save.chco"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"ChronoCode file", "chco");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(Manager.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					load(chooser.getSelectedFile().getName());
				}

			}
		});

		this.btnAddTask.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String baseName = (String) jComboBoxListProcess
						.getSelectedItem();

				if (!session.isExsitingTask(baseName)) {
					addNewLineEntry(baseName);
					session.addTask(baseName, baseName);
				}
			}
		});

	}

	private String getPathFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");
		fileChooser.setCurrentDirectory(new File("./"));
		fileChooser.setSelectedFile(new File("logs.txt"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("Fichier txt",
				"txt"));

		int userSelection = fileChooser.showSaveDialog(this);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			System.out.println("Save as file: " + fileToSave.getAbsolutePath());
			return fileToSave.getAbsolutePath();
		}
		return null;
	}

	private void printLogsToTxt() {

		Writer w = new Writer(getPathFile());

		ArrayList<Task> tasks = this.session.getTasks();

		int totFullTime = 0;

		for (int i = 0; i < tasks.size(); i++) {
			int totTime = 0;
			w.write("--" + tasks.get(i).getBaseName() + "--");
			ArrayList<Period> periods = tasks.get(i).getPeriods();
			for (int j = 0; j < periods.size(); j++) {
				String lineToAdd = periods.get(j).getStartDate().toString()
						+ " - " + periods.get(j).getEndDate().toString()
						+ " | " + periods.get(j).getElapsedTime() / 1000
						+ " sec";
				w.write(lineToAdd);
				totTime += periods.get(j).getElapsedTime() / 1000;

			}
			w.write("Total Elapsed Time " + totTime + " sec");
			totFullTime += totTime;
		}
		w.write("----------------------");
		w.write("Total Elapsed Time " + totFullTime + " sec");
	}

	private void printLogsToTxtDetails() {
		Writer w = new Writer(getPathFile());

		ArrayList<Task> tasks = this.session.getTasks();

		int totFullTime = 0;

		for (int i = 0; i < tasks.size(); i++) {
			int totTime = 0;
			w.write("--" + tasks.get(i).getBaseName() + "--");
			ArrayList<Period> periods = tasks.get(i).getPeriods();
			for (int j = 0; j < periods.size(); j++) {

				totTime += periods.get(j).getElapsedTime() / 1000;

			}
			w.write("Total Elapsed Time " + totTime + " sec");
			totFullTime += totTime;
		}
		w.write("----------------------");
		w.write("Total Elapsed Time " + totFullTime + " sec");
	}

	private void apperence() {

		this.mainBorderLayout = new BorderLayout();

		this.mainJPanel = new JPanel(this.mainBorderLayout);

		this.add(this.mainJPanel);

		this.jComboBoxListProcess = new JComboBox();
		this.mainJPanel.add(this.jComboBoxListProcess, "North");

		// this.mainJPanel.add(Box.createHorizontalGlue(), "North");
		this.bVBoxNorth = Box.createHorizontalBox();
		this.mainJPanel.add(this.bVBoxNorth, "North");

		this.btnAddTask = new JButton("Add");

		this.bVBoxNorth.add(this.jComboBoxListProcess);
		this.bVBoxNorth.add(this.btnAddTask);
		this.bVBoxNorth.add(Box.createHorizontalGlue());
		this.bVBoxNorth.add(Box.createHorizontalGlue());
		this.bVBoxNorth.add(Box.createHorizontalGlue());

		this.comboBoxWatchingProcess = new JComboBox();

		this.bVBoxCenter = Box.createVerticalBox();

		this.mainJPanel.add(this.bVBoxCenter);

		this.setVisible(true);
		// this.setSize(800, 600);
		this.pack();

	}

	public void addNewLineEntry(String name1) {

		final Box newLine = Box.createHorizontalBox();

		final String name = name1;

		Label lName = new Label("Name: " + name1);
		Label lElapsedTime = new Label("Elapsed Time: " + 0);
		JButton btnRemoveLine = new JButton("Remove");

		btnRemoveLine.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newLine.hide();
				session.removeTask(name);
				;
				pack();

			}
		});

		newLine.add(lName);
		// newLine.add(lElapsedTime);
		newLine.add(Box.createHorizontalGlue());

		newLine.add(btnRemoveLine);

		this.bVBoxCenter.add(newLine);
		this.pack();
	}

	private void populateJComboBoxListProcess() {
		for (int i = 0; i < this.session.getAllProccess().size(); i++) {
			this.jComboBoxListProcess.addItem(this.session.getAllProccess()
					.get(i));
		}
	}

	public void start() {
		threadCheckFocus = new Thread(new Runnable() {

			@Override
			public void run() {
				Task prevTask = null;
				while (true) {
					if (isStart) {
						char[] buffer = new char[MAX_TITLE_LENGTH * 2];
						User32DLL.GetWindowTextW(
								User32DLL.GetForegroundWindow(), buffer,
								MAX_TITLE_LENGTH);
						// System.out.println("Active window title: "
						// + Native.toString(buffer));

						PointerByReference pointer = new PointerByReference();
						User32DLL.GetWindowThreadProcessId(
								User32DLL.GetForegroundWindow(), pointer);
						Pointer process = Kernel32.OpenProcess(
								Kernel32.PROCESS_QUERY_INFORMATION
										| Kernel32.PROCESS_VM_READ, false,
								pointer.getValue());
						Psapi.GetModuleBaseNameW(process, null, buffer,
								MAX_TITLE_LENGTH);

						// TODO if no task
						if (session.isExsitingTask(Native.toString(buffer))) {
							currentTask = session.getTask(Native
									.toString(buffer));

							if (prevTask != null) {
								if (currentTask != prevTask) {
									prevTask.getLastPeriod().setEndDate();
									if (currentTask.getPeriods().size() > 0) {
										currentTask.newEntry();
									}
								}
							}

							System.out.println("Numbers of periods: "
									+ currentTask.getPeriods().size());

							System.out.println("baseName: "
									+ currentTask.getBaseName()
									+ " elapsedTime: "
									+ currentTask
											.getPeriods()
											.get(currentTask.getPeriods()
													.size() - 1)
											.getElapsedTime() / 1000);

							if (currentTask != null) {

								currentTask
										.getPeriods()
										.get(currentTask.getPeriods().size() - 1)
										.addTime(refreshTime);
							}
							prevTask = currentTask;
						} else {
							System.out.println("No task !");
						}
						try {
							Thread.sleep(refreshTime);
						} catch (InterruptedException e) {
							System.err
									.println("Impossible to sleep. too many noises !");
							e.printStackTrace();
						}
					}
				}
			}
		});

		this.threadCheckFocus.start();

	}

	private void save(String path) {
		try {
			// Serialize data object to a file
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(path));
			out.writeObject(this.session);
			out.close();

		} catch (IOException e) {
		}
	}

	private void load(String path) {

		this.session = new Session(this.BlacklistMenuChkBox.isSelected());
		this.bVBoxCenter.removeAll();

		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fis);
			this.session = (Session) in.readObject();

			for (int i = 0; i < this.session.getTasks().size(); i++) {
				System.out
						.println(this.session.getTasks().get(i).getBaseName());

				if (this.session.isExsitingTask(this.session.getTasks().get(i)
						.getBaseName())) {
					this.addNewLineEntry(this.session.getTasks().get(i)
							.getBaseName());
				}
			}

			in.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		isStart = false;
	}

	private void init() {
		this.session = new Session(this.BlacklistMenuChkBox.isSelected());
		populateJComboBoxListProcess();
	}

	public static void main(String[] args) throws Exception {
		Manager m = new Manager();

	}

	private static final int MAX_TITLE_LENGTH = 1024;
	private int refreshTime = 1000;
	private volatile boolean isStart;
	private Session session;
	private Thread threadCheckFocus;
	JMenuBar menuBar;
	private BorderLayout mainBorderLayout;
	private JPanel mainJPanel;
	private JComboBox jComboBoxListProcess;
	private Box bVBoxNorth;
	private JButton btnAddTask;
	private Box bVBoxCenter;
	private JComboBox comboBoxWatchingProcess;
	private JButton btnRemove;
	private Task currentTask = null;
	private JCheckBoxMenuItem BlacklistMenuChkBox;

	static class Psapi {
		static {
			Native.register("psapi");
		}

		public static native int GetModuleBaseNameW(Pointer hProcess,
				Pointer hmodule, char[] lpBaseName, int size);
	}

	static class Kernel32 {
		static {
			Native.register("kernel32");
		}
		public static int PROCESS_QUERY_INFORMATION = 0x0400;
		public static int PROCESS_VM_READ = 0x0010;

		public static native int GetLastError();

		public static native Pointer OpenProcess(int dwDesiredAccess,
				boolean bInheritHandle, Pointer pointer);
	}

	static class User32DLL {
		static {
			Native.register("user32");
		}

		public static native int GetWindowThreadProcessId(HWND hWnd,
				PointerByReference pref);

		public static native HWND GetForegroundWindow();

		public static native int GetWindowTextW(HWND hWnd, char[] lpString,
				int nMaxCount);
	}
}