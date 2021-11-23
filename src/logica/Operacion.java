package logica;

import interfaz.AppPrincipal;

public class Operacion {
	// VARIABLES USADAS PARA LA APLICACION.
	public String mtu = "";
	public String tamDatagrama = "";
	public String protocolo = "";
	public String iporigen = "";
	public String ipdestino = "";
	public int numIdentificacion = 0;
	public int tiempoVida = 0;
	public int versionDatagrama = 4;
	public int longitudEncabezado = 20;
	public int servicioDiferenciado = 0;
	public int arregloLongitudcadaFragmento[] = null;
	public String arrayDeFlags[] = null;
	public String longitudCadaFragmento = "";
	public int arregloDesplazamiento[] = null;
	public String desplazamiento = "";
	public String sumaDeComprobacion[] = null;
	public String datagramaip[] = null;
	public String datagramaipHexadecimal[] = null;
	public String datagramaipBinario[] = null;
	public String datagramaFinal = "";
	public Fragmentar fragmentar = new Fragmentar();

	public Operacion() {
		super();
	}

	// ESTE METODO ES LLAMADO DESDE LA CLASE AppPrincipal, PARA TENER EN CUENTA OS DATOS DE ENRADA
	// ESTE ORGANIZA Y TIENE EN CUENTA LOS DEMAS VARIABLES ASI COMO LAS ALEATORIAS, COMO LAS CALCULADAS
	// LUEGO SE ENCARGA DE ORGANZIAR EL DATAGRAMA EN HEXADECIMAL, Y EN BINARIO
	// PRACTICAMENTE ES QAUE SE ENCARGA DE EJECUTAR TODO LA OPERACION PARA RESOLVER EL EJERCICIO.
	public void datos(String mtu, String tamDatagrama, String protocolo, String ipOrigen, String ipDestino) {
		// VARIABLES DE ENTRADA
		this.mtu = mtu;
		this.tamDatagrama = tamDatagrama;
		this.protocolo = protocolo;
		this.iporigen = ipOrigen;
		this.ipdestino = ipDestino;
		
		// VARIABLES ALEATORIAS
		this.numIdentificacion = (int) Math.floor(Math.random() * (65535 - 0 + 1) + 0);
		this.tiempoVida = (int) Math.floor(Math.random() * (255 - 0 + 1) + 0);

		// VARIABLES A CALCULAR
		this.arregloLongitudcadaFragmento = this.longitudFragmentos(mtu, tamDatagrama);
		this.arregloDesplazamiento = this.hallarDesplazamiento(mtu, tamDatagrama);
		
		this.arrayDeFlags = new String[this.arregloDesplazamiento.length];
		for (int i = 0; i < this.arregloDesplazamiento.length; i++) {
			arrayDeFlags[i] = this.hallarFlags(mtu, tamDatagrama, this.arregloDesplazamiento[i]) + "0";//
		}
		
		this.datagramaip = ordenarParaSuma(mtu, tamDatagrama, protocolo, ipOrigen, ipDestino, this.numIdentificacion,
				this.tiempoVida, this.arregloDesplazamiento, this.arregloLongitudcadaFragmento, this.arrayDeFlags);
		
		this.sumaDeComprobacion = sumaComprobacion(this.datagramaip);

		// ORDENA DATAGRAMA EN HEXADECIMAL
		this.datagramaipHexadecimal = ordenarEncabezadoDatagramaHexadecimal(this.datagramaip, this.sumaDeComprobacion);

		// ORDENA DATAGRAMA EN BINARIO
		this.datagramaipBinario = ordenarEncabezadoDatagramaBinario(this.datagramaipHexadecimal);
	}

