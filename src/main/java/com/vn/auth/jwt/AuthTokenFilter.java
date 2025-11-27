package com.vn.auth.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vn.auth.service.UserDetailsServiceImpl;
import com.vn.backend.dto.CategoryDto;
import com.vn.backend.dto.ContactDto;
import com.vn.backend.dto.LogoDto;
import com.vn.backend.dto.MenuDto;
import com.vn.backend.dto.SliceDto;
import com.vn.backend.dto.UserDto;
import com.vn.backend.dto.VendorDto;
import com.vn.backend.model.Menu;
import com.vn.backend.service.CategoryService;
import com.vn.backend.service.ContactService;
import com.vn.backend.service.LogoService;
import com.vn.backend.service.MenuService;
import com.vn.backend.service.SliceService;
import com.vn.backend.service.UserService;
import com.vn.backend.service.VendorService;
import com.vn.utils.Constant;
import com.vn.utils.UserUtils;

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private ContactService contactService;

	@Autowired
	private SliceService sliceService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private LogoService logoService;

	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {

			@SuppressWarnings("unchecked")
			List<String> notes = (List<String>) request.getSession().getAttribute("NOTES_SESSION");
			String jwt = null;
			if (notes != null && notes.size() > 0) {
				jwt = notes.get(0);
			}
			// String getContextPath = request.getContextPath();
			// String getPathInfo = request.getPathInfo();
			// String getRequestURI = request.getRequestURI();
			String getServletPath = request.getServletPath();
			@SuppressWarnings("unchecked")
			List<MenuDto> menus = (List<MenuDto>) request.getSession().getAttribute(Constant.MENU_SESSION);
			if (menus == null) {
				menus = menuService.getListAll(Constant.DELETE_FLAG_ACTIVE);
			}

			if (exitsLinksInMenu(getServletPath, menus)) {
				setMenuSession(request, menuService.getListAll(Constant.DELETE_FLAG_ACTIVE));
				setContactSession(request);
				setSliceSession(request);
				setCategorySession(request);
				setVendorSession(request);
				setSessionLogo(request);
			}

			setSessionUserLogin(request);

			// String jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
		}

		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}

	private String parseJwtInSession(String headerAuth) {

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}

	private boolean exitsLinksInMenu(String link, List<MenuDto> menus) {
		if (menus != null) {
			for (MenuDto dto : menus) {
				if (link.equals(dto.getLink()) || "/".equals(link) || "/home".equals(link)) {
					return true;
				}
			}
		}
		return false;

	}

	public void setMenuSession(HttpServletRequest request, List<MenuDto> sessions) {
		String getServletPath = request.getServletPath();
		List<MenuDto> sessionsDto = new ArrayList<>();
		for (MenuDto dto : sessions) {
			if (getServletPath.equals(dto.getLink())) {
				dto.setActive("active");
			} else {
				dto.setActive("");
			}
			sessionsDto.add(dto);
		}
		request.getSession().setAttribute(Constant.MENU_SESSION, sessionsDto);
	}

	public void setContactSession(HttpServletRequest request) {
		List<ContactDto> sessions = contactService.getListAll(Constant.DELETE_FLAG_ACTIVE);
		if (sessions != null && sessions.size() > 0) {
			request.getSession().setAttribute(Constant.CONTACT_SESSION, sessions.get(0));
		} else {
			request.getSession().setAttribute(Constant.CONTACT_SESSION, new ContactDto());
		}

	}

	public void setSliceSession(HttpServletRequest request) {

		List<SliceDto> sessions = sliceService.getListAll(Constant.DELETE_FLAG_ACTIVE);
		List<SliceDto> sliceLeft = new ArrayList<>();
		List<SliceDto> sliceRightAbove = new ArrayList<>();
		List<SliceDto> sliceRightBelow = new ArrayList<>();
		if (sessions != null && sessions.size() > 0) {

			for (SliceDto sliceDto : sessions) {

				if (sliceDto.getPosition() != null && sliceDto.getPosition() == 1) {
					sliceLeft.add(sliceDto);
				}

				if (sliceDto.getPosition() != null && sliceDto.getPosition() == 2) {
					sliceRightAbove.add(sliceDto);
				}

				if (sliceDto.getPosition() != null && sliceDto.getPosition() == 3) {
					sliceRightBelow.add(sliceDto);
				}
			}

		}

		request.getSession().setAttribute(Constant.SLICE_SESSION, sliceLeft);
		if (sessions != null && sliceRightAbove.size() > 0) {
			request.getSession().setAttribute(Constant.SLICE_SESSION_ABOVE, sliceRightAbove.get(0));
		} else {
			request.getSession().setAttribute(Constant.SLICE_SESSION_ABOVE, new SliceDto());
		}

		if (sessions != null && sliceRightBelow.size() > 0) {
			request.getSession().setAttribute(Constant.SLICE_SESSION_BELOW, sliceRightBelow.get(0));
		} else {
			request.getSession().setAttribute(Constant.SLICE_SESSION_BELOW, new SliceDto());
		}

	}

	public void setCategorySession(HttpServletRequest request) {
		List<CategoryDto> sessions = categoryService.getListAll(Constant.DELETE_FLAG_ACTIVE);
		request.setAttribute("listCategory", sessions);
	}

	public void setVendorSession(HttpServletRequest request) {
		List<VendorDto> sessions = vendorService.getListAll(Constant.DELETE_FLAG_ACTIVE);
		request.getSession().setAttribute(Constant.VENDOR_SESSION, sessions);
	}

	public List<MenuDto> copyMenuToMenuDto(List<Menu> menus, String link) {

		List<MenuDto> sessionsDto = new ArrayList<>();
		for (Menu menu : menus) {
			MenuDto menuDto = new MenuDto();
			BeanUtils.copyProperties(menu, menuDto);
			if (link.equals(menu.getLink())) {
				menuDto.setActive("active");
			} else {
				menuDto.setActive("");
			}
			sessionsDto.add(menuDto);
		}

		return sessionsDto;
	}

	public void setSessionLogo(HttpServletRequest request) {

		List<LogoDto> sessions = logoService.getListAll(Constant.DELETE_FLAG_ACTIVE);
		for (LogoDto logoDto : sessions) {
			if (logoDto.getPosition() == 1) {
				request.getSession().setAttribute(Constant.LOGO_HEADER_SESSION, logoDto);
			}

			if (logoDto.getPosition() == 2) {
				request.getSession().setAttribute(Constant.LOGO_FOOTER_SESSION, logoDto);
			}
		}

	}

	public void setSessionUserLogin(HttpServletRequest request) throws Exception {

		@SuppressWarnings("unchecked")
		UserDto userDto = (UserDto) request.getSession().getAttribute(Constant.USER_LOGIN);
		if (userDto == null) {
			UserDto dto = userService.findByUsername(UserUtils.getUserLogin());
			request.getSession().setAttribute(Constant.USER_LOGIN, dto);
		}

	}

}
