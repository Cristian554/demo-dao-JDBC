package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createdSellerDao();	// assin se instancia o objeto
		
		Seller seller = sellerDao.findById(3);
		
		System.out.println(seller);
	}

}