	// SE ENCARGA DE ORDENAR EL DATAGRAMA EN BINARIO, ESTE RECIBE EL DATAGRAMA EN HEXADECIMAL, LO PASA A DECIMAL
	// Y LUEGO A BINARIO, PARA LUEGO ORGANIZARLO EN UN ARREGLO.
	public String[] ordenarEncabezadoDatagramaBinario(String[] datagramaipHexadecimal2) {
		// ORGANIZA EL DATAGRAMA EN BINARIO
		String encabezado[] = new String[datagramaipHexadecimal2.length];
		for (int j = 0; j < encabezado.length; j++) {
			String datoE = datagramaipHexadecimal2[j];
			String arrayE[] = datoE.split(" ");
			String aux = "", aux2 = "";
			for (int i = 0; i < arrayE.length; i++) {
				aux = convertirDecimalToBinario(convertirHexaToDecimal(arrayE[i].substring(0, 1)));
				if (arrayE[i].substring(0, 1).equals("1")) {
					aux2 += "0001";
				} else {
					if (aux.length() == 0) {
						aux2 += "0000";
					}
					if (aux.length() == 1) {
						aux2 += "000" + aux;
					}
					if (aux.length() == 2) {
						aux2 += "00" + aux;
					}
					if (aux.length() == 3) {
						aux2 += "0" + aux;
					}
					if (aux.length() == 4) {
						aux2 += aux;
					}
				}
				aux = "";
				aux = convertirDecimalToBinario(convertirHexaToDecimal(arrayE[i].substring(1)));
				if (arrayE[i].substring(1).equals("1")) {
					aux2 += " " + "0001" + " ";
				} else {
					if (aux.length() == 0) {
						aux2 += " " + "0000" + " ";
					}
					if (aux.length() == 1) {
						aux2 += " " + "000" + aux + " ";
					}
					if (aux.length() == 2) {
						aux2 += " " + "00" + aux + " ";
					}
					if (aux.length() == 3) {
						aux2 += " " + "0" + aux + " ";
					}
					if (aux.length() == 4) {
						aux2 += " " + aux + " ";
					}
				}
				aux = "";
			}
			encabezado[j] = aux2;
		}
		
		return encabezado;
	}

	// ORDENA EL DATAGRAMA EN HEXADECIMAL
	// TIENE EN CUENTA UN ARREGLO DEL DATAGRAMA EN DECIMAL PERO EN HEXADECIMAL Y LE SUMA LA SUMA DE COMPROBACION.
	public String[] ordenarEncabezadoDatagramaHexadecimal(String[] datagramaip2, String[] sumaDeComprobacion2) {
		// ORGANIZA EL ENCABEXADO DEL DATAGRAMA EN HEXADECIMAL

		String encabezado[] = new String[datagramaip2.length];
		for (int i = 0; i < datagramaip2.length; i++) {
			encabezado[i] = datagramaip2[i].substring(0, 29) + " " + sumaDeComprobacion2[i].substring(0, 2) + " "
					+ sumaDeComprobacion2[i].substring(2, 4) + " " + datagramaip2[i].substring(30);
		}
		return encabezado;
	}

