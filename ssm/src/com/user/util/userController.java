package com.user.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.user.POJO.POJO.Category;
import com.user.service.userService;

// ����spring mvc����һ����������
@Controller
@RequestMapping("")
public class userController {
	@Autowired
	userService categoryService;

	@RequestMapping("listCategory")
	public ModelAndView listCategory() {
		ModelAndView mav = new ModelAndView();
		List<Category> cs = categoryService.list();

		// ����ת������
		mav.addObject("cs", cs);
		// ����jsp·��
		mav.setViewName("listCategory");
		return mav;
	}
}
