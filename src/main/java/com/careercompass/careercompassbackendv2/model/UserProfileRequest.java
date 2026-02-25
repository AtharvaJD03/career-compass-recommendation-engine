package com.careercompass.careercompassbackendv2.model;

public class UserProfileRequest {

    private String skills;
    private String education;
    private String interests;
    private int experience;

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    public String getInterests() { return interests; }
    public void setInterests(String interests) { this.interests = interests; }

    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }
}