	// ORDENA EN UN ARREGLO CON TODAS LAS VARIABLES QUE ESTAN EN DECIMAL, PARA PASARLO EN HEXADECIMAL
	// ESTO SE HACE PARA ORGANIZARLO Y PREPARALO PARA UNIRLO CON LA SUMA DE COMPROBACION
	public String[] ordenarParaSuma(String mtu2, String tamDatagrama2, String protocolo2, String ipOrigen2,
			String ipDestino2, int numIdentificacion2, int tiempoVida2, int[] arregloDesplazamiento2,
			int[] arregloLongitudcadaFragmento2, String[] arrayDeFlags) {
		// ORDENAR PARA LA SUMA DE COMROBACION
		String datagramaParaSuma[] = new String[arregloDesplazamiento2.length];
		String aux = "";
		int aux2 = 0;
		String arregloAux[] = null;

		for (int i = 0; i < arregloDesplazamiento2.length; i++) {
			aux += String.valueOf(this.versionDatagrama);
			aux += String.valueOf(this.longitudEncabezado / 4) + " ";
			aux += String.valueOf(this.servicioDiferenciado) + "0" + " ";
			// LONGITUD CADA FRAGMENTO
			if (convertirDecimalToHexadecimal(String.valueOf(arregloLongitudcadaFragmento2[i])).length() < 4) {
				if (convertirDecimalToHexadecimal(String.valueOf(arregloLongitudcadaFragmento2[i])).length() == 3) {
					aux += "0" + convertirDecimalToHexadecimal(String.valueOf(arregloLongitudcadaFragmento2[i]))
							.substring(0, 1) + " ";
					aux += convertirDecimalToHexadecimal(String.valueOf(arregloLongitudcadaFragmento2[i])).substring(1,
							3) + " ";
				}
				if (convertirDecimalToHexadecimal(String.valueOf(arregloLongitudcadaFragmento2[i])).length() == 2) {
					aux += "00" + " ";
					aux += convertirDecimalToHexadecimal(String.valueOf(arregloLongitudcadaFragmento2[i])).substring(0,
							2) + " ";
				}
				if (convertirDecimalToHexadecimal(String.valueOf(arregloLongitudcadaFragmento2[i])).length() == 1) {
					aux += "00" + " ";
					aux += "0" + convertirDecimalToHexadecimal(String.valueOf(arregloLongitudcadaFragmento2[i]))
							.substring(0) + " ";
				}
			} else {
				aux += convertirDecimalToHexadecimal(String.valueOf(arregloLongitudcadaFragmento2[i])).substring(0, 2)
						+ " ";
				aux += convertirDecimalToHexadecimal(String.valueOf(arregloLongitudcadaFragmento2[i])).substring(2, 4)
						+ " ";
			}
			// NUMERO DE IDENTIFICACION
			if (convertirDecimalToHexadecimal(String.valueOf(numIdentificacion2)).length() < 4) {
				if (convertirDecimalToHexadecimal(String.valueOf(numIdentificacion2)).length() == 3) {
					aux += "0" + convertirDecimalToHexadecimal(String.valueOf(numIdentificacion2)).substring(0) + " ";
					aux += convertirDecimalToHexadecimal(String.valueOf(numIdentificacion2)).substring(1, 3) + " ";
				}
				if (convertirDecimalToHexadecimal(String.valueOf(numIdentificacion2)).length() == 2) {
					aux += "00" + " ";
					aux += convertirDecimalToHexadecimal(String.valueOf(numIdentificacion2)).substring(0, 2) + " ";
				}
				if (convertirDecimalToHexadecimal(String.valueOf(numIdentificacion2)).length() == 1) {
					aux += "00" + " ";
					aux += "0" + convertirDecimalToHexadecimal(String.valueOf(numIdentificacion2)).substring(0) + " ";
				}
			} else {
				aux += convertirDecimalToHexadecimal(String.valueOf(numIdentificacion2)).substring(0, 2) + " ";
				aux += convertirDecimalToHexadecimal(String.valueOf(numIdentificacion2)).substring(2, 4) + " ";
			}
			// FLAGS Y DESPLAZAMIENTO
			if (convertirDecimalToHexadecimal(String.valueOf(arregloDesplazamiento2[i])).length() < 4) {
				if (convertirDecimalToHexadecimal(String.valueOf(arregloDesplazamiento2[i])).length() == 3) {
					aux += convertirDecimalToHexadecimal(convertirBinarioToDecimal(arrayDeFlags[i]))
							+ convertirDecimalToHexadecimal(String.valueOf(arregloDesplazamiento2[i])).substring(0, 1)
							+ " ";
					aux += convertirDecimalToHexadecimal(String.valueOf(arregloDesplazamiento2[i])).substring(1, 3)
							+ " ";
				}
				if (convertirDecimalToHexadecimal(String.valueOf(arregloDesplazamiento2[i])).length() == 2) {
					aux += convertirDecimalToHexadecimal(convertirBinarioToDecimal(arrayDeFlags[i])) + "0" + " ";
					aux += convertirDecimalToHexadecimal(String.valueOf(arregloDesplazamiento2[i])).substring(0, 2)
							+ " ";
				}
				if (convertirDecimalToHexadecimal(String.valueOf(arregloDesplazamiento2[i])).length() == 1) {
					aux += convertirDecimalToHexadecimal(convertirBinarioToDecimal(arrayDeFlags[i])) + "0" + " ";
					aux += "0" + convertirDecimalToHexadecimal(String.valueOf(arregloDesplazamiento2[i])).substring(0)
							+ " ";
				}
			} else {
				aux2 = Integer.parseInt(convertirDecimalToHexadecimal(convertirBinarioToDecimal(arrayDeFlags[i])))
						+ Integer.parseInt(String.valueOf(arregloDesplazamiento2[i]).substring(0, 1));
				aux += convertirDecimalToHexadecimal(String.valueOf(aux2));
				aux += convertirDecimalToHexadecimal(String.valueOf(arregloDesplazamiento2[i])).substring(1, 2) + " ";
				aux += convertirDecimalToHexadecimal(String.valueOf(arregloDesplazamiento2[i])).substring(2, 4) + " ";
			}
			// TIEMPO DE VIDA
			if (convertirDecimalToHexadecimal(String.valueOf(tiempoVida2)).length() == 2) {
				aux += convertirDecimalToHexadecimal(String.valueOf(tiempoVida2)) + " ";
			} else {
				aux += "0" + convertirDecimalToHexadecimal(String.valueOf(tiempoVida2)) + " ";
			}
			// PROTOCOLO
			if (protocolo2.equals("ICMP")) {
				aux += "0" + convertirDecimalToHexadecimal(String.valueOf(1)) + " ";
			}
			if (protocolo2.equals("TCP")) {
				aux += "0" + convertirDecimalToHexadecimal(String.valueOf(6)) + " ";
			}
			if (protocolo2.equals("UDP")) {
				aux += convertirDecimalToHexadecimal(String.valueOf(17)) + " ";
			}
			// IP ORIGEN
			arregloAux = ipOrigen2.split("\\.");
			if (convertirDecimalToHexadecimal(arregloAux[0]).length() == 2) {
				aux += convertirDecimalToHexadecimal(String.valueOf(arregloAux[0])) + " ";
			} else {
				aux += "0" + convertirDecimalToHexadecimal(String.valueOf(arregloAux[0])) + " ";
			}
			if (convertirDecimalToHexadecimal(arregloAux[1]).length() == 2) {
				aux += convertirDecimalToHexadecimal(String.valueOf(arregloAux[1])) + " ";
			} else {
				aux += "0" + convertirDecimalToHexadecimal(String.valueOf(arregloAux[1])) + " ";
			}
			if (convertirDecimalToHexadecimal(arregloAux[2]).length() == 2) {
				aux += convertirDecimalToHexadecimal(String.valueOf(arregloAux[2])) + " ";
			} else {
				aux += "0" + convertirDecimalToHexadecimal(String.valueOf(arregloAux[2])) + " ";
			}
			if (convertirDecimalToHexadecimal(arregloAux[3]).length() == 2) {
				aux += convertirDecimalToHexadecimal(String.valueOf(arregloAux[3])) + " ";
			} else {
				aux += "0" + convertirDecimalToHexadecimal(String.valueOf(arregloAux[3])) + " ";
			}
			// IP DESTINO
			arregloAux = null;
			arregloAux = ipDestino2.split("\\.");
			if (convertirDecimalToHexadecimal(arregloAux[0]).length() == 2) {
				aux += convertirDecimalToHexadecimal(String.valueOf(arregloAux[0])) + " ";
			} else {
				aux += "0" + convertirDecimalToHexadecimal(String.valueOf(arregloAux[0])) + " ";
			}
			if (convertirDecimalToHexadecimal(arregloAux[1]).length() == 2) {
				aux += convertirDecimalToHexadecimal(String.valueOf(arregloAux[1])) + " ";
			} else {
				aux += "0" + convertirDecimalToHexadecimal(String.valueOf(arregloAux[1])) + " ";
			}
			if (convertirDecimalToHexadecimal(arregloAux[2]).length() == 2) {
				aux += convertirDecimalToHexadecimal(String.valueOf(arregloAux[2])) + " ";
			} else {
				aux += "0" + convertirDecimalToHexadecimal(String.valueOf(arregloAux[2])) + " ";
			}
			if (convertirDecimalToHexadecimal(arregloAux[3]).length() == 2) {
				aux += convertirDecimalToHexadecimal(String.valueOf(arregloAux[3])) + " ";
			} else {
				aux += "0" + convertirDecimalToHexadecimal(String.valueOf(arregloAux[3])) + " ";
			}
			// GUARDAR EN EL ARRAY
			datagramaParaSuma[i] = aux;
			aux = "";
		}
		
		// RESULTADO
		return datagramaParaSuma;
	}

