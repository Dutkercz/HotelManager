package ItaipuHotelManager.manager.entities;

import ItaipuHotelManager.manager.entities.utils.RoomStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "hosting")
public class Hosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer totalGuest;
    private Double basePrice;
    private Integer dailyNumber;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Double totalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    //@JsonIgnore
    private HotelClient client;

    @OneToMany(mappedBy = "hosting", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<HotelPerson> persons = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "room_id")
    private HotelRoom room;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    public Hosting() {
    }

    public Hosting(Long id, Integer totalGuest, Integer dailyNumber, HotelRoom room,
                   HotelClient client, LocalDateTime checkIn, LocalDateTime checkOut, List<HotelPerson> personList, RoomStatus status) {
        this.id = id;
        this.totalGuest = totalGuest;
        this.dailyNumber = dailyNumber;
        this.basePrice = hostingPrices();
        this.client = client;
        this.room = room;
        this.status = status;
        if (checkIn.getHour() == 0 && checkIn.getMinute() == 0) {
            this.checkIn = checkIn.minusDays(1);
        } else {
            this.checkIn = checkIn;
        }
        this.checkOut = checkOut;

        if (totalGuest > 1) {
            this.persons = personList;
        }
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

    public Integer getDailyNumber() {
        return dailyNumber;
    }

    public void setDailyNumber(Integer dailyNumber) {
        this.dailyNumber = dailyNumber;
    }

    public List<HotelPerson> getPersons() {
        return persons;
    }

    public void setPersons(List<HotelPerson> persons) {
        this.persons = persons;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public Double hostingPrices() {
        if (totalGuest == 1) {
            return this.basePrice = 125.00 * dailyNumber;
        } else if (totalGuest == 2) {
            return this.basePrice = 220.00 * dailyNumber;
        } else if (totalGuest == 3) {
            return this.basePrice = 310.00 * dailyNumber;
        } else if (totalGuest == 4) {
            return this.basePrice = 400.00 * dailyNumber;
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
        return "\nHospede Principal: " + client.getFullName() +
                "\nDemais hospedes: " + persons +
                "\nNumero de pessoas: " + totalGuest +
                "\nNumero de diárias: " + dailyNumber +
                "\nSub Total: R$ " + basePrice +
                "\nApartamento nº: " + room.getRoomNumber() +
                "\nCheck-in: " + checkIn + " / Check-out: " + checkOut +
                "\nStatus da diária: " + status;
    }
}
