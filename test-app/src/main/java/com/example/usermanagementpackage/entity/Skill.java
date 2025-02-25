package com.example.usermanagementpackage.entity;


import jakarta.persistence.*;


@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String skillName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    // add getters and setters for above attributes
    public Long getId() { return id; }
    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }    
}
