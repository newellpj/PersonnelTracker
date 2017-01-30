package co.srsp.rss.model;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

public class ValueNamePair implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String value;
	private String name;
	private List<?> valueList;
	
	public ValueNamePair(){
		
	}
	
	public ValueNamePair(String name, List<?> valueList){
		this.name = name;
		this.valueList = valueList;
	}
	
	public ValueNamePair(String name, String value){
		this.name = name;
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<?> getValueList() {
		return valueList;
	}
	public void setValueList(List<?> valueList) {
		this.valueList = valueList;
	}
	
	
	
	

}
