package hibernate;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


public class Test {

	private static SessionFactory factory; 
	
	public static void main(String[] args) {
		Logger log = Logger.getLogger("Hib");
		
		try{
	         Configuration conf = new Configuration();
	         conf.addAnnotatedClass(Employee.class);
	         conf.configure();
	         ServiceRegistry sv = new ServiceRegistryBuilder().applySettings(conf.getProperties()).buildServiceRegistry();
	         factory = conf.buildSessionFactory(sv);
	         
//	                   configure().buildSessionFactory();
	                   //addPackage("com.xyz") //add package if used.
	         log.info("Done getting factory session!");
	                   
	      }catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         log.error("Failed to create sessionFactory object");
	         throw new ExceptionInInitializerError(ex);
	         
	      }
		Test test1 = new Test();
		Integer empID1 = test1.addEmployee("Lucas", "Torres", 3000);
//		System.out.println(empID1);
		log.info("New added ID: " + empID1);

	}
	
	 public int addEmployee(String fname, String lname, int salary){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      Integer employeeID = null;
	      try{
	         tx = session.beginTransaction();
	         Employee employee = new Employee();
	         employee.setFirstName(fname);
	         employee.setLastName(lname);
	         employee.setSalary(salary);
	         employeeID = (Integer) session.save(employee);
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return employeeID;
	   }

}
