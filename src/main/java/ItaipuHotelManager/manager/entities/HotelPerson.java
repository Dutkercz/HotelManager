package ItaipuHotelManager.manager.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "others_guests")
public class HotelPerson {
    //Classe para adicionar mais pessoas em um mesmo apartamento, sem necessidade de obter outros dados.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "hosting_id")
    private Hosting hosting;

    public HotelPerson() {
    }

    public HotelPerson(Long id, String name, Hosting hosting) {
        this.id = id;
        this.name = name;
        this.hosting = hosting;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HotelPerson(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hosting getHosting() {
        return hosting;
    }

    public void setHosting(Hosting hosting) {
        this.hosting = hosting;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HotelPerson person = (HotelPerson) o;
        return Objects.equals(id, person.id) && Objects.equals(hosting, person.hosting);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hosting);
    }

    @Override
    public String toString() {
        return name + " ";
    }



}
