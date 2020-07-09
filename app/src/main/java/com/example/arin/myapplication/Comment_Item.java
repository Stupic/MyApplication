package com.example.arin.myapplication;


import java.io.Serializable;

public class Comment_Item implements Serializable {

	private String phone;
	private String nickname;
	private String id;
	private String fee;  //회비
	private String rotation_date; //매월 회비 내는 날
	private String target_amount; //계타는 금액
	private String goal; //목표 // 1 = 친목 2= 계타기
	public Comment_Item(){
		this.phone = "";
	}

	public Comment_Item(String nickname ,String phone,String id){
		this.nickname = nickname;
		this.phone = phone;
		this.id = id;
	}
	public void setTarget_amount(String target_amount) {this.target_amount =target_amount;}
	public String getTarget_amount(){return target_amount;}
	public void setFee(String fee ) {this.fee= fee; }
	public String getFee(){return fee;}
	public void setRotation_date(String rotation_date ) {this.rotation_date= rotation_date;}
	public String getRotation_date (){return rotation_date;}
	public void setGoal(String goal ) {this.goal= goal; }
	public String getGoal(){return goal;}
	public void setNickname(String nickname){ this.nickname = nickname; }
	public String getNickname(){ return nickname; }
	public void setphone(String phone){ this.phone = phone; }
	public String getphone(){ return phone; }
	public void setId(String id){ this.id = id; }
	public String getId(){ return id; }

}