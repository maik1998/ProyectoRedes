package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import logica.Fragmentar;
import logica.Operacion;

import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextArea;

public class AppPrincipal {

	private JFrame frame;
	private JTextField tfmtu;
	private JTextField tflongitud;
	private JTextField tfiporigen;
	private JTextField tfipdestino;
	
	public Fragmentar fragmentar = null;
	public Operacion operacion = null;
	private JTextArea tahexadecimal;
	private JTextArea tabinario;
	private JTextArea taentendible;

	/**
	 * Launch the application.
	 * Aqui inicializa la aplicacion.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppPrincipal window = new AppPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * AQUI SE TIENE EN CUENTA LA INSTANCIA CON LA CLASE Fragmentar Y Operacion.
	 */
	public AppPrincipal() {
		fragmentar = new Fragmentar();
		operacion = new Operacion();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * AQUI VA TODO LO RELACIONADO CON EL DISEÑO DE LA APLICACION
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(10, 10, 1300, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Construci\u00F3n y Fragmentaci\u00F3n de Datagramas IPv4");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel.setBounds(88, 11, 334, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Unidad m\u00E1xima de transferencia de la red (MTU)");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(16, 36, 251, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Longitud total del datagrama");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(109, 71, 158, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Direcci\u00F3n IP Origen");
		lblNewLabel_3.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNewLabel_3.setBounds(154, 147, 113, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Direcci\u00F3n IP Destino");
		lblNewLabel_4.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNewLabel_4.setBounds(154, 183, 113, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		tfmtu = new JTextField();
		tfmtu.setBounds(277, 36, 145, 20);
		frame.getContentPane().add(tfmtu);
		tfmtu.setColumns(10);
		
		tflongitud = new JTextField();
		tflongitud.setBounds(277, 68, 145, 20);
		frame.getContentPane().add(tflongitud);
		tflongitud.setColumns(10);
		
		tfiporigen = new JTextField();
		tfiporigen.setBounds(277, 144, 145, 20);
		frame.getContentPane().add(tfiporigen);
		tfiporigen.setColumns(10);
		
		tfipdestino = new JTextField();
		tfipdestino.setBounds(277, 180, 145, 20);
		frame.getContentPane().add(tfipdestino);
		tfipdestino.setColumns(10);
		
		JRadioButton rbudp = new JRadioButton("UDP");
		rbudp.setFont(new Font("Arial", Font.PLAIN, 11));
		rbudp.setBounds(359, 105, 63, 23);
		frame.getContentPane().add(rbudp);
		
		JRadioButton rbicmp = new JRadioButton("ICMP");
		rbicmp.setFont(new Font("Arial", Font.PLAIN, 11));
		rbicmp.setBounds(187, 105, 63, 23);
		frame.getContentPane().add(rbicmp);
		
		JRadioButton rbtcp = new JRadioButton("TCP");
		rbtcp.setFont(new Font("Arial", Font.PLAIN, 11));
		rbtcp.setBounds(276, 105, 63, 23);
		frame.getContentPane().add(rbtcp);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(444, 11, 10, 280);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBounds(16, 289, 425, 14);
		frame.getContentPane().add(separator_1);
		
		JLabel lblNewLabel_5 = new JLabel("Encabexado del Datagrama IP en Hexadecimal");
		lblNewLabel_5.setBounds(604, 11, 295, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		tahexadecimal = new JTextArea();
		tahexadecimal.setBackground(new Color(255, 255, 255));
		tahexadecimal.setFont(new Font("Monospaced", Font.PLAIN, 13));
		tahexadecimal.setForeground(Color.BLACK);
		tahexadecimal.setBounds(476, 31, 775, 116);
		JScrollPane scrollPane = new JScrollPane(tahexadecimal);
		scrollPane.setBounds(476, 31, 775, 116);
		frame.getContentPane().add(scrollPane);
		//frame.getContentPane().add(tahexadecimal);
		
		JLabel lblNewLabel_6 = new JLabel("Encabezado del Datagrama IP en Binario");
		lblNewLabel_6.setBounds(600, 158, 295, 14);
		frame.getContentPane().add(lblNewLabel_6);
		
		tabinario = new JTextArea();
		tabinario.setBounds(476, 178, 775, 113);
		JScrollPane scrollPane2 = new JScrollPane(tabinario);
		scrollPane2.setBounds(476, 178, 775, 113);
		frame.getContentPane().add(scrollPane2);
		//frame.getContentPane().add(tabinario);
		
		JLabel lblNewLabel_7 = new JLabel("Encabezado del datagrama IP de manera Wireshark");
		lblNewLabel_7.setBounds(109, 314, 383, 14);
		frame.getContentPane().add(lblNewLabel_7);
		
		taentendible = new JTextArea();
		taentendible.setBounds(16, 345, 590, 285);
		JScrollPane scrollPane3 = new JScrollPane(taentendible);
		scrollPane3.setBounds(16, 345, 590, 285);
		frame.getContentPane().add(scrollPane3);
		
		JButton btcalcular = new JButton("Calcular");
		btcalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TOMA LOS DATOS DE ENTRADA Y ENVIA AL METODO dato DE LA CLASE Operacion
				// LUEGO ORGANIZA EL MENSAJE PARA IMPRIMIR EN TEXTAREA PARA HEXADECIMAL, BINARIO Y EN MODO ENTENDIBLE SIMILAR A WIRESHARK
				
				tahexadecimal.setText("");
				tabinario.setText("");
				taentendible.setText("");
				
				String mtu = "";
				String tamDatagrama = "";
				String protocolo = "";
				String iporigen = "";
				String ipdestino = "";
				
				mtu = tfmtu.getText();
				fragmentar.setMtu(mtu);
				tamDatagrama = tflongitud.getText();
				fragmentar.setTamDatagrama(tamDatagrama);
				if(rbicmp.isSelected() == true) {
					protocolo = rbicmp.getText();
					fragmentar.setProtocolo(protocolo);
				}
				if(rbtcp.isSelected() == true) {
					protocolo = rbtcp.getText();
					fragmentar.setProtocolo(protocolo);
				}
				if(rbudp.isSelected() == true) {
					protocolo = rbudp.getText();
					fragmentar.setProtocolo(protocolo);
				}
				iporigen = tfiporigen.getText();
				fragmentar.setIporigen(iporigen);
				ipdestino = tfipdestino.getText();
				fragmentar.setIpdestino(ipdestino);
				
				operacion.datos(mtu, tamDatagrama, protocolo, iporigen, ipdestino);
				
				String msjHexa = "";
				String hexadecimal[] = operacion.datagramaipHexadecimal;
				for(int i=0;i<hexadecimal.length;i++) {
					msjHexa += "Datagrama IP #" + (i+1) + ": \n";
					msjHexa += hexadecimal[i] + "\n\n";
				}
				tahexadecimal.setText(msjHexa);
				
				String msjBin = "";
				String binario[] =  ordenarDatagramaIPBinario(operacion.datagramaipBinario);
				int j = 0, k = 0;
				for(int i=0;i<operacion.datagramaipBinario.length;i++) {
					msjBin += "Datagrama IP #" + (i+1) + ": \n";
					while(j<5) {
						msjBin += binario[k] + "\n";
						j++;
						k++;
					}
					j = 0;
					msjBin += "\n";
				}
				tabinario.setText(msjBin);
				
				String msj = operacion.imprime();
				taentendible.setText(msj);
				//JOptionPane.showMessageDialog(null, msj);
			}

			public String[] ordenarDatagramaIPBinario(String[] datagramaipBinario) {
				// Organizar el datagrama IP Binario en 32 bits por linea
				String arregloBinario[] = new String[5 * datagramaipBinario.length];
				int i = 0, j = 0, inicial = 0, end = 39;
				String aux = "";
				while(i < datagramaipBinario.length) {
					int k = 0;
					while(k < 5) {
						aux = datagramaipBinario[i].substring(inicial, end);
						arregloBinario[j] = aux;
						inicial += 40;
						end += 40;
						j++;
						k++;
					}
					inicial = 0;
					end = 39;
					i++;
				}
				
				return arregloBinario;
			}
		});
		btcalcular.setFont(new Font("Arial", Font.BOLD, 11));
		btcalcular.setBounds(109, 234, 141, 23);
		frame.getContentPane().add(btcalcular);
		
		JButton btaleatorio = new JButton("Aleatorio");
		btaleatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// AQUI VA DONDE SE DEBE ORGANIZAR EL EJERCICIO DE FORMA QUE LAS ENTRADAS DE DATOS DEBEN SER ALEATORIOS
				// Y ADEMAS SE DEBERA RESOLVER TAL CUAL COMO SE HIZO CON EL ANTERIOR.
			}
		});
		btaleatorio.setFont(new Font("Arial", Font.BOLD, 11));
		btaleatorio.setBounds(288, 234, 134, 23);
		frame.getContentPane().add(btaleatorio);
		
		String integrantes = "Integrantes del proyecto: \n\n";
		integrantes += "Juan David Moreno Cifuentes \n";
		integrantes += "Anjully Tatiana Mora Acosta \n";
		integrantes += "Jean Michael Mendoza \n";
		integrantes += "Andres Felipe Rincon";
		
		JTextArea taintegrantes = new JTextArea(integrantes);
		taintegrantes.setEditable(false);
		taintegrantes.setForeground(Color.BLACK);
		taintegrantes.setFont(new Font("Arial", Font.BOLD, 14));
		taintegrantes.setEnabled(true);
		taintegrantes.setBounds(774, 395, 233, 124);
		frame.getContentPane().add(taintegrantes);
	}
}
