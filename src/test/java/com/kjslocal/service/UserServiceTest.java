package com.kjslocal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import com.kjslocal.PccwTechExamApplication;
import com.kjslocal.dto.UserResponseDto;
import com.kjslocal.exceptions.UserException;
import com.kjslocal.repository.UserRepository;
import com.kjslocal.utils.EmailUtils;
import com.kjslocal.utils.TestMethodSource;

@SpringBootTest(classes = PccwTechExamApplication.class)
public class UserServiceTest {

	@InjectMocks
	private UserServiceImpl service;

	@Mock
	private UserRepository repository;

	@Mock
	private EmailUtils sender;

	@Test
	public void whenAddUser_Success() throws UserException {

		when(repository.save(any())).thenReturn(TestMethodSource.getUserEntity());

		doNothing().when(sender).sendWelcomeEmail(anyString());

		UserResponseDto response = service.addUser(TestMethodSource.getUserRequest());

		verify(repository, times(1)).save(any());
		verify(sender, times(1)).sendWelcomeEmail(anyString());
		assertEquals(1, response.getId());
		assertEquals("Kobe", response.getFname());
		assertEquals("Bryant", response.getLname());
		assertEquals("blackmamba@gmail.com", response.getEmail());

	}

	@Test
	public void whenAddUser_Failed() throws UserException {

		when(repository.save(any())).thenThrow(new DataAccessException(TestMethodSource.REGISTER_ERR) {

			private static final long serialVersionUID = 1L;

		});

		UserException error = assertThrows(UserException.class, () -> {
			service.addUser(TestMethodSource.getUserRequest());
		});

		assertEquals(TestMethodSource.REGISTER_ERR, error.getErrMessage());

	}

	@Test
	public void whenAddUser_FailSend() throws UserException {

		when(repository.save(any())).thenReturn(TestMethodSource.getUserEntity());

		doThrow(new UserException(TestMethodSource.MAIL_ERR)).when(sender).sendWelcomeEmail(anyString());

		UserException error = assertThrows(UserException.class, () -> {
			service.addUser(TestMethodSource.getUserRequest());
		});

		assertEquals(TestMethodSource.MAIL_ERR, error.getErrMessage());

	}

	@Test
	public void whenGetUsers_Success() throws UserException {

		when(repository.findAll()).thenReturn(TestMethodSource.getUserEntityList());

		List<UserResponseDto> response = service.getUsers();

		assertEquals(1, response.size());
		verify(repository, times(1)).findAll();
		response.stream().forEach(data -> {
			assertEquals(1, data.getId());
			assertEquals("Kobe", data.getFname());
			assertEquals("Bryant", data.getLname());
			assertEquals("blackmamba@gmail.com", data.getEmail());
		});

	}

	@Test
	public void whenGetUsers_Empty() throws UserException {

		when(repository.findAll()).thenReturn(Collections.emptyList());

		UserException error = assertThrows(UserException.class, () -> {
			service.getUsers();
		});

		verify(repository, times(1)).findAll();
		assertEquals(TestMethodSource.NOT_FOUND, error.getErrMessage());
	}

	@Test
	public void whenGetUsers_Failed() throws UserException {

		when(repository.findAll()).thenThrow(new DataAccessException(TestMethodSource.SEARCH_ERR) {

			private static final long serialVersionUID = 1L;

		});

		UserException error = assertThrows(UserException.class, () -> {
			service.getUsers();
		});

		verify(repository, times(1)).findAll();
		assertEquals(TestMethodSource.SEARCH_ERR, error.getErrMessage());

	}

	@Test
	public void whenGetUserById_Success() throws UserException {

		when(repository.findById(anyInt())).thenReturn(Optional.of(TestMethodSource.getUserEntity()));

		UserResponseDto response = service.getUserById(1);

		verify(repository, times(1)).findById(anyInt());
		assertEquals(1, response.getId());
		assertEquals("Kobe", response.getFname());
		assertEquals("Bryant", response.getLname());
		assertEquals("blackmamba@gmail.com", response.getEmail());

	}

	@Test
	public void whenGetUserById_Empty() throws UserException {

		when(repository.findById(anyInt())).thenReturn(Optional.empty());

		UserException error = assertThrows(UserException.class, () -> {
			service.getUserById(1);
		});

		verify(repository, times(1)).findById(anyInt());
		assertEquals(TestMethodSource.NOT_FOUND, error.getErrMessage());
	}

	@Test
	public void whenGetUserById_Failed() throws UserException {

		when(repository.findById(anyInt())).thenThrow(new DataAccessException(TestMethodSource.SEARCH_ERR) {

			private static final long serialVersionUID = 1L;

		});

		UserException error = assertThrows(UserException.class, () -> {
			service.getUserById(1);
		});

		verify(repository, times(1)).findById(anyInt());
		assertEquals(TestMethodSource.SEARCH_ERR, error.getErrMessage());

	}

	@Test
	void whenUpdateUser_Success() throws UserException {

		when(repository.update(anyString(), anyString(), anyString(), anyInt())).thenReturn(1);

		UserResponseDto response = service.updateUser(1, TestMethodSource.getUserRequestUpdate());

		verify(repository, times(1)).update(anyString(), anyString(), anyString(), anyInt());
		assertEquals(1, response.getId());
		assertEquals("Lebron", response.getFname());
		assertEquals("James", response.getLname());
		assertEquals("kingjames@gmail.com", response.getEmail());
	}

	@Test
	void whenUpdateUser_Empty() throws UserException {

		when(repository.update(anyString(), anyString(), anyString(), anyInt())).thenReturn(0);

		UserException error = assertThrows(UserException.class, () -> {
			service.updateUser(1, TestMethodSource.getUserRequestUpdate());
		});

		verify(repository, times(1)).update(anyString(), anyString(), anyString(), anyInt());
		assertEquals(TestMethodSource.NOT_FOUND, error.getErrMessage());
	}

	@Test
	void whenUpdateUser_Failed() throws UserException {

		when(repository.update(anyString(), anyString(), anyString(), anyInt()))
				.thenThrow(new DataAccessException(TestMethodSource.UPDATE_ERR) {

					private static final long serialVersionUID = 1L;

				});

		UserException error = assertThrows(UserException.class, () -> {
			service.updateUser(1, TestMethodSource.getUserRequestUpdate());
		});

		verify(repository, times(1)).update(anyString(), anyString(), anyString(), anyInt());
		assertEquals(TestMethodSource.UPDATE_ERR, error.getErrMessage());
	}

	@Test
	void whenDeleteUser_Success() throws UserException {
		
		when(repository.delete(anyInt())).thenReturn(1);

		String response = service.deleteUser(1);

		verify(repository, times(1)).delete(anyInt());
		assertEquals(TestMethodSource.DELETE_OK, response);

	}
	
	@Test
	void whenDeleteUser_Empty() throws UserException {
		
		when(repository.delete(anyInt())).thenReturn(0);
		
		UserException error = assertThrows(UserException.class, () -> {
			service.deleteUser(1);
		});

		verify(repository, times(1)).delete(anyInt());
		assertEquals(TestMethodSource.NOT_FOUND, error.getErrMessage());

	}

	@Test
	void whenDeleteUser_Failed() throws UserException {

		doThrow(new DataAccessException(TestMethodSource.DELETE_ERR) {

			private static final long serialVersionUID = 1L;

		}).when(repository).delete(anyInt());

		UserException error = assertThrows(UserException.class, () -> {
			service.deleteUser(1);
		});

		verify(repository, times(1)).delete(anyInt());
		assertEquals(TestMethodSource.DELETE_ERR, error.getErrMessage());

	}

}
