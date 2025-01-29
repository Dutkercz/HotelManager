package ItaipuHotelMananger.mananger.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "others_guests")
public class HotelPerson {
    //Classe para adicionar mais pessoas em um mesmo apartamento, sem necessidade de obter outros dados.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    Hosting hosting;

    public HotelPerson() {
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

    @Override
    public String toString() {
        return "" + name + " ";
    }
}
