package ge.ISS;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Atlas_Collimator {

	private JFrame frame;
	private JTextField SSOText;
	private JTextField SNText;
	private JLabel SSOFORMAT;
	final String regx1 = "RT\\d{7}";
	final String regx2 = "T\\d{7}";
	private JLabel SNFORMAT;
	private JLabel RESULT;
	private JLabel CLASS;
	private JLabel ODMAX;
	private JLabel ODMIN;
	private JLabel IDMAX;
	private JLabel IDMIN;
	private ButtonListener action = new ButtonListener();
	private JButton SUBMIT;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Atlas_Collimator window = new Atlas_Collimator();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Atlas_Collimator() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 501, 537);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblAtalsCollimatorMeasure = new JLabel("Atals Collimator Measure System");
		lblAtalsCollimatorMeasure.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAtalsCollimatorMeasure.setBounds(108, 54, 269, 42);
		frame.getContentPane().add(lblAtalsCollimatorMeasure);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\212710307\\Desktop\\Pic\\sucai\\ge-logo_small.png"));
		label.setBounds(10, 11, 188, 52);
		frame.getContentPane().add(label);

		JLabel SSO = new JLabel("SSO");
		SSO.setFont(new Font("Tahoma", Font.BOLD, 13));
		SSO.setBounds(94, 135, 46, 14);
		frame.getContentPane().add(SSO);

		SSOText = new JTextField();
		SSOText.setBounds(150, 128, 144, 29);
		frame.getContentPane().add(SSOText);
		SSOText.setColumns(10);
		SSOText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==10) {
					String sso = SSOText.getText();
					String regx = "\\d{9}";
						if (sso.matches(regx)) {
							SSOFORMAT.setText("");
							SNText.grabFocus();
						} else {
							SSOFORMAT.setText("Format Error");
							SSOText.setText("");
						}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		JLabel SN = new JLabel("SN");
		SN.setFont(new Font("Tahoma", Font.BOLD, 13));
		SN.setBounds(94, 186, 46, 14);
		frame.getContentPane().add(SN);

		SNText = new JTextField();
		SNText.setColumns(10);
		SNText.setBounds(150, 180, 144, 29);
		frame.getContentPane().add(SNText);
		SNText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==10) {
					String sn = SNText.getText();
						if (sn.matches(regx1) || sn.matches(regx2)) {
							SNFORMAT.setText("");
							SUBMIT.grabFocus();
						} else {
							SNFORMAT.setText("Format Error");
							SNText.setText("");
						}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				String sn = SNText.getText();
				if(sn!=null) {
				if(sn.length()>8) {
					if (sn.matches(regx1) || sn.matches(regx2)) {
						SNFORMAT.setText("");
						SUBMIT.grabFocus();
					} else {
						SNFORMAT.setText("Format Error");
						SNText.setText("");
					}
				}
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		SUBMIT = new JButton("ENTER");
		SUBMIT.setFont(new Font("Tahoma", Font.BOLD, 14));
		SUBMIT.setBounds(94, 266, 104, 42);
		frame.getContentPane().add(SUBMIT);
		SUBMIT.addActionListener(action);
		SUBMIT.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==10) {
					try {
						enterEvent();
					} catch (HeadlessException | InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});

		JButton CANCEL = new JButton("CANCEL");
		CANCEL.setFont(new Font("Tahoma", Font.BOLD, 14));
		CANCEL.setBounds(252, 266, 104, 42);
		frame.getContentPane().add(CANCEL);
		CANCEL.addActionListener(action);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(57, 343, 376, 144);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblMeasureResult = new JLabel("Measure Result");
		lblMeasureResult.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMeasureResult.setBounds(129, 0, 121, 28);
		panel.add(lblMeasureResult);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 26, 356, 2);
		panel.add(separator);

		RESULT = new JLabel("");
		RESULT.setFont(new Font("Tahoma", Font.BOLD, 14));
		RESULT.setBackground(Color.GRAY);
		RESULT.setBounds(152, 35, 46, 28);
		panel.add(RESULT);

		JLabel lblOdMax = new JLabel("OD MAX:");
		lblOdMax.setBounds(10, 81, 51, 14);
		panel.add(lblOdMax);

		JLabel lblOdMin = new JLabel("OD MIN:");
		lblOdMin.setBounds(10, 119, 46, 14);
		panel.add(lblOdMin);

		JLabel lblIdMax = new JLabel("ID MAX:");
		lblIdMax.setBounds(142, 81, 46, 14);
		panel.add(lblIdMax);

		JLabel lblIdMin = new JLabel("ID MIN:");
		lblIdMin.setBounds(142, 119, 46, 14);
		panel.add(lblIdMin);

		CLASS = new JLabel("");
		CLASS.setFont(new Font("Tahoma", Font.BOLD, 14));
		CLASS.setBounds(278, 97, 46, 14);
		panel.add(CLASS);

		ODMAX = new JLabel("");
		ODMAX.setBounds(67, 81, 65, 14);
		panel.add(ODMAX);

		ODMIN = new JLabel("");
		ODMIN.setBounds(66, 119, 66, 14);
		panel.add(ODMIN);

		IDMAX = new JLabel("");
		IDMAX.setBounds(204, 81, 65, 14);
		panel.add(IDMAX);

		IDMIN = new JLabel("");
		IDMIN.setBounds(204, 119, 65, 14);
		panel.add(IDMIN);

		SSOFORMAT = new JLabel("");
		SSOFORMAT.setBounds(310, 136, 109, 14);
		frame.getContentPane().add(SSOFORMAT);

		SNFORMAT = new JLabel("");
		SNFORMAT.setBounds(310, 187, 109, 14);
		frame.getContentPane().add(SNFORMAT);
	}

	public class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String button = e.getActionCommand();
			if ("ENTER".equals(button)) {
				try {
					enterEvent();
				} catch (HeadlessException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			//CANCEL action
			if("CANCEL".equals(button)) {
				resetting();
			}
			
		}

	}

	public boolean emptyCheck(String str) {
			if (str != null && !"".equals(str)) {
				return true;
			}
		return false;
		
	}

	public void errorSetting() {
		RESULT.setText("FAIL");
		ODMAX.setText("");
		ODMIN.setText("");
		IDMAX.setText("");
		IDMIN.setText("");
		CLASS.setText("");
		SNText.grabFocus();
	}
	public void resetting() {
		RESULT.setText("");
		ODMAX.setText("");
		ODMIN.setText("");
		IDMAX.setText("");
		IDMIN.setText("");
		CLASS.setText("");
		SNText.setText("");
		SNText.grabFocus();
	}
	//监控测试结果文件文件是否生成
	public  boolean monitorExcel(IOUtil io) throws InterruptedException {
		WatchService fileWatch;
		String filePath=io.getConfigurationproperty().getProperty("ResultReadPath");
		String fileName=io.getConfigurationproperty().getProperty("ResultFileName");
		try {
			fileWatch=FileSystems.getDefault().newWatchService();
			Paths.get(filePath).register(fileWatch, StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_MODIFY);		
			while(true) {
				WatchKey key=fileWatch.take();
				for (WatchEvent<?> event : key.pollEvents()) {
			        if(StandardWatchEventKinds.ENTRY_CREATE == event.kind()||StandardWatchEventKinds.ENTRY_MODIFY==event.kind()){
			        	System.out.println(event.context());
			        	return true;
			        }
			}
				boolean valid = key.reset();
				if (!valid) {
					break;
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
	public boolean dataCorruptCheck() {

		return false;
	}
	public void enterEvent() throws HeadlessException, InterruptedException {
		IOUtil io = new IOUtil();
		io.getConfigurationproperty();
		String path=io.getConfigurationproperty().getProperty("ResultReadPath");
		String excelName=io.getConfigurationproperty().getProperty("ResultFileName");
		String filename=path+excelName;
		if (emptyCheck(SSOText.getText()) && emptyCheck(SNText.getText())) {
			if (io.autoRun()) {
				if(monitorExcel(io)) {// 等待天准测试结束，约20秒
				File excelFile = new File(filename);
				if (excelFile.exists()) {
					
					io.setExcelArray();
					io.setStan_ResultArray();
					int i=io.getResult().size();
					if (io.getResult().size() == 1096) {  //需要区分normal与非normal
						if ("PASS".equals(io.passResult())) {
							RESULT.setText("PASS");
							try {
								IDMIN.setText(io.min(0, 547).toString().substring(0, 7));
								IDMAX.setText(io.max(0, 547).toString().substring(0, 7));
								ODMAX.setText(io.max(548, 1095).toString().substring(0, 7));
								ODMIN.setText(io.min(548, 1095).toString().substring(0, 7));
								if("C1".equals(io.classResult())) {
								  CLASS.setText("C1");
								  io.exportResult(SNText.getText()); 
								  SNText.setText("");
								  SNText.grabFocus();
								  } else {											  
								  CLASS.setText("NO");
								  io.exportResult(SNText.getText());
								  SNText.setText("");
								  SNText.grabFocus();
								  }										   
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								JOptionPane.showMessageDialog(frame, "极值计算失败，请确认");
								errorSetting();
							}
						} else if ("FAIL".equals(io.passResult())) {
							RESULT.setText("FAIL");
							try {
								IDMIN.setText(io.min(0, 547).toString().substring(0, 7));
								IDMAX.setText(io.max(0, 547).toString().substring(0, 7));
								ODMAX.setText(io.max(548, 1095).toString().substring(0, 7));
								ODMIN.setText(io.min(548, 1095).toString().substring(0, 7));
								io.exportResult(SNText.getText());
								SNText.setText("");
								SNText.grabFocus();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								JOptionPane.showMessageDialog(frame, "极值计算失败，请确认");
								errorSetting();
							}
						} else {
							JOptionPane.showMessageDialog(frame, "极值计算失败，请确认");
							errorSetting();
						}
					} else {
						JOptionPane.showMessageDialog(frame, "天准生成数据错误，请确认");
						errorSetting();
					}
				}
				}
			} else {
				JOptionPane.showMessageDialog(frame, "autorun 文件生成失败");
			}
		} else {
			JOptionPane.showMessageDialog(frame, "请检查输入格式");
		}

	}
		}
