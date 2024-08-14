package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createdSellerDao();	// assin se instancia o objeto
		
		System.out.println("=== TEST 1: SELLER FindById ===");
		Seller seller = sellerDao.findById(3);		
		System.out.println(seller);
		
		System.out.println("\n=== TEST 2: SELLER FindByDepartment ===");	
		Department department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		for(Seller obj : list) {
			System.out.println(obj);
		}
		System.out.println("\n=== TEST 3: SELLER FindAllt ===");	
		list = sellerDao.findAll();		
		for(Seller obj : list) {
			System.out.println(obj);
		
		}
		System.out.println("\n=== TEST 4: SELLER insert ===");	
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.00, department);
		sellerDao.insert(newSeller);// inserindo no banco de dados
		System.out.println("Inserted! New id = " + newSeller.getId());
	}
}


