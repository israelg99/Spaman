import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Spammer extends JFrame {
	
	private final int WIDTH;
	private final int HEIGHT;
	
	private JPanel Wrapper = new JPanel();
	private JPanel Logo = new JPanel();
	private JPanel TimeFieldPanel = new JPanel();
	private JPanel TimesFieldPanel = new JPanel();
	private JPanel TextFieldPanel = new JPanel();
	private JPanel TextsBoxPanel = new JPanel();
	private JPanel ButtonsPanel = new JPanel();
	
	private JTextField text1;
	private JTextField text2;
	private JTextField time;
	private JTextField times;
	private JButton start;
	private JButton stop;
	
	private JLabel logo = new JLabel("Spaman");
	private Font Logofont = new Font("Serif", Font.BOLD, 36);
	private JLabel subLogo = new JLabel("Created By Israel Gilyadov");
	private Font subLogofont = new Font("Serif", Font.BOLD, 18);
	
	private JCheckBox MessagesCheck = new JCheckBox("2 Messages", false);
	private JCheckBox CrasherMode = new JCheckBox("Crasher Mode", false);
	private JCheckBox EndlessTimes = new JCheckBox("Endless", true);
	
	private TitledBorder TextTitle = BorderFactory.createTitledBorder("Message Panel");
	private TitledBorder TimeTitle = BorderFactory.createTitledBorder("Delay Time in MS");
	private TitledBorder TimesTitle = BorderFactory.createTitledBorder("Message Quantity");
	
	private Timer msTimer = new Timer();
	
	private Boolean isRunning = false;
	
	private Robot presser;
	
	private boolean firstMessage;
	
	private int spamTimes;
	
	private Boolean endlessSelected;
	
	private String Stext;
	private String Stext1;
	private String Stext2;
	
	private int crashSlash;
	private boolean SlashMore = true;
	
	public Spammer(int width, int height, String title) {
		
		WIDTH = width;
		HEIGHT = height;
		setTitle(title);
		setSize(WIDTH, HEIGHT);
		setLayout(new FlowLayout());
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		logo.setFont(Logofont);
		logo.setForeground(Color.RED);
		subLogo.setFont(subLogofont);
		subLogo.setForeground(Color.GREEN);
		
		Wrapper.setPreferredSize(new Dimension(WIDTH - 30, HEIGHT - 30));
		Wrapper.setLayout(new BoxLayout(Wrapper, BoxLayout.PAGE_AXIS));
		
		TextFieldPanel.setLayout(new BoxLayout(TextFieldPanel, BoxLayout.PAGE_AXIS));
		TextsBoxPanel.setLayout(new FlowLayout());
		TimeFieldPanel.setLayout(new FlowLayout());
		TimesFieldPanel.setLayout(new FlowLayout());
		ButtonsPanel.setLayout(new FlowLayout());
		Logo.setLayout(new FlowLayout());
		
		TimeFieldPanel.setPreferredSize(new Dimension(WIDTH - 30, 50));
		TimesFieldPanel.setPreferredSize(new Dimension(WIDTH - 30, 50));
		TextsBoxPanel.setPreferredSize(new Dimension(WIDTH - 30, 50));
		Logo.setPreferredSize(new Dimension(WIDTH - 30, 60));
		
		Wrapper.add(Logo);
		Wrapper.add(TextFieldPanel);
		Wrapper.add(TimeFieldPanel);
		Wrapper.add(TimesFieldPanel);
		Wrapper.add(ButtonsPanel);
		
		TextFieldPanel.add(TextsBoxPanel);
		TextFieldPanel.add(MessagesCheck);
		TextFieldPanel.add(CrasherMode);
		
		Logo.add(logo);
		Logo.add(subLogo);
		
		TextFieldPanel.setBorder(TextTitle);
		TimeFieldPanel.setBorder(TimeTitle);
		TimesFieldPanel.setBorder(TimesTitle);
		
		text1 = new JTextField(20);
		TextsBoxPanel.add(text1);
		text2 = new JTextField(20);
		
		time = new JTextField(10);
		time.setEditable(false);
		TimeFieldPanel.add(time);
		
		times = new JTextField(10);
		times.setEditable(false);
		TimesFieldPanel.add(times);
		TimesFieldPanel.add(EndlessTimes);
		
		start = new JButton("Start!");
		ButtonsPanel.add(start);
		
		stop = new JButton("Stop!");
		ButtonsPanel.add(stop);
		
		add(Wrapper);
		
		text1.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if(text1.getText().length() > 0 && MessagesCheck.isSelected() == false || text1.getText().length() > 0 && text2.getText().length() > 0 && MessagesCheck.isSelected()) { time.setEditable(true); }
                else { time.setEditable(false); }
            }
        });
		text2.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if(text1.getText().length() > 0 && MessagesCheck.isSelected() == false || text1.getText().length() > 0 && text2.getText().length() > 0 && MessagesCheck.isSelected()) { time.setEditable(true); }
                else { time.setEditable(false); }
            }
        });	
		
		start.addActionListener(new StartHandler());
		
		stop.addActionListener(new StopHandler());
		
		MessagesCheck.addItemListener(new MessagesListener());
		
		CrasherMode.addItemListener(new CrashListener());
		
		EndlessTimes.addItemListener(new EndlessTimesListener());
		
	}
	
	private class StartHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			
            if(text1.getText().length() > 0 && !isRunning && !MessagesCheck.isSelected() || CrasherMode.isSelected() && !isRunning && !MessagesCheck.isSelected()) {
            	isRunning = true;
            	String Sms = time.getText();
            	endlessSelected = !EndlessTimes.isSelected();
            	String SspamTimes = endlessSelected ? times.getText() : null;
            	int ms = Pattern.matches("([0-9]*)+", Sms) == true && Sms.length() > 0 ? Integer.parseInt(Sms) : 1000;
            	if(endlessSelected) {
            		spamTimes = Pattern.matches("([0-9]*)+", SspamTimes) == true && SspamTimes.length() > 0 ? Integer.parseInt(SspamTimes) : 1000;
            	}
            	if(ms < 15) { ms = 15; }
            	crashSlash = 3;
            	msTimer = new Timer();
				msTimer.schedule(new TimerTask() {
            		  public void run() {
           			  if(CrasherMode.isSelected()) {
           				  Stext = "-";
           				  for(int i = 0; i < crashSlash; i++) {
           					  Stext += "-";
           				  }
           			  } else {
            				  Stext = text1.getText();
            			  }
            			  try { 
            				  presser = new Robot();
            				  Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            				  StringSelection stringSelections = new StringSelection( Stext );
            				  clipboard.setContents(stringSelections, stringSelections);
            				  
            				  presser.keyPress(KeyEvent.VK_T);
            				  presser.keyRelease(KeyEvent.VK_T);
            				  presser.keyPress(KeyEvent.VK_CONTROL);
            				  presser.keyPress(KeyEvent.VK_V);
            				  presser.keyRelease(KeyEvent.VK_V);
            				  presser.keyRelease(KeyEvent.VK_CONTROL);
            			  } 
            			  catch (AWTException e) {
            				  e.printStackTrace(); 
            			  }
            			  presser.keyPress(KeyEvent.VK_ENTER);
            			  presser.keyRelease(KeyEvent.VK_ENTER);
            			  if(endlessSelected) { spamTimes--; }
            			  if(spamTimes <= 0 && endlessSelected) {
            				  msTimer.cancel();
            				  isRunning = false;
            			  }
            			  if(crashSlash > 10) { SlashMore = false; }
            			  else if(crashSlash < 3) { SlashMore = true; }
            			  if(SlashMore) { crashSlash++; } else { crashSlash--; }
            			  
            		  }
            	}, ms, ms);
            }
            
            if(text1.getText().length() > 0 && text2.getText().length() > 0 && !isRunning && MessagesCheck.isSelected() || CrasherMode.isSelected() && !isRunning && MessagesCheck.isSelected()) {
            	isRunning = true;
            	String Sms = time.getText();
            	endlessSelected = !EndlessTimes.isSelected();
            	String SspamTimes = endlessSelected ? times.getText() : null;
            	firstMessage = true;
            	int ms = Pattern.matches("([0-9]*)+", Sms) == true && Sms.length() > 0 ? Integer.parseInt(Sms) : 1000;
            	if(endlessSelected) {
            		spamTimes = Pattern.matches("([0-9]*)+", SspamTimes) == true && SspamTimes.length() > 0 ? Integer.parseInt(SspamTimes) : 1000;
            	}
            	if(ms < 15) { ms = 15; }
            	msTimer = new Timer();
            	msTimer.schedule(new TimerTask() {
            		public void run() {
             			  if(CrasherMode.isSelected()) {
               				  Stext = "-";
               				  for(int i = 0; i < crashSlash; i++) {
               					  Stext += "-";
               				  }
               			  } else {
               				  Stext1 = text1.getText();
               				  Stext2 = text2.getText();
               			  }
            			try { 
            				presser = new Robot();
            				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            				StringSelection stringSelections;
            				if(CrasherMode.isSelected()) { stringSelections = new StringSelection( Stext ); }
            				else {
            					stringSelections = firstMessage ? new StringSelection( Stext1 ) : new StringSelection( Stext2 );
            				}
            				clipboard.setContents(stringSelections, stringSelections);
	            			
            				presser.keyPress(KeyEvent.VK_CONTROL);
            				presser.keyPress(KeyEvent.VK_V);
            				presser.keyRelease(KeyEvent.VK_V);
            				presser.keyRelease(KeyEvent.VK_CONTROL);
            			} 
            			catch (AWTException e) {
            				e.printStackTrace(); 
            			}
            			presser.keyPress(KeyEvent.VK_ENTER);
            			presser.keyRelease(KeyEvent.VK_ENTER);
            			firstMessage = !firstMessage;
            			if(endlessSelected) { spamTimes--; }
            			if(spamTimes <= 0 && endlessSelected) {
            				msTimer.cancel();
            				isRunning = false;
            			}
            			if(crashSlash > 10) { SlashMore = false; }
          			  	else if(crashSlash < 3) { SlashMore = true; }
          			  	if(SlashMore) { crashSlash++; } else { crashSlash--; }
            		}
            	}, ms, ms);
            }
		}
	}
	
	private class StopHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			
			msTimer.cancel();
			isRunning = false;
			
		}
	}
	
	private class MessagesListener implements ItemListener {
		
		public void itemStateChanged(ItemEvent ie) {
			
			if(MessagesCheck.isSelected()) {
				TextsBoxPanel.add(text2);
				TextsBoxPanel.validate();
				TextsBoxPanel.repaint();
				if(!CrasherMode.isSelected()) {
					if(text1.getText().length() > 0 && text2.getText().length() > 0) { time.setEditable(true); }
	                else { time.setEditable(false); }
				}
			} 
			else {
					TextsBoxPanel.remove(text2);
					TextsBoxPanel.validate();
					TextsBoxPanel.repaint();
					if(!CrasherMode.isSelected()) {
						if(text1.getText().length() > 0) { time.setEditable(true); }
		                else { time.setEditable(false); }
					}
			}
		}
	}
	
	private class CrashListener implements ItemListener {
		
		public void itemStateChanged(ItemEvent ie) {
			msTimer.cancel();
			isRunning = false;
			if(CrasherMode.isSelected()) {
				text1.setEditable(false);
				text2.setEditable(false);
				time.setEditable(true);
			} else {
				text1.setEditable(true);
				text2.setEditable(true);
				time.setEditable(false);
				if(MessagesCheck.isSelected()) {
					if(text1.getText().length() > 0 && text2.getText().length() > 0) { time.setEditable(true); }
					else { time.setEditable(false); } }
				else {
					if(text1.getText().length() > 0) { time.setEditable(true); }
	                else { time.setEditable(false); }
				}
			}
		}
	}
	
	private class EndlessTimesListener implements ItemListener {
		
		public void itemStateChanged(ItemEvent ie) {
			if(EndlessTimes.isSelected()) {
				times.setEditable(false);
			}
			if(EndlessTimes.isSelected() == false) {
				times.setEditable(true);
			}
		}
	}
}
