package model.dao;

public class DaoFactory {
	
	public static SellerDao createdSellerDao() {
		return new SellerDaoJDBC();
		
	}

}
