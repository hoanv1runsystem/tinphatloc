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

import com.vn.backend.dto.SliceDto;
import com.vn.backend.model.Slice;
import com.vn.backend.repository.SliceRepository;
import com.vn.backend.response.SliceResponse;
import com.vn.backend.service.SliceService;
import com.vn.utils.Constant;
import com.vn.utils.FileUploadUtil;
import com.vn.utils.Message;

@Service
public class SliceServiceImpl implements SliceService {
	@Value("${upload.path}")
	private String fileUpload;
	@Autowired
	private SliceRepository sliceRepository;

	@Value("${image.system}")
	private String images;

	@Override
	public List<SliceDto> getListAll(int deleteFlag) {
		List<Slice> slices = sliceRepository.findByDeleteFlag(deleteFlag);
		List<SliceDto> results = new ArrayList<>();
		for (Slice obj : slices) {
			SliceDto dto = new SliceDto();
			BeanUtils.copyProperties(obj, dto);
			results.add(dto);
		}
		return results;
	}

	@Override
	public SliceResponse getPagging(int deleteFlag, Pageable pagging) {
		SliceResponse results = new SliceResponse();

		Page<Slice> pages = sliceRepository.findAll(pagging);
		List<Slice> slices = pages.getContent();
		List<SliceDto> listObject = new ArrayList<>();
		for (Slice obj : slices) {
			SliceDto dto = new SliceDto();
			BeanUtils.copyProperties(obj, dto);
			listObject.add(dto);
		}
		results.setSlices(listObject);
		results.setPage(pages.getPageable().getPageNumber());
		results.setSize(pages.getSize());
		results.setTotalPages(pages.getTotalPages());
		results.setTotalElement(pages.getTotalElements());
		return results;
	}

	@Override
	public SliceDto getDetail(Long id, int deleteFlag) {
		Slice object = sliceRepository.findByIdAndDeleteFlag(id, deleteFlag);
		SliceDto dto = new SliceDto();
		BeanUtils.copyProperties(object, dto);
		return dto;
	}

	@Override
	public SliceDto add(SliceDto dto) {
		Slice entity = new Slice();
		BeanUtils.copyProperties(dto, entity);
		Slice result = sliceRepository.save(entity);
		BeanUtils.copyProperties(result, dto);
		return dto;
	}

	@Override
	public SliceResponse addValidate(SliceDto dto) throws Exception {
		SliceResponse response = new SliceResponse();
		List<Slice> listObject = sliceRepository.findByTitleAndDeleteFlag(dto.getTitle(), Constant.DELETE_FLAG_ACTIVE);
		if (listObject != null && listObject.size() > 0) {
			response.setStatus(Constant.DUPLICATE);
			response.setMessage(Message.NAME_CATEGORY_DUPLICATE);
			response.setSlice(dto);
			return response;
		}

		MultipartFile multipartFile = dto.getImageUpload();
		String fileName = multipartFile.getOriginalFilename();
		try {
			FileUploadUtil.saveFile(this.images, fileName, multipartFile);
		} catch (IOException e) {
			throw new Exception();
		}

		Slice entity = new Slice();
		BeanUtils.copyProperties(dto, entity);

		if (StringUtils.hasText(fileName)) {
			String url = File.separator + this.images + File.separator + fileName;
			entity.setImage(url);
		}

		Slice result = sliceRepository.save(entity);
		BeanUtils.copyProperties(result, dto);

		response.setStatus(Constant.STATUS_SUCCSESS);
		response.setMessage(Message.ADD_SUCCESS);
		response.setSlice(dto);

		return response;
	}

	@Override
	public SliceResponse update(SliceDto dto) throws Exception {
		Optional<Slice> option = sliceRepository.findById(dto.getId());
		SliceResponse response = new SliceResponse();
		if (option.isPresent()) {

			Slice entity = new Slice();
			Slice dataInDatabase = option.get();

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
			Slice dataAfterUpdate = sliceRepository.save(entity);

			// Copy data after update
			BeanUtils.copyProperties(dataAfterUpdate, dto);
			response.setStatus(Constant.STATUS_SUCCSESS);
			response.setMessage(Message.ADD_SUCCESS);
			response.setSlice(dto);
			return response;
		} else {
			response.setStatus(Constant.NOT_FOUND);
			response.setMessage(Message.NOT_FOUND);
			response.setSlice(dto);
			return response;
		}
	}

	@Override
	public boolean delete(Long id) throws Exception {
		Optional<Slice> option = sliceRepository.findById(id);

		if (option.isPresent()) {
			sliceRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
