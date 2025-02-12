package ItaipuHotelManager.manager.services;

import ItaipuHotelManager.manager.entities.Hosting;
import ItaipuHotelManager.manager.entities.HotelClient;
import ItaipuHotelManager.manager.entities.HotelRoom;
import ItaipuHotelManager.manager.entities.utils.RoomStatus;
import ItaipuHotelManager.manager.repositories.HostingRepository;
import ItaipuHotelManager.manager.repositories.HotelRoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HostingService {
    @Autowired
    private HostingRepository hostingRepository;
    @Autowired
    private HotelRoomRepository roomRepository;
    @Autowired
    private HotelClientService clientService;
    @Autowired
    private HotelRoomService roomService;

    @Transactional
    public void checkOut(Hosting hosting) {
        HotelRoom room = hosting.getRoom();
        room.setStatus(RoomStatus.DISPONIVEL);
        room.setClient(null);
        roomService.saveRoom(room);

        hosting.setStatus(RoomStatus.FINALIZADO);
        hosting.setCheckOut(LocalDateTime.now());
        hostingRepository.save(hosting);
    }

    @Transactional
    public List<Hosting> findAllHosting(String cpf) {
        HotelClient client = clientService.findByCpf(cpf);
        if (client == null) {
            System.out.println("Cliente não encontrado.");
        }
        return hostingRepository.findByClient(client);
    }

    @Transactional
    public List<Hosting> findAll() {
        return hostingRepository.findAll();
    }

    @Transactional
    public void checkIn(Hosting hosting, HotelClient selectedClient, HotelRoom selectedRoom) {
        hostingRepository.save(hosting);
        roomService.updateRoomStatusOccupied(selectedRoom.getRoomNumber(), selectedClient);
    }

    @Transactional
    public List<Hosting> findActiveHosting() {
        return hostingRepository.findByStatus(RoomStatus.OCUPADO);
    }

    @Transactional
    public Hosting findByRoomNumber(String roomNumber) {
        return hostingRepository.findByStatusAndRoomRoomNumber(RoomStatus.OCUPADO, roomNumber);
    }
}
