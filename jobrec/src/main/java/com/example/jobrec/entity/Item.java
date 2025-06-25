package com.example.jobrec.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Item {
    private String id;
    private String title;
    private String location;
    private String companyName;
    private String via;
    private String description;
    private Set<String> keywords;
    private boolean favorite;
    private List<String> job_highlights;
    private String url;

    public Item() {
    }

    public Item(String id, String title, String company_name, String location, String via, String description, List<String> job_highlights, String url, Set<String> keywords, boolean favorite) {
        this.id = id;
        this.title = title;
        this.companyName = company_name;
        this.location = location;
        this.via = via;
        this.description = description;
        this.job_highlights = job_highlights;
        this.url = url;
        this.keywords = keywords;
        this.favorite = favorite;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return favorite == item.favorite && Objects.equals(id, item.id) && Objects.equals(title, item.title) && Objects.equals(location, item.location) && Objects.equals(companyName, item.companyName) && Objects.equals(via, item.via) && Objects.equals(description, item.description) && Objects.equals(keywords, item.keywords) && Objects.equals(job_highlights, item.job_highlights) && Objects.equals(url, item.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, location, companyName, via, description, keywords, favorite, job_highlights, url);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", companyName='" + companyName + '\'' +
                ", via='" + via + '\'' +
                ", description='" + description + '\'' +
                ", keywords=" + keywords +
                ", favorite=" + favorite +
                ", job_highlights=" + job_highlights +
                ", url='" + url + '\'' +
                '}';
    }

    @JsonProperty("job_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @JsonProperty("job_title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    @JsonProperty("company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    @JsonProperty("via")
    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }
    public List<String> getJob_highlights() {
        return job_highlights;
    }

    public void setJob_highlights(List<String> job_highlights) {
        this.job_highlights = job_highlights;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

}