	// EN ESTE METODO SE REALIZA LA SUMA DE COMPROBACION, SE TIENE EN CUENTA LO QUE SE REALIZO EN EL METODO ordenarParaSuma
	// ESTE RETORNA UN ARREGLO EN HEXADECIMAL DE LA SUMA DE COMPROBACION
	public String[] sumaComprobacion(String[] datagramaip2) {
		String respuesta[] = new String[datagramaip2.length];
		String separados[] = null;
		String auxSeparados = "";
		String[] arreglo = new String[9];

		for (int n = 0; n < datagramaip2.length; n++) {
			auxSeparados = datagramaip2[n];
			separados = auxSeparados.split(" ");
			int j = 0, i = 0;
			String aux = "";
			while (j < separados.length) {
				aux += separados[j];
				if (j == 1 || j == 3 || j == 5 || j == 7 || j == 9 || j == 11 || j == 13 || j == 15 || j == 17) {
					arreglo[i] = aux;
					aux = "";
					i++;
				}
				j++;
			}

			int acarreo = 0, octeto1 = 0, octeto2 = 0;
			int auxiliar = 0;
			for (int w = 1; w < arreglo.length; w++) {
				if (w == 1) {
					octeto1 = Integer.parseInt(convertirHexaToDecimal(arreglo[0]));
					octeto2 = Integer.parseInt(convertirHexaToDecimal(arreglo[1]));
					auxiliar = octeto1 + octeto2;
					if (convertirDecimalToHexadecimal(String.valueOf(auxiliar)).length() > 4) {
						acarreo = 1;
						auxiliar = Integer.parseInt(convertirHexaToDecimal(
								convertirDecimalToHexadecimal(String.valueOf(auxiliar)).substring(1)));
						auxiliar = auxiliar + acarreo;
					}
				} else {
					octeto1 = auxiliar;
					octeto2 = Integer.parseInt(convertirHexaToDecimal(arreglo[w]));
					auxiliar = octeto1 + octeto2;
					if (convertirDecimalToHexadecimal(String.valueOf(auxiliar)).length() > 4) {
						acarreo = 1;
						auxiliar = Integer.parseInt(convertirHexaToDecimal(
								convertirDecimalToHexadecimal(String.valueOf(auxiliar)).substring(1)));
						auxiliar = auxiliar + acarreo;
					}
				}
			}

			octeto1 = Integer.parseInt(convertirHexaToDecimal("FFFF"));
			octeto2 = auxiliar;
			int resta = octeto1 - octeto2;
			respuesta[n] = convertirDecimalToHexadecimal(String.valueOf(resta));
			separados = null;
		}

		return respuesta;
	}

