package com.spring.sample.web.test1.Controller;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.sample.web.test1.Service.iTestService;

@Controller
public class TestController {

	@Autowired
	private iTestService iTestDao;
	
	@RequestMapping(value = "/test")
	public ModelAndView portList(ModelAndView mav) throws ServletException {
		
		int test = iTestDao.test();
		
		mav.addObject("test", test);
		mav.setViewName("test");
		return mav;
	}
}
