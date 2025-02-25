package com.example.usermanagementpackage.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "work_samples")
public class WorkSample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileUrl;
    private String fileType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ✅ Constructors
    public WorkSample() {}

    public WorkSample(String fileUrl, String fileType, User user) {
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.user = user;
    }

    // ✅ Getters and Setters
    public Long getId() { return id; }
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
