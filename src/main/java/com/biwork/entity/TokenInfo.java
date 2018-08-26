package com.biwork.entity;

public class TokenInfo {
    private String Decimals;
    private String Name;
    private String Symbol;
    private String TotalSupply;
	
	public String getDecimals() {
		return Decimals;
	}
	public void setDecimals(String decimals) {
		Decimals = decimals;
    }
    
    public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
    }
    
    public String getSymbol() {
		return Symbol;
	}
	public void setSymbol(String symbol) {
		Symbol = symbol;
    }
    
    public String getTotalSupply() {
		return TotalSupply;
	}
	public void setTotalSupply(String totalSupply) {
		TotalSupply = totalSupply;
	}
}