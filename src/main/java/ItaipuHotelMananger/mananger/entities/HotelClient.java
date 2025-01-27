package ItaipuHotelMananger.mananger.entities;

import ItaipuHotelMananger.mananger.entities.utils.CpfValidation;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "hotel_client")
public class HotelClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String cpf;
    private String city;
    private String address;
    private String email;
    private String phone;
    private String cnpj;

    @OneToMany(mappedBy = "client")
    private List<Hosting> hostingList;

    public HotelClient() {
    }

    public HotelClient(String cpf) {
        this.cpf = cpf;
    }

    public HotelClient(Long id, String fullName, String cpf,
                       String city, String address, String email,
                       String phone, String cnpj) {
        this.id = id;
        this.fullName = fullName;
        this.cpf = cpf;
        this.city = city;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.cnpj = cnpj;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public List<Hosting> getHostingList() {
        return hostingList;
    }

    public void setHostingList(List<Hosting> hostingList) {
        this.hostingList = hostingList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HotelClient that = (HotelClient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Nome = " + fullName + '\'' +
                "\nCpf = " + cpf + '\'' +
                "\nCidade = " + city + '\'' +
                "\nEndereço = " + address + '\'' +
                "\nEmail = " + email + '\'' +
                "\nTelefone = " + phone + '\'' +
                "\nCnpj = " + cnpj + '\''+
                "\n Lista de hospedagens" + hostingList;
    }
}
