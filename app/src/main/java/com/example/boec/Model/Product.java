package com.example.boec.Model;

public class Product {
    private String pname, description, image, category, pid;
    private Long price;

    public Product() {
    }

    public Product(String pname, String description, String image, String category, String pid, Long price) {
        this.pname = pname;
        this.description = description;
        this.image = image;
        this.category = category;
        this.pid = pid;
        this.price = price;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}

