package com.example.demo.entity;

/**
 * @author changmk
 * @version 1.0
 * @date 2023/6/9 18:01
 */
public class Mike {

    private String color;
    private String source;
    private String country;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Mike{" +
                "color='" + color + '\'' +
                ", source='" + source + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
