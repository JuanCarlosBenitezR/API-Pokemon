package com.example.pokemon.models;

public class Pokemon {
    private String name;
    private String url;
    private int number;

    public int getNumber() {
        String[] urlPartes=url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length-1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
