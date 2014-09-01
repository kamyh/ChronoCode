package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.PointerByReference;

//TODO config.txt to blacklist some apps

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
		menuBar.add(fileMenu);
		menuBar.add(actionMenu);
		JMenuItem newAction = new JMenuItem("New");
		JMenuItem openAction = new JMenuItem("Open");
		JMenuItem saveAction = new JMenuItem("Save");
		JMenuItem startPauseAction = new JMenuItem("Start/Pause");
		JMenuItem toTXTAction = new JMenuItem("logToTxt");
		fileMenu.add(newAction);
		fileMenu.add(openAction);
		fileMenu.add(saveAction);
		actionMenu.add(startPauseAction);
		actionMenu.add(toTXTAction);

		// TODO attributs action to menus
		newAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("You have clicked on the new action");
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

				isStart = !isStart;
			}
		});

		saveAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				save("./save.chco");
			}
		});

		openAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				load("./save.chco");
			}
		});

		this.btnAddTask.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String baseName = (String) jComboBoxListProcess
						.getSelectedItem();
				addNewLineEntry(baseName);
				session.addTask(baseName, baseName);

			}
		});

	}

	private void printLogsToTxt() {
		Writer w = new Writer("./test.txt");

		ArrayList<Task> tasks = this.session.getTasks();

		for (int i = 0; i < tasks.size(); i++) {
			w.write("--" + tasks.get(i).getBaseName() + "--");
			ArrayList<Period> periods = tasks.get(i).getPeriods();
			for (int j = 0; j < periods.size(); j++) {
				String lineToAdd = periods.get(j).getStartDate().toString()
						+ " - " + periods.get(j).getEndDate().toString()
						+ " | " + periods.get(j).getElapsedTime() / 1000;
				w.write(lineToAdd);
			}
		}
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

	public void addNewLineEntry(String name) {

		final Box newLine = Box.createHorizontalBox();

		Label lName = new Label("Name: " + name);
		Label lElapsedTime = new Label("Elapsed Time: " + 0);
		JButton btnRemoveLine = new JButton("Remove");

		btnRemoveLine.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newLine.hide();
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
		// TODO put in a thread

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

						Task currentTask;

						// TODO if no task
						if (session.isExsitingTask(Native.toString(buffer))) {
							currentTask = session.getTask(Native
									.toString(buffer));

							if (prevTask != null) {
								if (currentTask != prevTask) {
									prevTask.getLastPeriod().setEndDate();
									currentTask.newEntry();
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
		// TODO
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fis);
			this.session = (Session) in.readObject();
			in.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void listAllAppRunning() {
		// TODO
	}

	private void setWhiteList() {
		// TODO
	}

	private void init() {
		this.session = new Session();
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