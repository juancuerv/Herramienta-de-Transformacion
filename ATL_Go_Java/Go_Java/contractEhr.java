package main;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;

import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import static java.nio.charset.StandardCharsets.UTF_8;
	
@Contract(name = "Default",
	info = @Info(title = "default",
				description = "Este es un ejemplo por defecto de la estructura de la metadata del contrato",
				version = "1.0",
				license = 
						@License(name = "default",
								url = "www.google.com"),
								contact = @Contact(email = "default@gmail.com",
											name = "default",
											url = "www.google.com")))

@Default
public class contractEhr implements ContractInterface {

	public contractEhr() {
	}
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public void CreatePatient(Context ctx, String patientID, String name){
		
		boolean exists = Patient(ctx, patientID  );
		if (exists){
			throw new RuntimeException("The asset "+ patientID  +" already exists");
		}
		Patient asset = new Patient();
		asset.setID(patientID);
		asset.setName(name);
		
		ctx.getStub().putState( patientID  , asset.toJSONString().getBytes(UTF_8));
	}

	@Transaction(intent = Transaction.TYPE.EVALUATE)
	public boolean PatientExists(Context ctx, String patientID){
		
		byte [] buffer = ctx.GetStub().GetState( patientID );	
		return (buffer != null && buffer.length > 0);
	}

	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public void UpdatePatient(Context ctx, String patientID, String name){
		
		boolean exists = Patient(ctx, patientID  );
		if (!exists){
			throw new RuntimeException("The asset "+ patientID  +" does not exist");
		}
		Patient asset = new Patient();
		asset.setID(patientID);
		asset.setName(name);
		
		ctx.getStub().putState( patientID  , asset.toJSONString().getBytes(UTF_8));
	}

	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public void DeletePatient(Context ctx, String patientID){
		
		boolean exists = DeletePatient(ctx, patientID );
		if (!exists){
			throw new RuntimeException("The asset "+ patientID +" does not exist");
		}
		DeletePatient asset = new DeletePatient();
		asset.setID(patientID);
		
		ctx.getStub().delState( patientID , asset.toJSONString().getBytes(UTF_8));
	}

	@Transaction(intent = Transaction.TYPE.EVALUATE)
	public Patient QueryPatient(Context ctx, String patientID){
		
		boolean exists = Patient(ctx, patientID );
		if (!exists){
			throw new RuntimeException("The asset "+ patientID +" does not exist");
		}
		Patient asset = Patient.fromJSONString(new String(ctx.getStub().getState( patientID ),UTF_8));
		return asset;
	}


}
