import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ControlPanel extends JPanel implements Observer {

	private CellularAutomataFrame gf;
	private GameData m_GameData;
	private JButton m_StartButton;
	private JButton m_StopButton;
	private JButton m_StepButton;
	private JTextField m_WidthTextField;
	private JTextField m_HeightTextField;
	private JButton m_ResizeButton;
	private JButton m_ClearButton;
	private JButton m_RandomButton;
	private JButton m_LoadButton;
	private JButton m_SaveButton;
	private JRadioButton m_Ruleset0Button;
	private JRadioButton m_Ruleset1Button;
	private JRadioButton m_Ruleset2Button;
	private JCheckBox m_WrapCheckbox;

	public ControlPanel(CellularAutomataFrame _gf) {
		gf = _gf;

		setLayout(new GridLayout(20, 1, 2, 2));

		m_StartButton = new JButton("Start");
		m_StartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gf.getGraphicPanel().getAnimationTimer().start();
				m_StartButton.setEnabled(false);
				m_StopButton.setEnabled(true);
				m_StepButton.setEnabled(false);
				m_WidthTextField.setEnabled(false);
				m_HeightTextField.setEnabled(false);
				m_ResizeButton.setEnabled(false);
				m_ClearButton.setEnabled(false);
				m_RandomButton.setEnabled(false);
				m_LoadButton.setEnabled(false);
				m_SaveButton.setEnabled(false);
				m_Ruleset0Button.setEnabled(false);
				m_Ruleset1Button.setEnabled(false);
				m_Ruleset2Button.setEnabled(false);
				m_WrapCheckbox.setEnabled(false);
				gf.getGraphicPanel().setIsRunning(true);
			}
		});

		m_StopButton = new JButton("Stop");
		m_StopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gf.getGraphicPanel().getAnimationTimer().stop();
				m_StartButton.setEnabled(true);
				m_StopButton.setEnabled(false);
				m_StepButton.setEnabled(true);
				m_WidthTextField.setEnabled(true);
				m_HeightTextField.setEnabled(true);
				m_ResizeButton.setEnabled(true);
				m_ClearButton.setEnabled(true);
				m_RandomButton.setEnabled(true);
				m_LoadButton.setEnabled(true);
				m_SaveButton.setEnabled(true);
				m_Ruleset0Button.setEnabled(true);
				m_Ruleset1Button.setEnabled(true);
				m_Ruleset2Button.setEnabled(true);
				m_WrapCheckbox.setEnabled(true);
				gf.getGraphicPanel().setIsRunning(false);
			}
		});

		m_StepButton = new JButton("Step");
		m_StepButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m_GameData.step();
			}
		});

		m_ClearButton = new JButton("Clear");
		m_ClearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m_GameData.clear();
			}
		});

		m_RandomButton = new JButton("Randomize");
		m_RandomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m_GameData.randomize();
			}
		});

		m_LoadButton = new JButton("Load");
		m_LoadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String filename = "";
				JFileChooser chooser = new JFileChooser();
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"PNG files", "png");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					filename = chooser.getSelectedFile().getName();

					File file = chooser.getSelectedFile();
					m_GameData.load(file);
				}
			}
		});

		m_SaveButton = new JButton("Save");
		m_SaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String filename = "";
				JFileChooser chooser = new JFileChooser();
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"PNG files", "png");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File outfile = chooser.getSelectedFile();
					m_GameData.save(outfile);
					// if (outfile.exists()) {
					// System.out.println(outfile + " already exists.");
					// }
					//
					// // -- PrintWriter is used for writing text files
					// PrintWriter writer = null;
					// try {
					// writer = new PrintWriter(outfile);
					// } catch (FileNotFoundException e) {
					// System.out.println("Cannot wrap " + outfile.getName()
					// + " with a PrintWriter");
					// }

				}
			}
		});

		// -- each radio button needs it's own action listener
		m_Ruleset0Button = new JRadioButton("Ruleset 0", true);
		m_Ruleset0Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m_GameData.setRuleNum(0);
			}
		});
		m_Ruleset1Button = new JRadioButton("Ruleset 1", true);
		m_Ruleset1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m_GameData.setRuleNum(1);
			}
		});
		m_Ruleset2Button = new JRadioButton("Ruleset 2", true);
		m_Ruleset2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m_GameData.setRuleNum(2);
			}
		});

		// -- create a ButtonGroup to make the buttons work as a mutually
		// exclusive (only one will
		// ever be selected) set
		ButtonGroup group = new ButtonGroup();
		group.add(m_Ruleset0Button);
		group.add(m_Ruleset1Button);
		group.add(m_Ruleset2Button);

		// m_Ruleset0Button.setSelected(true);
		// m_Ruleset1Button.setSelected(false);

		// -- boolean variable is the default state of the button
		m_WrapCheckbox = new JCheckBox("Edge Wrap", true);
		m_WrapCheckbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (m_WrapCheckbox.isSelected()) {
					m_GameData.setBorderType(GameData.BORDER_WRAP);
				} else {
					m_GameData.setBorderType(GameData.BORDER_DONTCARE);
				}

			}
		});

		JLabel widthLabel = new JLabel("Width:");
		m_WidthTextField = new JTextField(3);
		JLabel heightLabel = new JLabel("Height:");
		m_HeightTextField = new JTextField(3);
		m_ResizeButton = new JButton("Resize");
		m_ResizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int width = Integer.parseInt(m_WidthTextField.getText());
					int height = Integer.parseInt(m_HeightTextField.getText());
					m_GameData.resize(height, width);
				} catch (NumberFormatException e) {
				}
			}
		});

		add(m_StartButton);
		add(m_StopButton);
		add(m_StepButton);
		add(widthLabel);
		add(m_WidthTextField);
		add(heightLabel);
		add(m_HeightTextField);
		add(m_ResizeButton);
		add(m_ClearButton);
		add(m_RandomButton);
		add(m_LoadButton);
		add(m_SaveButton);
		add(m_Ruleset0Button);
		add(m_Ruleset1Button);
		add(m_Ruleset2Button);
		add(m_WrapCheckbox);

		m_StartButton.setEnabled(true);
		m_StopButton.setEnabled(false);
		m_StepButton.setEnabled(true);
		m_WidthTextField.setEnabled(true);
		m_HeightTextField.setEnabled(true);
		m_ResizeButton.setEnabled(true);
		m_ClearButton.setEnabled(true);
		m_RandomButton.setEnabled(true);
		m_LoadButton.setEnabled(true);
		m_SaveButton.setEnabled(true);
		m_Ruleset0Button.setEnabled(true);
		m_Ruleset1Button.setEnabled(true);
		m_Ruleset2Button.setEnabled(true);
		m_WrapCheckbox.setEnabled(true);
		gf.getGraphicPanel().setIsRunning(false);
	}

	public Dimension getPreferredSize() {
		return new Dimension(100, 500);
	}

	public void setGameData(GameData gameData) {
		m_GameData = gameData;
		m_GameData.addObserver(this);
		update(m_GameData, null);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (m_GameData.getRuleNum() == 0) {
			m_Ruleset0Button.setSelected(true);
		} else if (m_GameData.getRuleNum() == 1) {
			m_Ruleset1Button.setSelected(true);
		}
		if (m_GameData.getBorderType() == GameData.BORDER_WRAP) {
			m_WrapCheckbox.setSelected(true);
		} else if (m_GameData.getBorderType() == GameData.BORDER_DONTCARE) {
			m_WrapCheckbox.setSelected(false);
		}
		m_WidthTextField.setText(Integer.toString(m_GameData.getWidth()));
		m_HeightTextField.setText(Integer.toString(m_GameData.getHeight()));
	}

}
