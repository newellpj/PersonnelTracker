package co.srsp.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import co.srsp.config.ConfigHandler;
import co.srsp.hibernate.orm.CompanyPositions;
import co.srsp.hibernate.orm.Employee;
import co.srsp.hibernate.orm.EmployeeSkillset;
import co.srsp.hibernate.orm.OrgDepartment;
import co.srsp.rss.model.ValueNamePair;
import co.srsp.viewmodel.EmployeeFacetWrapperModel;
import co.srsp.viewmodel.EmployeeModel;
import co.srsp.viewmodel.EmployeeSkillsetDataModel;
import co.srsp.viewmodel.FacetGroupModel;
import co.srsp.viewmodel.FacetModel;


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
	public List<EmployeeSkillset> getAllSkillsets(){
		 Session session = this.getSessionFactory().openSession();
		 
		 return session.createCriteria(EmployeeSkillset.class).list();
		 
	// List<EmployeeSkillset> list = session.createQuery(" from "+EmployeeSkillset.class.getName()).list();
		 

	}
	
	@Override
	public List<CompanyPositions> getCompanyPositions(){
		 Session session = this.getSessionFactory().openSession();
		 
		 return session.createCriteria(CompanyPositions.class).list();
		 
	// List<EmployeeSkillset> list = session.createQuery(" from "+EmployeeSkillset.class.getName()).list();
		 

	}
	
	@Override
	public List<OrgDepartment> getOrgDepts(){
		 Session session = this.getSessionFactory().openSession();
		 
		 return session.createCriteria(OrgDepartment.class).list();
		 
	// List<EmployeeSkillset> list = session.createQuery(" from "+EmployeeSkillset.class.getName()).list();
		 

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
	
	public EmployeeModel findEmployeePerformanceDetails(EmployeeModel empModel){
		String mainQuery = " select es.idemployee_skillset, dept_name, location, position_name, position_importance, skillset_name, es.proficiency, "+ 
			" current_position_relevance, years_experience "+
		 	" from  org_department o, company_positions c, employee_to_skillset_ratings ets, employee_skillset es, employee e "+
		 	" where e.idorg_department =  o.idorg_department and c.idcompany_positions = e.idcompany_positions and "+
		 	" ets.idemployee = e.idemployee and es.idemployee_skillset = ets.idemployee_skillset and ets.idemployee = "+empModel.getIdemployee();
		
		Session session = this.getSessionFactory().openSession();
		List<Object[]> list = session.createSQLQuery(mainQuery).list();
		//buildEmployeeFullProfileEmployeeModel
		return buildEmployeeFullProfileEmployeeModel(list, empModel);
	}
	
	
	/**
	 * when we already have the employee model build a list of supplementary data
	 * @param list
	 * @return
	 */
	private EmployeeModel buildEmployeeFullProfileEmployeeModel(List<Object[]> list, EmployeeModel empModel){

		List<EmployeeSkillsetDataModel> skillsets = null;	
		EmployeeSkillsetDataModel skillSetModel = null;
		String previousId = ""; 
		int count = 0;
		
		empModel.setEmpSkillsetsDataModel(new ArrayList<EmployeeSkillsetDataModel>());
		
		for(Object obj[] : list){
					
			skillSetModel = new EmployeeSkillsetDataModel();
			skillSetModel.setDepartmentName(obj[1].toString());
			
			log.info("department name : "+skillSetModel.getDepartmentName());
			
			skillSetModel.setCurrentPostionName(obj[3].toString());
			
			log.info("skillset position name : "+skillSetModel.getCurrentPostionName());
			
			skillSetModel.setSkillsetName(obj[5].toString());
			
			log.info("skillset name : "+skillSetModel.getSkillsetName());
			
			skillSetModel.setSkillSetProficiency(Integer.parseInt(obj[6].toString()));
			
			log.info("skillset prof : "+skillSetModel.getSkillSetProficiency());
			
			skillSetModel.setSkillsetToPositionRelevance(Integer.parseInt(obj[7].toString()));
			
			log.info("skillset pos relevance : "+skillSetModel.getSkillsetToPositionRelevance());
			
			skillSetModel.setSkillsetYearsExperience(Integer.parseInt(obj[8].toString()));
			
			log.info("skillset years experience : "+skillSetModel.getSkillsetYearsExperience());
			
			empModel.getEmpSkillsetsDataModel().add(skillSetModel);
			ConfigHandler.setProfilePicData(empModel);
			count++;
		}
		
		//add final model to list - within the loop it happens at start of loop due to duplicate employee personal data showing.			
		
		return empModel;
	}


	@Override
	public  EmployeeFacetWrapperModel  findEmployeesByAnyCriteriaLazyLoad(HashMap<String, String> searchCriteria, int offset, int numberOfRecords){
		log.info("findBooksByAnyCriteriaLazyLoad");
		Session session = this.getSessionFactory().openSession();
		StringBuffer extrasClause = new StringBuffer();
		int count = 0;

		for(String key : searchCriteria.keySet()){
			
			String value = searchCriteria.get(key);
			if(key.contains("1")){
				key = key.replaceAll("1", ".");
			}
			
			extrasClause.append(" and ");
			extrasClause.append(key+" = '"+value+"' ");
			
		}
		
		log.info("extras clause :::: "+extrasClause);
		
		//HashMap<String, String> tagsMap  = searchCriteria.get(SessionConstants.TAGS_SEARCH_CRITERIA);
		
		//cannot paginate this query as it CAN return duplicate employee records - if the employee has more than 1 skillset record - most employees will
		String mainQuery = "select e.idemployee, e.employee_surname, e.employee_first_name, e.employee_given_names, e.employee_age, "+
				" e.employee_gender, e.employee_marital_status, dept_name, location, position_name, position_importance, skillset_name, es.proficiency, "+
				" current_position_relevance, years_experience "+
				" from employee e,  org_department o, company_positions c, employee_to_skillset_ratings ets, employee_skillset es "+
				" where e.idorg_department =  o.idorg_department and c.idcompany_positions = e.idcompany_positions and "+
				" ets.idemployee = e.idemployee and es.idemployee_skillset = ets.idemployee_skillset "+extrasClause.toString()+" order by e.idemployee ";
		
		log.info("main query is : "+mainQuery);
		
		List<Object[]> list = session.createSQLQuery(mainQuery).list();
		
		log.info("THE LIST SIZE RETURNED IS : "+list.size());
		List<EmployeeModel> modelLizt = buildFullProfileEmployeeModel(list);
		HashMap<String, List> modelsAndFacetsMap = new HashMap<String, List>();
		
		EmployeeFacetWrapperModel wrapperModel = new EmployeeFacetWrapperModel();
		
		wrapperModel.setEmployeeModels(modelLizt);
		wrapperModel.setFacetGroupModels(countFacetItems(modelLizt));
		
		return wrapperModel;
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
	
	@Override
	public List<Employee> findEmployeePartialFirstNameMatch(String firstNamePartial, int offset, int numberOfRecords) {
		
		StringBuffer sqlAppender = new StringBuffer();	
		int count = 0;
		
		sqlAppender.append(" from "+Employee.class.getName()+" where ");
		sqlAppender.append("employee_first_name like '%"+firstNamePartial+"%'");
		log.info("sql to exec : "+sqlAppender.toString());
		
		Session session = this.getSessionFactory().openSession();	
		List<Employee> list = session.createQuery(sqlAppender.toString()).setFirstResult(offset).setMaxResults(numberOfRecords).list();
		return list;
	}
	
	@Override
	public List<Employee> findEmployeePartialGivenNamesMatch(String givenNamesPartial, int offset, int numberOfRecords) {
		
		StringBuffer sqlAppender = new StringBuffer();	
		int count = 0;
		
		sqlAppender.append(" from "+Employee.class.getName()+" where ");
		sqlAppender.append("employee_given_names like '%"+givenNamesPartial+"%'");
		log.info("sql to exec : "+sqlAppender.toString());
		
		Session session = this.getSessionFactory().openSession();	
		List<Employee> list = session.createQuery(sqlAppender.toString()).setFirstResult(offset).setMaxResults(numberOfRecords).list();
		return list;
	}
	
	@Override
	public List<Employee> getAllEmployees(int offset, int numberOfRecords) {
		
		StringBuffer sqlAppender = new StringBuffer();	
		int count = 0;
		
		sqlAppender.append(" from "+Employee.class.getName());
		log.info("sql to exec : "+sqlAppender.toString());
		
		Session session = this.getSessionFactory().openSession();	
		List<Employee> list = session.createQuery(sqlAppender.toString()).setFirstResult(offset).setMaxResults(numberOfRecords).list();
		return list;
	}
	
	public  List<EmployeeModel> getAllEmployeesFullProfile(Integer empID, int offset, int numberOfRecords){
		Session session = this.getSessionFactory().openSession();	
		
		
		String extraClause = "";
		
		if(empID != null){
			extraClause = " and e.idemployee = "+empID;
		}
		
		List<Object[]> list = session.createSQLQuery("select e.idemployee, e.employee_surname, e.employee_first_name, e.employee_given_names, e.employee_age, "+
		" e.employee_gender, e.employee_marital_status, dept_name, location, position_name, position_importance, skillset_name, es.proficiency, "+
		" current_position_relevance, years_experience "+
		" from employee e,  org_department o, company_positions c, employee_to_skillset_ratings ets, employee_skillset es "+
		" where e.idorg_department =  o.idorg_department and c.idcompany_positions = e.idcompany_positions and "+
		" ets.idemployee = e.idemployee and es.idemployee_skillset = ets.idemployee_skillset order by e.idemployee "+extraClause).
				setFirstResult(offset).setMaxResults(numberOfRecords).list();

		
		return buildFullProfileEmployeeModel(list);
	}
	
	private List<FacetGroupModel> countFacetItems(List<EmployeeModel> list){
		
		List<FacetModel> facetsModels = new ArrayList<FacetModel>(); 
		
		List<OrgDepartment> orgDeptLizt = getOrgDepts();
		
		List<FacetGroupModel> facetGroupModelLizt = new ArrayList<FacetGroupModel>();
		
		FacetModel facetModel = null;
		
		List<EmployeeModel> matchedList = null;
		
		FacetGroupModel groupModel = new FacetGroupModel();
		
		for(OrgDepartment orgDept : orgDeptLizt){
			matchedList = new ArrayList<EmployeeModel>();
			int count = 0;
			for(EmployeeModel model : list){
				if(orgDept.getDeptName().equalsIgnoreCase(model.getEmpSkillsetsDataModel().get(0).getDepartmentName())){
					count++;				
					matchedList.add(model);
				}
				
			} 
			facetModel = new FacetModel();
			facetModel.setFacetLabel(orgDept.getDeptName());
			facetModel.setEmployeesMatchedAgainstFacetCategory(matchedList);
			facetModel.setFacetCount(String.valueOf(count));
			
			groupModel.getFacetModelsMatchingGroupItems().add(facetModel);
		}
		
		groupModel.setGroupLabel("Organisation Department");

		facetGroupModelLizt.add(groupModel);
		
		List<CompanyPositions> positionsLizt = this.getCompanyPositions();
		
		
		groupModel = new FacetGroupModel();
		
		for(CompanyPositions companyPos : positionsLizt){
			matchedList = new ArrayList<EmployeeModel>();
			int count = 0;
			for(EmployeeModel model : list){
				if(companyPos.getPositionName().equalsIgnoreCase(model.getEmpSkillsetsDataModel().get(0).getCurrentPostionName())){
					count++;
					matchedList.add(model);
				}
			} 
			facetModel = new FacetModel();
			facetModel.setFacetLabel(companyPos.getPositionName());
			facetModel.setEmployeesMatchedAgainstFacetCategory(matchedList);
			facetModel.setFacetCount(String.valueOf(count));
			groupModel.getFacetModelsMatchingGroupItems().add(facetModel);
			
		}
		
		groupModel.setGroupLabel("Company Positions");
		facetGroupModelLizt.add(groupModel);
		
		
		List<EmployeeSkillset> skillsetsLizt = this.getAllSkillsets();
		
		groupModel = new FacetGroupModel();
		
		for(EmployeeSkillset skillset : skillsetsLizt){
			matchedList = new ArrayList<EmployeeModel>();
			int count = 0;
			for(EmployeeModel model : list){
				
				for(EmployeeSkillsetDataModel skillsetDataModel : model.getEmpSkillsetsDataModel()){
					if(skillset.getSkillsetName().equalsIgnoreCase(skillsetDataModel.getSkillsetName())){
						count++;
						matchedList.add(model); //may add the same 
					}
				}
			} 
			
			facetModel = new FacetModel();
			facetModel.setFacetLabel(skillset.getSkillsetName());
			facetModel.setEmployeesMatchedAgainstFacetCategory(matchedList);
			facetModel.setFacetCount(String.valueOf(count));			
			groupModel.getFacetModelsMatchingGroupItems().add(facetModel);
			
			
		}
		
		groupModel.setGroupLabel("Skillsets");
		facetGroupModelLizt.add(groupModel);

		return facetGroupModelLizt;
	}

	
	private List<EmployeeModel> buildFullProfileEmployeeModel(List<Object[]> list){

		List<EmployeeModel> employeeModels = new ArrayList<EmployeeModel>(); 
		EmployeeModel empModel = null;
		List<EmployeeSkillsetDataModel> skillsets = null;	
		EmployeeSkillsetDataModel skillSetModel = null;
		String previousId = ""; 
		int count = 0;
		for(Object obj[] : list){

			//we do not want to add the employee personal details multiple times to the list.
			//The same employee will be in the query returned MORE THAN ONCE if they have 2 or more skillsets
			//in the skillsets table. Hence this if condition that checks if the prev employee id matches the current
			// - then we will NOT add their basic personal details a second time.
			//While we could separate this query into smaller ones I believe doing a single db query at this stage is more efficient than
			//a lot of smaller ones.
			
			if(!previousId.equals(obj[0].toString())){
				
				if(empModel != null){ //once we determine we have a new employee 
									   //add the previous model to the list to return
					employeeModels.add(empModel);
				}
				
				empModel = new EmployeeModel();
				previousId = obj[0].toString(); 
				empModel.setIdemployee(Integer.parseInt(obj[0].toString()));
				empModel.setEmployeeSurname(obj[1].toString());
				empModel.setEmployeeFirstName(obj[2].toString());
				empModel.setEmployeeGivenNames(obj[3].toString());
				empModel.setEmployeeAge(obj[4].toString());
				empModel.setEmployeeGender(obj[5].toString());
				empModel.setEmployeeMaritalStatus(obj[6].toString());
				skillsets = new ArrayList<EmployeeSkillsetDataModel>();
				empModel.setEmpSkillsetsDataModel(skillsets);
				
			}
					
			skillSetModel = new EmployeeSkillsetDataModel();
			skillSetModel.setDepartmentName(obj[7].toString());
			skillSetModel.setCurrentPostionName(obj[9].toString());
			skillSetModel.setSkillsetName(obj[11].toString());
			skillSetModel.setSkillSetProficiency(Integer.parseInt(obj[12].toString()));
			skillSetModel.setSkillsetToPositionRelevance(Integer.parseInt(obj[13].toString()));
			skillSetModel.setSkillsetYearsExperience(Integer.parseInt(obj[14].toString()));
			empModel.getEmpSkillsetsDataModel().add(skillSetModel);
			ConfigHandler.setProfilePicData(empModel);
			
			log.info("obj object 1 : "+obj[0].toString());
			log.info("the object 2 : "+obj[1].toString());
			count++;
		}
		
		//add final model to list - within the loop it happens at start of loop due to duplicate employee personal data showing.
		employeeModels.add(empModel);
				
		
		return employeeModels;
	}
	
	public  List<EmployeeModel> getEmployeesFullProfile(String searchCriteria, int offset, int numberOfRecords){
		return null;
	}
	
	/*
	public  List<EmployeeModel> getEmployeesFullProfile(String searchCriteria, int offset, int numberOfRecords){
		Session session = this.getSessionFactory().openSession();	
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("select e.employee_surname, e.employee_first_name, e.employee_given_names, e.employee_age, ");
		sb.append(" e.employee_gender, e.employee_marital_status, dept_name, location, position_name, position_importance, skillset_name, proficiency, ");
		sb.append(" current_position_relevance, years_experience ");
		sb.append(" from employee e,  org_department o, company_positions c, employee_to_skillset_ratings ets, employee_skillset es where ");
		
		+<insert search criteria here>+
				+ "e.idorg_department =  o.idorg_department and c.idcompany_positions = e.idcompany_positions and "+
		" ets.idemployee = e.idemployee and es.idemployee_skillset = ets.idemployee_skillset").list();

		for(Object obj : list){
			log.info("obj class"+obj.getClass());
			log.info("the object : "+obj.toString());
			
		}
				
		
		return null;
	}*/
	
	/*
	public List<Books> findBooksByAnyCriteriaLazyLoad(HashMap<String, HashMap<String, String>> searchCriteria, int offset, int numberOfRecords){
		log.info("findBooksByAnyCriteriaLazyLoad");
		Session session = this.getSessionFactory().openSession();
		StringBuffer sqlAppender = new StringBuffer();
		int count = 0;
		
		HashMap<String, String> tagsMap  = searchCriteria.get(SessionConstants.TAGS_SEARCH_CRITERIA);
		HashMap<String, String> booksMap  = searchCriteria.get(SessionConstants.BOOKS_SEARCH_CRITERIA);
		
		if(tagsMap != null && tagsMap.size() > 0 && booksMap != null && booksMap.size() > 0){
			log.info("tags and books...................................");
			HashMap<String, String> tagsKeyValues = searchCriteria.get(SessionConstants.TAGS_SEARCH_CRITERIA);

			for(String key : tagsKeyValues.keySet()){
				
				count++;
				
				if(count > 1){
					sqlAppender.append(" and idbooks in (");
				}
				
				sqlAppender.append(" select idbooks from book_tags where ");
				sqlAppender.append("  UPPER(tag_type) = "+"UPPER('"+key+"')");
				sqlAppender.append(" and UPPER(tag_value) = "+"UPPER('"+tagsKeyValues.get(key)+"')");
				
				if(count > 1){
					sqlAppender.append(")");
				}
			
			}

			log.info("sql appender value ::: "+sqlAppender.toString());
			//List list = session.createSQLQuery(sqlAppender.toString()).list(); //allows you to create native sql query
			
			
			HashMap<String, String> booksSearchCriteria = searchCriteria.get(SessionConstants.BOOKS_SEARCH_CRITERIA);
			
			String booksWhereClause = "";
			
			if(sqlAppender.toString().length() > 0){
				booksWhereClause += " and idbooks in (select idbooks from books ";
			}
			
			booksWhereClause += " where ";
			
			count = 0;
			
			Map booksValuesMap = new HashMap();
			
			for(String booksKey : booksSearchCriteria.keySet()){
			
				if(count > 0){
					booksWhereClause += " and ";
				}
				
				booksWhereClause += "UPPER("+booksKey.toLowerCase()+") = UPPER('"+booksSearchCriteria.get(booksKey)+"')";
				booksValuesMap.put(booksKey.toLowerCase(), searchCriteria.get(booksKey));
				count++;
			}
			
			booksWhereClause += ")";
			
			log.info("booksWhereClause :::: "+booksWhereClause);
			
			sqlAppender.append(booksWhereClause);
			
			//TODO the pagination part of this query set
			
			log.info("sql appender ::: "+sqlAppender.toString());
			log.info("sql offset ::: "+offset);
			log.info("sql numberOfRecords ::: "+numberOfRecords);
			
			//setProperties(booksValuesMap).setFirstResult(offset).setMaxResults(numberOfRecords).list();
			
			//List list = session.createSQLQuery(sqlAppender.toString()).setFirstResult(offset).setMaxResults(numberOfRecords).list();
			
			List list = session.createSQLQuery(sqlAppender.toString()).list();
			
			log.info("list size returned : "+list);
			log.info("list size returned : "+list.size());
			
			List<Books> books = new ArrayList<Books>();
			
			for(Object obj : list){
				
				int idbooks = (Integer)obj;
				log.info("id books to search on : "+idbooks);
				String sql = " from "+Books.class.getName()+" where idbooks = :booksid ";
				books.addAll(session.createQuery(sql).setParameter("booksid", idbooks).list());
				log.info("id books returned : "+idbooks);
				
			}
			
			session.close();
			
			return books;
			
		}else if((tagsMap != null && tagsMap.size() > 0) && (booksMap == null || booksMap.size() <= 0)){
			//TODO the pagination part of this query set
			log.info("tags only search criteria");
			return findBooksByTagsLazyLoad(searchCriteria.get(SessionConstants.TAGS_SEARCH_CRITERIA), offset, numberOfRecords);
			
		}else if(booksMap != null && booksMap.size() > 0){
			log.info("books only search criteria");
			HashMap<String, String> booksSearchCriteria = searchCriteria.get(SessionConstants.BOOKS_SEARCH_CRITERIA);
			
			String booksWhereClause = "";
			
			if(sqlAppender.toString().length() > 0){
				booksWhereClause += " and idbooks in (select idbooks from books ";
			}
			
			booksWhereClause = " where ";
			
			count = 0;
			Map booksValuesMap = new HashMap();
			log.info("before key message size : "+booksSearchCriteria.size());
			for(String booksKey : booksSearchCriteria.keySet()){
			
				log.info("count in books search criteria : "+count);
				log.info("key in books search criteria : "+booksKey);
				
				if(count > 0){
					booksWhereClause += " and ";
				}
				log.info("books key : "+booksKey.toLowerCase());
				log.info("books criteria values : "+booksSearchCriteria.get(booksKey));
				
				booksWhereClause += " UPPER("+booksKey.toLowerCase()+") = UPPER(:"+booksKey+") ";
				booksValuesMap.put(booksKey.toLowerCase(), booksSearchCriteria.get(booksKey));
				count++;
				
			}
			
			log.info("booksWhereClause :::: "+booksWhereClause);
			log.info("sql appender ::: "+sqlAppender.toString());
			log.info("sql offset ::: "+offset);
			log.info("sql numberOfRecords ::: "+numberOfRecords);
			
			
			//TODO the pagination part of this query set
			try{
				List list = session.createQuery(" from "+Books.class.getName()+booksWhereClause).setProperties(booksValuesMap).setFirstResult(offset).setMaxResults(numberOfRecords).list();
				log.info("list size returned : "+list);
				log.info("list size returned : "+list.size());
				
				return list;
				
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage());
			}
			
		}
		
		return null;
	}
	*/

}
