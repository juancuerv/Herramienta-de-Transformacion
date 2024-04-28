package main

import (
	"encoding/json"
	"fmt"
	"github.com/hyperledger/fabric-contract-api-go/contractapi"
)

type EHRContract struct {
	contractapi.Contract
}
type Patient struct {
	ID string `json:"ID"`
	Name string `json:"name"`
}

func (ec *EHRContract) CreatePatient(ctx contractapi.TransactionContextInterface, patientID string, name string) (error){

	exists, err := ec.PatientExists(ctx,  patientID  )
	if err != nil {
		return err
	}
	if exists {
		return fmt.Errorf("The Patient %s already exists",  patientID  )
	}

	Patient := &Patient{
		ID:    patientID  ,		
	}

	PatientJSON, err := json.Marshal(Patient)
	if err != nil {
		return err
	}

	return ctx.GetStub().PutState( patientID  , PatientJSON)

}

func (ec *EHRContract) PatientExists(ctx contractapi.TransactionContextInterface, patientID string) (bool, error){

	patientExistspatientExistsJSON, err := ctx.GetStub().GetState( patientID )
	if err != nil {
		return false, err
	}
	return patientExistspatientExistsJSON != nil, nil

}

func (ec *EHRContract) UpdatePatient(ctx contractapi.TransactionContextInterface, patientID string, name string) (error){

	exists, err := ec.PatientExists(ctx,  patientID  )
	if err != nil {
		return err
	}
	if !exists {
		return fmt.Errorf("Patient with ID %s does not exist",  patientID  )
	}

	patient := Patient{
		ID:		 patientID  ,
	}

	patientJSON, err := json.Marshal(patient)
	if err != nil {
		return err
	}

	return ctx.GetStub().PutState( patientID  , patientJSON)

}

func (ec *EHRContract) DeletePatient(ctx contractapi.TransactionContextInterface, patientID string) (error){

	exists, err := ec.PatientExists(ctx,  patientID )
	if err != nil {
		return err
	}
	if !exists {
		return fmt.Errorf("Patient with ID %s does not exist",  patientID )
	}

	return ctx.GetStub().DelState( patientID )

}

func (ec *EHRContract) QueryPatient(ctx contractapi.TransactionContextInterface, patientID string) (*Patient, error){

	patientJSON, err := ctx.GetStub().GetState( patientID )
	if err != nil {
		return nil, err
	}
	if patientJSON == nil {
		return nil, fmt.Errorf("patient with ID %s does not exist",  patientID )
	}

	patient := new(Patient)
	err = json.Unmarshal(patientJSON, patient)
	if err != nil {
		return nil, err
	}

	return patient, nil

}



