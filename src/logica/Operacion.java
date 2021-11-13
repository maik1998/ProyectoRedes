package logica;

import interfaz.AppPrincipal;

public class Operacion {
	public String mtu = "";
	public String tamDatagrama = "";
	public String protocolo = "";
	public String iporigen = "";
	public String ipdestino = "";
	public int numIdentificacion = 0;
	public int tiempoVida = 0;
	public int versionDatagrama = 4;
	public int longitudEncabezado = 5;
	public int servicioDiferenciado = 0;
	public String sumaDeComprobacion = "";
	public String datagramaip = "";
	public Fragmentar fragmentar= new Fragmentar();
	//public AppPrincipal app;
	
	
	
	public Operacion() {
		super();
		//app = new AppPrincipal();
	}

	public void datos(String mtu, String tamDatagrama, String protocolo, String ipOrigen, String ipDestino) {
		this.mtu = mtu;
		this.tamDatagrama = tamDatagrama;
		this.protocolo = protocolo;
		this.iporigen = ipOrigen;
		this.ipdestino = ipDestino;
		this.numIdentificacion = (int) Math.floor(Math.random()*(65535 - 0 + 1) + 0);
		this.tiempoVida = (int) Math.floor(Math.random()*(255 - 0 + 1) + 0);
		
		//Variables a calcular
		this.datagramaip = "45 00 02 24 32 F9 00 B9 01 01 C0 A8 01 66 80 3B 17 64"; //Este datagrama es de prueba
		this.sumaDeComprobacion = sumaComprobacion(this.datagramaip);
		
		//app.mensaje(mtu, tamDatagrama, protocolo, ipOrigen, ipDestino, numIdentificacion, tiempoVida, versionDatagrama, longitudEncabezado, servicioDiferenciado);
		//imprime();
	}

	public String imprime() {
		String texto = "Resultado Final:\n\n";
		texto += "El MTU es: " + this.mtu + "\n";
		texto += "Longitud total del datagrama es: " + this.tamDatagrama + "\n";
		texto +="El protocolo es: " + this.protocolo + "\n";
		texto += "Direccion IP Origen: " + this.iporigen + "\n";
		texto += "Direccion IP Destino: " + this.ipdestino + "\n";
		texto += "Numero de Identificacion: " + this.numIdentificacion + "\n";
		texto += "Tiempo de Vida: " + tiempoVida + "\n";
		texto += "Version del Datagrama: " + this.versionDatagrama + "\n";
		texto += "Longitud del encabezado: " + this.longitudEncabezado + "\n";
		texto += "Sevicios Diferenciados: " + this.servicioDiferenciado + "\n";
		texto += "Suma de comprobacion: " + this.sumaDeComprobacion;
		/*
		System.out.println("El MTU es: " + mtu);
		System.out.println("Longitud total del datagrama es: " + tamDatagrama);
		System.out.println("El protocolo es: " + protocolo);
		System.out.println("Direccion IP Origen: " + iporigen);
		System.out.println("Direccion IP Destino: " + ipdestino);
		System.out.println("Numero de Identificacion: " + numIdentificacion);
		System.out.println("Tiempo de Vida: " + tiempoVida);
		System.out.println("Version del Datagrama: " + versionDatagrama);
		System.out.println("Longitud del encabezado: " + longitudEncabezado);
		System.out.println("Sevicios Diferenciados: " + servicioDiferenciado);
		*/
		return texto;
	}
	
