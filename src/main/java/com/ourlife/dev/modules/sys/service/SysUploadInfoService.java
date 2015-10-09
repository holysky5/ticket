package com.ourlife.dev.modules.sys.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.google.common.collect.Lists;
import com.ourlife.dev.common.config.Global;
import com.ourlife.dev.common.service.BaseService;
import com.ourlife.dev.modules.sys.dao.SysUploadInfoDao;
import com.ourlife.dev.modules.sys.entity.SysUploadInfo;
import com.ourlife.dev.modules.sys.utils.AESCipherUtil;

/**
 * 文件上传信息Service
 * 
 */
@Service
@Transactional(readOnly = true)
public class SysUploadInfoService extends BaseService {

	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String STATUS = "status";
	public static final String MESSAGE = "message";

	private static final String UPLOAD_SERVICE = Global.getConfig("uploadPath");
	private static final String UPLOAD_ROOT = Global.getConfig("uploadDir");
	private static final Long MAX_FILE_SIZE = 1024 * 1024 * (Long
			.valueOf(Global.getConfig("upload_file_max_size")));// 5M

	private static final String PIC_EXT = Global
			.getConfig("upload_file_suffix");

	public static final String KEY = "oa";

	@Autowired
	private SysUploadInfoDao sysUploadInfoDao;

	public SysUploadInfo get(Long id) {
		return sysUploadInfoDao.findOne(id);
	}

	public List<SysUploadInfo> getList(String fileIds) {
		List<SysUploadInfo> list = Lists.newArrayList();
		if (fileIds.length() > 0) {
			String[] ids = fileIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				list.add(this.get(Long.valueOf(ids[i])));
			}
		}
		return list;
	}

	public SysUploadInfo get(String idKey) {
		String fid = AESCipherUtil.decrypt(idKey, SysUploadInfoService.KEY);
		Long id = Long.parseLong(fid);
		return sysUploadInfoDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public void save(SysUploadInfo area) {
		sysUploadInfoDao.clear();
		sysUploadInfoDao.save(area);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		sysUploadInfoDao.deleteById(id);
	}

	public Set<MultipartFile> getFileSet(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Set<MultipartFile> fileset = new LinkedHashSet<MultipartFile>();
		for (Iterator<String> it = multipartRequest.getFileNames(); it
				.hasNext();) {
			String key = it.next();
			MultipartFile file = multipartRequest.getFile(key);
			if (file.getOriginalFilename().length() > 0) {
				fileset.add(file);
			}
		}
		return fileset;
	}

	public boolean uploadFileAndCallback(MultipartFile file,
			Map<String, Object> jsonMap) throws IOException {
		if (validateFile(file, jsonMap)) {
			String filename = file.getOriginalFilename();
			String extName = filename.substring(filename.lastIndexOf("."))
					.toLowerCase();
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String dirName = format.format(date);
			String lastFileName = System.currentTimeMillis() + "-"
					+ filename.hashCode() + extName;
			// 图片存储的相对路径
			String fileFullPath = UPLOAD_SERVICE + UPLOAD_ROOT + "/" + dirName;
			File dir = new File(fileFullPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileCopyUtils.copy(file.getBytes(), new File(fileFullPath + "/"
					+ lastFileName));

			jsonMap.put("link", UPLOAD_ROOT + "/" + dirName + "/"
					+ lastFileName);
			jsonMap.put("path", UPLOAD_ROOT + "/" + dirName + "/"
					+ lastFileName);

			SysUploadInfo upinfo = new SysUploadInfo();
			if (filename.indexOf("/") == -1 && filename.indexOf("\\") == -1) {// 可能是火狐等浏览器
				upinfo.setFileName(filename);
			} else {
				String[] nameArry;
				if (filename.indexOf("/") != -1) {
					nameArry = filename.split("/");
				} else {
					nameArry = filename.split("\\");
				}
				upinfo.setFileName(nameArry[nameArry.length - 1]);
			}
			upinfo.setFileType(extName.substring(1));
			upinfo.setFilePath(UPLOAD_ROOT + "/" + dirName + "/" + lastFileName);
			this.save(upinfo);

			jsonMap.put("fileId",
					AESCipherUtil.encrypt(upinfo.getId() + "", KEY));
			jsonMap.put("fid", upinfo.getId());
			jsonMap.put("fileInfo", upinfo);
			return true;
		} else {
			jsonMap.put(STATUS, ERROR);
			jsonMap.put(SUCCESS, "false");
			return false;
		}

	}

	private boolean validateFile(MultipartFile file, Map<String, Object> jsonMap) {
		if (file.getSize() < 0 || file.getOriginalFilename() == "") {
			jsonMap.put(MESSAGE, "未找到文件");
			return false;
		}
		if (file.getSize() > MAX_FILE_SIZE) {
			jsonMap.put(MESSAGE, "文件超过大小限制");
			return false;
		}
		String filename = file.getOriginalFilename();
		String extName = filename.substring(filename.lastIndexOf("."))
				.toLowerCase();
		for (String ext : PIC_EXT.split(",")) {
			if (extName.equals(ext)) {
				return true;
			}
		}
		jsonMap.put(MESSAGE, "上传文件类型不符合规格");
		return false;

	}

	public List<SysUploadInfo> upload(
			DefaultMultipartHttpServletRequest request, String fileName)
			throws IOException {
		List<SysUploadInfo> fileList = Lists.newArrayList();
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		List<MultipartFile> mfs = request.getFiles(fileName);
		for (MultipartFile mf : mfs) {
			Boolean ok = this.uploadFileAndCallback(mf, jsonMap);
			if (ok) {
				fileList.add((SysUploadInfo) jsonMap.get("fileInfo"));
			} else {
				throw new IOException(jsonMap.get(MESSAGE).toString());
			}
		}
		return fileList;
	}
}
