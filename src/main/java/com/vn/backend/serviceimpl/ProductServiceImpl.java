package com.vn.backend.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.vn.backend.dto.CategoryDto;
import com.vn.backend.dto.ProductDto;
import com.vn.backend.model.Product;
import com.vn.backend.repository.ProductRepository;
import com.vn.backend.response.ProductResponse;
import com.vn.backend.service.CategoryService;
import com.vn.backend.service.ProductService;
import com.vn.utils.Constant;
import com.vn.utils.FileUploadUtil;
import com.vn.utils.Message;
import com.vn.utils.Util;

@Service
public class ProductServiceImpl implements ProductService {

	@Value("${upload.path}")
	private String fileUpload;

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryService categoryService;

	@Override
	public List<ProductDto> getListProduct() {
		List<Product> products = productRepository.findAll();
		List<ProductDto> results = new ArrayList<>();

		for (Product product : products) {
			ProductDto dto = new ProductDto();

			BeanUtils.copyProperties(product, dto);
			dto.setUrlFriendy(Util.removeAccent(product.getName()));
			results.add(dto);

		}
		return results;
	}

	@Override
	public ProductResponse getPaggingProduct(int deleteFlag, Pageable pagging) {
		ProductResponse results = new ProductResponse();

		Page<Product> pages = productRepository.findByDeleteFlag(deleteFlag, pagging);
		List<Product> products = pages.getContent();
		List<ProductDto> listResult = new ArrayList<>();

		for (Product product : products) {
			ProductDto dto = new ProductDto();
			BeanUtils.copyProperties(product, dto);
			CategoryDto cate = categoryService.getDetail(product.getCategoryId(), Constant.DELETE_FLAG_ACTIVE);
			dto.setUrlFriendy(Util.removeAccent(product.getName()));
			dto.setCategoryName(cate.getName());
			dto.setCategory(cate);
			listResult.add(dto);
		}
		results.setProducts(listResult);
		results.setPage(pages.getPageable().getPageNumber());
		results.setSize(pages.getSize());
		results.setTotalPages(pages.getTotalPages());
		results.setTotalElement(pages.getTotalElements());
		return results;
	}

	@Override
	public ProductDto getDetail(Long id, int deleteFlag) {
		Product dataDetail = productRepository.findByIdAndDeleteFlag(id, deleteFlag);
		ProductDto dto = new ProductDto();
		MultipartFile multipartFile = dto.getImageProduct();
		dto.setImageProduct(multipartFile);
		BeanUtils.copyProperties(dataDetail, dto);
		return dto;
	}

	@Override
	public ProductDto add(ProductDto productDto) {
		Product newObject = new Product();

		MultipartFile multipartFile = productDto.getImageProduct();
		String fileName = multipartFile.getOriginalFilename();
		try {
			FileUploadUtil.saveFile(this.fileUpload, fileName, multipartFile);
		} catch (IOException e) {
			logger.error("Upload image has erro: " + productDto.getId());
			logger.error("Message: " + e.getMessage());
		}
		String url = File.separator + this.fileUpload + File.separator + fileName;
		productDto.setImage(url);
		BeanUtils.copyProperties(productDto, newObject);
		Product result = productRepository.save(newObject);
		BeanUtils.copyProperties(result, productDto);
		return productDto;
	}

	@Override
	public ProductResponse addValidate(ProductDto productDto) {
		ProductResponse result = new ProductResponse();

		if (!StringUtils.hasText(productDto.getName())) {
			result.setStatus(Constant.NOT_NULL);
			result.setMessage(Message.PRODUCT_NAME_NOT_NULL);
			result.setProduct(productDto);
			return result;
		}

		if (!StringUtils.hasText(productDto.getProductCode())) {
			result.setStatus(Constant.NOT_NULL);
			result.setMessage(Message.PRODUCT_CODE_NOT_NULL);
			result.setProduct(productDto);
			return result;
		}

		List<Product> exitsProductNane = productRepository.findByNameAndDeleteFlag(productDto.getName(),
				Constant.DELETE_FLAG_ACTIVE);

		if (exitsProductNane != null && exitsProductNane.size() > 0) {
			result.setStatus(Constant.DUPLICATE);
			result.setMessage(Message.NAME_PRODUCT_DUPLICATE);
			result.setProduct(productDto);
			return result;
		}

		List<Product> exitsProductCode = productRepository.findByProductCodeAndDeleteFlag(productDto.getProductCode(),
				Constant.DELETE_FLAG_ACTIVE);
		if (exitsProductCode != null && exitsProductCode.size() > 0) {
			result.setStatus(Constant.DUPLICATE);
			result.setMessage(Message.PRODUCT_CODE_DUPLICATE);
			result.setProduct(productDto);
			return result;
		}

		Product entity = new Product();
		BeanUtils.copyProperties(productDto, entity);
		Product dataAfterSave = productRepository.save(entity);
		BeanUtils.copyProperties(dataAfterSave, productDto);

		result.setStatus(Constant.STATUS_SUCCSESS);
		result.setMessage(Message.ADD_SUCCESS);
		result.setProduct(productDto);

		return result;
	}

