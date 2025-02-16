package com.springproject.opportunity_odyssey.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "job_seeker_profile")
public class JobSeekerProfile {

    @Id
    private Integer userAccountId;

    @MapsId
    @OneToOne
    @JoinColumn(name="user_account_id")
    private Users userId;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="city")
    private String city;
    @Column(name="state")
    private String state;

    @Column(name="country")
    private String country;

    @Column(name="resume")
    private String resume;

    @Column(name="employment_type")
    private String employmentType;


    @Column(name="work_authorization")
    private String workAuthorization;

    @OneToMany(mappedBy = "jobSeekerProfile", cascade = CascadeType.ALL)
    private List<Skills> skills;



    @Column(name="profile_photo", nullable=true,length=64)
    private String profilePhoto;

    @Transient
    public String getPhotosImagePath(){
        if(profilePhoto==null || userAccountId==null){
            return null;
        }
        return "/photos/candidate/"+ userAccountId + "/" + profilePhoto;
    }



    public JobSeekerProfile(){

    }

    public JobSeekerProfile(Users userId) {
        this.userId = userId;
    }

    public JobSeekerProfile(Integer userAccountId, Users userId, String city, String country, String resume, String employmentType, String workAuthorization, String state, String profilePhoto, String firstName, String lastName) {
        this.userAccountId = userAccountId;
        this.userId = userId;
        this.city = city;
        this.country = country;
        this.resume = resume;
        this.employmentType = employmentType;
        this.workAuthorization = workAuthorization;
        this.state = state;
        this.profilePhoto = profilePhoto;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Integer userAccountId) {
        this.userAccountId = userAccountId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getWorkAuthorization() {
        return workAuthorization;
    }

    public void setWorkAuthorization(String workAuthorization) {
        this.workAuthorization = workAuthorization;
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

    public List<Skills> getSkills() {
        return skills;
    }

    public void setSkills(List<Skills> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "JobSeekerProfile{" +
                "userAccountId=" + userAccountId +
                ", userId=" + userId +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", resume='" + resume + '\'' +
                ", employmentType='" + employmentType + '\'' +
                ", workAuthorization='" + workAuthorization + '\'' +
                ", state='" + state + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
