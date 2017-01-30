package co.srsp.viewmodel;

import java.util.List;

public class FacetModel {
	
	private String facetLabel;
	private String facetCount;
	private List<EmployeeModel> employeesMatchedAgainstFacetCategory;
	
	public String getFacetLabel() {
		return facetLabel;
	}
	public void setFacetLabel(String facetLabel) {
		this.facetLabel = facetLabel;
	}
	public String getFacetCount() {
		return facetCount;
	}
	public void setFacetCount(String facetCount) {
		this.facetCount = facetCount;
	}
	public List<EmployeeModel> getEmployeesMatchedAgainstFacetCategory() {
		return employeesMatchedAgainstFacetCategory;
	}
	public void setEmployeesMatchedAgainstFacetCategory(List<EmployeeModel> employeesMatchedAgainstFacetCategory) {
		this.employeesMatchedAgainstFacetCategory = employeesMatchedAgainstFacetCategory;
	}
	
    
	
}
