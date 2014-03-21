package cn.yuol.action;

import com.opensymphony.xwork2.ModelDriven;

import cn.yuol.exception.MyException;
import cn.yuol.model.Message;

public class MessageAction implements Action,ModelDriven<Message> {
	private Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String show() {
		return SUCCESS;
	}
	public String add(){
		int i=10;
		if(i==10)
			throw new MyException("出错了");
		return SUCCESS;
	}

	@Override
	public Message getModel() {
		if(message==null){
			message=new Message();
		}
		return message;
	}
}
