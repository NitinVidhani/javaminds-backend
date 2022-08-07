package tech.javaminds.javaminds.dto;

import tech.javaminds.javaminds.entity.Category;

public class CategoryDto {

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategoryFromDto() {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        System.out.println("Name: " + name + "Des: " + description);
        return category;
    }

}
