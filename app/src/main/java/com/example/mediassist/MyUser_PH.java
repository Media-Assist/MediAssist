package com.example.mediassist;

public class MyUser_PH {
    String Date,Doctor_Name,Patient_Id,Patient_No,Patient_Name,List_Of_Medicinces,Address,City,State;

    public  MyUser_PH(){}
    public MyUser_PH(String date, String doctor_Name, String patient_Id, String patient_No, String patient_Name, String list_Of_Medicinces, String address, String city, String state) {
        Date = date;
        Doctor_Name = doctor_Name;
        Patient_Id = patient_Id;
        Patient_No = patient_No;
        Patient_Name = patient_Name;
        List_Of_Medicinces = list_Of_Medicinces;
        Address = address;
        City = city;
        State = state;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDoctor_Name() {
        return Doctor_Name;
    }

    public void setDoctor_Name(String doctor_Name) {
        Doctor_Name = doctor_Name;
    }

    public String getPatient_Id() {
        return Patient_Id;
    }

    public void setPatient_Id(String patient_Id) {
        Patient_Id = patient_Id;
    }

    public String getPatient_No() {
        return Patient_No;
    }

    public void setPatient_No(String patient_No) {
        Patient_No = patient_No;
    }

    public String getPatient_Name() {
        return Patient_Name;
    }

    public void setPatient_Name(String patient_Name) {
        Patient_Name = patient_Name;
    }

    public String getList_Of_Medicinces() {
        return List_Of_Medicinces;
    }

    public void setList_Of_Medicinces(String list_Of_Medicinces) {
        List_Of_Medicinces = list_Of_Medicinces;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
