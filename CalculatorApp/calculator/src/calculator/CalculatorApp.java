package calculator;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class CalculatorApp extends JFrame {
	
	// Panels
	public EntryPanel entryPanel;
	public ButtonsPanel buttonsPanel;
	
	// All Buttons
	public NumButtons button1;
	public NumButtons button2;
	public NumButtons button3;
	public NumButtons button4;
	public NumButtons button5;
	public NumButtons button6;
	public NumButtons button7;
	public NumButtons button8;
	public NumButtons button9;
	public NumButtons button0;
	public SymbolButtons buttonC;
	public SymbolButtons buttonCE;
	public SymbolButtons buttonPercentage;
	public SymbolButtons buttonDel;
	public SymbolButtons buttonPlus;
	public SymbolButtons buttonMinus;
	public SymbolButtons buttonMultiply;
	public SymbolButtons buttonDivide;
	public SymbolButtons buttonDecimal;
	public SymbolButtons buttonEquals;
	
	//TextField
	public JTextField entryField;
	
	//Icons
	public ImageIcon calculatorIcon;
	
	//Colors
	public Color numButtonsColor = new Color(93,93,93);
	public Color symbolButtonsColor = new Color(36,36,36);
	public Color mainColor = new Color(27,27,27);
	
	//variables
	public char operator;
	public double prevNum;
	public double nextNum;
	public double answer;
	
	public CalculatorApp() {
		//Calculator Icon Initialization
		calculatorIcon = new ImageIcon("calculator.png");
		
		//TextField Initialization
		entryField = new JTextField();
		entryField.setBackground(mainColor);
		entryField.setBounds(0,0,310,150);
		entryField.setBorder(BorderFactory.createEmptyBorder());
		entryField.setFont(new Font("Dialog", Font.BOLD, 35));
		entryField.setForeground(Color.white);
		entryField.setHorizontalAlignment(JTextField.RIGHT);
		
		
		//Buttons Initialization
		button1 = new NumButtons("1");
		button2 = new NumButtons("2");
		button3 = new NumButtons("3");
		button4 = new NumButtons("4");
		button5 = new NumButtons("5");
		button6 = new NumButtons("6");
		button7 = new NumButtons("7");
		button8 = new NumButtons("8");
		button9 = new NumButtons("9");
		button0 = new NumButtons("0");
		buttonC = new SymbolButtons("C");
		buttonCE = new SymbolButtons("CE");
		buttonPercentage = new SymbolButtons("%");
		buttonDel = new SymbolButtons("Del");
		buttonPlus = new SymbolButtons("+");
		buttonMinus = new SymbolButtons("-");
		buttonMultiply = new SymbolButtons("×");
		buttonDivide = new SymbolButtons("÷");
		buttonDecimal = new SymbolButtons(".");
		buttonEquals = new SymbolButtons("=");
		
		//Panels Initialization
		buttonsPanel = new ButtonsPanel();
		buttonsPanel.setBounds(0, 150, 330, 425);
		entryPanel = new EntryPanel();
		entryPanel.setBounds(0,0,340,150);
		
		this.setTitle("Calculator");
		this.setVisible(true);
		this.setResizable(false);
		this.setSize(340,540);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(calculatorIcon.getImage());
		this.add(buttonsPanel);
		this.add(entryPanel);
	}
	
	public class EntryPanel extends JPanel{
		public EntryPanel() {
			this.setBackground(mainColor);
			this.setLayout(null);
			this.add(entryField);
		}
	}
	
	public class ButtonsPanel extends JPanel {
		public ButtonsPanel () {
			this.setLayout(new GridLayout(6,4,5,5));
			this.setBackground(mainColor);
			this.add(buttonCE);
			this.add(buttonC);
			this.add(buttonDel);
			this.add(buttonDivide);
			this.add(button7);
			this.add(button8);
			this.add(button9);
			this.add(buttonMultiply);
			this.add(button4);
			this.add(button5);
			this.add(button6);
			this.add(buttonMinus);
			this.add(button1);
			this.add(button2);
			this.add(button3);
			this.add(buttonPlus);
			this.add(buttonDecimal);
			this.add(button0);
			this.add(buttonPercentage);
			this.add(buttonEquals);
		}
	}
	
	public double evaluate (char c) {
		switch (c) {
		case '+':
			answer = prevNum + nextNum;
			break;
		case '-':
			answer = prevNum - nextNum;
			break;
		case 'x':
			answer = prevNum * nextNum;
			break;
		case '/':
			answer = prevNum / nextNum;
			break;
		case '%':
			answer = (prevNum/100) * nextNum;
			break;
		}
		return answer;
	}
	
	
	public class SymbolButtons extends JButton implements ActionListener, MouseListener {
		public SymbolButtons (String s) {
			this.setFocusable(false);
			this.setBorder(BorderFactory.createEmptyBorder());
			this.setBackground(symbolButtonsColor);
			this.setText(s);
			this.setForeground(Color.white);
			this.setFont(new Font("Dialog", Font.BOLD, 24));
			this.addActionListener(this);
			this.addMouseListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == buttonDel) {
				entryField.setText(entryField.getText().substring(0, entryField.getText().length() - 1));
			}
			if (e.getSource() == buttonC) {
				entryField.setText("");
				prevNum = 0.0;
				nextNum = 0.0;
			}
			if (e.getSource() == buttonCE) {
				entryField.setText("");
			}
			if (e.getSource() == buttonPlus) {
				operator = '+';
				prevNum = Double.parseDouble(entryField.getText());
				entryField.setText("");
			}
			if (e.getSource() == buttonMinus) {
				operator = '-';
				prevNum = Double.parseDouble(entryField.getText());
				entryField.setText("");
			}
			if (e.getSource() == buttonMultiply) {
				operator = 'x';
				prevNum = Double.parseDouble(entryField.getText());
				entryField.setText("");
			}
			if (e.getSource() == buttonDivide) {
				operator = '/';
				prevNum = Double.parseDouble(entryField.getText());
				entryField.setText("");
			}
			if (e.getSource() == buttonPercentage) {
				operator = '%';
				prevNum = Double.parseDouble(entryField.getText());
				entryField.setText("");
			}
			if (e.getSource() == buttonEquals) {
				nextNum = Double.parseDouble(entryField.getText());
				answer = evaluate (operator);
				entryField.setText(String.valueOf(answer));
				nextNum = 0.0;
				prevNum = 0.0;
			}
			
			if (e.getSource() == buttonDecimal) {
				entryField.setText(entryField.getText() + ".");
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {
			((JButton) e.getSource()).setBackground(new Color(31,31,31));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			((JButton) e.getSource()).setBackground(symbolButtonsColor);
		}
	}
	
	public class NumButtons extends JButton implements ActionListener, MouseListener{
		public NumButtons (String s) {
			this.setFocusable(false);
			this.setBorder(BorderFactory.createEmptyBorder());
			this.setBackground(numButtonsColor);
			this.setText(s);
			this.setForeground(Color.white);
			this.setFont(new Font("Dialog", Font.BOLD, 24));
			this.addActionListener(this);
			this.addMouseListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button1) {
				entryField.setText( entryField.getText() +"1");
			}
			if (e.getSource() == button2) {
				entryField.setText( entryField.getText() +"2");
			}
			if (e.getSource() == button3) {
				entryField.setText( entryField.getText() +"3");
			}
			if (e.getSource() == button4) {
				entryField.setText( entryField.getText() +"4");
			}
			if (e.getSource() == button5) {
				entryField.setText( entryField.getText() +"5");
			}
			if (e.getSource() == button6) {
				entryField.setText( entryField.getText() +"6");
			}
			if (e.getSource() == button7) {
				entryField.setText( entryField.getText() +"7");
			}
			if (e.getSource() == button8) {
				entryField.setText( entryField.getText() +"8");
			}
			if (e.getSource() == button9) {
				entryField.setText( entryField.getText() +"9");
			}
			if (e.getSource() == button0) {
				entryField.setText( entryField.getText() +"0");
			}
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {
			((JButton) e.getSource()).setBackground(symbolButtonsColor);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			((JButton) e.getSource()).setBackground(numButtonsColor);
		}
	}
	
	public static void main (String [] args) {
		new CalculatorApp();
	}
}
