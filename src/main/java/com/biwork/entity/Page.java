package com.biwork.entity;

import java.util.List;

/**
 * @author zhangxiaohui
 * @date  2018年3月14日-下午4:39:38
 * 分页查询显示
 */
public class Page<T> {

	public Page() {
		super();
	}
	
	public Page( int beginPageNo,List<T> lists) {
		super();
		this.beginPageNo = beginPageNo;
		this.lists = lists;
	}

	private int count = 10;//每页显示10条记录
	private int  beginPageNo;
	private int beginPageCount ;
	private int endPageCount ;
	private List<T> lists;
	
	
	public int getBeginPageNo() {
		return beginPageNo;
	}
	public void setBeginPageNo(int beginPageNo) {
		this.beginPageNo = beginPageNo;
	}
	
	public int getBeginPageCount(){
		this.beginPageCount = count*beginPageNo;;
		return beginPageCount;
	}
	public int getEndPageCount(){
		this.endPageCount =count*beginPageNo+count-1;
		return endPageCount;
	}
	public List<T> getLists() {
		return lists;
	}
	public void setLists(List<T> lists) {
		this.lists = lists;
	}
	@Override
	public String toString() {
		return "Page [beginPageCount=" + beginPageCount + ", endPageCount=" + endPageCount + ", lists=" + lists.toString() + "]";
	}
	
	
}
