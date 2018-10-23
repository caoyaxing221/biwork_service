package com.biwork.po.request;

public class AddCoinPojo {
	private String searchMark;
    private String likeQueryName;
    private String contractAddress;
	
    
    public String getSearchMark() {
    	return searchMark;
    }
    public void setSearchMark(String searchMark)
    {
    	this.searchMark = searchMark;
    }   
    
	public String getLikeQueryName() {
		return likeQueryName;
	}
	public void setLikeQueryName(String likeQueryName) {
		this.likeQueryName = likeQueryName;
	}
	
	public String getContractAddress() {
		return contractAddress;
	}
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
}
