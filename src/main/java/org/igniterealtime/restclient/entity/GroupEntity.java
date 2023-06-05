package org.igniterealtime.restclient.entity;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * The Class GroupEntity.
 */
@XmlRootElement(name = "group")
@XmlType(propOrder = { "name", "description" ,"admins" ,"members" })
public class GroupEntity {

	/** The name. */
	private String name;

	/** The description. */
	private String description;

	private List<String> admins;

	private List<String> members;

	/**
	 * Instantiates a new group entity.
	 */
	public GroupEntity() {
	}

	/**
	 * Instantiates a new group entity.
	 *
	 * @param name
	 *            the name
	 * @param description
	 *            the description
	 */
	public GroupEntity(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public GroupEntity(String name,String description,List<String> admins,List<String> members){
		this.name=name;
		this.description=description;
		this.admins=admins;
		this.members=members;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@XmlElement
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	@XmlElement
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElementWrapper(name = "admins")
	@XmlElement(name = "admin")
	public List<String> getAdmins() {
		return admins;
	}

	public void setAdmins(List<String> admins) {
		this.admins = admins;
	}

	@XmlElementWrapper(name = "members")
	@XmlElement(name = "member")
	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}
}
