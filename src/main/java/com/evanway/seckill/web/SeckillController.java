package com.evanway.seckill.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.evanway.seckill.entity.Seckill;
import com.evanway.seckill.service.SeckillService;

@Controller //或者@Service @Component 目的是为了把controller放入spring容器里面
@RequestMapping("/seckill")
public class SeckillController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	@RequestMapping(name="/list",method=RequestMethod.GET)
	public String list(Model model) {
		//获取列表页
		List<Seckill> list = seckillService.getSeckillList();
		model.addAttribute("list", list);
		//list.jsp + model = ModelAndView
		return "list";//WEB-INF/jsp/'list'.jsp
	}
}
