package com.products.dto.response;

import java.util.List;
import com.products.dto.UserRoleDTO;

public class UserInfoResponse {
    private Long id;
    private String email;
    private List<UserRoleDTO> roles;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<UserRoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRoleDTO> roles) {
		this.roles = roles;
	}

	public UserInfoResponse(Long id, String email, List<UserRoleDTO> roles) {
        this.id = id;
        this.email = email;
        this.roles = roles;
    }
}
