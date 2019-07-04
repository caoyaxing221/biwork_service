package com.biwork.entity;

public class Version {
    private Integer id;

    private String type;

    private String newversion;

    private String minversion;

    private String apkurl;

    private String updatedescription;

    private String size;
    private Integer forceUpdate;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getNewversion() {
        return newversion;
    }

    public void setNewversion(String newversion) {
        this.newversion = newversion == null ? null : newversion.trim();
    }

    public String getMinversion() {
        return minversion;
    }

    public void setMinversion(String minversion) {
        this.minversion = minversion == null ? null : minversion.trim();
    }

    public String getApkurl() {
        return apkurl;
    }

    public void setApkurl(String apkurl) {
        this.apkurl = apkurl == null ? null : apkurl.trim();
    }

    public String getUpdatedescription() {
        return updatedescription;
    }

    public void setUpdatedescription(String updatedescription) {
        this.updatedescription = updatedescription == null ? null : updatedescription.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

	public Integer getForceUpdate() {
		return forceUpdate;
	}

	public void setForceUpdate(Integer forceUpdate) {
		this.forceUpdate = forceUpdate;
	}
}