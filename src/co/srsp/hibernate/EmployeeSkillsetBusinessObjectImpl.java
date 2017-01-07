 package co.srsp.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import co.srsp.hibernate.orm.Employee;
import co.srsp.hibernate.orm.EmployeeSkillset;
import co.srsp.hibernate.orm.EmployeeToSkillsetRatings;

public class EmployeeSkillsetBusinessObjectImpl extends HibernateDaoSupport implements EmployeeSkillsetBusinessObject {

	private final static Logger log = Logger.getLogger(EmployeeSkillsetBusinessObjectImpl.class); 
	
	@Override
	public List<Employee> findEmployeesWithSkillset(String skillset) {
		
		List<EmployeeSkillset> skillsList = this.findEmployeeSkillsetByParetialMatch(skillset);	
		
		List<Employee> employeesList = null;
		
		for(EmployeeSkillset skillSet : skillsList){
			StringBuffer sqlAppender = new StringBuffer();
			sqlAppender.append("from "+EmployeeToSkillsetRatings.class.getName()+" where ");
			sqlAppender.append("idemployee_skillset = "+skillSet.getIdemployeeSkillset());
			log.info("sql to exec : "+sqlAppender.toString());
			Session session = this.getSessionFactory().openSession();	
			List<EmployeeToSkillsetRatings> list = session.createQuery(sqlAppender.toString()).list();
			
			StringBuffer employeeId = new StringBuffer();
			employeeId.append("in ( ");
			
			int size = list.size();
			int count = 0;
			
			for(EmployeeToSkillsetRatings skillsetRatings : list){
				count++;
				employeeId.append(skillsetRatings.getEmployee());
				
				if(count < size){
					employeeId.append(",");
				}
			}
			
			employeeId.append(") ");
			
			return session.createQuery("from "+Employee.class.getName()+" where idemployee "+employeeId.toString()).list();
			
		//	List<Employee> employeesWithSkillsetList = 
			
		}
		
	   return null;
	}

	@Override
	public List<EmployeeSkillset> findEmployeeSkillsetByParetialMatch(String skillsetPartial) {

		StringBuffer sqlAppender = new StringBuffer();	
		sqlAppender.append("from "+EmployeeSkillset.class.getName()+" where ");
		sqlAppender.append("position_name like '%"+skillsetPartial+"%' ");
		Session session = this.getSessionFactory().openSession();	
		List<EmployeeSkillset> list = session.createQuery(sqlAppender.toString()).list();
		return list;
		
	}
	
	public void save(EmployeeSkillset employeeSkillset) {
		Session session = this.getSessionFactory().openSession();
		session.save(employeeSkillset);
		session.flush();
		session.close();
	}

	@Override
	@Transactional
	public void update(EmployeeSkillset employeeSkillset) {
	
		Session session = this.getSessionFactory().openSession();
		session.update(employeeSkillset);
		session.flush();
		session.close();
	}

	@Override
	public void delete(EmployeeSkillset employeeSkillset) {
		Session session = this.getSessionFactory().openSession();
		session.delete(employeeSkillset);
		session.flush();
		session.close();
	}

}
