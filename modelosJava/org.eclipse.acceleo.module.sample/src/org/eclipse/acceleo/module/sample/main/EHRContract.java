package org.example;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;

import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import static java.nio.charset.StandardCharsets.UTF_8;
	
@Contract(name = "ehr",
	info = @Info(title = "ehr",
				description = "",
				version = "1",
				license = 
						@License(name = "apache",
								url = "apache.com"),
								contact = @Contact(email = "j@gmail.com",
											name = "Juan",
											url = "ehr.com")))

@Default
public class EHRContract implements ContractInterface {

	public EHRContract() {
	}
	
	@Transaction(intent = Transaction.TYPE.EVALUATE)
	public String EHRAssetExists(Context ctx, String ID){
		
		byte [] buffer = ctx.GetStub().GetState(ID);	
		return (buffer != null && buffer.length > 0);
	}

	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public void createEHRAsset(Context ctx, String ID, String name, String[] data){
		
		boolean exists = EHRAsset(ctx,ID);
		if (exists){
			throw new RuntimeException("The asset "+ ID   +" already exists");
		}
		EHRAsset asset = new EHRAsset();
		asset.setName(name);
		asset.setData(data);
		
		ctx.getStub().putState( ID   , asset.toJSONString().getBytes(UTF_8));
	}

	@Transaction(intent = Transaction.TYPE.EVALUATE)
	public EHRAsset readEHRAsset(Context ctx, String ID){
		
		boolean exists = EHRAsset(ctx,ID);
		if (!exists){
			throw new RuntimeException("The asset "+ID+" does not exist");
		}
		EHRAsset newAsset = EHRAsset.fromJSONString(new String(ctx.getStub().getState( ID ),UTF_8));
		return asset;
	}


}