	public String sumaComprobacion(String dato) {
		String respuesta = "";
		String separados[] = dato.split(" ");
		String[] arreglo = new String[9];
		
		int j = 0, i = 0;
		String aux = "";
		while(j<separados.length) {
			aux += separados[j];
			if(j==1 || j==3 || j==5 || j==7 || j==9 || j==11 || j==13 || j==15 || j==17) {
				arreglo[i] = aux;
				aux = "";
				i++;
			} 
			//System.out.println(separados[j]);
			j++;
		}
		//System.out.println(separados.length);
		
		int acarreo = 0, octeto1 = 0, octeto2 = 0;
		int auxiliar = 0;
		for(int w = 1 ; w < arreglo.length ; w++) {
			//System.out.println((w) + " ->  " + arreglo[w]);
			if(w==1) {
				octeto1 = Integer.parseInt(convertirHexaToDecimal(arreglo[0]));
				octeto2 = Integer.parseInt(convertirHexaToDecimal(arreglo[1]));
				auxiliar = octeto1 + octeto2;
				if(convertirDecimalToHexadecimal(String.valueOf(auxiliar)).length()>4) {
					acarreo = 1;
					auxiliar = Integer.parseInt(convertirHexaToDecimal(convertirDecimalToHexadecimal(String.valueOf(auxiliar)).substring(1)));
					auxiliar = auxiliar + acarreo;
					//System.out.println("acarreo 0 y 1:   = >     " + auxiliar);
				}
			} else {
				octeto1 = auxiliar;
				octeto2 = Integer.parseInt(convertirHexaToDecimal(arreglo[w]));
				auxiliar = octeto1 + octeto2;
				if(convertirDecimalToHexadecimal(String.valueOf(auxiliar)).length()>4) {
					acarreo = 1;
					auxiliar = Integer.parseInt(convertirHexaToDecimal(convertirDecimalToHexadecimal(String.valueOf(auxiliar)).substring(1)));
					auxiliar = auxiliar + acarreo;
				}
			}
			//System.out.println("sumas:   = >     " + auxiliar);
			//System.out.println("suma en hexa:   = >     " + convertirDecimalToHexadecimal(String.valueOf(auxiliar)));
		}
		
		octeto1 = Integer.parseInt(convertirHexaToDecimal("FFFF"));
		octeto2 = auxiliar;
		int resta = octeto1 - octeto2;
		respuesta = convertirDecimalToHexadecimal(String.valueOf(resta));
		
		return respuesta;
	}
	
	public static String convertirDecimalToHexadecimal(String dato) {
		int num = Integer.parseInt(dato);
		int c = 0, r = 0, m = 0, numero = num;
		String n = "", cad = "", aux = "", resultadoH = "";
		
		if(num == 0)
		{
			resultadoH = "0";
		}
		if(num >= 1)
		{
			while(num >= 1)
			{
				c = num / 16;
				m = 16 * c;
				r = num - m;
				if(r == 10)
				{
					n = "A";//A
				}
				if(r == 11)
				{
					n = "B";//B
				}
				if(r == 12)
				{
					n = "C";//C
				}
				if(r == 13)
				{
					n = "D";//D
				}
				if(r == 14)
				{
					n = "E";//E
				}
				if(r == 15)
				{
					n = "F";//F
				}
				if(r >=0 && r <=9)
				{
					n = String.valueOf(r);
				}
				cad += n;
				num = c;
				//System.out.println(cad);
			}
		}
		for(int i = cad.length()-1 ; i >= 0 ; i --)
		{
			aux = String.valueOf(cad.charAt(i));
			resultadoH += aux;
		}
		return resultadoH;
	}
	
	public static String convertirHexaToDecimal(String dato)
	{
		String num = dato;
		int n  [] = new int[num.length()];
		int cad = 0, n1 = 0, n2 = 0, respuesta = 0;
		
		//CAPTURA EL STRING EN CARACTERES PARA SER GUARDADOS EN UN ARREGLO
		for(int i = 0 ; i < num.length() ; i ++)
		{
			cad = num.charAt(i);
			if(cad >=  48 && cad <= 57)
			{
				n[i] = Integer.parseInt("" + num.charAt(i));
			}
			if(cad >= 65 && cad <= 70 || cad >= 97 && cad <= 102)
			{
				if(cad == 65 || cad == 97)
				{
					n[i] = 10;//A
				}
				if(cad == 66 || cad == 98)
				{
					n[i] = 11;//B
				}
				if(cad == 67 || cad == 99)
				{
					n[i] = 12;//C
				}
				if(cad == 68 || cad == 100)
				{
					n[i] = 13;//D
				}
				if(cad == 69 || cad == 101)
				{
					n[i] = 14;//E
				}
				if(cad == 70 || cad == 102)
				{
					n[i] = 15;//F
				}
			}
		}
		//CONVERSION
		for(int j = 0, k = n.length-1 ; j < n.length ; j ++, k --)
		{
			n1 = n[j];
			n2 = (int) Math.pow(16, k);
			respuesta += n1 * n2;
		}
		return String.valueOf(respuesta);
	}
	
