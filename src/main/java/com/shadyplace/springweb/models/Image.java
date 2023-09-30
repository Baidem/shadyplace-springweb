package com.shadyplace.springweb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.GregorianCalendar;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "original_name")
    private String originalName;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false, name = "mime-type")
    private String mimeType;
    @Column
    private String description;
    @Column(nullable = false)
    @NotBlank(message = "Please enter a title")
    private String title;

    @Temporal(TemporalType.DATE)
    private GregorianCalendar addedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GregorianCalendar getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(GregorianCalendar addedAt) {
        this.addedAt = addedAt;
    }
}
