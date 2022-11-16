package okligaNPC;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;



public class Principal {
	
	private static ArrayList<Jugadores> listaJugadores = new ArrayList<Jugadores>();
	private static ArrayList<Equipos> listaEquipos = new ArrayList<Equipos>(); 
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, SQLException {
		leerCSV();
		crearXML();
		insertBBDD();
	}

	private static void insertBBDD() throws SQLException {
		// TODO GUARDAR LOS DATOS DE LOS JUGADORES EN UNA TABLA DE LA BBDD : OKLIGA
		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/okliga", "root","");
		
		PreparedStatement ps = conexion.prepareStatement("INSERT INTO jugadores (codigo,nombre,id_equipo) VALUES (?,?,?)");
		for(int i = 0; i < listaJugadores.size();i++) {
			ps.setInt(1, listaJugadores.get(i).getCodigo());
			ps.setString(2, listaJugadores.get(i).getNombre());
			ps.setInt(3, listaJugadores.get(i).getId_equipo());
			ps.executeUpdate();	//	EJECUTAMOS LA CONSULTA
		}
		
	}

	private static void crearXML() throws ParserConfigurationException, TransformerException {
		// TODO GENEREAR UN FICHERO XML A PARTIR DE LOS DOS CSV, DATOS GUARDADOS EN ARRAYLIST
		DocumentBuilderFactory factoria =  DocumentBuilderFactory.newInstance();
		DocumentBuilder db = factoria.newDocumentBuilder();
		Document documento = db.newDocument();
		
		// CREACION PARTE OKLIGA, DONDE SE AGRUPA JUGADORES Y EQUIPOS
		Element okliga = documento.createElement("okliga");
		documento.appendChild(okliga);
		
		//	CREACION PARTE JUGADORES
		Element jugadores = documento.createElement("jugadores");
		okliga.appendChild(jugadores);
		for(int i = 0; i < listaJugadores.size(); i++) {
			Element jugador = documento.createElement("jugador");
			jugadores.appendChild(jugador);
			
			//CODIGO
			Element codigo = documento.createElement("codigo");
			codigo.setTextContent(String.valueOf(listaJugadores.get(i).getCodigo())); 
			jugador.appendChild(codigo);
			//NOMBRE
			Element nombre = documento.createElement("nombre");
			nombre.setTextContent(listaJugadores.get(i).getNombre());
			jugador.appendChild(nombre);
			//ID_EQUIPO
			Element id_equipo = documento.createElement("id_equipo");
			id_equipo.setTextContent(String.valueOf(listaJugadores.get(i).getId_equipo()));
			jugador.appendChild(id_equipo);
		}
		//	CREACION PARTE EQUIPOS
		Element equipos = documento.createElement("equipos");
		okliga.appendChild(equipos);
		for(int i = 0; i < listaEquipos.size(); i++) {
			Element equipo = documento.createElement("equipo");
			equipos.appendChild(equipo);
			//COD_EQU
			Element codigoEq = documento.createElement("codigoEq");
			codigoEq.setTextContent(String.valueOf(listaEquipos.get(i).getCodigoEq()));
			equipo.appendChild(codigoEq);
			//NOMBREEQ
			Element nombreEq = documento.createElement("nombreEq");
			nombreEq.setTextContent(listaEquipos.get(i).getNombreEq());
			equipo.appendChild(nombreEq);
			//LOCALIDAD
			Element localidad = documento.createElement("localidad");
			localidad.setTextContent(listaEquipos.get(i).getLocalidad());
			equipo.appendChild(localidad);
		}
		
		//	CREACION DEL DOCUMENTO
		TransformerFactory ft = TransformerFactory.newInstance();
		Transformer trans = ft.newTransformer();
		DOMSource dom = new DOMSource(documento);
		//	RUTA DEL DOCUMENTO, DONDE SE VA A CREAR
		StreamResult sr = new StreamResult("C:\\PRUEBAS\\OKLIGA\\okliga.xml");
		trans.transform(dom, sr);
		
		
	}

	private static void leerCSV() throws IOException {
		// TODO LECTURA DE FICHEROS CSV Y ALMACENAR EN ARRAYLIST
		Path ficheroJugadores = Paths.get("C:\\PRUEBAS\\OKLIGA\\jugadores.csv");
		Path ficheroEquipos = Paths.get("C:\\PRUEBAS\\OKLIGA\\equipos.csv");
		
			//LECTURA JUGADORES
		BufferedReader brJ = Files.newBufferedReader(ficheroJugadores);
		String linea;
		String valores[];
		while ((linea = brJ.readLine()) != null) {
			valores = linea.split(",");
			listaJugadores.add(new Jugadores (Integer.parseInt(valores[0]), valores[1], Integer.parseInt(valores[2])) );
			System.out.println(linea); //	PARA COMPROBAR SU LECTURA
		}
			//LECTURA EQUIPOS
		BufferedReader brE = Files.newBufferedReader(ficheroEquipos);
		while((linea = brE.readLine()) != null) {
			valores = linea.split(",");
			listaEquipos.add(new Equipos (Integer.parseInt(valores[0]), valores[1], valores[2]) );
			System.out.println(linea); //	PARA COMPROBAR SU LECTURA
		}
	}
}
