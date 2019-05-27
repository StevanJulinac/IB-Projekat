package ib.project.model;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {
	private Integer id;
	private String name;
	
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return null;
	}

	public Authority() {
		super();
	}

	public Authority(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
