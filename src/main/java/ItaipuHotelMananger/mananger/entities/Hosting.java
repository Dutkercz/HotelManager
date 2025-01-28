package ItaipuHotelMananger.mananger.entities;

import jakarta.persistence.*;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "hosting")
public class Hosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer totalGuest;
    private Double basePrice;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private HotelClient client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private HotelRoom room;

    public Hosting() {
    }

    public Hosting(Long id, Integer totalGuest, HotelRoom room, HotelClient client) {
        this.id = id;
        this.totalGuest = totalGuest;
        this.basePrice = totalBasePrice();
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

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
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


    public Double totalBasePrice(){
        if (totalGuest == 1 ){
            return this.basePrice = 125.00;
        } else if (totalGuest == 2) {
            return this.basePrice = 220.00;
        } else if (totalGuest == 3) {
            return this.basePrice = 310.00;
        } else if (totalGuest == 4) {
            return this.basePrice = 400.00;
        }
        return null;
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

    @Override
    public String toString() {
        return "Hosting{" +
                "totalGuest=" + totalGuest +
                ", basePrice=" + basePrice +
                ", client=" + client.getFullName() +
                ", room=" + room.getRoomNumber() +
                '}';
    }
}
