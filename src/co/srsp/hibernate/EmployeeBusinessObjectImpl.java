package co.srsp.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import co.srsp.hibernate.orm.Employee;


@Configuration
@EnableAspectJAutoProxy
public class EmployeeBusinessObjectImpl extends HibernateDaoSupport implements EmployeeBusinessObject{

	private final static Logger log = Logger.getLogger(EmployeeBusinessObjectImpl.class); 


	@Override
	@Transactional
	public void save(Employee employee) {
		Session session = this.getSessionFactory().openSession();
		session.save(employee);
		session.flush();
		session.close();
	}

	@Override
	@Transactional
	public void update(Employee employee) {
	
		Session session = this.getSessionFactory().openSession();
		session.update(employee);
		session.flush();
		session.close();
	}

	@Override
	public void delete(Employee employee) {
		Session session = this.getSessionFactory().openSession();
		session.delete(employee);
		session.flush();
		session.close();
	}

	@Override
	public List<Employee> findEmployeeBySurnameOnly(String surname, int offset, int numberOfRecords) {
		// TODO Auto-generated method stub
		return findEmployeePartialSurnameMatch(surname, offset, numberOfRecords);
	}

	@Override
	public List<Employee> findEmployeeByFullName(String surname, String firstName, String givenNames, int offset,
			int numberOfRecords) {
		StringBuffer sqlAppender = new StringBuffer();	
		sqlAppender.append("from "+Employee.class.getName()+" where ");
		sqlAppender.append("employee_surname like '%"+surname+"%' and employee_first_name like %"+firstName+"%"+" and employee_given_name like %"+givenNames+"% ");
		Session session = this.getSessionFactory().openSession();	
		List<Employee> list = session.createQuery(sqlAppender.toString()).setFirstResult(offset).setMaxResults(numberOfRecords).list();
		return list;

	}

	@Override
	public List<Employee> findEmployeePartialSurnameMatch(String surnamePartial, int offset, int numberOfRecords) {
		
		StringBuffer sqlAppender = new StringBuffer();	
		int count = 0;
		
		sqlAppender.append(" from "+Employee.class.getName()+" where ");
		sqlAppender.append("employee_surname like '%"+surnamePartial+"%'");
		log.info("sql to exec : "+sqlAppender.toString());
		
		Session session = this.getSessionFactory().openSession();	
		List<Employee> list = session.createQuery(sqlAppender.toString()).setFirstResult(offset).setMaxResults(numberOfRecords).list();
		return list;
	}

}
