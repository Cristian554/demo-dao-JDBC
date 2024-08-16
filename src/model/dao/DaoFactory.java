package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	
	public static SellerDao createdSellerDao() { // uma maneira estatica sem precisar explicitar a instancia no programa principal
		return new SellerDaoJDBC(DB.getConnection());		
	}
	public static DepartmentDao createdDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
		
	}

}
