package com.example.footballtalking;

public class Team {
    private String name;
    private String imageURL;

    public Team(String name , String imageURL) {
        this.name = name;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
