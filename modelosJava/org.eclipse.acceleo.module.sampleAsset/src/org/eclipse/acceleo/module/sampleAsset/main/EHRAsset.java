package org.example;

import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;

@DataType()
public class EHRAsset{

	@Property
	public String name;
	@Property
	public String[] data;

	public EHRAsset(){
	}

	public String getName(){
		return name;
	}
	public String[] getData(){
		return data;
	}

	public void setName(String name){
		this.name = name;
	}
	public void setData(String[] data){
		this.data = data;
	}

	public String toJSONString() {
		return new JSONObject(this).toString();
	}

	public static EHRAsset fromJSONString(String json) {
		JSONObject jsonObject = new JSONObject(json);
		String name = jsonObject.getString("name");
		String[] data = jsonObject.getString("data");
	    EHRAsset asset = new EHRAsset();
		asset.setName(name);
		asset.setData(data);
	    return asset;
	}
}
