package Main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

public class Manager extends JFrame {

	public Manager() {
		super("Chrono Code");
		apperence();
		control();

		this.isStart = true;
		init();
		start();
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
		JMenuItem exitAction = new JMenuItem("Exit");
		JMenuItem startPauseAction = new JMenuItem("Start/Pause");
		fileMenu.add(newAction);
		fileMenu.add(openAction);
		fileMenu.add(exitAction);
		actionMenu.add(startPauseAction);

		// TODO attributs action to menus
		newAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("You have clicked on the new action");
			}
		});

		startPauseAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				isStart = !isStart;
				System.out.println("isStart: " + isStart);
			}
		});

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

		this.setVisible(true);
		this.setSize(800, 600);

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

				while (true) {
					System.out.println("isStart From Thread:" + isStart);
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

						// for (int i = 0; i < session.getTasks().size(); i++) {
						//
						// if (session.getTasks().get(i).getBaseName()
						// .equals(Native.toString(buffer))) {
						// session.getTasks()
						// .get(i)
						// .setElapsedTime(
						// session.getTasks().get(i)
						// .getElapsedTime()
						// + refreshTime);
						// System.out.println(session.getTasks().get(i)
						// .getElapsedTime() / 1000);
						// }
						// }

						Task currentTask;
						currentTask = session.getTask(Native.toString(buffer));
						
						System.out.println("baseName: "+currentTask.getBaseName()+" elapsedTime: "+currentTask.getPeriods().get(currentTask.getPeriods().size() - 1).getElapsedTime()/1000);

						if (currentTask != null) {
							currentTask.getPeriods()
									.get(currentTask.getPeriods().size() - 1)
									.addTime(refreshTime);
						}
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
		});

		this.threadCheckFocus.start();

	}

	private void save(String path) {
		// TODO
	}

	private void load(String path) {
		// TODO
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