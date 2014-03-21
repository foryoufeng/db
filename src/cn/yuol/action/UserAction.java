package cn.yuol.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.yuol.model.User;

public class UserAction implements ModelDriven<User>,Action{
   private User user;

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}
 public String add(){
	 return "success";
 }

@Override
public User getModel() {
	user=new User();
	ActionContext.getContext().put("user", user);
	return user;
}
}
