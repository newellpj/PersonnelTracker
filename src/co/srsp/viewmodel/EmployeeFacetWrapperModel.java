package co.srsp.viewmodel;

import java.util.List;

public class EmployeeFacetWrapperModel {
	
	private List<EmployeeModel> employeeModels;
	
	private List<FacetGroupModel> facetGroupModels;

	public List<EmployeeModel> getEmployeeModels() {
		return employeeModels;
	}

	public void setEmployeeModels(List<EmployeeModel> employeeModels) {
		this.employeeModels = employeeModels;
	}

	public List<FacetGroupModel> getFacetGroupModels() {
		return facetGroupModels;
	}

	public void setFacetGroupModels(List<FacetGroupModel> facetModels) {
		this.facetGroupModels = facetModels;
	}
	
	

}
