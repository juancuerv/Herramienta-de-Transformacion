package main

import (
	"encoding/json"
	"fmt"

	"github.com/hyperledger/fabric-contract-api-go/contractapi"
)

type SmartContract struct {
	contractapi.Contract
}

// Food describes basic details of what makes up a food
type Food struct {
	Farmer  string `json:"farmer"`
	Variety string `json:"variety"`
}

func (s *SmartContract) Set(ctx contractapi.TransactionContextInterface, foodId string, farmer string, variety string) error {
	//Validaciones de sintaxis

	//Creación de un objeto
	food := Food{
		Farmer:  farmer,
		Variety: variety,
	}

	//Se convierte el objeto a Bytes
	foodAsBytes, _ := json.Marshal(food)

	return ctx.GetStub().PutState(foodId, foodAsBytes)
}

func (s *SmartContract) Query(ctx contractapi.TransactionContextInterface, foodId string) (*Food, error) {
	foodAsBytes, err := ctx.GetStub().GetState(foodId)
	if err != nil {
		return nil, fmt.Errorf("Failed to read from world state. %s", err.Error())
	}

	if foodAsBytes == nil {
		return nil, fmt.Errorf("%s does not exist", err.Error())
	}

	food := new(Food)

	err = json.Unmarshal(foodAsBytes, food)

	if err != nil {
		return nil, fmt.Errorf("Unmarshal error %s", err.Error())
	}

	return food, nil
}

func main() {
	chaincode, err := contractapi.NewChaincode(new(SmartContract))
	if err != nil {
		fmt.Printf("Error create foodcontrol chaincode: %s", err.Error())
		return
	}

	if err := chaincode.Start(); err != nil {
		fmt.Printf("Error create foodcontrol chaincode: %s", err.Error())
	}
}
