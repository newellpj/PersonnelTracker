package co.srsp.viewmodel;

import java.util.List;

public class HTMLModel {
	
	private String imageWidth;
	private String imageHeight;
	private String profilePicURL;
	private String employeeFirstName;
	private String employeeGivenNames;
	private String employeeSurname;
	private String employeeAge;
	private String employeeGender;
	private String employeeMaritalStatus;
	private String currentPostionName;
	private String departmentName;
	private List<HTMLModelSkillsets> skillsetsList;

	
	
	
	
	public List<HTMLModelSkillsets> getskillsetsList() {
		return skillsetsList;
	}

	public void setskillsetsList(List<HTMLModelSkillsets> skillsetsList) {
		this.skillsetsList = skillsetsList;
	}

	public String getcurrentPositionName() {
		return currentPostionName;
	}

	public void setcurrentPositionName(String currentPostionName) {
		this.currentPostionName = currentPostionName;
	}

	public String getimageWidth() {
		return imageWidth;
	}
	
	public void setimageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}
	
	public String getimageHeight() {
		return imageHeight;
	}
	
	public void setimageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}
	
	public String getprofilePicURL() {
		return profilePicURL;
	}
	
	public void setprofilePicURL(String profilePicURL) {
		this.profilePicURL = profilePicURL;
	}
	
	public String getemployeeFirstName() {
		return employeeFirstName;
	}
	
	public void setemployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}
	
	public String getemployeeGivenNames() {
		return employeeGivenNames;
	}
	
	public void setemployeeGivenNames(String employeeGivenNames) {
		this.employeeGivenNames = employeeGivenNames;
	}
	
	public String getemployeeSurname() {
		return employeeSurname;
	}
	
	public void setemployeeSurname(String employeeSurname) {
		this.employeeSurname = employeeSurname;
	}
	
	public String getemployeeAge() {
		return employeeAge;
	}
	
	public void setemployeeAge(String employeeAge) {
		this.employeeAge = employeeAge;
	}
	
	public String getemployeeGender() {
		return employeeGender;
	}
	
	public void setemployeeGender(String employeeGender) {
		this.employeeGender = employeeGender;
	}
	
	public String getemployeeMaritalStatus() {
		return employeeMaritalStatus;
	}
	
	public void setemployeeMaritalStatus(String employeeMaritalStatus) {
		this.employeeMaritalStatus = employeeMaritalStatus;
	}
	
	public String getdepartmentName() {
		return departmentName;
	}
	
	public void setdepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	


	
}
