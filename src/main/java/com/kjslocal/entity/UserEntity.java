package com.kjslocal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TBL_USER")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID", nullable = false)
	int id;
	
	@Column(name = "FIRST_NAME", length = 150, nullable = false)
	String fname;
	
	@Column(name = "LAST_NAME", length = 150, nullable = false)
	String lname;
	
	@Column(name = "EMAIL", length = 50, nullable = false)
	String email;

}
