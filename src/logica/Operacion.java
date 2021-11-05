package logica;

public class Operacion {
	public int numIdentificacion = 0;
	public int tiempoVida = 0;
	public int versionDatagrama = 4;
	public int longitudEncabezado = 5;
	public int servicioDiferenciado = 0;
	public Fragmentar fragmentar= null;
	
	
	
	public Operacion() {
		super();
		fragmentar= new Fragmentar();
	}

	public void datos() {
		this.numIdentificacion = (int) Math.floor(Math.random()*(65535 - 0 + 1) + 0);
		this.tiempoVida = (int) Math.floor(Math.random()*(65535 - 0 + 1) + 0);
		imprime();
	}

	public void imprime() {
		System.out.println("El MTU es: " + fragmentar.mtu);
		System.out.println("Longitud total del datagrama es: " + this.fragmentar.getTamDatagrama());
		System.out.println("El protocolo es: " + fragmentar.getProtocolo());
		System.out.println("Direccion IP Origen: " + fragmentar.getIporigen());
		System.out.println("Direccion IP Destino: " + fragmentar.getIpdestino());
		System.out.println("Numero de Identificacion: " + numIdentificacion);
		System.out.println("Tiempo de Vida: " + tiempoVida);
		System.out.println("Version del Datagrama: " + versionDatagrama);
		System.out.println("Longitud del encabezado: " + longitudEncabezado);
		System.out.println("Sevicios Diferenciados: " + servicioDiferenciado);
	}
}
