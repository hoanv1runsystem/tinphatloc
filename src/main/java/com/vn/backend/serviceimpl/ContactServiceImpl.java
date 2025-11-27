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

import com.vn.backend.dto.ContactDto;
import com.vn.backend.model.Contact;
import com.vn.backend.repository.ContactRepository;
import com.vn.backend.response.ContactResponse;
import com.vn.backend.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository contactRepository;

	@Override
	public List<ContactDto> getListAll(int deleteFlag) {
		List<Contact> contacts = contactRepository.findByDeleteFlag(deleteFlag);
		List<ContactDto> results = new ArrayList<>();
		for (Contact contact : contacts) {
			ContactDto dto = new ContactDto();
			BeanUtils.copyProperties(contact, dto);
			results.add(dto);
		}
		return results;
	}

	@Override
	public ContactResponse getPaggingContact(int deleteFlag, Pageable pagging) {
		ContactResponse results = new ContactResponse();

		Page<Contact> pages = contactRepository.findByDeleteFlag(deleteFlag, pagging);
		List<Contact> contacts = pages.getContent();
		List<ContactDto> listResult = new ArrayList<>();

		for (Contact contact : contacts) {
			ContactDto dto = new ContactDto();
			BeanUtils.copyProperties(contact, dto);
			listResult.add(dto);
		}
		results.setContacts(listResult);
		results.setPage(pages.getPageable().getPageNumber());
		results.setSize(pages.getSize());
		results.setTotalPages(pages.getTotalPages());
		results.setTotalElement(pages.getTotalElements());
		return results;
	}

	@Override
	public ContactDto getDetail(Long id, int deleteFlag) {
		Contact dataDetail = contactRepository.findByIdAndDeleteFlag(id, deleteFlag);
		ContactDto dto = new ContactDto();
		BeanUtils.copyProperties(dataDetail, dto);
		return dto;
	}

	@Override
	public ContactDto add(ContactDto contactDto) {
		Contact newObject = new Contact();
		BeanUtils.copyProperties(contactDto, newObject);
		Contact result = contactRepository.save(newObject);
		BeanUtils.copyProperties(result, contactDto);
		return contactDto;
	}

	@Override
	public ContactDto update(ContactDto contactDto) throws Exception {
		Optional<Contact> option = contactRepository.findById(contactDto.getId());

		if (option.isPresent()) {
			Contact dataDb = option.get();
			if (!StringUtils.isEmpty(contactDto.getName())) {
				dataDb.setName(contactDto.getName());
			}

			if (!StringUtils.isEmpty(contactDto.getEmail())) {
				dataDb.setEmail(contactDto.getEmail());
			}
			if (!StringUtils.isEmpty(contactDto.getPhone())) {
				dataDb.setEmail(contactDto.getEmail());
			}
			if (!StringUtils.isEmpty(contactDto.getGoogleMap())) {
				dataDb.setGoogleMap(contactDto.getGoogleMap());
			}
			if (!StringUtils.isEmpty(contactDto.getAddress())) {
				dataDb.setAddress(contactDto.getAddress());
			}

			Contact result = contactRepository.save(dataDb);
			BeanUtils.copyProperties(contactDto, result);
			return contactDto;
		} else {
			throw new Exception("Update category failure");
		}
	}

	@Override
	public boolean delete(Long id) throws Exception {

		Optional<Contact> option = contactRepository.findById(id);
		if (option.isPresent()) {
			contactRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