	// ESTE METODO SE ENCARGA DE CONVERTIR DE DECIMAL A HEXADECIMAL, PASA COMO PARAMETRO UN VALOR TIPO STRING Y LO RETORNA EN STRING
	public static String convertirDecimalToHexadecimal(String dato) {
		int num = Integer.parseInt(dato);
		int c = 0, r = 0, m = 0, numero = num;
		String n = "", cad = "", aux = "", resultadoH = "";

		if (num == 0) {
			resultadoH = "0";
		}
		if (num >= 1) {
			while (num >= 1) {
				c = num / 16;
				m = 16 * c;
				r = num - m;
				if (r == 10) {
					n = "A";// A
				}
				if (r == 11) {
					n = "B";// B
				}
				if (r == 12) {
					n = "C";// C
				}
				if (r == 13) {
					n = "D";// D
				}
				if (r == 14) {
					n = "E";// E
				}
				if (r == 15) {
					n = "F";// F
				}
				if (r >= 0 && r <= 9) {
					n = String.valueOf(r);
				}
				cad += n;
				num = c;
				// System.out.println(cad);
			}
		}
		for (int i = cad.length() - 1; i >= 0; i--) {
			aux = String.valueOf(cad.charAt(i));
			resultadoH += aux;
		}
		return resultadoH;
	}

