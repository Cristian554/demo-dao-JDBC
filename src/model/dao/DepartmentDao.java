package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {
	
	void insert(Department obj);   // inserir no banco de dados a entrada o argumento
	void update(Department obbj); // atualização do banco de dados
	void deleteById(Department obj); // deletar do banco de dados
	Department findById(Integer id);// buscar através do Id o Objeto no banco de dados
	List<Department> findAll(); // procurar tudo na lista
	
}
