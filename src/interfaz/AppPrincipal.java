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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AppPrincipal {

	private JFrame frame;
	private JTextField tfmtu;
	private JTextField tflongitud;
	private JTextField tfiporigen;
	private JTextField tfipdestino;
	
	public Fragmentar fragmentar = null;
	public Operacion operacion = null;

	/**
	 * Launch the application.
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
	 */
	public AppPrincipal() {
		fragmentar = new Fragmentar();
		operacion = new Operacion();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 510, 323);
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
		
		JButton btcalcular = new JButton("Calcular");
		btcalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
					//System.out.println("ICMP");
				}
				if(rbtcp.isSelected() == true) {
					protocolo = rbtcp.getText();
					fragmentar.setProtocolo(protocolo);
					//System.out.println("TCP");
				}
				if(rbudp.isSelected() == true) {
					protocolo = rbudp.getText();
					fragmentar.setProtocolo(protocolo);
					//System.out.println("UDP");
				}
				iporigen = tfiporigen.getText();
				fragmentar.setIporigen(iporigen);
				ipdestino = tfipdestino.getText();
				fragmentar.setIpdestino(ipdestino);
				
				operacion.datos(mtu, tamDatagrama, protocolo, iporigen, ipdestino);
				
				String msj = operacion.imprime();
				JOptionPane.showMessageDialog(null, msj);
			}
		});
		btcalcular.setFont(new Font("Arial", Font.BOLD, 11));
		btcalcular.setBounds(109, 234, 141, 23);
		frame.getContentPane().add(btcalcular);
		
		JButton btaleatorio = new JButton("Aleatorio");
		btaleatorio.setFont(new Font("Arial", Font.BOLD, 11));
		btaleatorio.setBounds(288, 234, 134, 23);
		frame.getContentPane().add(btaleatorio);
	}
	/*
	public void mensaje(String mtu, String tamDatagrama, String protocolo, String ipOrigen, String ipDestino, int numIdentificacion, int tiempoVida, int versionDatagrama, int longitudEncabezado, int servicioDiferenciado) {
		String texto = "Resultado Final:\n\n";
		texto += "El MTU es: " + mtu + "\n";
		texto += "Longitud total del datagrama es: " + tamDatagrama + "\n";
		texto +="El protocolo es: " + protocolo + "\n";
		texto += "Direccion IP Origen: " + ipOrigen + "\n";
		texto += "Direccion IP Destino: " + ipDestino + "\n";
		texto += "Numero de Identificacion: " + numIdentificacion + "\n";
		texto += "Tiempo de Vida: " + tiempoVida + "\n";
		texto += "Version del Datagrama: " + versionDatagrama + "\n";
		texto += "Longitud del encabezado: " + longitudEncabezado + "\n";
		texto += "Sevicios Diferenciados: " + servicioDiferenciado;
		JOptionPane.showInputDialog(texto);
	}
	*/
}