	public static String convertirDecimalToBinario(String dato)
	{
		int num = Integer.parseInt(dato);
		int res = 0;
		String resultado = "";
		int n[] = new int [num];
		String numBin = "";
		for(int i=0;num>=2;i++)
		{
			res = num % 2;
			num = num/2;
			numBin +=res;
			if(num<2)
			{
				numBin += num;
			}
		}
		for(int m=numBin.length()-1;m>=0;m--)
		{
			resultado += numBin.charAt(m);
		}
		return resultado;
	}
	
	public static String convertirBinarioToDecimal(String dato)
	{
		String num = dato;
		double res = 0;
		int n[] = new int [num.length()];
		char cadN = 0;
		for(int i=0;i<num.length();i++)
		{
			cadN = num.charAt(i);
			n[i] = cadN - 48;
		}
		int j = 0;
		for(int z=num.length()-1;z>=0;z--)
		{
			res += n[j] * (Math.pow(2, z));
			j++;
		}
		return String.valueOf((int) res);		
	}
	
public static int[] hallarDesplazamiento (String mtu, String longitudDatagrama) {
		
		double Mtu= Double.parseDouble(mtu);
		double tamaño= Mtu-20;
		double longitud= Double.parseDouble(longitudDatagrama);
		int suma=0;
		int numFragmentos= numeroFragmentos(Mtu,longitud);
		
		int [] desplazamiento= new int [numFragmentos];
		desplazamiento[0]=0;
		
		for (int i=1; i<desplazamiento.length;i++) {
			suma+=tamaño;
			desplazamiento[i]= suma/8;
		}
		return desplazamiento;
	}
	
   public static int numeroFragmentos (double mtu, double longitudDatagrama) {
		
		int fragmentos;
		double n= longitudDatagrama/ mtu;
		int nCasteado= (int) n;
		
		
		if(n % nCasteado == 0) {
			fragmentos=(int)n;
		}
		else {
			fragmentos=((int)n) + 1;
		}
		return fragmentos;
	}
   
   public static int [] longitudFragmentos (String mtu, String longitudDatagrama) {
		
	   double Mtu= Double.parseDouble(mtu);
	   double longitudDatagram= Double.parseDouble(longitudDatagrama);
	   double suma=0;
	   double longitudEncabezado=20;
	   double longitudDatos=0;
		
	   int numFragmentos= numeroFragmentos(Mtu,longitudDatagram);
	   
	   int [] longitud= new int [numFragmentos];
	   
	   for (int i=0; i<longitud.length-1;i++) {
			longitud[i]= (int) Mtu;
			longitudDatos=Mtu - longitudEncabezado;
			suma+=longitudDatos;
		}
	   
	   longitud[longitud.length-1]= (int) (((longitudDatagram - longitudEncabezado) - suma) + longitudEncabezado);
	  
		return longitud;
	}
   
   public static String[] hallarFlags (String mtu, String longitudDatagrama, int desplazamiento) {
		
		double Mtu= Double.parseDouble(mtu);
		double longitud= Double.parseDouble(longitudDatagrama);
		int numeroFragmentos=0;
		int [] desplazamientos= hallarDesplazamiento(mtu,longitudDatagrama);
		String [] flags= new String [3];
		flags[0]= "0";
		
		if(longitud > Mtu) {
			flags[1]= "0";
			numeroFragmentos= numeroFragmentos (Mtu, longitud);
			for(int i=0; i<desplazamientos.length;i++)
			{
				if(desplazamientos[i] == desplazamiento) {
					if((i+1)==numeroFragmentos) {
						flags[2]="0";
					}
					else {
						flags[2]="1";
					}
				}
			}
		}
		else {
			flags[1]= "1";
			flags[2]= "0";
		}
		
		return flags;
	}
}