	// ESTE METODO SE ENCARGA DE CONVERTIR DE HEXADECIMAL A DECIMAL, PASA COMO PARAMETRO UN VALOR TIPO STRING Y LO RETORNA EN STRING
	public static String convertirHexaToDecimal(String dato) {
		String num = dato;
		int n[] = new int[num.length()];
		int cad = 0, n1 = 0, n2 = 0, respuesta = 0;

		// CAPTURA EL STRING EN CARACTERES PARA SER GUARDADOS EN UN ARREGLO
		for (int i = 0; i < num.length(); i++) {
			cad = num.charAt(i);
			if (cad >= 48 && cad <= 57) {
				n[i] = Integer.parseInt("" + num.charAt(i));
			}
			if (cad >= 65 && cad <= 70 || cad >= 97 && cad <= 102) {
				if (cad == 65 || cad == 97) {
					n[i] = 10;// A
				}
				if (cad == 66 || cad == 98) {
					n[i] = 11;// B
				}
				if (cad == 67 || cad == 99) {
					n[i] = 12;// C
				}
				if (cad == 68 || cad == 100) {
					n[i] = 13;// D
				}
				if (cad == 69 || cad == 101) {
					n[i] = 14;// E
				}
				if (cad == 70 || cad == 102) {
					n[i] = 15;// F
				}
			}
		}
		// CONVERSION
		for (int j = 0, k = n.length - 1; j < n.length; j++, k--) {
			n1 = n[j];
			n2 = (int) Math.pow(16, k);
			respuesta += n1 * n2;
		}
		return String.valueOf(respuesta);
	}

	// ESTE METODO SE ENCARGA DE CONVERTIR DE DECIMAL A BINARIO, PASA COMO PARAMETRO UN VALOR TIPO STRING Y LO RETORNA EN STRING
	public static String convertirDecimalToBinario(String dato) {
		int num = Integer.parseInt(dato);
		int res = 0;
		String resultado = "";
		int n[] = new int[num];
		String numBin = "";
		for (int i = 0; num >= 2; i++) {
			res = num % 2;
			num = num / 2;
			numBin += res;
			if (num < 2) {
				numBin += num;
			}
		}
		for (int m = numBin.length() - 1; m >= 0; m--) {
			resultado += numBin.charAt(m);
		}
		return resultado;
	}

	// ESTE METODO SE ENCARGA DE CONVERTIR DE BINARIO A DECIMAL, PASA COMO PARAMETRO UN VALOR TIPO STRING Y LO RETORNA EN STRING
	public static String convertirBinarioToDecimal(String dato) {
		String num = dato;
		double res = 0;
		int n[] = new int[num.length()];
		char cadN = 0;
		for (int i = 0; i < num.length(); i++) {
			cadN = num.charAt(i);
			n[i] = cadN - 48;
		}
		int j = 0;
		for (int z = num.length() - 1; z >= 0; z--) {
			res += n[j] * (Math.pow(2, z));
			j++;
		}
		return String.valueOf((int) res);
	}

