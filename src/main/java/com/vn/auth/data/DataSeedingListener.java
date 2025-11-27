package com.vn.auth.data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.vn.auth.model.ERole;
import com.vn.auth.model.Role;
import com.vn.auth.model.User;
import com.vn.auth.repository.RoleRepository;
import com.vn.auth.repository.UserRepository;
import com.vn.backend.model.Advantage;
import com.vn.backend.model.Category;
import com.vn.backend.model.Contact;
import com.vn.backend.model.Menu;
import com.vn.backend.model.Product;
import com.vn.backend.model.Slice;
import com.vn.backend.model.Vendor;
import com.vn.backend.repository.AdvantageRepository;
import com.vn.backend.repository.CategoryRepository;
import com.vn.backend.repository.ContactRepository;
import com.vn.backend.repository.MenuRepository;
import com.vn.backend.repository.ProductRepository;
import com.vn.backend.repository.SliceRepository;
import com.vn.backend.repository.VendorRepository;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private SliceRepository sliceRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private AdvantageRepository advantageRepository;

	@Autowired
	private VendorRepository vendorRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// Roles
		addRole();
		addUser();
		addMenu();
		addContact();
		addSlice();
		addCategory();
		addAdvantage();
		addVendor();
		addProduct();
	}

	private void addRole() {
		Optional<Role> optionalAdmin = roleRepository.findByName(ERole.ROLE_ADMIN);
		if (!optionalAdmin.isPresent()) {
			Role role = new Role(ERole.ROLE_ADMIN);
			roleRepository.save(role);
		}
		Optional<Role> optionalUser = roleRepository.findByName(ERole.ROLE_USER);
		if (!optionalUser.isPresent()) {
			Role role = new Role(ERole.ROLE_USER);
			roleRepository.save(role);
		}

	}

	private void addUser() {
		List<User> listUser = userRepository.findAll();
		// Admin account
		if (listUser == null || (listUser != null && listUser.size() == 0)) {
			User admin = new User();
			admin.setUsername("tinphatloc_admin");
			admin.setEmail("tinphatloc@gmail.com");
			admin.setPassword(passwordEncoder.encode("123456"));
			HashSet<Role> roles = new HashSet<>();
			Role roleUser = roleRepository.findByName(ERole.ROLE_USER).get();
			Role roleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN).get();
			roles.add(roleUser);
			roles.add(roleAdmin);
			admin.setRoles(roles);
			userRepository.save(admin);
		}

	}

	private void addMenu() {
		List<Menu> list = menuRepository.findAll();
		if (list == null || (list != null && list.size() == 0)) {
			Menu menu = new Menu();
			menu.setName("Trang chủ");
			menu.setLink("/");
			menu.setPositon(1);
			menu.setDeleteFlag(0);
			menuRepository.save(menu);

			Menu menu1 = new Menu();
			menu1.setName("Sản Phẩm");
			menu1.setLink("/product");
			menu1.setPositon(10);
			menu1.setDeleteFlag(0);
			menuRepository.save(menu1);

			Menu menu2 = new Menu();
			menu2.setName("Về Chúng Tôi");
			menu2.setLink("/about");
			menu2.setPositon(20);
			menu2.setDeleteFlag(0);
			menuRepository.save(menu2);

			Menu menu3 = new Menu();
			menu3.setName("Liên Hệ");
			menu3.setLink("/contact");
			menu3.setPositon(30);
			menu3.setDeleteFlag(0);
			menuRepository.save(menu3);
		}

	}

	private void addContact() {
		List<Contact> list = contactRepository.findAll();
		if (list == null || (list != null && list.size() == 0)) {
			Contact contact = new Contact();
			contact.setName("Công Ty Cổ Phần Đầu Tư Gạch Phú Mỹ");
			contact.setEmail("ctydtgachphumy@gmail.com");
			contact.setPhone("0978 414 433");
			contact.setGoogleMap("");
			// "https://www.google.com/maps/embed?pb=!1m16!1m12!1m3!1d7843.73143489679!2d107.14137119728865!3d10.589670397755814!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!2m1!1zVGjDtG4gVMOibiBDaMOidSwgWMOjIENow6J1IFBoYSwgVGjhu4sgeMOjIFBow7ogTeG7uSwgVOG7iW5oIELDoCBS4buLYSAtIFbFqW5nIFTDoHUsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1661224233538!5m2!1svi!2s"
			contact.setAddress("Thôn Tân Châu, Xã Châu Pha, Thị xã Phú Mỹ, Tỉnh Bà Rịa - Vũng Tàu, Việt Nam");
			contact.setDeleteFlag(0);
			contactRepository.save(contact);

		}
	}

	private void addSlice() {
		List<Slice> list = sliceRepository.findAll();
		if (list == null || (list != null && list.size() == 0)) {
			Slice slice = new Slice();
			slice.setTitle("Tín Phát Lộc");
			slice.setDescription("Xây Vững niềm tin - Dựng uy tín vàng");
			slice.setImage("/images/Slice01.jpg");
			slice.setPosition(1);
			slice.setDeleteFlag(0);
			sliceRepository.save(slice);

			Slice slice1 = new Slice();
			slice1.setTitle("Tín Phát Lộc");
			slice1.setDescription("Xây Vững niềm tin - Dựng uy tín vàng");
			slice1.setImage("/images/Slice02.jpg");
			slice.setPosition(1);
			slice1.setDeleteFlag(0);
			sliceRepository.save(slice1);

			Slice slice2 = new Slice();
			slice2.setTitle("Tín Phát Lộc");
			slice2.setDescription("Xây Vững niềm tin - Dựng uy tín vàng");
			slice2.setImage("/images/Slice03.jpg");
			slice.setPosition(1);
			slice2.setDeleteFlag(0);
			sliceRepository.save(slice2);

			Slice slice3 = new Slice();
			slice3.setTitle("LUÔN CÓ SẴN SỐ LƯỢNG LỚN");
			slice3.setDescription("Cam kết chiết khấu cao nhất");
			slice3.setImage("/images/Slice05.jpg");
			slice.setPosition(2);
			slice3.setDeleteFlag(0);
			sliceRepository.save(slice2);

			Slice slice4 = new Slice();
			slice4.setTitle("TƯ VẤN THI CÔNG MIỄN PHÍ");
			slice4.setDescription("Hỗ trợ gọi xe vận chuyển tận nơi");
			slice4.setImage("/images/Slice06.jpg");
			slice.setPosition(2);
			slice4.setDeleteFlag(0);
			sliceRepository.save(slice4);

		}
	}

	private void addCategory() {
		List<Category> list = categoryRepository.findAll();
		if (list == null || (list != null && list.size() == 0)) {
			Category category = new Category();
			category.setName("Gạch Không Nung");
			category.setDeleteFlag(0);
			categoryRepository.save(category);

			Category category1 = new Category();
			category1.setName("Vật Liệu Xây Dựng");
			category1.setDeleteFlag(0);
			categoryRepository.save(category1);

			Category category2 = new Category();
			category2.setName("Sản Phẩm Khác");
			category2.setDeleteFlag(0);
			categoryRepository.save(category2);
		}

	}

	private void addAdvantage() {
		List<Advantage> list = advantageRepository.findAll();
		if (list == null || (list != null && list.size() == 0)) {
			Advantage advantage = new Advantage();
			advantage.setName("SIÊU CÁCH ÂM");
			advantage.setDescription(
					"Khả năng cách âm từ 40db - 47db, gạch bê tông khí chưng áp cách âm tốt gấp 2 lần gạch xây thông thường.");
			advantage.setDeleteFlag(0);
			advantageRepository.save(advantage);

			Advantage advantage1 = new Advantage();
			advantage1.setName("SIÊU CÁCH NHIỆT");
			advantage1.setDescription(
					"Bằng 1/4 - 1/5 so với gạch nung, 1/6 gạch bê tông thông thường và giảm tới 40% chi phí điện năng tiêu thụ");
			advantage1.setDeleteFlag(0);
			advantageRepository.save(advantage1);

			Advantage advantage2 = new Advantage();
			advantage2.setName("TỶ TRỌNG NHẸ");
			advantage2.setDescription(
					"Tỷ trọng tương đương 1/2 gạch đặc, 2/3 gạch rỗng 2 lỗ giúp giảm kết cấu móng, dầm, cột, giảm chi phí xây thô từ 10 đến 12%.");
			advantage2.setDeleteFlag(0);
			advantageRepository.save(advantage2);

			Advantage advantage3 = new Advantage();
			advantage3.setName("CHỐNG CHÁY HIỆU QUẢ");
			advantage3.setDescription(
					"Tính năng chống cháy  đạt tiêu chuẩn cấp 1  Quốc Gia giúp ngăn ngừa cháy lan và khả năng chống cháy từ 4 - 6 giờ đồng hồ.");
			advantage3.setDeleteFlag(0);
			advantageRepository.save(advantage3);

			Advantage advantage4 = new Advantage();
			advantage4.setName("CHỐNG CHẤN ĐỘNG");
			advantage4.setDescription(
					"Trọng lượng gạch và tấm bê  tông khí thấp nên giảm trọng lực ngôi nhà lên mặt đất. Sản phẩm được ứng dụng phổ biến tại Nhật Bản");
			advantage4.setDeleteFlag(0);
			advantageRepository.save(advantage4);

			Advantage advantage5 = new Advantage();
			advantage5.setName("THI CÔNG NHANH");
			advantage5.setDescription(
					"Dễ dàng khoan, cắt, không cần trát vữa... sản phẩm bê tông khí được đánh giá nhanh hơn nhà truyền thống 50%.");
			advantage5.setDeleteFlag(0);
			advantageRepository.save(advantage5);

			Advantage advantage6 = new Advantage();
			advantage6.setName("THÂN THIỆN MÔI TRƯỜNG");
			advantage6.setDescription(
					"Công nghệ sản xuất không nung đốt, hạn chế tối đa phát sinh khí thải, giảm hiệu ứng nhà kính.");
			advantage6.setDeleteFlag(0);
			advantageRepository.save(advantage6);

			Advantage advantage7 = new Advantage();
			advantage7.setName("KÍCH THƯỚC LINH HOẠT");
			advantage7.setDescription(
					"Linh hoạt về chiều dài và chiều dày của tấm giúp dễ dàng xử lý các kỹ thuật thi công. ");
			advantage7.setDeleteFlag(0);
			advantageRepository.save(advantage7);
		}

	}

	private void addVendor() {
		List<Vendor> list = vendorRepository.findAll();
		if (list == null || (list != null && list.size() == 0)) {
			Vendor vendor = new Vendor();
			vendor.setName("Công Ty Xây Dựng Xuân Thành");
			vendor.setImage("/frontend/img/vendor-1.jpg");
			vendor.setDeleteFlag(0);
			vendorRepository.save(vendor);

			Vendor vendor1 = new Vendor();
			vendor1.setName("Công Ty Xây Dựng Phương Nam");
			vendor1.setImage("/frontend/img/vendor-2.jpg");
			vendor1.setDeleteFlag(0);
			vendorRepository.save(vendor1);

			Vendor vendor2 = new Vendor();
			vendor2.setName("Công Ty Xây Dựng Thành Đạt");
			vendor2.setImage("/frontend/img/vendor-3.jpg");
			vendor2.setDeleteFlag(0);
			vendorRepository.save(vendor2);

			Vendor vendor3 = new Vendor();
			vendor3.setName("Công Ty Xây Dựng Bảo Tín");
			vendor3.setImage("/frontend/img/vendor-4.jpg");
			vendor3.setDeleteFlag(0);
			vendorRepository.save(vendor3);

			Vendor vendor4 = new Vendor();
			vendor4.setName("Công Ty Xây Dựng Xuân Thành");
			vendor4.setImage("/frontend/img/vendor-5.jpg");
			vendor4.setDeleteFlag(0);
			vendorRepository.save(vendor4);

			Vendor vendor5 = new Vendor();
			vendor5.setName("Công Ty Xây Dựng Phương Nam");
			vendor5.setImage("/frontend/img/vendor-6.jpg");
			vendor5.setDeleteFlag(0);
			vendorRepository.save(vendor5);

			Vendor vendor6 = new Vendor();
			vendor6.setName("Công Ty Xây Dựng Thành Đạt");
			vendor6.setImage("/frontend/img/vendor-7.jpg");
			vendor6.setDeleteFlag(0);
			vendorRepository.save(vendor6);

			Vendor vendor7 = new Vendor();
			vendor7.setName("Công Ty Xây Dựng Bảo Tín");
			vendor7.setImage("/frontend/img/vendor-8.jpg");
			vendor7.setDeleteFlag(0);
			vendorRepository.save(vendor7);

		}

	}

	private void addProduct() {
		List<Product> list = productRepository.findAll();
		BigDecimal BIG_5 = new BigDecimal("5000");
		if (list == null || (list != null && list.size() == 0)) {
			Product product = new Product();
			product.setName("Gạch xi măng cốt liệu");
			product.setImage("/uploads/product01.jpg");
			product.setCategoryId(1L);
			product.setProductCode("SKU01");
			product.setAmount(80000);
			product.setPrice(BIG_5);
			product.setNewProduct("1");
			product.setIngredient("Đá xay, bột đá, xi măng, phụ gia");
			product.setStandard("ISO 9001");
			product.setSpecifications("Cườn độ chị nén 55");
			product.setSize("200 x 10 x 30");
			product.setWeight("2.0 kg/viên");
			product.setDescription1("");
			product.setDescription2("");
			product.setDeleteFlag(0);
			productRepository.save(product);

			Product product1 = new Product();
			product1.setName("Gạch babanh");
			product1.setImage("/uploads/product02.jpg");
			product1.setCategoryId(1L);
			product1.setProductCode("SKU02");
			product1.setAmount(80000);
			product1.setPrice(BIG_5);
			product1.setNewProduct("1");
			product1.setIngredient("Đá xay, bột đá, xi măng, phụ gia");
			product1.setStandard("ISO 9001");
			product1.setSpecifications("Cườn độ chị nén 55");
			product1.setSize("200 x 10 x 30");
			product1.setWeight("2.0 kg/viên");
			product1.setDescription1("");
			product1.setDescription2("");
			product1.setDeleteFlag(0);
			productRepository.save(product1);

			Product product2 = new Product();
			product2.setName("Gạch không nung tự nhiên");
			product2.setImage("/uploads/product03.jpg");
			product2.setCategoryId(1L);
			product2.setProductCode("SKU03");
			product2.setAmount(80000);
			product2.setPrice(BIG_5);
			product2.setNewProduct("1");
			product2.setIngredient("Đá xay, bột đá, xi măng, phụ gia");
			product2.setStandard("ISO 9001");
			product2.setSpecifications("Cườn độ chị nén 55");
			product2.setSize("200 x 10 x 30");
			product2.setWeight("2.0 kg/viên");
			product2.setDescription1("");
			product2.setDescription2("");
			product2.setDeleteFlag(0);
			productRepository.save(product2);

			Product product3 = new Product();
			product3.setName("Gạch bê tông nhẹ");
			product3.setImage("/uploads/product04.jpg");
			product3.setCategoryId(1L);
			product3.setProductCode("SKU04");
			product3.setAmount(80000);
			product3.setPrice(BIG_5);
			product3.setNewProduct("1");
			product3.setIngredient("Đá xay, bột đá, xi măng, phụ gia");
			product3.setStandard("ISO 9001");
			product3.setSpecifications("Cườn độ chị nén 55");
			product3.setSize("200 x 10 x 30");
			product3.setWeight("2.0 kg/viên");
			product3.setDescription1("");
			product3.setDescription2("");
			product3.setDeleteFlag(0);
			productRepository.save(product3);

			Product product4 = new Product();
			product4.setName("Gạch không nung rỗng 3 thành vách MT-KM100V3S");
			product4.setImage("/uploads/product05.jpg");
			product4.setCategoryId(2L);
			product4.setProductCode("SKU05");
			product4.setAmount(80000);
			product4.setPrice(BIG_5);
			product4.setNewProduct("1");
			product4.setIngredient("Đá xay, bột đá, xi măng, phụ gia");
			product4.setStandard("ISO 9001");
			product4.setSpecifications("Cườn độ chị nén 55");
			product4.setSize("200 x 95 x 60");
			product4.setWeight("2.0 kg/viên");
			product4.setDescription1("");
			product4.setDescription2("");
			product4.setDeleteFlag(0);
			productRepository.save(product4);

			Product product5 = new Product();
			product5.setName("Gạch đặc không nung MT-KM95DA");
			product5.setImage("/uploads/product06.jpg");
			product5.setCategoryId(2L);
			product5.setProductCode("SKU06");
			product5.setAmount(80000);
			product5.setPrice(BIG_5);
			product5.setNewProduct("1");
			product5.setIngredient("Đá xay, bột đá, xi măng, phụ gia");
			product5.setStandard("ISO 9001");
			product5.setSpecifications("Cườn độ chị nén 55");
			product5.setSize("200 x 95 x 60 mm");
			product5.setWeight("2.0 kg/viên");
			product5.setDescription1("");
			product5.setDescription2("");
			product5.setDeleteFlag(0);
			productRepository.save(product5);

			Product product6 = new Product();
			product6.setName("Gạch không nung tự nhiên");
			product6.setImage("/uploads/product07.jpg");
			product6.setCategoryId(2L);
			product6.setProductCode("SKU07");
			product6.setAmount(80000);
			product6.setPrice(BIG_5);
			product6.setNewProduct("1");
			product6.setIngredient("Đá xay, bột đá, xi măng, phụ gia");
			product6.setStandard("ISO 9001");
			product6.setSpecifications("Cườn độ chị nén 55");
			product6.setSize("200 x 10 x 30");
			product6.setWeight("2.0 kg/viên");
			product6.setDescription1("");
			product6.setDescription2("");
			product6.setDeleteFlag(0);
			productRepository.save(product6);

			Product product7 = new Product();
			product7.setName("Gạch bê tông nhẹ");
			product7.setImage("/uploads/product08.jpg");
			product7.setCategoryId(2L);
			product7.setProductCode("SKU08");
			product7.setAmount(80000);
			product7.setPrice(BIG_5);
			product7.setNewProduct("1");
			product7.setIngredient("Đá xay, bột đá, xi măng, phụ gia");
			product7.setStandard("ISO 9001");
			product7.setSpecifications("Cườn độ chị nén 55");
			product7.setSize("200 x 10 x 30");
			product7.setWeight("2.0 kg/viên");
			product7.setDescription1("");
			product7.setDescription2("");
			product7.setDeleteFlag(0);
			productRepository.save(product7);

		}

	}

}
