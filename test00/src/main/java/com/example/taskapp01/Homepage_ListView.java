package com.example.taskapp01;

public class Homepage_ListView {
    public String Usertype, Username, House, House_Area, Tenant_tel_no, Job, Location, Message, Status, Image0, Image1, Image2;

    public Homepage_ListView(String username, String usertype, String house, String house_area, String tenant_tel_no, String job, String location, String message, String status, String image0, String image1, String image2) {

        this.Usertype = usertype;
        this.Username = username;
        this.House = house;
        this.House_Area = house_area;
        this.Tenant_tel_no = tenant_tel_no;
        this.Job = job;
        this.Location = location;
        this.Message = message;
        this.Status = status;
        this.Image0 = image0;
        this.Image1 = image1;
        this.Image2 = image2;
    }

    public String getUsertype() {
        return Usertype;
    }

    public void setUsertype(String usertype) {
        this.Usertype = usertype;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getHouse() {
        return House;
    }

    public void setHouse(String house) {
        this.House = house;
    }

    public String getHouse_Area() {
        return House_Area;
    }

    public void setHouse_Area(String house_area) {
        this.House_Area = house_area;
    }

    public String getTenant_tel_no() {
        return Tenant_tel_no;
    }

    public void setTenant_tel_no(String tenant_tel_no) {
        this.Tenant_tel_no = tenant_tel_no;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        this.Job = job;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        this.Location = location;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getImage0() {
        return Image0;
    }

    public void setImage0(String image0) {
        this.Image0 = image0;
    }

    public String getImage1() {
        return Image1;
    }

    public void setImage1(String image1) {
        this.Image1 = image1;
    }

    public String getImage2() {
        return Image2;
    }

    public void setImage2(String image2) {
        this.Image2 = image2;
    }
}