	// METODO PARA CALCULAR EL DESPLAZAMIENTO, PASA COMO PARAMETRO EL MTU, Y LA LONGITUD TOTAL DEL DATAGRAMA
	// TENER EN CUENTA QUE longitudDatagrama ES LA QUE SE PASA POR ENTRADA DE DATOS EN EL JTEXTFIELD
	public static int[] hallarDesplazamiento(String mtu, String longitudDatagrama) {

		double Mtu = Double.parseDouble(mtu);
		double tamaño = Mtu - 20;
		double longitud = Double.parseDouble(longitudDatagrama);
		int suma = 0;
		int numFragmentos = numeroFragmentos(Mtu, longitud);

		int[] desplazamiento = new int[numFragmentos];
		desplazamiento[0] = 0;

		for (int i = 1; i < desplazamiento.length; i++) {
			suma += tamaño;
			desplazamiento[i] = suma / 8;
		}
		return desplazamiento;
	}

	// METODO PARA CALCULAR EL NUMERO DE FRAGMENTOS, PASA COMO PARAMETRO EL MTU, Y LA LONGITUD TOTAL DEL DATAGRAMA
	// TENER EN CUENTA QUE longitudDatagrama ES LA QUE SE PASA POR ENTRADA DE DATOS EN EL JTEXTFIELD
	// ESTE LO UTILIZA EL METODO hallarDesplazamiento
	public static int numeroFragmentos(double mtu, double longitudDatagrama) {

		int fragmentos;
		double n = longitudDatagrama / mtu;
		int nCasteado = (int) n;

		if (n % nCasteado == 0) {
			fragmentos = (int) n;
		} else {
			fragmentos = ((int) n) + 1;
		}
		return fragmentos;
	}

	// METODO PARA CALCULAR LA LONGITUD DE FRAGMENTOS DE CADA DATAGRAMA, PASA COMO PARAMETRO EL MTU, Y LA LONGITUD TOTAL DEL DATAGRAMA
	// TENER EN CUENTA QUE longitudDatagrama ES LA QUE SE PASA POR ENTRADA DE DATOS EN EL JTEXTFIELD
	public static int[] longitudFragmentos(String mtu, String longitudDatagrama) {

		double Mtu = Double.parseDouble(mtu);
		double longitudDatagram = Double.parseDouble(longitudDatagrama);
		double suma = 0;
		double longitudEncabezado = 20;
		double longitudDatos = 0;

		int numFragmentos = numeroFragmentos(Mtu, longitudDatagram);

		int[] longitud = new int[numFragmentos];

		for (int i = 0; i < longitud.length - 1; i++) {
			longitud[i] = (int) Mtu;
			longitudDatos = Mtu - longitudEncabezado;
			suma += longitudDatos;
		}

		longitud[longitud.length - 1] = (int) (((longitudDatagram - longitudEncabezado) - suma) + longitudEncabezado);

		return longitud;
	}

	// METODO PARA CALCULAR LOS FLAGS DE CADA DATAGRAMA, PASA COMO PARAMETRO EL MTU, Y LA LONGITUD TOTAL DEL DATAGRAMA
	// TENER EN CUENTA QUE longitudDatagrama ES LA QUE SE PASA POR ENTRADA DE DATOS EN EL JTEXTFIELD
	// TAMBIEN SE PASA POR PARAMETRO EL DESPLAZAMIENTO QUE ES UN ENTERO
	// ESTE EN EL METODO datos ES LLAMADO Y PASA EL DESLAZAMIENTO POR CADA DATAGRAMA QUE HAY.
	public static String hallarFlags(String mtu, String longitudDatagrama, int desplazamiento) {

		double Mtu = Double.parseDouble(mtu);
		double longitud = Double.parseDouble(longitudDatagrama);
		int numeroFragmentos = 0;
		int[] desplazamientos = hallarDesplazamiento(mtu, longitudDatagrama);
		String flags = "";
		flags = "0";

		if (longitud > Mtu) {
			flags += "0";
			numeroFragmentos = numeroFragmentos(Mtu, longitud);
			for (int i = 0; i < desplazamientos.length; i++) {
				if (desplazamientos[i] == desplazamiento) {
					if ((i + 1) == numeroFragmentos) {
						flags += "0";
					} else {
						flags += "1";
					}
				}
			}
		} else {
			flags += "1";
			flags += "0";
		}

		return flags;
	}

