import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

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

	private JPanel panelRecursosTotais, panelRecursosDisponiveis, panelRequisicoes;
	private JScrollPane panelLog;
	private JTextArea areaLog;

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
					desenharTabelaDeRecursosTotais();
					desenharTabelaDeRecursosDisponiveis();
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
					processo.principal = Principal.this;

					sistema.downMutex();
					sistema.adicionarProcesso(processo);
					sistema.upMutex();

					desenharTabelaDeRequisicoes();

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

		// Array E
		JLabel labelE = new JLabel("Recursos Totais");
		labelE.setBounds(10, 230, 500, 15);
		panel.add(labelE);

		this.panelRecursosTotais = new JPanel();
		this.panelRecursosTotais.setBounds(10, 250, 500, 30);
		this.panelRecursosTotais.setLayout(new BorderLayout());
		panel.add(this.panelRecursosTotais);

		JLabel labelA = new JLabel("Recursos Disponíveis");
		labelA.setBounds(10, 295, 500, 15);
		panel.add(labelA);

		this.panelRecursosDisponiveis = new JPanel();
		this.panelRecursosDisponiveis.setBounds(10, 310, 500, 30);
		this.panelRecursosDisponiveis.setLayout(new BorderLayout());
		panel.add(this.panelRecursosDisponiveis);

		JLabel labelR = new JLabel("Requisições");
		labelR.setBounds(10, 355, 500, 15);
		panel.add(labelR);

		this.panelRequisicoes = new JPanel();
		this.panelRequisicoes.setBounds(10, 375, 500, 30);
		this.panelRequisicoes.setLayout(new BorderLayout());
		panel.add(this.panelRequisicoes);

		JLabel labelL = new JLabel("Logs");
		labelL.setBounds(10, 415, 500, 20);
		panel.add(labelL);

		this.areaLog = new JTextArea();
		this.panelLog = new JScrollPane(this.areaLog);
		this.panelLog.setBounds(10, 435, 500, 80);
		this.panelLog.setEnabled(false);
		panel.add(this.panelLog);

		// Desenhando tabelas
		this.desenharTabelaDeRecursosTotais();
		this.desenharTabelaDeRecursosDisponiveis();
		this.desenharTabelaDeRequisicoes();

		// Fazendo o SO começar a rodar
		this.sistema.start();

	}

	public void desenharTabelaDeRecursosTotais() {
		String[] columnNames = new String[this.sistema.recursos.size()];
		Object[][] columnData = new Object[1][this.sistema.recursos.size()];
		for(int i = 0; i < this.sistema.recursos.size(); i++) {
			columnNames[i] = this.sistema.recursos.get(i).nome;
			columnData[0][i] = this.sistema.recursos.get(i).maxInstancias;
		}
		this.panelRecursosTotais.removeAll();
		JTable table = new JTable(columnData, columnNames);
		this.panelRecursosTotais.add(table.getTableHeader(), BorderLayout.PAGE_START);
		this.panelRecursosTotais.add(table, BorderLayout.CENTER);
		this.panelRecursosTotais.revalidate();
	}

	public void desenharTabelaDeRecursosDisponiveis() {
		String[] columnNames = new String[this.sistema.recursos.size()];
		Object[][] columnData = new Object[1][this.sistema.recursos.size()];
		for(int i = 0; i < this.sistema.recursos.size(); i++) {
			columnNames[i] = this.sistema.recursos.get(i).nome;
			columnData[0][i] = this.sistema.recursos.get(i).instancias;
		}
		this.panelRecursosDisponiveis.removeAll();
		JTable table = new JTable(columnData, columnNames);
		this.panelRecursosDisponiveis.add(table.getTableHeader(), BorderLayout.PAGE_START);
		this.panelRecursosDisponiveis.add(table, BorderLayout.CENTER);
		this.panelRecursosDisponiveis.revalidate();
	}

	public void desenharTabelaDeRequisicoes() {
		String[] columnNames = new String[this.sistema.processos.size()];
		Object[][] columnData = new Object[1][this.sistema.processos.size()];
		for(int i = 0; i < this.sistema.processos.size(); i++) {
			columnNames[i] = "P" + this.sistema.processos.get(i).pid;
			String r = this.sistema.processos.get(i).requisicaoCorrente >= 0 ? ("" + this.sistema.processos.get(i).requisicaoCorrente) : "-";
			columnData[0][i] = r;
		}
		this.panelRequisicoes.removeAll();
		JTable table = new JTable(columnData, columnNames);
		this.panelRequisicoes.add(table.getTableHeader(), BorderLayout.PAGE_START);
		this.panelRequisicoes.add(table, BorderLayout.CENTER);
		this.panelRequisicoes.revalidate();
	}

	public void log(String message) {
		this.areaLog.append(message + "\n");
	}

}
