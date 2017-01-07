package co.srsp.viewmodel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.srsp.hibernate.orm.CompanyPositions;
import co.srsp.hibernate.orm.OrgDepartment;

public class EmployeeModel {
	
	private Integer idemployee;
	private CompanyPositions companyPositions;
	private OrgDepartment orgDepartment;
	private String employeeSurname;
	private String employeeFirstName;
	private String employeeGivenNames;
	private String employeeAddress;
	private String employeeAge;
	private String employeeGender;
	private String employeeMaritalStatus;
	private String employeecol;
	private Set employeeToSkillsetRatingses = new HashSet(0);
	
	private String imageHeight;
	
	private String imageWidth;
	
	private String thumbnailLocation;
	
	public EmployeeModel(){
		
	}

	public String getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getImageWidth() {
		return imageWidth;
	}



	public void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}



	public String getThumbnailLocation() {
		return thumbnailLocation;
	}



	public void setThumbnailLocation(String thumbnailLocation) {
		this.thumbnailLocation = thumbnailLocation;
	}



	public Integer getIdemployee() {
		return idemployee;
	}


	public void setIdemployee(Integer idemployee) {
		this.idemployee = idemployee;
	}


	public CompanyPositions getCompanyPositions() {
		return companyPositions;
	}


	public void setCompanyPositions(CompanyPositions companyPositions) {
		this.companyPositions = companyPositions;
	}


	public OrgDepartment getOrgDepartment() {
		return orgDepartment;
	}


	public void setOrgDepartment(OrgDepartment orgDepartment) {
		this.orgDepartment = orgDepartment;
	}


	public String getEmployeeSurname() {
		return employeeSurname;
	}


	public void setEmployeeSurname(String employeeSurname) {
		this.employeeSurname = employeeSurname;
	}


	public String getEmployeeFirstName() {
		return employeeFirstName;
	}


	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}


	public String getEmployeeGivenNames() {
		return employeeGivenNames;
	}


	public void setEmployeeGivenNames(String employeeGivenNames) {
		this.employeeGivenNames = employeeGivenNames;
	}


	public String getEmployeeAddress() {
		return employeeAddress;
	}


	public void setEmployeeAddress(String employeeAddress) {
		this.employeeAddress = employeeAddress;
	}


	public String getEmployeeAge() {
		return employeeAge;
	}


	public void setEmployeeAge(String employeeAge) {
		this.employeeAge = employeeAge;
	}


	public String getEmployeeGender() {
		return employeeGender;
	}


	public void setEmployeeGender(String employeeGender) {
		this.employeeGender = employeeGender;
	}


	public String getEmployeeMaritalStatus() {
		return employeeMaritalStatus;
	}


	public void setEmployeeMaritalStatus(String employeeMaritalStatus) {
		this.employeeMaritalStatus = employeeMaritalStatus;
	}


	public String getEmployeecol() {
		return employeecol;
	}


	public void setEmployeecol(String employeecol) {
		this.employeecol = employeecol;
	}


	public Set getEmployeeToSkillsetRatingses() {
		return employeeToSkillsetRatingses;
	}


	public void setEmployeeToSkillsetRatingses(Set employeeToSkillsetRatingses) {
		this.employeeToSkillsetRatingses = employeeToSkillsetRatingses;
	}
	
	

	
}
