package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{
	
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO department "
					+ "(Name) "
				    + "VALUES "
					+ "(?)",
					Statement.RETURN_GENERATED_KEYS); // Coloca na tela o Id do vendedor no caso o Generate Key
					
			
			st.setString(1, obj.getName());
			
					
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);// Popular dentro do novo id instanciado no obj
					obj.setId(id);
					
				} DB.closeResultSet(rs);
			} 
			else { // Lança uma excessão no caso se nenhuma linha foi alterado
				
				throw new DbException("Unexpected error! No rows Affected");
				
			}
		
		
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE department "
					+ "SET Name = ? "
					+ "WHERE Id = ? ");
					
					
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
		
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
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ? ");
					
			st.setInt(1, id);		
					
			st.executeUpdate();
			
		} catch(SQLException e){
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}
		
	

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM department WHERE Id = ?");
						
			st.setInt(1,id);
			
			rs = st.executeQuery(); // comando que busca sql
			
		    if(rs.next()) {
		    	
		       Department obj = new Department();
			   obj.setId(rs.getInt("Id"));
			   obj.setName(rs.getString("Name"));
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


	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM department ORDER BY name");
			
			rs = st.executeQuery();
			
			List<Department> list = new ArrayList<>();
			
			while(rs.next()) {
				Department obj = new Department();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				list.add(obj);
				
			} return list;
			
			
		} catch (SQLException e ) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	
		
	}

	
	}


