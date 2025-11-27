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

import com.vn.backend.dto.VendorDto;
import com.vn.backend.model.Vendor;
import com.vn.backend.repository.VendorRepository;
import com.vn.backend.response.VendorResponse;
import com.vn.backend.service.VendorService;
import com.vn.utils.Constant;
import com.vn.utils.FileUploadUtil;
import com.vn.utils.Message;

@Service
public class VendorServiceImpl implements VendorService {
	@Value("${upload.path}")
	private String fileUpload;

	@Autowired
	private VendorRepository vendorRepository;

	@Override
	public List<VendorDto> getListAll(int deleteFlag) {
		List<Vendor> vendors = vendorRepository.findByDeleteFlag(deleteFlag);
		List<VendorDto> results = new ArrayList<>();
		for (Vendor obj : vendors) {
			VendorDto dto = new VendorDto();
			BeanUtils.copyProperties(obj, dto);
			results.add(dto);
		}
		return results;
	}

	@Override
	public VendorResponse getPagging(int deleteFlag, Pageable pagging) {
		VendorResponse results = new VendorResponse();

		Page<Vendor> pages = vendorRepository.findAll(pagging);
		List<Vendor> vendors = pages.getContent();
		List<VendorDto> listObject = new ArrayList<>();
		for (Vendor obj : vendors) {
			VendorDto dto = new VendorDto();
			BeanUtils.copyProperties(obj, dto);
			listObject.add(dto);
		}
		results.setVendors(listObject);
		results.setPage(pages.getPageable().getPageNumber());
		results.setSize(pages.getSize());
		results.setTotalPages(pages.getTotalPages());
		results.setTotalElement(pages.getTotalElements());
		return results;
	}

	@Override
	public VendorDto getDetail(Long id, int deleteFlag) {
		Vendor object = vendorRepository.findByIdAndDeleteFlag(id, deleteFlag);
		VendorDto dto = new VendorDto();
		BeanUtils.copyProperties(object, dto);
		return dto;
	}

	@Override
	public VendorDto add(VendorDto dto) {
		Vendor entity = new Vendor();
		BeanUtils.copyProperties(dto, entity);
		Vendor result = vendorRepository.save(entity);
		BeanUtils.copyProperties(result, dto);
		return dto;
	}

	@Override
	public VendorResponse addValidate(VendorDto dto) throws Exception {
		VendorResponse response = new VendorResponse();
		List<Vendor> listObject = vendorRepository.findByNameAndDeleteFlag(dto.getName(), Constant.DELETE_FLAG_ACTIVE);
		if (listObject != null && listObject.size() > 0) {
			response.setStatus(Constant.DUPLICATE);
			response.setMessage(Message.NAME_CATEGORY_DUPLICATE);
			response.setVendor(dto);
			return response;
		}

		MultipartFile multipartFile = dto.getImageUpload();
		String fileName = multipartFile.getOriginalFilename();
		try {
			FileUploadUtil.saveFile(this.fileUpload, fileName, multipartFile);
		} catch (IOException e) {
			throw new Exception();
		}

		Vendor entity = new Vendor();
		BeanUtils.copyProperties(dto, entity);

		if (StringUtils.hasText(fileName)) {
			String url = File.separator + this.fileUpload + File.separator + fileName;
			entity.setImage(url);
		}

		Vendor result = vendorRepository.save(entity);
		BeanUtils.copyProperties(result, dto);

		response.setStatus(Constant.STATUS_SUCCSESS);
		response.setMessage(Message.ADD_SUCCESS);
		response.setVendor(dto);

		return response;
	}

	@Override
	public VendorResponse update(VendorDto dto) throws Exception {
		Optional<Vendor> option = vendorRepository.findById(dto.getId());
		VendorResponse response = new VendorResponse();
		if (option.isPresent()) {

			Vendor entity = new Vendor();
			Vendor dataInDatabase = option.get();

			// Copy data from dto to entity
			BeanUtils.copyProperties(dto, entity);

			MultipartFile multipartFile = dto.getImageUpload();
			String fileName = multipartFile.getOriginalFilename();
			if (StringUtils.hasText(fileName)) {
				try {
					FileUploadUtil.saveFile(this.fileUpload, fileName, multipartFile);
				} catch (IOException e) {
					throw new Exception();
				}
				String url = File.separator + this.fileUpload + File.separator + fileName;
				entity.setImage(url);
			}
			entity.setDeleteFlag(dataInDatabase.getDeleteFlag());
			Vendor dataAfterUpdate = vendorRepository.save(entity);

			// Copy data after update
			BeanUtils.copyProperties(dataAfterUpdate, dto);
			response.setStatus(Constant.STATUS_SUCCSESS);
			response.setMessage(Message.ADD_SUCCESS);
			response.setVendor(dto);
			return response;
		} else {
			response.setStatus(Constant.NOT_FOUND);
			response.setMessage(Message.NOT_FOUND);
			response.setVendor(dto);
			return response;
		}
	}

	@Override
	public boolean delete(Long id) throws Exception {
		Optional<Vendor> option = vendorRepository.findById(id);

		if (option.isPresent()) {
			vendorRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
