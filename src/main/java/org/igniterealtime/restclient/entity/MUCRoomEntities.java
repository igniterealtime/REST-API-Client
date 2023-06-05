package org.igniterealtime.restclient.entity;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "chatRooms")
public class MUCRoomEntities {
	List<MUCRoomEntity> mucRooms;

	public MUCRoomEntities() {
	}

	public MUCRoomEntities(List<MUCRoomEntity> mucRooms) {
		this.mucRooms = mucRooms;
	}

	@XmlElement(name = "chatRoom")
	public List<MUCRoomEntity> getMucRooms() {
		return mucRooms;
	}

	public void setMucRooms(List<MUCRoomEntity> mucRooms) {
		this.mucRooms = mucRooms;
	}
}
