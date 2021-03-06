/*
 * Copyright(c) 2016 cncounter.com All rights reserved.
 * distributed with this file and available online at
 * http://www.cncounter.com/
 */
package com.cncounter.manage.controller;

import com.cncounter.manage.model.Favorite;
import com.cncounter.manage.mvc.controller.base.ControllerBase;
import com.cncounter.manage.mvc.msg.JSONMessage;
import com.cncounter.manage.service.FavoriteService;
import com.cncounter.util.common.StringNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @author 
 */
@Controller
@RequestMapping("/manage/favorite")
public class FavoriteController extends ControllerBase {
    
    @Autowired
    private FavoriteService favoriteService;
    
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public JSONMessage list(HttpServletRequest request) {
		// get params
		Map<String, Object> params = parseParamMapObject(request);
        processPageParams(params);
		//
		Integer count = favoriteService.countBy(params);
		List<Favorite> favoriteList = favoriteService.listPage(params);
		//
		JSONMessage jsonMessage = JSONMessage.successMessage();
		jsonMessage.setTotal(count);
		jsonMessage.setData(favoriteList);
        //logger.info("Attr:test:" + request.getSession().getAttribute("test"));
        //request.getSession().setAttribute("test", "testXXX");

		return jsonMessage;
	}

	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONMessage doAdd(HttpServletRequest request) {
		// get params
		Map<String, Object> params = parseParamMapObject(request);
		//
		Favorite favorite = new Favorite();
		//
		map2Bean(params, favorite);
		//
		Integer rows = favoriteService.add(favorite);

		//
		JSONMessage jsonMessage = JSONMessage.successMessage();
		if(rows < 1){
			jsonMessage = JSONMessage.failureMessage();
		}
		return jsonMessage;
	}
	

	@RequestMapping(value = "/edit.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONMessage doEdit(HttpServletRequest request) {
		// get params
		Map<String, Object> params = parseParamMapObject(request);
		//
		Favorite favorite = new Favorite();
		//
		map2Bean(params, favorite);
		//
		Integer rows = favoriteService.update(favorite);

		//
		JSONMessage jsonMessage = JSONMessage.successMessage();
		if(rows < 1){
			jsonMessage = JSONMessage.failureMessage();
		}
		return jsonMessage;
	}
	

	@RequestMapping(value = "/delete.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONMessage delete(HttpServletRequest request) {
		// get params
		Map<String, Object> params = parseParamMapObject(request);
		//
		Integer id = 0;
		Object _id = params.get("id");
		if(null != _id && StringNumberUtil.isLong(_id.toString())){
			id = StringNumberUtil.parseInt(_id.toString(), 0);
		}
		//
		Integer rows = favoriteService.delete(id);

		//
		JSONMessage jsonMessage = JSONMessage.successMessage();
		if(rows < 1){
			jsonMessage = JSONMessage.failureMessage();
		}
		return jsonMessage;
	}

}
