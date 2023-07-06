package com.kjslocal.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kjslocal.dto.UserRequestDto;
import com.kjslocal.dto.UserResponseDto;
import com.kjslocal.exceptions.UserException;
import com.kjslocal.service.UserService;

@RestController
@RequestMapping("/v1/user")
@Validated
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponseDto> addUser(@Validated @RequestBody UserRequestDto req) throws UserException {
		return new ResponseEntity<>(service.addUser(req), HttpStatus.OK);
	}

	@GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserResponseDto>> getUsers() throws UserException {
		return new ResponseEntity<>(service.getUsers(), HttpStatus.OK);
	}

	@GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable int id) throws UserException {
		return new ResponseEntity<>(service.getUserById(id), HttpStatus.OK);
	}

	@PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponseDto> updateUser(@PathVariable int id, @Validated @RequestBody UserRequestDto req)
			throws UserException {
		return new ResponseEntity<>(service.updateUser(id, req), HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable int id) throws UserException {
		return new ResponseEntity<>(service.deleteUser(id), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deleteMultiple")
	public ResponseEntity<Void> deleteMultiple(@RequestParam("ids") List<Integer> ids) throws UserException {
		service.deleteMultipleUsers(ids);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
