package ItaipuHotelMananger.mananger.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class HotelRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomNumber;
    private Integer doubleBeds;
    private Integer singleBeds;


    @OneToMany(mappedBy = "room") // 'client' é o atributo na entidade Booking
    private List<Hosting> hostingList;


    public HotelRoom() {
    }

    public HotelRoom(Long id, String roomNumber, Integer doubleBeds, Integer singleBeds) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.doubleBeds = doubleBeds;
        this.singleBeds = singleBeds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getDoubleBeds() {
        return doubleBeds;
    }

    public void setDoubleBeds(Integer doubleBeds) {
        this.doubleBeds = doubleBeds;
    }

    public Integer getSingleBeds() {
        return singleBeds;
    }

    public void setSingleBeds(Integer singleBeds) {
        this.singleBeds = singleBeds;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HotelRoom hotelRoom = (HotelRoom) o;
        return Objects.equals(id, hotelRoom.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Apartamento = " + roomNumber + "\n\tCama casal > " + doubleBeds +
                "\n\tCama de solteiro > " + singleBeds+"\n";
    }
}
