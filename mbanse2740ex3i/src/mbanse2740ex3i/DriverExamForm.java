package mbanse2740ex3i;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.UIManager;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class DriverExamForm extends JFrame {

	private JPanel contentPane;
	private JList responsesList;
	private JLabel resultLabel;
	private JLabel questNumLabel;
	private JTextField inputAnswerTextField;
	private DefaultListModel responsesListModel;
	private JButton prevButton;
	private JButton nextButton;
	private DriverExam exam;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DriverExamForm frame = new DriverExamForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DriverExamForm() {
		setTitle("Ex3i: Driver Exam");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 374, 406);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblResponses = new JLabel("Responses:");
		lblResponses.setBounds(30, 24, 80, 16);
		contentPane.add(lblResponses);
		
		JLabel lblResult = new JLabel("Result:");
		lblResult.setBounds(152, 24, 56, 16);
		contentPane.add(lblResult);
		
		JList questNumList = new JList();
		questNumList.setBackground(UIManager.getColor("Button.background"));
		questNumList.setModel(new AbstractListModel() {
			String[] values = new String[] {"1.", "2.", "3.", "4.", "5.", "6.", "7.", "8.", "9.", "10"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		questNumList.setEnabled(false);
		questNumList.setBounds(30, 53, 24, 184);
		contentPane.add(questNumList);
		
		responsesList = new JList();
		responsesList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		responsesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		responsesList.setBounds(66, 53, 42, 184);
		contentPane.add(responsesList);
		
		resultLabel = new JLabel("");
		resultLabel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		resultLabel.setBounds(152, 56, 140, 23);
		contentPane.add(resultLabel);
		
		JButton calcPassButton = new JButton("Pass");
		calcPassButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_calcPassButton_actionPerformed(arg0);
			}
		});
		calcPassButton.setBounds(152, 107, 117, 25);
		contentPane.add(calcPassButton);
		
		JButton calcCorrectButton = new JButton("Correct");
		calcCorrectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_calcCorrectButton_actionPerformed(e);
			}
		});
		calcCorrectButton.setBounds(152, 132, 117, 25);
		contentPane.add(calcCorrectButton);
		
		JButton calcIncorrectButton = new JButton("Incorrect");
		calcIncorrectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_calcIncorrectButton_actionPerformed(e);
			}
		});
		calcIncorrectButton.setBounds(152, 157, 117, 25);
		contentPane.add(calcIncorrectButton);
		
		JButton listIncorrectButton = new JButton("List Incorrect");
		listIncorrectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				do_listIncorrectButton_actionPerformed(e);
			}
		});
		listIncorrectButton.setBounds(152, 182, 117, 25);
		contentPane.add(listIncorrectButton);
		
		questNumLabel = new JLabel("#0");
		questNumLabel.setBounds(30, 250, 26, 16);
		contentPane.add(questNumLabel);
		
		inputAnswerTextField = new JTextField();
		inputAnswerTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				do_inputAnswerTextField_focusGained(arg0);
			}
		});
		inputAnswerTextField.setBounds(66, 247, 42, 22);
		contentPane.add(inputAnswerTextField);
		inputAnswerTextField.setColumns(10);
		
		prevButton = new JButton("Prev");
		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_prevButton_actionPerformed(e);
			}
		});
		prevButton.setEnabled(false);
		prevButton.setBounds(126, 246, 66, 25);
		contentPane.add(prevButton);
		
		nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_nextButton_actionPerformed(e);
			}
		});
		nextButton.setBounds(126, 271, 66, 25);
		contentPane.add(nextButton);
		
		DriverExamObjMapper mapper = new DriverExamObjMapper("DriverExam.txt");
		this.exam = mapper.getDriverExam();
		this.responsesListModel = exam.getAnswers();
		responsesList.setModel(this.responsesListModel);
	}
	
	protected void do_calcPassButton_actionPerformed(ActionEvent arg0) {
		this.exam.setResponses((DefaultListModel) responsesList.getModel());
		int invalid = this.exam.validate();
		if (invalid >= 0){
			resultLabel.setText("Invalid response # " + Integer.toString(invalid + 1));
			responsesList.setSelectedIndex(invalid);
		}
		else 
		{
			if (exam.passed()) resultLabel.setText("You Passed");
			else resultLabel.setText("You Failed");
		}
	}
	
	protected void do_calcCorrectButton_actionPerformed(ActionEvent e) {
		this.exam.setResponses((DefaultListModel) responsesList.getModel());
		int invalid = this.exam.validate();
		if (invalid >= 0){
			resultLabel.setText("Invalid response # " + Integer.toString(invalid + 1));
			responsesList.setSelectedIndex(invalid);
		}
		else 
		{
			if (exam.passed()) resultLabel.setText("Total Correct: " + Integer.toString(exam.totalCorrect()));
		}
	}
	
	protected void do_calcIncorrectButton_actionPerformed(ActionEvent e) {
		this.exam.setResponses((DefaultListModel) responsesList.getModel());
		int invalid = this.exam.validate();
		if (invalid >= 0){
			resultLabel.setText("Invalid response # " + Integer.toString(invalid + 1));
			responsesList.setSelectedIndex(invalid);
		}
		else 
		{
			if (exam.passed()) resultLabel.setText("Total Incorrect: " + Integer.toString(exam.totalIncorrect()));
		}
	}
	
	protected void do_listIncorrectButton_actionPerformed(ActionEvent e) {
		
		this.exam.setResponses((DefaultListModel) responsesList.getModel());
		int invalid = this.exam.validate();
		if (invalid >= 0){
			resultLabel.setText("Invalid response # " + Integer.toString(invalid + 1));
			responsesList.setSelectedIndex(invalid);
		}
		else
		{
			if (exam.passed()) {
				int[] missed = exam.questionsMissed();
				String result = "Incorrect: ";
				int i = 0;
				while (i < missed.length && missed[i]!= 0) {
					result += missed[i] + " ";
					i++;
				}
			resultLabel.setText(result);	
			}
		}
	}
	
	protected void do_prevButton_actionPerformed(ActionEvent e) {
        this.responsesListModel.setElementAt(
                inputAnswerTextField.getText().toUpperCase(), 
                responsesList.getSelectedIndex());
        responsesList.setSelectedIndex(responsesList.getSelectedIndex() - 1);
        questNumLabel.setText("#" + Integer.toString((responsesList.getSelectedIndex() + 1)));
        inputAnswerTextField.setText((String)responsesList.getSelectedValue());    

        nextButton.setEnabled(true);
        if (responsesList.getSelectedIndex() == 0) 
            prevButton.setEnabled(false);
        inputAnswerTextField.requestFocus();
	}
	
	protected void do_nextButton_actionPerformed(ActionEvent e) {
	      this.responsesListModel.setElementAt(
	                inputAnswerTextField.getText().toUpperCase(), 
	                responsesList.getSelectedIndex());
	        responsesList.setSelectedIndex(responsesList.getSelectedIndex() + 1);
	        questNumLabel.setText("#" + Integer.toString((responsesList.getSelectedIndex() + 1)));
	        inputAnswerTextField.setText((String)responsesList.getSelectedValue());
	        
	        prevButton.setEnabled(true);
	        if (responsesList.getSelectedIndex() == responsesListModel.getSize() - 1)
	            nextButton.setEnabled(false);
	        inputAnswerTextField.requestFocus();
	}
	
    protected void do_responsesList_valueChanged(ListSelectionEvent arg0) {
        questNumLabel.setText("#" + Integer.toString((responsesList.getSelectedIndex() + 1)));
        inputAnswerTextField.setText((String)responsesList.getSelectedValue());    

        prevButton.setEnabled(true);
        nextButton.setEnabled(true);
        if (responsesList.getSelectedIndex() == responsesListModel.getSize() - 1)
            nextButton.setEnabled(false);
        if (responsesList.getSelectedIndex() == 0) 
            prevButton.setEnabled(false);
        inputAnswerTextField.requestFocus();        
    }
    
	protected void do_inputAnswerTextField_focusGained(FocusEvent arg0) {
		inputAnswerTextField.requestFocus();
	}
}
