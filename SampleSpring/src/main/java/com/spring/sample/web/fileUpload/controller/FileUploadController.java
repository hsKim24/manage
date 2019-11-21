package com.spring.sample.web.fileUpload.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.sample.common.CommonProperties;
import com.spring.sample.util.Utils;

@Controller
public class FileUploadController {
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	@RequestMapping(value = "/fileUpload", method = RequestMethod.GET)
	public ModelAndView fileUpload(HttpServletRequest request, HttpSession session, ModelAndView modelAndView) {

		modelAndView.setViewName("fileUpload/fileUpload");

		return modelAndView;
	}

	@RequestMapping(value = "/fileUploadAjax", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String fileUploadAjax(HttpServletRequest request, ModelAndView modelAndView) throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		/* File Upload Logic */
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		String uploadExts = CommonProperties.FILE_EXT;
		String uploadPath = CommonProperties.FILE_UPLOAD_PATH;
		String fileFullName = "";

		List<String> fileNames = new ArrayList<String>();
		try {
			@SuppressWarnings("rawtypes")
			final Map files = multipartRequest.getFileMap();
			Iterator<String> iterator = multipartRequest.getFileNames();

			while (iterator.hasNext()) {
				String key = iterator.next();
				MultipartFile file = (MultipartFile) files.get(key);
				if (file.getSize() > 0) {
					String fileRealName = file.getOriginalFilename(); // 실제파일명
					String fileTmpName = Utils.getPrimaryKey(); // 고유 날짜키 받기
					String fileExt = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase(); // 파일
					// 확장자추출

					if (uploadExts.toLowerCase().indexOf(fileExt) < 0) {
						throw new Exception("Not allowded file extension : " + fileExt.toLowerCase());
					} else {
						// 물리적으로 저장되는 파일명(실제파일명을 그대로 저장할지 rename해서 저장할지는 협의 필요)
						fileFullName = fileTmpName + fileRealName;
						file.transferTo(new File(new File(uploadPath), fileFullName));

						fileNames.add(fileFullName);
					}
				}
			}

			modelMap.put("result", CommonProperties.RESULT_SUCCESS);
		} catch (Exception e) {
			// 공통 Exception 처리
			e.printStackTrace();
			modelMap.put("result", CommonProperties.RESULT_ERROR);
		}

		modelMap.put("fileName", fileNames);

		return mapper.writeValueAsString(modelMap);
	}
}