	@Override
	public ProductResponse update(ProductDto productDto) {
		Optional<Product> option = productRepository.findById(productDto.getId());

		ProductResponse result = new ProductResponse();

		if (!StringUtils.hasText(productDto.getName())) {
			result.setStatus(Constant.NOT_NULL);
			result.setMessage(Message.PRODUCT_NAME_NOT_NULL);
			result.setProduct(productDto);
			return result;
		}

		if (!StringUtils.hasText(productDto.getProductCode())) {
			result.setStatus(Constant.NOT_NULL);
			result.setMessage(Message.PRODUCT_CODE_NOT_NULL);
			result.setProduct(productDto);
			return result;
		}

		List<Product> exitsProductNane = productRepository.findByNameAndDeleteFlag(productDto.getName(),
				Constant.DELETE_FLAG_ACTIVE);

		if (exitsProductNane != null && exitsProductNane.size() > 0
				&& exitsProductNane.get(0).getId().compareTo(productDto.getId()) != 0) {
			result.setStatus(Constant.DUPLICATE);
			result.setMessage(Message.NAME_PRODUCT_DUPLICATE);
			result.setProduct(productDto);
			return result;
		}

		List<Product> exitsProductCode = productRepository.findByProductCodeAndDeleteFlag(productDto.getProductCode(),
				Constant.DELETE_FLAG_ACTIVE);
		if (exitsProductCode != null && exitsProductCode.size() > 0
				&& exitsProductCode.get(0).getId().compareTo(productDto.getId()) != 0) {
			result.setStatus(Constant.DUPLICATE);
			result.setMessage(Message.PRODUCT_CODE_DUPLICATE);
			result.setProduct(productDto);
			return result;
		}

		if (option.isPresent()) {
			Product dataDb = option.get();

//			if (productDto.getCategoryId() != null) {
//				dataDb.setCategoryId(productDto.getCategoryId());
//			}
//
//			if (StringUtils.hasText(productDto.getProductCode())) {
//				dataDb.setProductCode(productDto.getProductCode());
//			}
//			if (StringUtils.hasText(productDto.getName())) {
//				dataDb.setName(productDto.getName());
//			}
//			if (productDto.getAmount() != null) {
//				dataDb.setAmount(productDto.getAmount());
//			}
//			if (productDto.getPrice() != null) {
//				dataDb.setPrice(productDto.getPrice());
//			}
//
//			if (StringUtils.hasText(productDto.getIngredient())) {
//				dataDb.setIngredient(productDto.getIngredient());
//			}
//
//			if (StringUtils.hasText(productDto.getStandard())) {
//				dataDb.setStandard(productDto.getStandard());
//			}
//
//			if (StringUtils.hasText(productDto.getSpecifications())) {
//				dataDb.setSpecifications(productDto.getSpecifications());
//			}
//
//			if (StringUtils.hasText(productDto.getSize())) {
//				dataDb.setSize(productDto.getSize());
//			}
//
//			if (StringUtils.hasText(productDto.getWeight())) {
//				dataDb.setWeight(productDto.getWeight());
//			}
//
//			if (StringUtils.hasText(productDto.getNewProduct())) {
//				dataDb.setNewProduct(productDto.getNewProduct());
//			}
//
//			if (StringUtils.hasText(productDto.getDescription1())) {
//				dataDb.setDescription1(productDto.getDescription1());
//			}
//
//			if (StringUtils.hasText(productDto.getDescription2())) {
//				dataDb.setDescription2(productDto.getDescription2());
//			}

			MultipartFile multipartFile = productDto.getImageProduct();
			String fileName = multipartFile.getOriginalFilename();
			try {
				FileUploadUtil.saveFile(this.fileUpload, fileName, multipartFile);
			} catch (IOException e) {
				logger.error("Upload image has erro: " + productDto.getId());
				logger.error("Message: " + e.getMessage());
			}

			String url = File.separator + this.fileUpload + File.separator + fileName;
			if (StringUtils.hasText(fileName)) {
				productDto.setImage(url);
			}

			Product entity = new Product();
			// Copy dto to entity
			BeanUtils.copyProperties(productDto, entity);
			entity.setDeleteFlag(dataDb.getDeleteFlag());

			Product dataAfterUpdate = productRepository.save(entity);

			// Copy dataAfterUpdate to productDto
			BeanUtils.copyProperties(dataAfterUpdate, productDto);
			result.setStatus(Constant.STATUS_SUCCSESS);
			result.setMessage(Message.UPDATE_SUCCESS);
			result.setProduct(productDto);
			return result;
		} else {
			result.setStatus(Constant.NOT_NULL);
			result.setMessage(Message.PRODUCT_NOT_EXIST);
			result.setProduct(productDto);
			return result;
		}
	}

	@Override
	public boolean delete(Long id) throws Exception {

		Optional<Product> option = productRepository.findById(id);
		if (option.isPresent()) {
			productRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
