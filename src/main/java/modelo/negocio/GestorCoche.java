package modelo.negocio;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import modelo.entidad.Coche;
import modelo.persistencia.DaoCocheMySQL;
import modelo.persistencia.iterface.DaoCoche;

public class GestorCoche {
	
	private DaoCoche daoCoche = new DaoCocheMySQL();
	
	public int alta(Coche c) {
		if(c.getMatricula().length() >= 7) {
			if(!(c.getMatricula().equals(daoCoche.buscarPorMatricula(c.getMatricula()).getMatricula()))) {
				if(c.getKilometros() >= 0) {
					boolean alta = daoCoche.alta(c);
					if(alta) {
						return 0;
					}else {
						return 1;
					}
				}else { //km negativos
					return 3;
				}
			}else { //la matricula ya esta introducida
				return 4;
			}
			
		}else { //matricula menor de 7 caracter
			return 2;
		}
	}
	
	public boolean baja(int id) {
		return daoCoche.baja(id);
	}
	
	public int modificar(Coche c) {
		if(c.getMatricula().length() >= 7) {
			if(!(c.getMatricula().equals(daoCoche.buscarPorMatricula(c.getMatricula()).getMatricula()))) {
				if(c.getKilometros() >= 0) {
					boolean mod = daoCoche.modificar(c);
					if(mod) {
						return 0; //todo correcto
					}else {
						return 1;
					}
				}else {
					return 3; //km negativos
				}
			}else {
				return 4; //matricula ya introducida
			}
		}else {
			return 2; //Matricula menor de 7 caracteres
		}
	}
	
	public Coche buscarPorID(int id) {
		return daoCoche.buscarPorID(id);
	}
	
	public Coche buscarPorMatricula(String matricula) {
		return daoCoche.buscarPorMatricula(matricula);
	}
	
	public Coche buscarCochePorMarca(String marca) {
		return daoCoche.buscarPorMarca(marca);
	}
	
	public Coche buscarCochePorModelo(String modelo) {
		return daoCoche.buscarPorModelo(modelo);
	}
	
	public List<Coche> listar(){
		return daoCoche.listar();
	}
	
	public void exportJSON() {
		try {
			List<Coche> listaCoches = listar();
			Writer writer = new FileWriter("src/main/resources/ListaDeCoches.txt");
			Gson gson = new GsonBuilder().create();
			gson.toJson(listaCoches, writer);
			writer.flush();
			writer.close();
			System.out.println("El archivo ha sido generado con exito");
		}catch(IOException e){
			System.out.println("No se ha podido escribir");
			e.printStackTrace();
		}
	}
	
	public void exportPDF(){
		List<Coche> listaCoches = listar();
		
		try (PDDocument doc = new PDDocument()){
			PDPage a = new PDPage();
			doc.addPage(a);
			try (PDPageContentStream cnt = new PDPageContentStream(doc, a)){
				cnt.beginText();
				
				cnt.setFont(PDType1Font.TIMES_ROMAN, 8);
				cnt.setLeading(10f);
				
				cnt.newLineAtOffset(30, 700);
				String linea1 = "Lista de Coches en la BBDD";
				cnt.showText(linea1);
				cnt.newLine();
				
				listaCoches.forEach((x) -> {
					try {
						cnt.showText(x.toString());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				
				
				cnt.endText();
			}catch(IOException ioe) {
				ioe.printStackTrace();
			}
			
			doc.save("src/main/resources/ListaDeCoches.pdf");
			System.out.println("Fichero creado");
		}catch(IOException e) {
			System.out.println("Error en la escritura");
			e.printStackTrace();
		}
	}
	
	public boolean login(String user, String pass) {
		try {
			HttpRequest request = HttpRequest.newBuilder().uri(new URI("http://localhost:8080/21WebLogin/usuarios/login?user=" + user + "&pass=" + pass)).GET().build();
			HttpClient client = HttpClient.newHttpClient();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			JSONObject json = new JSONObject(response.body());
			return json.getBoolean("validado");
		}catch(URISyntaxException e) {
			e.printStackTrace();
			return false;
		}catch(IOException e) {
			e.printStackTrace();
			return false;
		}catch(InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
}
