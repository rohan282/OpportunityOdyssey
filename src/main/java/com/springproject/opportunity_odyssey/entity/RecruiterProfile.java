package com.springproject.opportunity_odyssey.entity;

import jakarta.persistence.*;

@Entity
@Table(name="recruiter_profile")
public class RecruiterProfile {

    @Id
    private int userAccountId;

    @MapsId
    @OneToOne
    @JoinColumn(name="user_account_id")
    private Users userId;



    @Column(name="city")
    private String city;

    @Column(name="company")
    private String company;

    @Column(name="country")
    private String country;

    @Column(name="state")
    private String state;

    @Column(name="profile_photo", nullable=true,length=64)
    private String profilePhoto;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    public RecruiterProfile(){

    }

    public RecruiterProfile(Users userId) {
        this.userId = userId;
    }

    public RecruiterProfile(int userAccountId, Users userId, String city, String company, String country, String state, String profilePhoto, String firstName, String lastName) {
        this.userAccountId = userAccountId;
        this.userId = userId;
        this.city = city;
        this.company = company;
        this.country = country;
        this.state = state;
        this.profilePhoto = profilePhoto;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public int getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Transient
    public String getPhotosImagePath(){
        if(profilePhoto==null){
            return null;
        }
        return "/photos/recruiter/"+ userAccountId + "/" + profilePhoto;
    }

    @Override
    public String toString() {
        return "RecruiterProfile{" +
                "userAccountId=" + userAccountId +
                ", city='" + city + '\'' +
                ", company='" + company + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
