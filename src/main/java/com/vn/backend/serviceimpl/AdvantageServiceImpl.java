package com.vn.backend.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vn.backend.dto.AdvantageDto;
import com.vn.backend.model.Advantage;
import com.vn.backend.repository.AdvantageRepository;
import com.vn.backend.response.AdvantageResponse;
import com.vn.backend.service.AdvantageService;
import com.vn.utils.Constant;
import com.vn.utils.Message;

@Service
public class AdvantageServiceImpl implements AdvantageService {

	@Autowired
	private AdvantageRepository advantageRepository;

	@Override
	public List<AdvantageDto> getListAdvantage() {

		List<Advantage> advantages = advantageRepository.findAll();
		List<AdvantageDto> results = new ArrayList<>();
		for (Advantage advantage : advantages) {
			AdvantageDto dto = new AdvantageDto();
			BeanUtils.copyProperties(advantage, dto);
			results.add(dto);

		}
		return results;
	}

	@Override
	public List<AdvantageDto> getListAll(int deleteFlag) {
		List<Advantage> listObject = advantageRepository.findByDeleteFlag(deleteFlag);
		List<AdvantageDto> results = new ArrayList<>();
		for (Advantage obj : listObject) {
			AdvantageDto dto = new AdvantageDto();
			BeanUtils.copyProperties(obj, dto);
			results.add(dto);
		}
		return results;
	}

	@Override
	public AdvantageResponse getPagging(int deleteFlag, Pageable pagging) {
		AdvantageResponse results = new AdvantageResponse();

		Page<Advantage> pages = advantageRepository.findAll(pagging);
		List<Advantage> advantages = pages.getContent();
		List<AdvantageDto> listObject = new ArrayList<>();
		for (Advantage obj : advantages) {
			AdvantageDto dto = new AdvantageDto();
			BeanUtils.copyProperties(obj, dto);
			listObject.add(dto);
		}
		results.setAdvantages(listObject);
		results.setPage(pages.getPageable().getPageNumber());
		results.setSize(pages.getSize());
		results.setTotalPages(pages.getTotalPages());
		results.setTotalElement(pages.getTotalElements());
		return results;
	}

	@Override
	public AdvantageDto getDetail(Long id, int deleteFlag) {
		Advantage object = advantageRepository.findByIdAndDeleteFlag(id, deleteFlag);
		AdvantageDto dto = new AdvantageDto();
		BeanUtils.copyProperties(object, dto);
		return dto;
	}

	@Override
	public AdvantageDto add(AdvantageDto dto) {
		Advantage entity = new Advantage();
		BeanUtils.copyProperties(dto, entity);
		Advantage result = advantageRepository.save(entity);
		BeanUtils.copyProperties(result, dto);
		return dto;
	}

	@Override
	public AdvantageResponse addValidate(AdvantageDto dto) throws Exception {
		AdvantageResponse response = new AdvantageResponse();
		List<Advantage> listObject = advantageRepository.findByNameAndDeleteFlag(dto.getName(),
				Constant.DELETE_FLAG_ACTIVE);
		if (listObject != null && listObject.size() > 0) {
			response.setStatus(Constant.DUPLICATE);
			response.setMessage(Message.NAME_CATEGORY_DUPLICATE);
			response.setAdvantage(dto);
			return response;
		}

		Advantage entity = new Advantage();
		BeanUtils.copyProperties(dto, entity);

		Advantage result = advantageRepository.save(entity);
		BeanUtils.copyProperties(result, dto);

		response.setStatus(Constant.STATUS_SUCCSESS);
		response.setMessage(Message.ADD_SUCCESS);
		response.setAdvantage(dto);

		return response;
	}

	@Override
	public AdvantageResponse update(AdvantageDto dto) throws Exception {
		Optional<Advantage> option = advantageRepository.findById(dto.getId());
		AdvantageResponse response = new AdvantageResponse();
		if (option.isPresent()) {

			Advantage entity = new Advantage();
			Advantage dataInDatabase = option.get();

			// Copy data from dto to entity
			BeanUtils.copyProperties(dto, entity);

			entity.setDeleteFlag(dataInDatabase.getDeleteFlag());
			Advantage dataAfterUpdate = advantageRepository.save(entity);

			// Copy data after update
			BeanUtils.copyProperties(dataAfterUpdate, dto);
			response.setStatus(Constant.STATUS_SUCCSESS);
			response.setMessage(Message.ADD_SUCCESS);
			response.setAdvantage(dto);
			return response;
		} else {
			response.setStatus(Constant.NOT_FOUND);
			response.setMessage(Message.NOT_FOUND);
			response.setAdvantage(dto);
			return response;
		}
	}

	@Override
	public boolean delete(Long id) throws Exception {
		Optional<Advantage> option = advantageRepository.findById(id);

		if (option.isPresent()) {
			advantageRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
