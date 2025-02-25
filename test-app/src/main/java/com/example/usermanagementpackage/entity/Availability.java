package com.example.usermanagementpackage.entity;


import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "availability")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> daysAvailable;

    // ✅ Constructors
    public Availability() {}

    public Availability(List<String> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    // ✅ Getters and Setters
    public Long getId() { return id; }
    public List<String> getDaysAvailable() { return daysAvailable; }
    public void setDaysAvailable(List<String> daysAvailable) { this.daysAvailable = daysAvailable; }
}
