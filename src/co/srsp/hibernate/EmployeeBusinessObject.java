package co.srsp.hibernate;

import java.util.HashMap;
import java.util.List;

import co.srsp.hibernate.orm.BookReviews;
import co.srsp.hibernate.orm.Books;
import co.srsp.hibernate.orm.Employee;

public interface EmployeeBusinessObject {
	public void save(Employee employee);
	public void update(Employee employee);
	public void delete(Employee employee);
	
	public List<Employee> findEmployeeBySurnameOnly(String surname, int offset, int numberOfRecords);
	
	/**
	 * gets book by title and/or author. If author is null it will just search on title.
	 * @param title
	 * @param author
	 * @return Books ORM object
	 */
	public  List<Employee> findEmployeeByFullName(String surname, String firstName, String givenNames, int offset, int numberOfRecords);
	
	public  List<Employee> findEmployeePartialSurnameMatch(String surnamePartial,  int offset, int numberOfRecords);
}
