package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
	
	private Connection conn; // conexão com banco de dados
	
	public SellerDaoJDBC(Connection conn) { // dependente da conexão banco de dados
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
				    + "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS); // Coloca na tela o Id do vendedor no caso o Generate Key
					
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime())); // Instanciou data do SQL
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
					
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);// Popular dentro do novo id instanciado no obj
					obj.setId(id);
				} DB.closeResultSet(rs);
			} else { // Lança uma excessão no caso se nenhuma linha foi alterado
				throw new DbException("Unexpected error! No rows Affected");
				
			}
		
		
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
		
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ? ");
					
					
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime())); // Instanciou data do SQL
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());
					
			st.executeUpdate();
		
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}	
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM seller WHERE Id = ? ");
					
			st.setInt(1, id);		
					
			st.executeUpdate();
			
		} catch(SQLException e){
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					 + "FROM seller INNER JOIN department "
					 + "ON seller.DepartmentId = department.Id "
					 + "WHERE seller.Id = ? ");	
			
			st.setInt(1,id);
			rs = st.executeQuery(); // comando que busca sql
		    if(rs.next()) {
		    	Department dep = instanciateDepartment(rs);
			    Seller obj = instanciateSeller(rs, dep);
			    return obj;
			    
		} 
			return null;
		} catch(SQLException e) {
			throw new DbException(e.getMessage());			
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		}

	private Seller instanciateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj  = new Seller();
	    obj.setId(rs.getInt("Id"));
	    obj.setName(rs.getString("Name"));
	    obj.setEmail(rs.getString("Email"));
	    obj.setBaseSalary(rs.getDouble("BaseSalary"));
	    obj.setBirthDate(rs.getDate("BirthDate"));
	    obj.setDepartment(dep);
		return obj;
	}

	private Department instanciateDepartment(ResultSet rs) throws SQLException { // Não será tratado a excessão apenas propagar pois está sendo tratdo a cima
		 Department dep = new Department();
	    	dep.setId(rs.getInt("DepartmentId")); // Instanciando na memoria o seller junto com Department
		    dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() { // Buscar todos os vendedores e ordenar por nome
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = conn.prepareStatement(
						"SELECT seller.*,department.Name as DepName "
						+ "FROM seller INNER JOIN department "
						+ "ON seller.DepartmentId = department.Id "
						+ "ORDER BY Name ");
				
				rs = st.executeQuery(); // comando que busca sql
				
				List<Seller> list = new ArrayList<>();// cria lista de procura de vendedores
				Map<Integer, Department> map = new HashMap<>();
				
				
			    while(rs.next()) {
			    	
			    	Department dep = map.get(rs.getInt("DepartmentId")); // testar se o Departamento já existe
			    	
			    	if(dep == null) {
			    		dep = instanciateDepartment(rs); // Esse código fica de maneira correta com apontando apenas para um tipo de departamento
			    		map.put(rs.getInt("DepartmentId"), dep);
			    	}
			    			
			    	
				    Seller obj = instanciateSeller(rs, dep);
				    list.add(obj);
				    
			} 
				return list;
			} catch(SQLException e) {
				throw new DbException(e.getMessage());			
			} finally {
				DB.closeStatement(st);
				DB.closeResultSet(rs);
			}
		
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name ");
			
			st.setInt(1,department.getId());
			
			rs = st.executeQuery(); // comando que busca sql
			
			List<Seller> list = new ArrayList<>();// cria lista de procura de vendedores
			Map<Integer, Department> map = new HashMap<>();
			
			
		    while(rs.next()) {
		    	
		    	Department dep = map.get(rs.getInt("DepartmentId")); // testar se o Departamento já existe
		    	
		    	if(dep == null) {
		    		dep = instanciateDepartment(rs); // Esse código fica de maneira correta com apontando apenas para um tipo de departamento
		    		map.put(rs.getInt("DepartmentId"), dep);
		    	}
		    			
		    	
			    Seller obj = instanciateSeller(rs, dep);
			    list.add(obj);
			    
		} 
			return list;
		} catch(SQLException e) {
			throw new DbException(e.getMessage());			
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
