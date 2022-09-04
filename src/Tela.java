import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JCheckBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class Tela {

	private JFrame frame;
	private JTextField txtProfessora;
	private JTextField txtContedoInteressante;
	private JTextField txtMigu_1;
	private JTextField txtMigu_3;
	private JTextField textField_2;
	private JTextField txtNerd_4;
	private JTextField textField_6;
	private JTextField txtMigu_5;
	private JTextField textField;
	private JTextField txtMigu_4;
	private JTextField txtNerd_1;
	private JTextField txtNerd_2;
	private JTextField textField_9;
	private JTextField txtTrabalhador_1;
	private JTextField txtMigu_7;
	private JTextField txtNerd_5;
	private JTextField txtTrabalhador_4;
	private JTextField txtNerd;
	private JTextField txtTrabalhador;
	private JTextField textField_16;
	private JTextField textField_17;
	private JTextField textField_18;
	private JTextField txtNerd_6;
	private JTextField txtAluno;
	private JTextField txtMigu_2;
	private JTextField txtMigu;
	private JTextField txtMigu_6;
	private JTextField txtTrabalhador_3;
	private JTextField txtTrabalhador_2;
	private JTextField txtNerd_3;
	private JTextField textField_27;
	private JTextField textField_28;
	private JTextField txtPergunta;
	private JTextField txtViajando;
	private JTextField txtForaDeSala;
	private JTextField txtTempoRestante;
	private JTextField txtNerd_7;
	private JTextField txtMigu_8;
	private JTextField txtPalestrinha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("NERD 😀");
					Tela window = new Tela();
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
	public Tela() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 937, 506);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtPalestrinha = new JTextField();
		txtPalestrinha.setText("Palestrinha");
		txtPalestrinha.setHorizontalAlignment(SwingConstants.CENTER);
		txtPalestrinha.setEditable(false);
		txtPalestrinha.setColumns(10);
		txtPalestrinha.setBackground(UIManager.getColor("Button.select"));
		txtPalestrinha.setBounds(325, 221, 114, 18);
		frame.getContentPane().add(txtPalestrinha);
		
		txtMigu_8 = new JTextField();
		txtMigu_8.setText("Migué");
		txtMigu_8.setHorizontalAlignment(SwingConstants.CENTER);
		txtMigu_8.setEditable(false);
		txtMigu_8.setColumns(10);
		txtMigu_8.setBackground(UIManager.getColor("Button.select"));
		txtMigu_8.setBounds(173, 221, 114, 18);
		frame.getContentPane().add(txtMigu_8);
		
		txtNerd_7 = new JTextField();
		txtNerd_7.setText("NERD");
		txtNerd_7.setHorizontalAlignment(SwingConstants.CENTER);
		txtNerd_7.setEditable(false);
		txtNerd_7.setColumns(10);
		txtNerd_7.setBackground(UIManager.getColor("Button.select"));
		txtNerd_7.setBounds(24, 221, 114, 18);
		frame.getContentPane().add(txtNerd_7);
		
		txtForaDeSala = new JTextField();
		txtForaDeSala.setText("Fora de Sala");
		txtForaDeSala.setHorizontalAlignment(SwingConstants.CENTER);
		txtForaDeSala.setEditable(false);
		txtForaDeSala.setColumns(10);
		txtForaDeSala.setBackground(new Color(238, 232, 170));
		txtForaDeSala.setBounds(325, 178, 114, 18);
		frame.getContentPane().add(txtForaDeSala);
		
		txtViajando = new JTextField();
		txtViajando.setText("Viajando");
		txtViajando.setHorizontalAlignment(SwingConstants.CENTER);
		txtViajando.setEditable(false);
		txtViajando.setColumns(10);
		txtViajando.setBackground(new Color(143, 188, 143));
		txtViajando.setBounds(173, 178, 114, 18);
		frame.getContentPane().add(txtViajando);
		
		txtPergunta = new JTextField();
		txtPergunta.setBackground(new Color(102, 205, 170));
		txtPergunta.setEditable(false);
		txtPergunta.setHorizontalAlignment(SwingConstants.CENTER);
		txtPergunta.setText("Pergunta");
		txtPergunta.setBounds(24, 178, 114, 18);
		frame.getContentPane().add(txtPergunta);
		txtPergunta.setColumns(10);
		
		txtProfessora = new JTextField();
		txtProfessora.setEditable(false);
		txtProfessora.setHorizontalAlignment(SwingConstants.CENTER);
		txtProfessora.setText("PROFESSORA");
		txtProfessora.setBounds(204, 12, 538, 50);
		frame.getContentPane().add(txtProfessora);
		txtProfessora.setColumns(10);
		
		txtContedoInteressante = new JTextField();
		txtContedoInteressante.setText("CONTEÚDO INTERESSANTE");
		txtContedoInteressante.setHorizontalAlignment(SwingConstants.CENTER);
		txtContedoInteressante.setEditable(false);
		txtContedoInteressante.setColumns(10);
		txtContedoInteressante.setBounds(204, 74, 538, 58);
		frame.getContentPane().add(txtContedoInteressante);
		
		txtMigu_1 = new JTextField();
		txtMigu_1.setText("Migué");
		txtMigu_1.setHorizontalAlignment(SwingConstants.CENTER);
		txtMigu_1.setEditable(false);
		txtMigu_1.setColumns(10);
		txtMigu_1.setBounds(12, 361, 139, 46);
		frame.getContentPane().add(txtMigu_1);
		
		txtMigu_3 = new JTextField();
		txtMigu_3.setText("Migué");
		txtMigu_3.setHorizontalAlignment(SwingConstants.CENTER);
		txtMigu_3.setEditable(false);
		txtMigu_3.setColumns(10);
		txtMigu_3.setBounds(161, 361, 139, 46);
		frame.getContentPane().add(txtMigu_3);
		
		textField_2 = new JTextField();
		textField_2.setText("Palestrinha");
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(312, 361, 139, 46);
		frame.getContentPane().add(textField_2);
		
		txtNerd_4 = new JTextField();
		txtNerd_4.setText("NERD");
		txtNerd_4.setHorizontalAlignment(SwingConstants.CENTER);
		txtNerd_4.setEditable(false);
		txtNerd_4.setColumns(10);
		txtNerd_4.setBounds(786, 361, 139, 46);
		frame.getContentPane().add(txtNerd_4);
		
		textField_6 = new JTextField();
		textField_6.setText("Palestrinha");
		textField_6.setHorizontalAlignment(SwingConstants.CENTER);
		textField_6.setEditable(false);
		textField_6.setColumns(10);
		textField_6.setBounds(635, 361, 139, 46);
		frame.getContentPane().add(textField_6);
		
		txtMigu_5 = new JTextField();
		txtMigu_5.setText("Migué");
		txtMigu_5.setHorizontalAlignment(SwingConstants.CENTER);
		txtMigu_5.setEditable(false);
		txtMigu_5.setColumns(10);
		txtMigu_5.setBounds(484, 361, 139, 46);
		frame.getContentPane().add(txtMigu_5);
		
		textField = new JTextField();
		textField.setText("Palestrinha");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(786, 303, 139, 46);
		frame.getContentPane().add(textField);
		
		txtMigu_4 = new JTextField();
		txtMigu_4.setText("Migué");
		txtMigu_4.setHorizontalAlignment(SwingConstants.CENTER);
		txtMigu_4.setEditable(false);
		txtMigu_4.setColumns(10);
		txtMigu_4.setBounds(635, 303, 139, 46);
		frame.getContentPane().add(txtMigu_4);
		
		txtNerd_1 = new JTextField();
		txtNerd_1.setText("NERD");
		txtNerd_1.setHorizontalAlignment(SwingConstants.CENTER);
		txtNerd_1.setEditable(false);
		txtNerd_1.setColumns(10);
		txtNerd_1.setBounds(484, 303, 139, 46);
		frame.getContentPane().add(txtNerd_1);
		
		txtNerd_2 = new JTextField();
		txtNerd_2.setText("NERD");
		txtNerd_2.setHorizontalAlignment(SwingConstants.CENTER);
		txtNerd_2.setEditable(false);
		txtNerd_2.setColumns(10);
		txtNerd_2.setBounds(312, 303, 139, 46);
		frame.getContentPane().add(txtNerd_2);
		
		textField_9 = new JTextField();
		textField_9.setText("Palestrinha");
		textField_9.setHorizontalAlignment(SwingConstants.CENTER);
		textField_9.setEditable(false);
		textField_9.setColumns(10);
		textField_9.setBounds(161, 303, 139, 46);
		frame.getContentPane().add(textField_9);
		
		txtTrabalhador_1 = new JTextField();
		txtTrabalhador_1.setText("Trabalhador");
		txtTrabalhador_1.setHorizontalAlignment(SwingConstants.CENTER);
		txtTrabalhador_1.setEditable(false);
		txtTrabalhador_1.setColumns(10);
		txtTrabalhador_1.setBounds(12, 303, 139, 46);
		frame.getContentPane().add(txtTrabalhador_1);
		
		txtMigu_7 = new JTextField();
		txtMigu_7.setText("Migué");
		txtMigu_7.setHorizontalAlignment(SwingConstants.CENTER);
		txtMigu_7.setEditable(false);
		txtMigu_7.setColumns(10);
		txtMigu_7.setBounds(786, 245, 139, 46);
		frame.getContentPane().add(txtMigu_7);
		
		txtNerd_5 = new JTextField();
		txtNerd_5.setText("NERD");
		txtNerd_5.setHorizontalAlignment(SwingConstants.CENTER);
		txtNerd_5.setEditable(false);
		txtNerd_5.setColumns(10);
		txtNerd_5.setBounds(635, 245, 139, 46);
		frame.getContentPane().add(txtNerd_5);
		
		txtTrabalhador_4 = new JTextField();
		txtTrabalhador_4.setText("Trabalhador");
		txtTrabalhador_4.setHorizontalAlignment(SwingConstants.CENTER);
		txtTrabalhador_4.setEditable(false);
		txtTrabalhador_4.setColumns(10);
		txtTrabalhador_4.setBounds(484, 245, 139, 46);
		frame.getContentPane().add(txtTrabalhador_4);
		
		txtNerd = new JTextField();
		txtNerd.setText("NERD");
		txtNerd.setHorizontalAlignment(SwingConstants.CENTER);
		txtNerd.setEditable(false);
		txtNerd.setColumns(10);
		txtNerd.setBounds(312, 245, 139, 46);
		frame.getContentPane().add(txtNerd);
		
		txtTrabalhador = new JTextField();
		txtTrabalhador.setText("Trabalhador");
		txtTrabalhador.setHorizontalAlignment(SwingConstants.CENTER);
		txtTrabalhador.setEditable(false);
		txtTrabalhador.setColumns(10);
		txtTrabalhador.setBounds(161, 245, 139, 46);
		frame.getContentPane().add(txtTrabalhador);
		
		textField_16 = new JTextField();
		textField_16.setText("Palestrinha");
		textField_16.setHorizontalAlignment(SwingConstants.CENTER);
		textField_16.setEditable(false);
		textField_16.setColumns(10);
		textField_16.setBounds(12, 245, 139, 46);
		frame.getContentPane().add(textField_16);
		
		textField_17 = new JTextField();
		textField_17.setText("Palestrinha");
		textField_17.setHorizontalAlignment(SwingConstants.CENTER);
		textField_17.setEditable(false);
		textField_17.setColumns(10);
		textField_17.setBounds(786, 187, 139, 46);
		frame.getContentPane().add(textField_17);
		
		textField_18 = new JTextField();
		textField_18.setText("Palestrinha");
		textField_18.setHorizontalAlignment(SwingConstants.CENTER);
		textField_18.setEditable(false);
		textField_18.setColumns(10);
		textField_18.setBounds(635, 187, 139, 46);
		frame.getContentPane().add(textField_18);
		
		txtNerd_6 = new JTextField();
		txtNerd_6.setText("NERD");
		txtNerd_6.setHorizontalAlignment(SwingConstants.CENTER);
		txtNerd_6.setEditable(false);
		txtNerd_6.setColumns(10);
		txtNerd_6.setBounds(484, 187, 139, 46);
		frame.getContentPane().add(txtNerd_6);
		
		txtAluno = new JTextField();
		txtAluno.setText("Aluno 3");
		txtAluno.setHorizontalAlignment(SwingConstants.CENTER);
		txtAluno.setEditable(false);
		txtAluno.setColumns(10);
		txtAluno.setBounds(312, 187, 139, 46);
		frame.getContentPane().add(txtAluno);
		
		txtMigu_2 = new JTextField();
		txtMigu_2.setText("Aluno 2");
		txtMigu_2.setHorizontalAlignment(SwingConstants.CENTER);
		txtMigu_2.setEditable(false);
		txtMigu_2.setColumns(10);
		txtMigu_2.setBounds(161, 187, 139, 46);
		frame.getContentPane().add(txtMigu_2);
		
		txtMigu = new JTextField();
		txtMigu.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtMigu.setText("Aluno 1");
		txtMigu.setHorizontalAlignment(SwingConstants.CENTER);
		txtMigu.setEditable(false);
		txtMigu.setColumns(10);
		txtMigu.setBounds(12, 187, 139, 46);
		frame.getContentPane().add(txtMigu);
		
		txtMigu_6 = new JTextField();
		txtMigu_6.setText("Migué");
		txtMigu_6.setHorizontalAlignment(SwingConstants.CENTER);
		txtMigu_6.setEditable(false);
		txtMigu_6.setColumns(10);
		txtMigu_6.setBounds(786, 419, 139, 46);
		frame.getContentPane().add(txtMigu_6);
		
		txtTrabalhador_3 = new JTextField();
		txtTrabalhador_3.setText("Trabalhador");
		txtTrabalhador_3.setHorizontalAlignment(SwingConstants.CENTER);
		txtTrabalhador_3.setEditable(false);
		txtTrabalhador_3.setColumns(10);
		txtTrabalhador_3.setBounds(635, 419, 139, 46);
		frame.getContentPane().add(txtTrabalhador_3);
		
		txtTrabalhador_2 = new JTextField();
		txtTrabalhador_2.setText("Trabalhador");
		txtTrabalhador_2.setHorizontalAlignment(SwingConstants.CENTER);
		txtTrabalhador_2.setEditable(false);
		txtTrabalhador_2.setColumns(10);
		txtTrabalhador_2.setBounds(484, 419, 139, 46);
		frame.getContentPane().add(txtTrabalhador_2);
		
		txtNerd_3 = new JTextField();
		txtNerd_3.setText("NERD");
		txtNerd_3.setHorizontalAlignment(SwingConstants.CENTER);
		txtNerd_3.setEditable(false);
		txtNerd_3.setColumns(10);
		txtNerd_3.setBounds(312, 419, 139, 46);
		frame.getContentPane().add(txtNerd_3);
		
		textField_27 = new JTextField();
		textField_27.setText("Palestrinha");
		textField_27.setHorizontalAlignment(SwingConstants.CENTER);
		textField_27.setEditable(false);
		textField_27.setColumns(10);
		textField_27.setBounds(161, 419, 139, 46);
		frame.getContentPane().add(textField_27);
		
		textField_28 = new JTextField();
		textField_28.setText("Palestrinha");
		textField_28.setHorizontalAlignment(SwingConstants.CENTER);
		textField_28.setEditable(false);
		textField_28.setColumns(10);
		textField_28.setBounds(12, 419, 139, 46);
		frame.getContentPane().add(textField_28);
		
		txtTempoRestante = new JTextField();
		txtTempoRestante.setFont(txtTempoRestante.getFont().deriveFont(txtTempoRestante.getFont().getStyle() | Font.BOLD));
		txtTempoRestante.setText("Tempo Restante: 3 min");
		txtTempoRestante.setHorizontalAlignment(SwingConstants.CENTER);
		txtTempoRestante.setEditable(false);
		txtTempoRestante.setColumns(10);
		txtTempoRestante.setBounds(25, 56, 154, 50);
		frame.getContentPane().add(txtTempoRestante);
	}
}
