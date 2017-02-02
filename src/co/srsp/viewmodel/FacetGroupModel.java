package co.srsp.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FacetGroupModel {
	
	private String groupLabel;
	
	private List<FacetModel> facetModelsMatchingGroupItems;

	public String getGroupLabel() {
		return groupLabel;
	}

	public void setGroupLabel(String groupLabel) {
		this.groupLabel = groupLabel;
	}

	public List<FacetModel> getFacetModelsMatchingGroupItems() {
		if(facetModelsMatchingGroupItems == null){
			facetModelsMatchingGroupItems = new ArrayList<FacetModel>();
		}
		
		return facetModelsMatchingGroupItems;
	}

	public void setFacetModelsMatchingGroupItems(List<FacetModel> facetModelsMatchingGroupItems) {
		this.facetModelsMatchingGroupItems = facetModelsMatchingGroupItems;
	}
	
	

}
