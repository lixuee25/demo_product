package com.products.dto;

public class UserRoleDTO {
    private Long id;
    private String name;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserRoleDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
 
}