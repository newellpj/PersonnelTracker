package co.srsp.hibernate;


import java.util.List;

import co.srsp.hibernate.orm.Employee;
import co.srsp.hibernate.orm.EmployeeSkillset;

/**
 * Home object for domain model class EmployeeSkillset.
 * @see .EmployeeSkillset
 * @author Hibernate Tools
 */
public interface EmployeeSkillsetBusinessObject {

	public void save(EmployeeSkillset employeeSkillset);
	
	public void update(EmployeeSkillset employeeSkillset);
	
	public void delete(EmployeeSkillset employeeSkillset);
	
	public  List<Employee> findEmployeesWithSkillset(String skillset);
	
	public  List<EmployeeSkillset> findEmployeeSkillsetByParetialMatch(String skillsetPartial);
}
