package interfaz;

import java.util.Scanner;

import logica.Fragmentar;
import logica.Operacion;

public class App {
	
	public static Fragmentar fragmentar = null;
	public static Operacion operacion = null;

	public static void main(String[] args) {
		String titulo = "Aplicacion de fragmentacion de datagramas:\n";
		System.out.println(titulo);
		fragmentar = new Fragmentar();
		operacion = new Operacion();
		entradaDeDatos();
		operacion.datos();
	}

	public static void entradaDeDatos() {
		int i = 0;
		String pregunta = "";
		String mtu = "";
		String tamDatagrama = "";
		String protocolo = "";
		String iporigen = "";
		String ipdestino = "";
		while(i<5) {
			if(i==0) {
				pregunta = "MTU: ";
				System.out.print(pregunta);
				Scanner entrada = new Scanner(System.in);
				mtu = entrada.nextLine();
				fragmentar.setMtu(mtu);
				System.out.print(fragmentar.getMtu());
			}
			if(i==1) {
				pregunta = "Longitud total del datagrama: ";
				System.out.print(pregunta);
				Scanner entrada = new Scanner(System.in);
				tamDatagrama = entrada.nextLine();
				fragmentar.setTamDatagrama(tamDatagrama);
			}
			if(i==2) {
				pregunta = "Protocolo (Por favor digite cualquiera de las 3 opciones ICMP, TCP O UDP): ";
				System.out.print(pregunta);
				Scanner entrada = new Scanner(System.in);
				protocolo = entrada.nextLine();
				fragmentar.setProtocolo(protocolo);
			}
			if(i==3) {
				pregunta = "Direccion IP Origen: ";
				System.out.print(pregunta);
				Scanner entrada = new Scanner(System.in);
				iporigen = entrada.nextLine();
				fragmentar.setIporigen(iporigen);
			}
			if(i==4) {
				pregunta = "Direccion IP Destino: ";
				System.out.print(pregunta);
				Scanner entrada = new Scanner(System.in);
				ipdestino = entrada.nextLine();
				fragmentar.setIpdestino(ipdestino);
			}
			//System.out.print(cadena);
			i++;
		}
	}

}
