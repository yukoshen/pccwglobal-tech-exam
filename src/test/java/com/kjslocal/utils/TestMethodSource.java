package com.kjslocal.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.kjslocal.dto.UserRequestDto;
import com.kjslocal.dto.UserResponseDto;
import com.kjslocal.entity.UserEntity;

public class TestMethodSource {

	public static final String URL = "/v1/user/";

	public static final String SEARCH_ERR = "Error during search";

	public static final String REGISTER_ERR = "Error during register";

	public static final String UPDATE_ERR = "Error during update";

	public static final String DELETE_ERR = "Error during delete";
	
	public static final String DELETE_OK = "Delete successful";

	public static final String NOT_FOUND = "Data not found";

	public static final String MAIL_ERR = "Error during mail send";

	public static final String ID = "$.userId";

	public static final String FNAME = "$.firstName";

	public static final String LNAME = "$.lastName";

	public static final String EMAIL = "$.email";

	public static final String LIST_ID = "$.[0].userId";

	public static final String LIST_FNAME = "$.[0].firstName";

	public static final String LIST_LNAME = "$.[0].lastName";

	public static final String LIST_EMAIL = "$.[0].email";
	
	public static final String ERR_MSG = "$.errorMessage";

	public static Stream<Arguments> requestErrors() {

		String longString = "abcd".repeat(50);

		return Stream.of(
				Arguments.of("{ \"firstName\": \"\", \"lastName\": \"Bryant\", \"email\": \"blackmamba@gmail.com\" }",
						"firstName is required."),
				Arguments.of(
						"{ \"firstName\": \"" + longString
								+ "\", \"lastName\": \"Bryant\", \"email\": \"blackmamba@gmail.com\" }",
						"firstName max length is 150."),
				Arguments.of("{ \"firstName\": \"Kobe\", \"lastName\": \"\", \"email\": \"blackmamba@gmail.com\" }",
						"lastName is required."),
				Arguments.of("{ \"firstName\": \"Kobe\", \"lastName\": \"" + longString
						+ "\", \"email\": \"blackmamba@gmail.com\" }", "lastName max length is 150."),
				Arguments.of("{ \"firstName\": \"Kobe\", \"lastName\": \"Bryant\", \"email\": \"\" }",
						"email is required."),
				Arguments.of("{ \"firstName\": \"Kobe\", \"lastName\": \"Bryant\", \"email\": \"" + longString + "\" }",
						"email max length is 50."));
	}

	public static UserEntity getUserEntity() {
		return UserEntity.builder().id(1).fname("Kobe").lname("Bryant").email("blackmamba@gmail.com").build();
	}

	public static List<UserEntity> getUserEntityList() {
		List<UserEntity> entityList = new ArrayList<>();
		entityList.add(getUserEntity());

		return entityList;
	}

	public static UserRequestDto getUserRequest() {
		return UserRequestDto.builder().fname("Kobe").lname("Bryant").email("blackmamba@gmail.com").build();
	}

	public static UserResponseDto getUserResponse() {
		return UserResponseDto.builder().id(1).fname("Kobe").lname("Bryant").email("blackmamba@gmail.com").build();
	}
	
	public static List<UserResponseDto> getUserResponseList() {
		List<UserResponseDto> responseList = new ArrayList<>();
		responseList.add(getUserResponse());

		return responseList;
	}

	public static UserEntity getUpdatedUserEntity() {
		return UserEntity.builder().id(1).fname("Lebron").lname("James").email("kingjames@gmail.com").build();
	}

	public static UserRequestDto getUserRequestUpdate() {
		return UserRequestDto.builder().fname("Lebron").lname("James").email("kingjames@gmail.com").build();
	}

}
