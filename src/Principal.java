import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;

public class Principal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	private JPanel panel;
	private JTextField tfIdentificadorProcesso;
	private JTextField tfTempoSolicitacaoProcesso;
	private JTextField tfTempoUtilizacaoProcesso;
	private JTextField tfNomeRecurso;
	public static JTextField STATUS;
	private JTextField tfIdentificadorRecurso;
	private JTextField tftfinstanciasRecurso;
	private JTextField tfTempoVerificacao;
	Random gerador = new Random();
	public static Recurso[] rec = new Recurso[10];
	public static Processo[] proc = new Processo[15];
	public static int contNum = 0;
	public static JTable tableProcesso;
	public static JTable tableRecurso;
	public static int solicitacaoAtualRecurso = 0;
	public static int solicitacaoAtualProcesso = 0;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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

	public Principal() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 600);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);
		
		JLabel lblSistemaOperacionalsOperacionaisPart = new JLabel("Sistemas Operacionais");
		lblSistemaOperacionalsOperacionaisPart.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSistemaOperacionalsOperacionaisPart.setForeground(Color.RED);
		lblSistemaOperacionalsOperacionaisPart.setBounds(135, 0, 285, 35);
		panel.add(lblSistemaOperacionalsOperacionaisPart);
		
		
		JLabel lblProcessos = new JLabel("PROCESSO");
		lblProcessos.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProcessos.setBounds(10, 80, 80, 14);
		panel.add(lblProcessos);
		
		JLabel lblRecurso = new JLabel("RECURSO");
		lblRecurso.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRecurso.setBounds(127, 80, 98, 14);
		panel.add(lblRecurso);
		
		JLabel lblSistemaOperacional = new JLabel("SISTEMA OPERACIONAL");
		lblSistemaOperacional.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSistemaOperacional.setBounds(311, 80, 80, 14);
		panel.add(lblSistemaOperacional);
		
		tfIdentificadorProcesso = new JTextField(String.valueOf(solicitacaoAtualProcesso));
		tfIdentificadorProcesso.setBounds(10, 101, 46, 20);
		panel.add(tfIdentificadorProcesso);
		tfIdentificadorProcesso.setColumns(10);
		
		tfTempoSolicitacaoProcesso = new JTextField();
		tfTempoSolicitacaoProcesso.setBounds(10, 132, 46, 20);
		panel.add(tfTempoSolicitacaoProcesso);
		tfTempoSolicitacaoProcesso.setColumns(10);
		
		tfTempoUtilizacaoProcesso = new JTextField();
		tfTempoUtilizacaoProcesso.setBounds(10, 163, 46, 20);
		panel.add(tfTempoUtilizacaoProcesso);
		tfTempoUtilizacaoProcesso.setColumns(10);
		
		JLabel lbIdentificador = new JLabel("ID");
		lbIdentificador.setBounds(66, 105, 46, 14);
		panel.add(lbIdentificador);
		
		JLabel lblTempoSolicitacao = new JLabel("TS");
		lblTempoSolicitacao.setBounds(66, 135, 46, 14);
		panel.add(lblTempoSolicitacao);
		
		JLabel lblTempoUtilizacao = new JLabel("TU");
		lblTempoUtilizacao.setBounds(66, 166, 46, 14);
		panel.add(lblTempoUtilizacao);
		
		JButton btnEnviar = new JButton("START");
		btnEnviar.setBounds(10, 191, 89, 23);
        btnEnviar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	int tempoSolicitacao = Integer.parseInt(tfTempoSolicitacaoProcesso.getText());
    			int tempoUtilizacao = Integer.parseInt(tfTempoUtilizacaoProcesso.getText());
    			Random gerador = new Random();
    			int r = gerador.nextInt(contNum);
    			proc[solicitacaoAtualProcesso] = new Processo(solicitacaoAtualProcesso, tempoSolicitacao, tempoUtilizacao, r);
    			proc[solicitacaoAtualProcesso].start();
    			tableProcesso.setValueAt(solicitacaoAtualProcesso, solicitacaoAtualProcesso, 0);
    			tableProcesso.setValueAt("-", solicitacaoAtualProcesso, 1);
    			solicitacaoAtualProcesso++;
    			tfIdentificadorProcesso.setText(String.valueOf(solicitacaoAtualProcesso));
    			tfTempoSolicitacaoProcesso.setText("");
    			tfTempoUtilizacaoProcesso.setText("");
            }
        });
        panel.add(btnEnviar);
        
		
		tfNomeRecurso = new JTextField();
		tfNomeRecurso.setBounds(127, 101, 86, 20);
		panel.add(tfNomeRecurso);
		tfNomeRecurso.setColumns(10);
		
		tfIdentificadorRecurso = new JTextField(String.valueOf(solicitacaoAtualRecurso));
		tfIdentificadorRecurso.setBounds(127, 132, 86, 20);
		panel.add(tfIdentificadorRecurso);
		tfIdentificadorRecurso.setColumns(10);
		
		tftfinstanciasRecurso = new JTextField();
		tftfinstanciasRecurso.setBounds(127, 163, 86, 20);
		panel.add(tftfinstanciasRecurso);
		tftfinstanciasRecurso.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(223, 104, 50, 14);
		panel.add(lblNome);
		
		JLabel lbIdentificador_1 = new JLabel("ID");
		lbIdentificador_1.setBounds(223, 135, 46, 14);
		panel.add(lbIdentificador_1);
		
		JLabel lblQuantidade = new JLabel("Instancias");
		lblQuantidade.setBounds(223, 166, 70, 14);
		panel.add(lblQuantidade);
		
		JButton btnCadastrar = new JButton("Configurar");
		btnCadastrar.setBounds(124, 191, 120, 23);
		btnCadastrar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	String nome = tfNomeRecurso.getText();
    			int quant = Integer.parseInt(tftfinstanciasRecurso.getText());
    			rec[solicitacaoAtualRecurso]= new Recurso(nome, solicitacaoAtualRecurso, quant);
    			rec[solicitacaoAtualRecurso].nome = nome;
    			rec[solicitacaoAtualRecurso].instanciasDoRecurso = quant;
    			tableRecurso.setValueAt(nome, solicitacaoAtualRecurso, 0);
    			tableRecurso.setValueAt(quant, solicitacaoAtualRecurso, 1);
    			tableRecurso.setValueAt(quant, solicitacaoAtualRecurso, 2);
				contNum++;
				solicitacaoAtualRecurso++;
				tfIdentificadorRecurso.setText(String.valueOf(solicitacaoAtualRecurso));
    			tfNomeRecurso.setText("");
    			tftfinstanciasRecurso.setText("");
    			
            }
        });
        panel.add(btnCadastrar);
		
		tfTempoVerificacao = new JTextField();
		tfTempoVerificacao.setBounds(311, 105, 86, 20);
		panel.add(tfTempoVerificacao);
		tfTempoVerificacao.setColumns(10);
		
		JLabel lblAt = new JLabel("AT");
		lblAt.setBounds(407, 107, 46, 14);
		panel.add(lblAt);
		
		JButton btnIr = new JButton("IR");
		btnIr.setBounds(435, 105, 46, 23);
		btnIr.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	int at = Integer.parseInt(tfTempoVerificacao.getText());
    			Sistema O= new Sistema(at);
    			O.start();
    			tfTempoVerificacao.setText("");
            }
        });
        panel.add(btnIr);
        
        JLabel T_STATUS = new JLabel("STATUS DO SISTEMA");
        T_STATUS.setForeground(Color.RED);
        T_STATUS.setFont(new Font("Tahoma", Font.BOLD, 14));
        T_STATUS.setBounds(310, 155, 170, 20);
		panel.add(T_STATUS);
        
        STATUS = new JTextField(Sistema.getStatus());
        STATUS.setBounds(310, 175, 170, 20);
		panel.add(STATUS);
		STATUS.setColumns(15);
        
        JLabel lblRecursos = new JLabel("RECURSOS");
        lblRecursos.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblRecursos.setBounds(84, 235, 98, 14);
        panel.add(lblRecursos);
        
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 260, 234, 188);
        panel.add(scrollPane);
        
        tableRecurso = new JTable();
        scrollPane.setViewportView(tableRecurso);
		tableRecurso.setToolTipText("");
		tableRecurso.setFont(new Font("Tahoma", Font.BOLD, 11));
		tableRecurso.setBackground(Color.WHITE);
		tableRecurso.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
					"NOME", "E=[ ]", "A=[ ]"
			}
		));
		tableRecurso.getColumnModel().getColumn(0).setPreferredWidth(78);
		tableRecurso.getColumnModel().getColumn(0).setMinWidth(18);
		tableRecurso.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		scrollPane.setViewportView(tableRecurso);
		
		
		JScrollPane scrollPaneP = new JScrollPane();
        scrollPaneP.setBounds(250, 260, 234, 188);
        panel.add(scrollPaneP);
        
        tableProcesso = new JTable();
        scrollPaneP.setViewportView(tableProcesso);
        tableProcesso.setToolTipText("");
        tableProcesso.setFont(new Font("Tahoma", Font.BOLD, 11));
        tableProcesso.setBackground(Color.WHITE);
        tableProcesso.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
					"PROCESSO", "STATUS"
			}
		));
        tableProcesso.getColumnModel().getColumn(0).setPreferredWidth(78);
        tableProcesso.getColumnModel().getColumn(0).setMinWidth(18);
        tableProcesso.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		scrollPaneP.setViewportView(tableProcesso);
      
      
       
		
		
	}
}
