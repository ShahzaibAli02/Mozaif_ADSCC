package com.example.adscc.Model;

import java.io.Serializable;

public class Booking implements Serializable
{

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getExamType() {
        return ExamType;
    }

    public void setExamType(String examType) {
        ExamType = examType;
    }

    public String getNoOfPeople() {
        return NoOfPeople;
    }

    public void setNoOfPeople(String noOfPeople) {
        NoOfPeople = noOfPeople;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    String uid;
    String userId;
    String Date;
    String Time;
    String Address;

    String ExamType;

    String NoOfPeople;

    String PaymentMethod;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserMobile() {
        return UserMobile;
    }

    public void setUserMobile(String userMobile) {
        UserMobile = userMobile;
    }

    public String getUserEmiratesID() {
        return UserEmiratesID;
    }

    public void setUserEmiratesID(String userEmiratesID) {
        UserEmiratesID = userEmiratesID;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    String UserName;

    String UserMobile;
    String UserEmiratesID;
    String UserEmail;

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    String TotalPrice;


}
