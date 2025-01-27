package ItaipuHotelMananger.mananger.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "hosting")
public class Hosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer totalGuest;
    private Double totalPrice;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private HotelClient client;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private HotelRoom room;

    public Hosting() {
    }

    public Hosting(Long id, Integer totalGuest, Double totalPrice, LocalDateTime checkIn, HotelClient client, HotelRoom room) {
        this.id = id;
        this.totalGuest = totalGuest;
        this.totalPrice = totalPrice;
        this.checkIn = checkIn;
        this.client = client;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalGuest() {
        return totalGuest;
    }

    public void setTotalGuest(Integer totalGuest) {
        this.totalGuest = totalGuest;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public HotelClient getClient() {
        return client;
    }

    public void setClient(HotelClient client) {
        this.client = client;
    }

    public HotelRoom getRoom() {
        return room;
    }

    public void setRoom(HotelRoom room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Hosting hosting = (Hosting) o;
        return Objects.equals(id, hosting.id) && Objects.equals(client, hosting.client) && Objects.equals(room, hosting.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, room);
    }
}
