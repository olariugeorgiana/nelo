package org.nelo.entities;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.persistence.*;

@Entity
@Table(name = "ROOM_IMG")
public class RoomImg {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int roomImgId;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room room;

    @Column
    private String imgName;

    @Column
    private byte[] imgBytes;

    @Transient
    private String imgBase64;

    public int getRoomImgId() {
        return roomImgId;
    }

    public void setRoomImgId(int roomImgId) {
        this.roomImgId = roomImgId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public byte[] getImgBytes() {
        return imgBytes;
    }

    public void setImgBytes(byte[] imgBytes) {
        this.imgBytes = imgBytes;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getImgBase64() {
        return String.valueOf(Base64.encode(imgBytes));
    }
}
