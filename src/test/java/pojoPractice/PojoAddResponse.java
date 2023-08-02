package pojoPractice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PojoAddResponse {
	@JsonProperty
	private String msg;
	@JsonProperty
	private String id;
	
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getID() {
		return id;
	}
	public void setID(String iD) {
		this.id = iD;
	}
	
	
	
	

}
