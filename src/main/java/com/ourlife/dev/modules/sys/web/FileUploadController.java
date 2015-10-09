package com.ourlife.dev.modules.sys.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.modules.sys.entity.SysUploadInfo;
import com.ourlife.dev.modules.sys.service.SysUploadInfoService;
import com.ourlife.dev.modules.sys.utils.AESCipherUtil;

@RequestMapping("/file")
@Controller
public class FileUploadController {

	@Autowired
	private SysUploadInfoService sysUploadInfoService;

	@RequestMapping(value = { "/toUpload" })
	public String toUpload(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return "modules/demo/upload";
	}

	@RequestMapping(value = { "upload" })
	public String upload(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Set<MultipartFile> mfs = sysUploadInfoService.getFileSet(request);
		try {
			for (MultipartFile mf : mfs) {
				Boolean ok = sysUploadInfoService.uploadFileAndCallback(mf,
						jsonMap);
				if (ok) {
					jsonMap.put(SysUploadInfoService.SUCCESS, "true");
					jsonMap.put(SysUploadInfoService.STATUS,
							SysUploadInfoService.SUCCESS);
					// 不考虑多文件
					break;
				}
			}
		} catch (IOException e) {
			jsonMap.put(SysUploadInfoService.STATUS, SysUploadInfoService.ERROR);
			jsonMap.put(SysUploadInfoService.SUCCESS, "false");
			jsonMap.put(SysUploadInfoService.MESSAGE, "文件上传出错,请稍候再试");
			e.printStackTrace();
		}
		model.addAllAttributes(jsonMap);
		// return ajaxJson(response, jsonMap);
		return "modules/demo/uploadSuccess";
	}

	@RequestMapping("down/{idKey}")
	public void download(@PathVariable("idKey") String idKey,
			HttpServletResponse res) throws IOException {
		OutputStream os = res.getOutputStream();
		try {
			res.reset();
			String fid = AESCipherUtil.decrypt(idKey, SysUploadInfoService.KEY);
			Long fileId;
			try {
				fileId = Long.parseLong(fid);
				SysUploadInfo entiti = sysUploadInfoService.get(fileId);
				String fileName = entiti.getFileName();
				fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
				res.setHeader("Content-Disposition", "attachment; filename="
						+ fileName);
				res.setContentType("application/octet-stream; charset=utf-8");
				File file = new File(Global.getConfig("uploadPath")
						+ entiti.getFilePath());
				os.write(FileUtils.readFileToByteArray(file));
				os.flush();
			} catch (NumberFormatException e) {
				System.out.println("fileId Exception..... idKey=" + idKey);
				e.printStackTrace();
			}
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

}
