package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
	
	void insert(Seller obj);   
	void update(Seller obbj); 
	void deleteById(Seller obj);
	Seller findById(Integer id);
	List<Seller> findAll(); // 
	List<Seller> findByDepartment(Department department);// Método de busca do department

}
