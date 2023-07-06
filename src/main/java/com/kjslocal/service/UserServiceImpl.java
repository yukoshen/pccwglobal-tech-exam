package com.kjslocal.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kjslocal.dto.UserRequestDto;
import com.kjslocal.dto.UserResponseDto;
import com.kjslocal.entity.UserEntity;
import com.kjslocal.exceptions.UserException;
import com.kjslocal.repository.UserRepository;
import com.kjslocal.utils.EmailUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	EmailUtils emailUtils;

	private final String SEARCH_ERR = "Error during search";

	private final String REGISTER_ERR = "Error during register";

	private final String UPDATE_ERR = "Error during update";

	private final String DELETE_ERR = "Error during delete";

	private final String DELETE_OK = "Delete successful";

	private final String NOT_FOUND = "Data not found";

	private final String LOG_TEMPLATE = "{}::{}() - {}";

	@Override
	public UserResponseDto addUser(UserRequestDto req) throws UserException {

		try {
			UserEntity entity = UserEntity.builder().fname(req.getFname()).lname(req.getLname()).email(req.getEmail())
					.build();

			entity = repository.save(entity);

			emailUtils.sendWelcomeEmail(entity.getEmail());

			return UserResponseDto.builder().id(entity.getId()).fname(entity.getFname()).lname(entity.getLname())
					.email(entity.getEmail()).build();

		} catch (DataAccessException ex) {

			log.error(LOG_TEMPLATE, getClass().getSimpleName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), ex.getMessage());

			throw new UserException(REGISTER_ERR);
		}

	}

	@Override
	public List<UserResponseDto> getUsers() throws UserException {
		try {
			List<UserEntity> entityList = repository.findAll();

			if (!CollectionUtils.isEmpty(entityList)) {
				return mapUsers(entityList);
			} else {
				throw new UserException(NOT_FOUND);
			}

		} catch (DataAccessException ex) {

			log.error(LOG_TEMPLATE, getClass().getSimpleName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), ex.getMessage());

			throw new UserException(SEARCH_ERR);
		}
	}

	@Override
	public UserResponseDto getUserById(int id) throws UserException {
		try {
			Optional<UserEntity> entity = repository.findById(id);

			if (entity.isPresent()) {
				return UserResponseDto.builder().id(entity.get().getId()).fname(entity.get().getFname())
						.lname(entity.get().getLname()).email(entity.get().getEmail()).build();
			} else {
				throw new UserException(NOT_FOUND);
			}

		} catch (DataAccessException ex) {

			log.error(LOG_TEMPLATE, getClass().getSimpleName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), ex.getMessage());

			throw new UserException(SEARCH_ERR);
		}
	}

	@Override
	public UserResponseDto updateUser(int id, UserRequestDto req) throws UserException {
		try {

			int result = repository.update(req.getFname(), req.getLname(), req.getEmail(), id);

			if (result == 1) {
				return UserResponseDto.builder().id(id).fname(req.getFname()).lname(req.getLname())
						.email(req.getEmail()).build();

			} else {
				throw new UserException(NOT_FOUND);
			}

		} catch (DataAccessException ex) {

			log.error(LOG_TEMPLATE, getClass().getSimpleName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), ex.getMessage());

			throw new UserException(UPDATE_ERR);
		}
	}

	@Override
	public String deleteUser(int id) throws UserException {
		try {
			int result = repository.delete(id);
			if (result == 1) {
				return DELETE_OK;
			} else {
				throw new UserException(NOT_FOUND);
			}
		} catch (DataAccessException ex) {

			log.error(LOG_TEMPLATE, getClass().getSimpleName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), ex.getMessage());

			throw new UserException(DELETE_ERR);
		}
	}

	@Override
	public void deleteMultipleUsers(List<Integer> ids) throws UserException {
		try {
			repository.deleteMultiple(ids);

		} catch (DataAccessException ex) {

			log.error(LOG_TEMPLATE, getClass().getSimpleName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), ex.getMessage());

			throw new UserException(DELETE_ERR);
		}
	}

	public List<UserResponseDto> mapUsers(List<UserEntity> entityList) {
		return entityList.stream().map(data -> UserResponseDto.builder().id(data.getId()).fname(data.getFname())
				.lname(data.getLname()).email(data.getEmail()).build()).collect(Collectors.toList());
	}

}
