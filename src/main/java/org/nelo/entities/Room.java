package org.nelo.entities;

import org.nelo.entities.enums.RoomType;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ROOM")
public class Room implements Comparable<Room> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROOM_SEQ")
    @SequenceGenerator(name = "ROOM_SEQ", sequenceName = "ROOM_SEQ")
    private int roomId;

    @Column(nullable = false)
    private String roomNumber;

    @Column(nullable = false)
    private String roomType;

    @Column(length = 4000)
    private String description;

    @Column
    private Integer price;

    @Column
    private Boolean internet;

    @Column
    private Boolean airConditioner;

    @Column
    private Boolean phone;

    @Column
    private Boolean roomView;

    @Column
    private Boolean smoking;

    @Column
    private Boolean pets;

    @Column
    private Boolean safe;

    @Column
    private Boolean teaCoffee;

    @Column
    private Boolean hydromassageTub;

    @Column
    private Boolean virtualRoom;

    @OneToOne
    @JoinColumn(name = "fk_real_room_id")
    private Room room;

    @Column
    private Boolean deleted;

    @Transient
    private ArrayList<MultipartFile> files; // prop folosita pentru salvarea fisierelor la upload

    @Transient
    private List<RoomImg> images; // prop folosita pentru a reprezenta imaginile pe interfata

    @Transient
    private String roomdIdsToDelete;

    public Room() {
    }

    public Room(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getInternet() {
        return internet;
    }

    public void setInternet(Boolean internet) {
        this.internet = internet;
    }

    public Boolean getAirConditioner() {
        return airConditioner;
    }

    public void setAirConditioner(Boolean airConditioner) {
        this.airConditioner = airConditioner;
    }

    public Boolean getPhone() {
        return phone;
    }

    public void setPhone(Boolean phone) {
        this.phone = phone;
    }

    public Boolean getRoomView() {
        return roomView;
    }

    public void setRoomView(Boolean roomView) {
        this.roomView = roomView;
    }

    public Boolean getSmoking() {
        return smoking;
    }

    public void setSmoking(Boolean smoking) {
        this.smoking = smoking;
    }

    public Boolean getPets() {
        return pets;
    }

    public void setPets(Boolean pets) {
        this.pets = pets;
    }

    public Boolean getSafe() {
        return safe;
    }

    public void setSafe(Boolean safe) {
        this.safe = safe;
    }

    public Boolean getTeaCoffee() {
        return teaCoffee;
    }

    public void setTeaCoffee(Boolean teaCoffee) {
        this.teaCoffee = teaCoffee;
    }

    public Boolean getHydromassageTub() {
        return hydromassageTub;
    }

    public void setHydromassageTub(Boolean hydromassageTub) {
        this.hydromassageTub = hydromassageTub;
    }

    public ArrayList<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<MultipartFile> files) {
        this.files = files;
    }

    public List<RoomImg> getImages() {
        return images;
    }

    public void setImages(List<RoomImg> images) {
        this.images = images;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getRoomdIdsToDelete() {
        return roomdIdsToDelete;
    }

    public void setRoomdIdsToDelete(String roomdIdsToDelete) {
        this.roomdIdsToDelete = roomdIdsToDelete;
    }

    public Boolean getVirtualRoom() {
        return virtualRoom;
    }

    public void setVirtualRoom(Boolean virtualRoom) {
        this.virtualRoom = virtualRoom;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getNiceType() {
        for (RoomType value : RoomType._values) {
            if (getRoomType().equals(value.getType()))
                return value.getDescription();
        }
        return null;
    }

    @Override
    public int compareTo(Room o) {
        return Integer.parseInt(this.roomNumber) - Integer.parseInt(o.getRoomNumber());
    }
}
