package ItaipuHotelManager.manager.entities;

import ItaipuHotelManager.manager.entities.utils.RoomStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
public class HotelRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomNumber;
    private Integer doubleBeds;
    private Integer singleBeds;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Hosting> hosting = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private HotelClient client;

    public HotelRoom() {
    }

    public HotelRoom(Long id, String roomNumber, Integer doubleBeds, Integer singleBeds, RoomStatus status, HotelClient client) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.doubleBeds = doubleBeds;
        this.singleBeds = singleBeds;
        this.status = status;
        this.client = client;
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

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public HotelClient getClient() {
        return client;
    }

    public void setClient(HotelClient client) {
        this.client = client;
    }

    public boolean isOccupied() {
        return status == RoomStatus.OCUPADO;
    }

    public List<Hosting> getHosting() {
        return hosting;
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
        return "Apartamento: " + roomNumber + "\nSituação: " + status + "\n\tCama casal > " + doubleBeds +
                "\n\tCama de solteiro > " + singleBeds + "\n" + (client != null ? "Cliente " + client : "\n");
    }
}

