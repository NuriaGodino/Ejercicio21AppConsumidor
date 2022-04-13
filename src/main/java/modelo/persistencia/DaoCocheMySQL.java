package modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.entidad.Coche;
import modelo.persistencia.iterface.DaoCoche;

public class DaoCocheMySQL implements DaoCoche{
	
	private Connection conexion;
	
	public boolean abrirConexion() {
		String url = "jdbc:mysql://localhost:3306/ejercicio13";
		String usuario = "root";
		String password = "";
		
		try {
			conexion = DriverManager.getConnection(url, usuario, password);
		}catch(SQLException e){
			System.out.println("No se ha podido establecer conexion con la BD");
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public boolean cerrarConexion() {
		try {
			conexion.close();
		} catch(SQLException e) {
			System.out.println("No se ha podido establecer conexion con la BD");
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}

	@Override
	public boolean alta(Coche c) {
		if(!abrirConexion()) {
			return false;
		}
		
		boolean alta = true;
		String query = "insert into coches (ID, MATRICULA, MARCA, MODELO, KILOMETROS) " + " values(?,?,?,?,?)";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, c.getId());
			ps.setString(2, c.getMatricula());
			ps.setString(3, c.getMarca());
			ps.setString(4, c.getModelo());
			ps.setInt(5, c.getKilometros());
			
			int numFilasAfectadas = ps.executeUpdate();
			if(numFilasAfectadas == 0) {
				alta = false;
			}
		}catch(SQLException e) {
			System.out.println("Alta ----> Error al insertar: " + c);
			alta = false;
			e.printStackTrace();
		}finally {
			cerrarConexion();
		}
		return alta;
	}

	@Override
	public boolean baja(int id) {
		if(!abrirConexion()) {
			return false;
		}
		
		boolean baja = true;
		String query = "delete from coches where id = ?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			
			int numFilasAfectadas = ps.executeUpdate();
			if(numFilasAfectadas == 0) {
				baja = false;
			}
		}catch(SQLException e) {
			System.out.println("Baja ----> No se ha podido dar de baja el id " + id);
			baja = false;
			e.printStackTrace();
		}finally {
			cerrarConexion();
		}
		
		return baja;
	}

	@Override
	public boolean modificar(Coche c) {
		if(!abrirConexion()) {
			return false;
		}
		
		boolean modificado = true;
		String query = "update coches set MATRICULA=?, MARCA=?, MODELO=?, KILOMETROS=? WHERE ID=?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, c.getMatricula());
			ps.setString(2, c.getMarca());
			ps.setString(3, c.getModelo());
			ps.setInt(4, c.getKilometros());
			ps.setInt(5, c.getId());
			
			int numFilasAfectadas = ps.executeUpdate();
			if(numFilasAfectadas == 0){
				modificado = false; 
			}else {
				modificado = true;
			}
		}catch (SQLException e) {
			System.out.println("Modificar ----> No se ha podido modificar el coche con el id ");
			modificado = false;
			e.printStackTrace();
		}finally {
			cerrarConexion();
		}
		return modificado;
	}
	
	@Override
	public Coche buscarPorID(int id) {
		if(!abrirConexion()) {
			return null;
		}
		
		Coche c = new Coche();
		String query = "select ID, MATRICULA, MARCA, MODELO, KILOMETROS from coches " + "where ID = ?";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				c.setId(rs.getInt(1));
				c.setMatricula(rs.getString(2));
				c.setMarca(rs.getString(3));
				c.setModelo(rs.getString(4));
				c.setKilometros(rs.getInt(5));
			}
			
		}catch(SQLException e) {
			System.out.println("No se encontró el coche por el ID " + id);
			e.printStackTrace();
		}finally {
			cerrarConexion();
		}
		return c;
	}

	@Override
	public Coche buscarPorMatricula(String matricula) {
		if(!abrirConexion()) {
			return null;
		}
		
		Coche c = new Coche();
		String query = "select ID, MATRICULA, MARCA, MODELO, KILOMETROS from coches " + "where MATRICULA = ?";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, matricula);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				c.setId(rs.getInt(1));
				c.setMatricula(rs.getString(2));
				c.setMarca(rs.getString(3));
				c.setModelo(rs.getString(4));
				c.setKilometros(rs.getInt(5));
			}
			
		}catch(SQLException e) {
			System.out.println("No se encontró el coche por la matricula " + matricula);
			e.printStackTrace();
		}finally {
			cerrarConexion();
		}
		return c;
	}

	@Override
	public Coche buscarPorMarca(String marca) {
		if(!abrirConexion()) {
			return null;
		}
		
		Coche c = new Coche();
		String query = "select ID, MATRICULA, MARCA, MODELO, KILOMETROS from coches " + "where MARCA = ?";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, marca);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				c.setId(rs.getInt(1));
				c.setMatricula(rs.getString(2));
				c.setMarca(rs.getString(3));
				c.setModelo(rs.getString(4));
				c.setKilometros(rs.getInt(5));
			}
			
		}catch(SQLException e) {
			System.out.println("No se encontró el coche por la marca " + marca);
			e.printStackTrace();
		}finally {
			cerrarConexion();
		}
		return c;
	}

	@Override
	public Coche buscarPorModelo(String modelo) {
		if(!abrirConexion()) {
			return null;
		}
		
		Coche c = new Coche();
		String query = "select ID, MATRICULA, MARCA, MODELO, KILOMETROS from coches " + "where MODELO = ?";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, modelo);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				c.setId(rs.getInt(1));
				c.setMatricula(rs.getString(2));
				c.setMarca(rs.getString(3));
				c.setModelo(rs.getString(4));
				c.setKilometros(rs.getInt(5));
			}
			
		}catch(SQLException e) {
			System.out.println("No se encontró el coche por el modelo " + modelo);
			e.printStackTrace();
		}finally {
			cerrarConexion();
		}
		return c;
	}

	@Override
	public List<Coche> listar() {
		if(!abrirConexion()) {
			return null;
		}
		
		List<Coche> listaCoches = new ArrayList<Coche>();
		String query = "select ID, MATRICULA, MARCA, MODELO, KILOMETROS from coches";
		Coche c = new Coche();
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				c.setId(rs.getInt(1));
				c.setMatricula(rs.getString(2));
				c.setMarca(rs.getString(3));
				c.setModelo(rs.getString(4));
				c.setKilometros(rs.getInt(5));
				
				listaCoches.add(c);
			}
		}catch(SQLException e) {
			System.out.println("Listar ----> Error al obtener los coches");
			System.out.println(e.getMessage());
		}finally {
			cerrarConexion();
		}
		return listaCoches;
	}
}
