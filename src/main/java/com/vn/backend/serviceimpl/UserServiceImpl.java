package com.vn.backend.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.vn.auth.model.ERole;
import com.vn.auth.model.Role;
import com.vn.auth.model.User;
import com.vn.auth.repository.RoleRepository;
import com.vn.auth.repository.UserRepository;
import com.vn.backend.dto.UserDto;
import com.vn.backend.response.UserResponse;
import com.vn.backend.service.UserService;
import com.vn.utils.Constant;
import com.vn.utils.FileUploadUtil;
import com.vn.utils.Message;
import com.vn.utils.UserUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${image.system}")
	private String images;

	@Override
	public UserResponse getPaggingUser(int deleteFlag, Pageable pagging) {
		Page<User> pages = userRepository.findAll(pagging);
		UserResponse results = new UserResponse();

		List<User> users = pages.getContent();
		List<UserDto> listResult = new ArrayList<>();

		for (User user : users) {
			UserDto dto = new UserDto();
			BeanUtils.copyProperties(user, dto);
			listResult.add(dto);
		}
		results.setUsers(listResult);
		results.setPage(pages.getPageable().getPageNumber());
		results.setSize(pages.getSize());
		results.setTotalPages(pages.getTotalPages());
		results.setTotalElement(pages.getTotalElements());
		return results;
	}

	@Override
	public UserDto getDetail(Long id, int deleteFlag) {
		Optional<User> dataDetail = userRepository.findById(id);
		if (dataDetail.isPresent()) {
			UserDto dto = new UserDto();
			BeanUtils.copyProperties(dataDetail.get(), dto);
			return dto;
		}

		return null;
	}

	@Override
	public UserDto add(UserDto userDto) {
		User newObject = new User();
		BeanUtils.copyProperties(userDto, newObject);
		newObject.setPassword(passwordEncoder.encode(userDto.getPassword()));

		HashSet<Role> roles = new HashSet<>();
		Role roleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN).get();

		roles.add(roleAdmin);
		newObject.setRoles(roles);

		User result = userRepository.save(newObject);
		BeanUtils.copyProperties(result, userDto);
		return userDto;
	}

	@Override
	public UserResponse addVaildate(UserDto dto) throws Exception {

		UserResponse result = vailidate(dto);

		if (result.getStatus() != Constant.STATUS_SUCCSESS) {
			return result;
		}

		User entity = new User();
		BeanUtils.copyProperties(dto, entity);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));

		HashSet<Role> roles = new HashSet<>();
		Role roleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN).get();
		roles.add(roleAdmin);
		entity.setRoles(roles);

		MultipartFile multipartFile = dto.getImageUpload();
		String fileName = multipartFile.getOriginalFilename();
		try {
			FileUploadUtil.saveFile(this.images, fileName, multipartFile);
		} catch (IOException e) {
			throw new Exception();
		}
		if (StringUtils.hasText(fileName)) {
			String url = File.separator + this.images + File.separator + fileName;
			entity.setImage(url);
		}
		User dataAfterSave = userRepository.save(entity);
		BeanUtils.copyProperties(dataAfterSave, dto);
		result.setStatus(Constant.STATUS_SUCCSESS);
		result.setMessage(Message.ADD_SUCCESS);
		result.setUser(dto);

		return result;
	}

	@Override
	public UserDto update(UserDto dto) throws Exception {
		Optional<User> option = userRepository.findById(dto.getId());

		if (option.isPresent()) {
			User dataDb = option.get();
			if (StringUtils.hasText(dto.getEmail())) {
				dataDb.setEmail(dto.getEmail());
			}

			MultipartFile multipartFile = dto.getImageUpload();
			String fileName = multipartFile.getOriginalFilename();
			if (StringUtils.hasText(fileName)) {
				try {
					FileUploadUtil.saveFile(this.images, fileName, multipartFile);
				} catch (IOException e) {
					throw new Exception();
				}
				String url = File.separator + this.images + File.separator + fileName;
				dataDb.setImage(url);
			} else {
				dataDb.setImage(dto.getImage());
			}

			User result = userRepository.save(dataDb);
			BeanUtils.copyProperties(dto, result);
			return dto;
		} else {
			throw new Exception(Message.UPDATE_FAILURE);
		}
	}

	@Override
	public boolean delete(Long id) throws Exception {
		Optional<User> option = userRepository.findById(id);
		if (option.isPresent()) {
			userRepository.deleteById(id);
			return true;
		}
		return false;
	}

	private UserResponse vailidate(UserDto dto) {
		UserResponse result = new UserResponse();

		if (!StringUtils.hasText(dto.getUsername())) {
			result.setStatus(Constant.NOT_NULL);
			result.setMessage(Message.NAMENAME_NOT_NULL);
			result.setUser(dto);
			return result;
		}

		if (!StringUtils.hasText(dto.getPassword()) || !StringUtils.hasText(dto.getConfirmPassword())) {
			result.setStatus(Constant.NOT_NULL);
			result.setMessage(Message.PASSWORD_NOT_NULL);
			result.setUser(dto);
			return result;
		}

		if (!StringUtils.hasText(dto.getEmail())) {
			result.setStatus(Constant.NOT_NULL);
			result.setMessage(Message.EMAIL_NOT_NULL);
			result.setUser(dto);
			return result;
		}

		if (!dto.getPassword().equals(dto.getConfirmPassword())) {
			result.setStatus(Constant.NOT_DUPLICATE);
			result.setMessage(Message.PASSWORD_NOT_DUPLICATE);
			result.setUser(dto);
			return result;
		}

		if (dto.getPassword().length() > 50 || dto.getConfirmPassword().length() > 50
				|| dto.getConfirmPassword().length() > 50) {
			result.setStatus(Constant.MAX_LENGTH);
			result.setMessage(Message.MAX_LENGHT_50);
			result.setUser(dto);
			return result;
		}

		boolean existsByUsername = userRepository.existsByUsername(dto.getUsername());
		if (existsByUsername) {
			result.setStatus(Constant.DUPLICATE);
			result.setMessage(Message.USERNAME_DUPLICATE);
			result.setUser(dto);
			return result;
		}

		result.setStatus(Constant.STATUS_SUCCSESS);
		result.setMessage(Message.VALIDATE_SUCCESS);
		result.setUser(dto);
		return result;
	}

	@Override
	public UserResponse changePassword(UserDto dto) {
		UserResponse result = new UserResponse();

		String passwordOld = "";
		String password = "";
		String ConfirmPassword = "";

		if (StringUtils.hasText(dto.getPasswordOld())) {
			passwordOld = dto.getPasswordOld();
		}

		if (StringUtils.hasText(dto.getPassword())) {
			password = dto.getPassword();
		}

		if (StringUtils.hasText(dto.getConfirmPassword())) {
			ConfirmPassword = dto.getConfirmPassword();
		}

		String username = UserUtils.getUserLogin();
		User user = null;
		Optional<User> optional = userRepository.findByUsername(username);
		if (optional.isPresent()) {
			user = optional.get();
		}

		if (user == null) {
			result.setStatus(Constant.NOT_FOUND);
			result.setMessage(Message.USERNAME_NOT_FOUND);
			result.setUser(dto);
			return result;
		}

		boolean checkPassword = passwordEncoder.matches(passwordOld, user.getPassword());
		if (!checkPassword) {
			result.setStatus(Constant.NOT_DUPLICATE);
			result.setMessage(Message.PASSWORD_INVALID);
			result.setUser(dto);
			return result;
		}

		if (!dto.getPassword().equals(dto.getConfirmPassword())) {
			result.setStatus(Constant.NOT_DUPLICATE);
			result.setMessage(Message.PASSWORD_NOT_DUPLICATE);
			result.setUser(dto);
			return result;
		}

		if (passwordOld.length() > 50 || password.length() > 50 || ConfirmPassword.length() > 50) {
			result.setStatus(Constant.MAX_LENGTH);
			result.setMessage(Message.MAX_LENGHT_50);
			result.setUser(dto);
			return result;
		}

		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		userRepository.save(user);
		result.setStatus(Constant.STATUS_SUCCSESS);
		result.setMessage(Message.UPDATE_SUCCESS);
		result.setUser(dto);
		return result;
	}

	public UserDto findByUsername(String username) throws Exception {
		Optional<User> optional = userRepository.findByUsername(username);
		if (optional.isPresent()) {
			UserDto dto = new UserDto();
			BeanUtils.copyProperties(optional.get(), dto);
			return dto;
		} else {
			throw new Exception("Username khong toi tai !!!!");
		}

	}
}
