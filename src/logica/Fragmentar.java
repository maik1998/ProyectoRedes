package logica;

// ESTE METODO SIRVE COMO ENTIDAD O MODELO PARA ESAS VARIABLES DE ENTRADA QUE EL USUARIO DIGITA POR MEDIO DE LA INTERFAZ.
public class Fragmentar {
	public String mtu = "";
	public String tamDatagrama = "";
	public String protocolo = "";
	public String iporigen = "";
	public String ipdestino = "";
	
	public Fragmentar(String mtu, String tamDatagrama, String protocolo, String iporigen, String ipdestino) {
		super();
		this.mtu = mtu;
		this.tamDatagrama = tamDatagrama;
		this.protocolo = protocolo;
		this.iporigen = iporigen;
		this.ipdestino = ipdestino;
	}
	
	public Fragmentar() {
		super();
	}

	public String getMtu() {
		return mtu;
	}
	public void setMtu(String mtu) {
		this.mtu = mtu;
	}
	public String getTamDatagrama() {
		return tamDatagrama;
	}
	public void setTamDatagrama(String tamDatagrama) {
		this.tamDatagrama = tamDatagrama;
	}
	public String getProtocolo() {
		return protocolo;
	}
	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}
	public String getIporigen() {
		return iporigen;
	}
	public void setIporigen(String iporigen) {
		this.iporigen = iporigen;
	}
	public String getIpdestino() {
		return ipdestino;
	}
	public void setIpdestino(String ipdestino) {
		this.ipdestino = ipdestino;
	}

	@Override
	public String toString() {
		return "Fragmentar [mtu=" + mtu + ", tamDatagrama=" + tamDatagrama + ", protocolo=" + protocolo + ", iporigen="
				+ iporigen + ", ipdestino=" + ipdestino + "]";
	}
	
	
}
