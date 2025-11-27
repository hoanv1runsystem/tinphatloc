package com.vn.backend.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.vn.backend.dto.LogoDto;
import com.vn.backend.model.Logo;
import com.vn.backend.repository.LogoRepository;
import com.vn.backend.response.LogoResponse;
import com.vn.backend.service.LogoService;
import com.vn.utils.Constant;
import com.vn.utils.FileUploadUtil;
import com.vn.utils.Message;

@Service
public class LogoServiceImpl implements LogoService {

	@Autowired
	private LogoRepository logoRepository;

	@Value("${image.system}")
	private String images;

	@Override
	public List<LogoDto> getListAll(int deleteFlag) {
		List<Logo> logos = logoRepository.findByDeleteFlag(deleteFlag);
		List<LogoDto> results = new ArrayList<>();
		for (Logo obj : logos) {
			LogoDto dto = new LogoDto();
			BeanUtils.copyProperties(obj, dto);
			results.add(dto);
		}
		return results;
	}

	@Override
	public LogoResponse getPagging(int deleteFlag, Pageable pagging) {
		LogoResponse results = new LogoResponse();

		Page<Logo> pages = logoRepository.findAll(pagging);
		List<Logo> logos = pages.getContent();
		List<LogoDto> listObject = new ArrayList<>();
		for (Logo obj : logos) {
			LogoDto dto = new LogoDto();
			BeanUtils.copyProperties(obj, dto);
			listObject.add(dto);
		}
		results.setLogos(listObject);
		results.setPage(pages.getPageable().getPageNumber());
		results.setSize(pages.getSize());
		results.setTotalPages(pages.getTotalPages());
		results.setTotalElement(pages.getTotalElements());
		return results;
	}

	@Override
	public LogoDto getDetail(Long id, int deleteFlag) {
		Logo object = logoRepository.findByIdAndDeleteFlag(id, deleteFlag);
		LogoDto dto = new LogoDto();
		BeanUtils.copyProperties(object, dto);
		return dto;
	}

	@Override
	public LogoDto add(LogoDto dto) {
		Logo entity = new Logo();
		BeanUtils.copyProperties(dto, entity);
		Logo result = logoRepository.save(entity);
		BeanUtils.copyProperties(result, dto);
		return dto;
	}

	@Override
	public LogoResponse addValidate(LogoDto dto) throws Exception {
		LogoResponse response = new LogoResponse();

		MultipartFile multipartFile = dto.getImageUpload();
		String fileName = multipartFile.getOriginalFilename();
		try {
			FileUploadUtil.saveFile(this.images, fileName, multipartFile);
		} catch (IOException e) {
			throw new Exception();
		}

		Logo entity = new Logo();
		BeanUtils.copyProperties(dto, entity);

		if (StringUtils.hasText(fileName)) {
			String url = File.separator + this.images + File.separator + fileName;
			entity.setImage(url);
		}

		Logo result = logoRepository.save(entity);
		BeanUtils.copyProperties(result, dto);

		response.setStatus(Constant.STATUS_SUCCSESS);
		response.setMessage(Message.ADD_SUCCESS);
		response.setLogo(dto);

		return response;
	}

	@Override
	public LogoResponse update(LogoDto dto) throws Exception {
		Optional<Logo> option = logoRepository.findById(dto.getId());
		LogoResponse response = new LogoResponse();
		if (option.isPresent()) {

			Logo entity = new Logo();
			Logo dataInDatabase = option.get();

			// Copy data from dto to entity
			BeanUtils.copyProperties(dto, entity);

			MultipartFile multipartFile = dto.getImageUpload();
			String fileName = multipartFile.getOriginalFilename();
			if (StringUtils.hasText(fileName)) {
				try {
					FileUploadUtil.saveFile(this.images, fileName, multipartFile);
				} catch (IOException e) {
					throw new Exception();
				}
				String url = File.separator + this.images + File.separator + fileName;
				entity.setImage(url);
			}
			entity.setDeleteFlag(dataInDatabase.getDeleteFlag());
			Logo dataAfterUpdate = logoRepository.save(entity);

			// Copy data after update
			BeanUtils.copyProperties(dataAfterUpdate, dto);
			response.setStatus(Constant.STATUS_SUCCSESS);
			response.setMessage(Message.ADD_SUCCESS);
			response.setLogo(dto);
			return response;
		} else {
			response.setStatus(Constant.NOT_FOUND);
			response.setMessage(Message.NOT_FOUND);
			response.setLogo(dto);
			return response;
		}
	}

	@Override
	public boolean delete(Long id) throws Exception {
		Optional<Logo> option = logoRepository.findById(id);

		if (option.isPresent()) {
			logoRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
