package cn.yuol.model;

import java.util.Date;

public class Information implements Model{
    private int info_id;
    private String tags;
    private double price;
    private String name;
    private String phone;
    private String qq;
    private String img;
    private String content;
    private Date post_time;
    private int user_id;
    
	public Information(int info_id, String tags, double price, String name,
			String phone, String qq, String img, String content, Date post_time,
			int user_id) {
		this.info_id = info_id;
		this.tags = tags;
		this.price = price;
		this.name = name;
		this.phone = phone;
		this.qq = qq;
		this.img = img;
		this.content = content;
		this.post_time = post_time;
		this.user_id = user_id;
	}

	public Information() {
	}

	public int getInfo_id() {
		return info_id;
	}

	public void setInfo_id(int id) {
		this.info_id = id;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPost_time() {
		return post_time;
	}

	public void setPost_time(Date post_time) {
		this.post_time = post_time;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "Information [id=" + info_id + ", tags=" + tags + ", price=" + price
				+ ", name=" + name + ", phone=" + phone + ", qq=" + qq
				+ ", img=" + img + ", content=" + content + ", post_time="
				+ post_time + ", user_id=" + user_id + "]";
	}
}
