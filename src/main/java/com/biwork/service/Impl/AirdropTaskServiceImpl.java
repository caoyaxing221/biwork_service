package com.biwork.service.Impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.biwork.entity.AirdropAddress;
import com.biwork.entity.AirdropTask;
import com.biwork.exception.BusiException;
import com.biwork.mapper.AirdropAddressMapper;
import com.biwork.mapper.AirdropTaskMapper;
import com.biwork.mapper.ServiceMapper;
import com.biwork.mapper.TeamMapper;
import com.biwork.service.AirdropTaskService;
import com.biwork.util.Constants;
import com.biwork.util.TimeUtils;
import com.biwork.vo.AddressListVo;
import com.biwork.vo.TaskListVo;
import com.biwork.vo.TaskVo;
import com.biwork.vo.TeamVo;
import com.sargeraswang.util.ExcelUtil.ExcelLogs;
import com.sargeraswang.util.ExcelUtil.ExcelUtil;



@Service("AirdropTaskService")
public class AirdropTaskServiceImpl implements AirdropTaskService {
	static Logger log = LoggerFactory.getLogger(AirdropTaskService.class);
	@Autowired
	private TeamMapper teamMapper;
	@Autowired
	private AirdropTaskMapper airdropTaskMapper;
	@Autowired
	private AirdropAddressMapper airdropAddressMapper;
	@Autowired
	private ServiceMapper serviceMapper;
	@Override
	public int addTask(String teamId,String userId, String name, String endTime, String title, String remark,String needAttach,String bannerUrl,String type) throws ParseException {
		com.biwork.entity.Service service=serviceMapper.selectByUserId(Integer.parseInt(userId));
        if(service.getExpireDate().compareTo(new Date())<0){
        	throw new BusiException(Constants.SERVICE_TIMOUT_CODE,Constants.SERVICE_TIMOUT_MESSAGE);
        }
     
		AirdropTask taskDb = airdropTaskMapper.selectByName(name, Integer.parseInt(teamId));
		if(null!=taskDb){
			throw new BusiException(Constants.FAIL_CODE,Constants.AIRDROP_ALREADY_EXISTS);
		}
		AirdropTask task=new AirdropTask();
		task.setEndtime(null==endTime?null:TimeUtils.getDateTime(endTime));
		task.setName(name);
		task.setBannerUrl(bannerUrl);
		task.setRemark(remark);
		task.setTeamId(teamId);
		task.setTitle(title);
		task.setCreateUserid(Integer.parseInt(userId));
		task.setType(Integer.parseInt(type));
		task.setNeedAttach(Integer.parseInt(needAttach));
		airdropTaskMapper.insertSelective(task);
		int taskId=task.getId();
		
		
		return taskId;
	}
	@Override
	public boolean editTask(String taskId,String userId, String name, String endTime, String title, String remark,String needAttach,String bannerUrl) throws ParseException {
		com.biwork.entity.Service service=serviceMapper.selectByUserId(Integer.parseInt(userId));
        if(service.getExpireDate().compareTo(new Date())<0){
        	throw new BusiException(Constants.SERVICE_TIMOUT_CODE,Constants.SERVICE_TIMOUT_MESSAGE);
        }
		AirdropTask taskDb = airdropTaskMapper.selectByPrimaryKey(Integer.parseInt(taskId));
		if(null==taskDb){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		if(!taskDb.getCreateUserid().toString().equals(userId)||taskDb.getType()==1){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		AirdropTask task=new AirdropTask();
		task.setId(taskDb.getId());
		task.setEndtime(TimeUtils.getDateTime(endTime));
		task.setName(name);
		task.setBannerUrl(bannerUrl);
		task.setRemark(remark);
		task.setNeedAttach(Integer.parseInt(needAttach));
		task.setTitle(title);
		task.setUpdatetime(new Date());
		airdropTaskMapper.updateByPrimaryKeySelective(task);
		return true;
	}
	@Override
	public List<TaskListVo> queryTaskList(String teamId,String userId,String type,String fetch,String offset) {
		List<TaskListVo> taskList=airdropTaskMapper.selectByTeamId(Integer.parseInt(teamId),Integer.parseInt(userId)
				,Integer.parseInt(fetch),Integer.parseInt(offset),(null==type||"".equals(type))?null:Integer.parseInt(type));
		return taskList;
	}
	@Override
	public TaskVo queryTaskInfo(String taskId,String userId) {
//		TaskVo task=airdropTaskMapper.selectByTaskId(Integer.parseInt(taskId),Integer.parseInt(userId));
		TaskVo task=airdropTaskMapper.selectByTaskId(Integer.parseInt(taskId),null);
		return task;
	}
	@Override
	public List<AddressListVo>  queryAddressList(String taskId,String userId,String fetch,String offset) {
		TaskVo task=airdropTaskMapper.selectByTaskId(Integer.parseInt(taskId),Integer.parseInt(userId));
		if(null==task){
			throw new BusiException(Constants.FAIL_CODE,Constants.TEAM_NOT_FOUND);
		}
		List<AddressListVo> addressList=airdropAddressMapper.selectByTaskId(Integer.parseInt(taskId),Integer.parseInt(fetch),Integer.parseInt(offset));
		return addressList;
	}
	@Override
	public int addAddress(String taskId,String address,String attachUrl) {
		TaskVo task=airdropTaskMapper.selectByTaskId(Integer.parseInt(taskId),null);
		if(null==task){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		try {
			if(!"0".equals(task.getState())||TimeUtils.getDateTime(task.getEndTime()).compareTo(new Date())<=0){
				throw new BusiException(Constants.FAIL_CODE,Constants.AIRDROP_EXPIRES);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AirdropAddress addrDb = airdropAddressMapper.selectByAddress(Integer.parseInt(taskId), address);
		if(null!=addrDb){
			throw new BusiException(Constants.FAIL_CODE,Constants.ADDRESS_ALREADY_EXISTS);
		}
		AirdropAddress addr=new AirdropAddress();
		addr.setAddress(address);
		addr.setTaskid(Integer.parseInt(taskId));
		addr.setAttachUrl(attachUrl);
		airdropAddressMapper.insertSelective(addr);
		return addr.getId();
		 
	}
	@Override
	public boolean endTask(String taskId,String userId) throws ParseException {
		TaskVo taskdb=airdropTaskMapper.selectByTaskId(Integer.parseInt(taskId),Integer.parseInt(userId));
		if(null==taskdb){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		if(TimeUtils.getDateTime(taskdb.getEndTime()).compareTo(new Date())<0
				||!taskdb.getState().equals("0")){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		AirdropTask task=new AirdropTask();
		task.setId(Integer.parseInt(taskdb.getId()));
		task.setState(1);
		task.setUpdatetime(new Date());
		airdropTaskMapper.updateByPrimaryKeySelective(task);
		
		return true;
	}
	@Override
	public boolean importAddress(String taskId, MultipartFile file) throws IOException {
		TaskVo taskdb=airdropTaskMapper.selectByTaskId(Integer.parseInt(taskId),null);
		if(null==taskdb){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		if(Integer.parseInt(taskdb.getType())!=1){
			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
		}
		//从excel中读出地址入库
	    InputStream inputStream= file.getInputStream();
	     ExcelLogs logs =new ExcelLogs();
	    Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, "yyyy/MM/dd HH:mm:ss", logs , 0);
	     for(Map m : importExcel){
    	 if(null==m.get("address")){
 			throw new BusiException(Constants.FAIL_CODE,Constants.RECORDS_NOT_FOUND);
 		}
	    String address=m.get("address").toString();
	     // log.info("{}",m);
	  	AirdropAddress addr=new AirdropAddress();
		addr.setAddress(address);
		addr.setTaskid(Integer.parseInt(taskId));
		airdropAddressMapper.insertSelective(addr);
	    }
		return true;
	}
	
	
	
	


}
