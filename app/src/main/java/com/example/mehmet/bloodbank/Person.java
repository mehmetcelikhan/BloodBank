package com.example.mehmet.bloodbank;

public class Person {
    private static Person instance;
    String name;
    String surName;
    String phoneNumber;
    String e_mail;
    String passWord;
    String bloodGroup;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    String city;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }


    public static Person GetInstance()
    {
        return (instance == null) ? instance = new Person() : instance;
    }


    @Override
    public String toString() {
        return name+" "+surName+" "+phoneNumber+" "+e_mail+" "+city+" "+"   "+bloodGroup ;
    }
}
