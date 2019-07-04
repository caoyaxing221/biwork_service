package com.biwork.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.biwork.vo.AddressListVo;
import com.biwork.vo.TaskListVo;
import com.biwork.vo.TaskVo;

public interface AirdropTaskService {
	int addTask(String teamId,String userId, String name, String endTime, String title, String remark,String needAttach,String bannerUrl,String type) throws ParseException;

	boolean editTask(String taskId, String userId, String name, String endTime, String title, String remark,String needAttach,
			String bannerUrl) throws ParseException;

	List<TaskListVo> queryTaskList(String teamId, String userId, String type, String fetch, String offset);

	TaskVo queryTaskInfo(String taskId, String userId);

	List<AddressListVo> queryAddressList(String taskId, String userId, String fetch, String offset);

	int addAddress(String taskId, String address,String attachUrl);

	boolean endTask(String taskId, String userId) throws ParseException;

	boolean importAddress(String taskId, MultipartFile file) throws IOException;
}
