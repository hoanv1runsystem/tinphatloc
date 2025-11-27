package com.vn.backend.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vn.backend.dto.AboutDto;
import com.vn.backend.model.About;
import com.vn.backend.repository.AboutRepository;
import com.vn.backend.response.AboutResponse;
import com.vn.backend.service.AboutService;

@Service
public class AboutServiceImpl implements AboutService {

	@Autowired
	private AboutRepository aboutRepository;

	@Override
	public AboutResponse getPaggingAbout(int deleteFlag, Pageable pagging) {
		AboutResponse results = new AboutResponse();

		Page<About> pages = aboutRepository.findByDeleteFlag(deleteFlag, pagging);
		List<About> abouts = pages.getContent();
		List<AboutDto> listResult = new ArrayList<>();

		for (About about : abouts) {
			AboutDto dto = new AboutDto();
			BeanUtils.copyProperties(about, dto);
			listResult.add(dto);
		}
		results.setAbouts(listResult);
		results.setPage(pages.getPageable().getPageNumber());
		results.setSize(pages.getSize());
		results.setTotalPages(pages.getTotalPages());
		results.setTotalElement(pages.getTotalElements());
		return results;
	}

	@Override
	public AboutDto getDetail(Long id, int deleteFlag) {
		About dataDetail = aboutRepository.findByIdAndDeleteFlag(id, deleteFlag);
		AboutDto dto = new AboutDto();
		BeanUtils.copyProperties(dataDetail, dto);
		return dto;
	}

	@Override
	public AboutDto add(AboutDto aboutDto) {
		About newObject = new About();
		BeanUtils.copyProperties(aboutDto, newObject);
		About result = aboutRepository.save(newObject);
		BeanUtils.copyProperties(result, aboutDto);
		return aboutDto;
	}

	@Override
	public AboutDto update(AboutDto aboutDto) throws Exception {
		Optional<About> option = aboutRepository.findById(aboutDto.getId());

		if (option.isPresent()) {
			About dataDb = option.get();
			if (StringUtils.hasText(aboutDto.getNameTab())) {
				dataDb.setNameTab(aboutDto.getNameTab());
			}

			if (StringUtils.hasText(aboutDto.getTitle())) {
				dataDb.setTitle(aboutDto.getTitle());
			}
			if (StringUtils.hasText(aboutDto.getDecription())) {
				dataDb.setDecription(aboutDto.getDecription());
			}

			if (aboutDto.getPosition() != null) {
				dataDb.setPosition(aboutDto.getPosition());
			}

			About result = aboutRepository.save(dataDb);
			BeanUtils.copyProperties(aboutDto, result);
			return aboutDto;
		} else {
			throw new Exception("Update About failure");
		}
	}

	@Override
	public boolean delete(Long id) throws Exception {
		Optional<About> option = aboutRepository.findById(id);
		if (option.isPresent()) {
			aboutRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
