package com.yusheng.contactbackend1.pojo;

public class phone_number {
    int id;
    String number;
    int people_id;

    public phone_number() {}

    public phone_number(int id, String number, int people_id) {
        this.id = id;
        this.number = number;
        this.people_id = people_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPeople_id() {
        return people_id;
    }

    public void setPeople_id(int people_id) {
        this.people_id = people_id;
    }

    @Override
    public String toString() {
        return "phone_number{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", people_id=" + people_id +
                '}';
    }
}
