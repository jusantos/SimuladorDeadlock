import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

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
	public static int contNum = 0;
	public static JTable tableProcesso;
	public static JTable tableRecurso;

	public Sistema sistema = new Sistema(2);

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
		lblSistemaOperacional.setBounds(311, 80, 160, 14);
		panel.add(lblSistemaOperacional);

		tfIdentificadorProcesso = new JTextField("1");
		tfIdentificadorProcesso.setBounds(10, 101, 46, 20);
		panel.add(tfIdentificadorProcesso);
		tfIdentificadorProcesso.setEnabled(false);
		tfIdentificadorProcesso.setColumns(10);

		tfTempoSolicitacaoProcesso = new JTextField("5");
		tfTempoSolicitacaoProcesso.setBounds(10, 132, 46, 20);
		panel.add(tfTempoSolicitacaoProcesso);
		tfTempoSolicitacaoProcesso.setColumns(10);

		tfTempoUtilizacaoProcesso = new JTextField("100");
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

		tfNomeRecurso = new JTextField("Impressora");
		tfNomeRecurso.setBounds(127, 101, 86, 20);
		panel.add(tfNomeRecurso);
		tfNomeRecurso.setColumns(10);

		tfIdentificadorRecurso = new JTextField("1");
		tfIdentificadorRecurso.setBounds(127, 132, 86, 20);
		panel.add(tfIdentificadorRecurso);
		tfIdentificadorRecurso.setEnabled(false);
		tfIdentificadorRecurso.setColumns(10);

		tftfinstanciasRecurso = new JTextField("2");
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

		final JButton btnCadastrar = new JButton("Configurar");
		btnCadastrar.setBounds(124, 191, 120, 23);
		btnCadastrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					String nome = tfNomeRecurso.getText().trim();
					int instancias = Integer.parseInt(tftfinstanciasRecurso.getText().trim());
					Recurso recurso = new Recurso(nome, instancias);
					sistema.downMutex();
					sistema.adicionarRecurso(recurso);
					sistema.upMutex();
					System.out.println("Novo recurso criado: " + recurso.nome + ", com " + recurso.instancias + " instâncias");
				} catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Valor(es) inválido(s)!");
				}
			}
		});
		panel.add(btnCadastrar);

		JButton btnEnviar = new JButton("Criar");
		btnEnviar.setBounds(10, 191, 89, 23);
		btnEnviar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {

					int pid = Integer.parseInt(tfIdentificadorProcesso.getText());
					int tempoSolicitacao = Integer.parseInt(tfTempoSolicitacaoProcesso.getText());
					int tempoUtilizacao = Integer.parseInt(tfTempoUtilizacaoProcesso.getText());
					Processo processo = new Processo(pid, tempoSolicitacao, tempoUtilizacao, sistema);

					sistema.downMutex();
					sistema.adicionarProcesso(processo);
					sistema.upMutex();

					processo.start();

					System.out.println("Novo processo criado");
					tfIdentificadorProcesso.setText("" + (pid + 1));

					// Desabilitando a criação de recursos
					tfIdentificadorRecurso.setEnabled(false);
					tfNomeRecurso.setEnabled(false);
					tftfinstanciasRecurso.setEnabled(false);
					btnCadastrar.setEnabled(false);

				} catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Valor(es) inválido(s)!");
				}
			}
		});
		panel.add(btnEnviar);

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
				String tempo = tfTempoVerificacao.getText().trim();
				try {
					sistema.setIntervaloDeVerificacao(Integer.parseInt(tempo));
				} catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Valor inválido!");
				}
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

		// Fazendo o SO começar a rodar
		this.sistema.start();

	}
}
