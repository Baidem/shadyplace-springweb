package com.shadyplace.springweb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.GregorianCalendar;

@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    @NotBlank(message = "Please enter a title")
    private String title;

    @Column(name = "content", nullable = false, columnDefinition="TEXT")
    @NotBlank(message = "Please enter a content")
    private String content;
    @Column(nullable = false, name = "publication_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private GregorianCalendar publicationDate;

    @Column(name = "add_link")
    private String addLink;

    @ManyToOne(fetch = FetchType.EAGER)
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public GregorianCalendar getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(GregorianCalendar publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getAddLink() {
        return addLink;
    }

    public void setAddLink(String addLink) {
        this.addLink = addLink;
    }
}
