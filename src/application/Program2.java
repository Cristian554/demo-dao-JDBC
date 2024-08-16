package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		DepartmentDao departmentdao = DaoFactory.createdDepartmentDao();
		
		System.out.println("=== TEST 1: DEPARTMENT FindById ===");
		Department department = departmentdao.findById(1);
		System.out.println(department);
		
		System.out.println("=== TEST 2: DEPARTMENT FindAll ===");
		List<Department> list = departmentdao.findAll();
		for(Department obj : list) {
			System.out.println(obj);
		}	
		System.out.println("=== TEST 3: DEPARTMENT DeleteById===");	
		System.out.print("Enter id for delete test: ");
		int id = sc.nextInt();
		departmentdao.deleteById(id);
		System.out.println("Delete Complete");
		
		System.out.println("=== TEST 4: DEPARTMENT update===");	
		department = departmentdao.findById(1);
		department.setName("Games");
		departmentdao.update(department);
		System.out.println("Update Complete");
		
		System.out.println("\n=== TEST 5: DEPARTMENT insert ===");	
		Department newDepartment = new Department(null, "Pc Gamer");
		departmentdao.insert(newDepartment);
		System.out.println("Inserted! New Id =" + newDepartment.getId());
				
		sc.close();
		}

	}


