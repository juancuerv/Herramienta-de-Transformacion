package default;

import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;

@DataType()
public class Patient{

	@Property
	public String ID;
	@Property
	public String Name;

	public Patient(){
	}

	public String getID(){
		return ID;
	}
	public String getName(){
		return Name;
	}

	public void setID(String ID){
		this.ID = ID;
	}
	public void setName(String Name){
		this.Name = Name;
	}

	public String toJSONString() {
		return new JSONObject(this).toString();
	}

	public static Patient fromJSONString(String json) {
		JSONObject jsonObject = new JSONObject(json);
		String ID = jsonObject.getString("ID");
		String Name = jsonObject.getString("Name");
	    Patient asset = new Patient();
		asset.setID(ID);
		asset.setName(Name);
	    return asset;
	}
}