	// ESTE METODO SE ENCARGA DE ORGANIZAR EL DATAGRAMA PARA QUE SE IMPRIMA EN UN TEXTAREA
	// ESTE LO ORGANIZA DE MANERA SIMILAR COMO SE VE EN LA APLICACION WIRESHARK.
	public String imprime() {		
		//  LO SIGUIENTE ES LO QUE IMPRIME EN PANTALA TAL CUAL COMO SE VE EN WIRESHARK
		String protocolo2 = "";
		if (this.protocolo.equals("ICMP")) {
			protocolo2 = "0x01";
		}
		if (this.protocolo.equals("TCP")) {
			protocolo2 = "0x06";
		}
		if (this.protocolo.equals("UDP")) {
			protocolo2 = "0x11";
		}
		int i = 0;
		String textoW = "";
		while (i < this.datagramaipHexadecimal.length) {
			textoW += "Datagrama #" + (i + 1) + ":" + "\n";
			textoW += "Internet Protocol:" + "\n";
			textoW += "Version: " + this.versionDatagrama + "\n";
			textoW += "Header length: " + this.longitudEncabezado + " bytes" + "\n";
			textoW += "Differentiated Services Field: 0x00" + "\n";
			textoW += "Total Length: " + this.arregloLongitudcadaFragmento[i] + "\n";
			textoW += "Identification: 0x" + this.convertirDecimalToHexadecimal("" + this.numIdentificacion) + "  (" + this.numIdentificacion + ")" + "\n";
			
			if(this.arrayDeFlags[i].equals("0100")) {
				textoW += "Flags: 0x02 (Don´t Fragment)" + "\n";
				textoW += "0.. = Reserved bit: Not Set" + "\n";
				textoW += ".1. = Don´t fragment: Set" + "\n";
				textoW += "..0 = More fragments: Not Set" + "\n";
			}
			if(this.arrayDeFlags[i].equals("0010")) {
				textoW += "Flags: 0x03 (More Fragment)" + "\n";
				textoW += "0.. = Reserved bit: Not Set" + "\n";
				textoW += ".0. = Don´t fragment: Not Set" + "\n";
				textoW += "..1 = More fragments: Set" + "\n";
			}
			if(this.arrayDeFlags[i].equals("")) {
				textoW += "Flags: 0x02 (Don´t Fragment)" + "\n";
				textoW += "0.. = Reserved bit: Not Set" + "\n";
				textoW += ".1. = Don´t fragment: Not Set" + "\n";
				textoW += "..0 = More fragments: Not Set" + "\n";
			}
			if(this.arrayDeFlags[i].equals("0000")) {
				textoW += "Flags: 0x02 (Don´t Fragment)" + "\n";
				textoW += "0.. = Reserved bit: Not Set" + "\n";
				textoW += ".1. = Don´t fragment: Not Set" + "\n";
				textoW += "..0 = More fragments: Not Set" + "\n";
			}
			
			textoW += "Fragment offset: " + this.arregloDesplazamiento[i] + "\n";
			textoW += "Time to live: " + this.tiempoVida + "\n";
			// ACONTINUACION SE USA ESTE PROTOCOLO
			textoW += "Protocol: " + this.protocolo + " (" + protocolo2 + ")" + "\n";
			textoW += "Header checksum: 0x" + this.sumaDeComprobacion[i] + " [correct]" + "\n";
			textoW += "Source: " + this.iporigen + " (" + this.iporigen + ")" + "\n";
			textoW += "Destination: " + this.ipdestino + " (" + this.ipdestino + ")" + "\n\n";
			i++;
		}
		return textoW;
	}

}
