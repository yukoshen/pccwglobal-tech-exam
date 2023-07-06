package com.kjslocal.api;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjslocal.exceptions.UserException;
import com.kjslocal.service.UserService;
import com.kjslocal.utils.TestMethodSource;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private UserService service;

	@Test
	public void testAddUser() throws Exception {

		when(service.addUser(any())).thenReturn(TestMethodSource.getUserResponse());

		this.mockMvc
				.perform(post(TestMethodSource.URL.concat("add")).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(mapper.writeValueAsString(TestMethodSource.getUserRequest())))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath(TestMethodSource.ID, is(1))).andExpect(jsonPath(TestMethodSource.FNAME, is("Kobe")))
				.andExpect(jsonPath(TestMethodSource.LNAME, is("Bryant")))
				.andExpect(jsonPath(TestMethodSource.EMAIL, is("blackmamba@gmail.com")));
	}

	@Test
	public void testAddUser_Failed() throws Exception {

		when(service.addUser(any())).thenThrow(new UserException(TestMethodSource.REGISTER_ERR));

		this.mockMvc
				.perform(post(TestMethodSource.URL.concat("add")).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(mapper.writeValueAsString(TestMethodSource.getUserRequest())))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath(TestMethodSource.ERR_MSG).value(TestMethodSource.REGISTER_ERR));
	}

	@ParameterizedTest
	@MethodSource("com.kjslocal.utils.TestMethodSource#requestErrors")
	public void testAddUser_RequestError(String request, String error) throws Exception {

		this.mockMvc
				.perform(post(TestMethodSource.URL.concat("add")).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(request))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath(TestMethodSource.ERR_MSG).value(error));
	}

	@Test
	public void testGetUsers() throws Exception {

		when(service.getUsers()).thenReturn(TestMethodSource.getUserResponseList());

		this.mockMvc.perform(get(TestMethodSource.URL.concat("get")).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath(TestMethodSource.LIST_ID, is(1)))
				.andExpect(jsonPath(TestMethodSource.LIST_FNAME, is("Kobe")))
				.andExpect(jsonPath(TestMethodSource.LIST_LNAME, is("Bryant")))
				.andExpect(jsonPath(TestMethodSource.LIST_EMAIL, is("blackmamba@gmail.com")));
	}

	@Test
	public void testGetUsers_Failed() throws Exception {

		when(service.getUsers()).thenThrow(new UserException(TestMethodSource.SEARCH_ERR));

		this.mockMvc.perform(get(TestMethodSource.URL.concat("get")).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath(TestMethodSource.ERR_MSG).value(TestMethodSource.SEARCH_ERR));
	}

	@Test
	public void testGetUserById() throws Exception {

		when(service.getUserById(anyInt())).thenReturn(TestMethodSource.getUserResponse());

		this.mockMvc.perform(get(TestMethodSource.URL.concat("get/1")).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath(TestMethodSource.ID, is(1))).andExpect(jsonPath(TestMethodSource.FNAME, is("Kobe")))
				.andExpect(jsonPath(TestMethodSource.LNAME, is("Bryant")))
				.andExpect(jsonPath(TestMethodSource.EMAIL, is("blackmamba@gmail.com")));
	}

	@Test
	public void testGetUserById_Failed() throws Exception {

		when(service.getUserById(anyInt())).thenThrow(new UserException(TestMethodSource.SEARCH_ERR));

		this.mockMvc.perform(get(TestMethodSource.URL.concat("get/1")).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath(TestMethodSource.ERR_MSG).value(TestMethodSource.SEARCH_ERR));
	}

	@Test
	public void testUpdateUser() throws Exception {

		when(service.updateUser(anyInt(), any())).thenReturn(TestMethodSource.getUserResponse());

		this.mockMvc
				.perform(put(TestMethodSource.URL.concat("update/1")).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(mapper.writeValueAsString(TestMethodSource.getUserRequest())))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath(TestMethodSource.ID, is(1))).andExpect(jsonPath(TestMethodSource.FNAME, is("Kobe")))
				.andExpect(jsonPath(TestMethodSource.LNAME, is("Bryant")))
				.andExpect(jsonPath(TestMethodSource.EMAIL, is("blackmamba@gmail.com")));
	}

	@Test
	public void testUpdateUser_Failed() throws Exception {

		when(service.updateUser(anyInt(), any())).thenThrow(new UserException(TestMethodSource.UPDATE_ERR));

		this.mockMvc
				.perform(put(TestMethodSource.URL.concat("update/1")).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(mapper.writeValueAsString(TestMethodSource.getUserRequest())))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath(TestMethodSource.ERR_MSG).value(TestMethodSource.UPDATE_ERR));
	}

	@Test
	public void testDeleteUser() throws Exception {

		when(service.deleteUser(anyInt())).thenReturn(TestMethodSource.DELETE_OK);

		this.mockMvc
				.perform(delete(TestMethodSource.URL.concat("delete/1")).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().string(TestMethodSource.DELETE_OK));
	}

	@Test
	public void testDeleteUser_Failed() throws Exception {

		doThrow(new UserException(TestMethodSource.DELETE_ERR)).when(service).deleteUser(anyInt());

		this.mockMvc
				.perform(delete(TestMethodSource.URL.concat("delete/1")).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath(TestMethodSource.ERR_MSG).value(TestMethodSource.DELETE_ERR));
	}

}
