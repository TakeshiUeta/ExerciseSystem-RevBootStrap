package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.user.model.MUser;
import com.example.repository.UserMapper;
import com.example.service.UserServise;
@Service
public class UserServiceImpl implements UserServise{
	@Autowired
	private UserMapper userMapper;
	/**ユーザー一件表示*/
	@Override
	public MUser findUserOne(String userID){
		MUser user= userMapper.findById(userID);
		return user;
	}
	/** ユーザー全件表示 */
	@Override
	public List<MUser> findUserAll(){
		List<MUser> userList = userMapper.findAll();
		return userList;
	}
	/**MaxId取得*/
	@Override
	public String findMaxIdUser() {
		//テーブルの最後のレコードを取得する
		MUser user = userMapper.findMaxUser();
		//idを取得する
		String id = user.getUserID();
		//アルファベット部分を切り離す
		String start = id.substring(0,2);
		//数字部分を切り離す
		String end = id.substring(2,6);
		//数字を文字型から数値にキャストして1加える
		int value = Integer.parseInt(end) + 1;
		//空の文字列を作成
		String maxValue = "";
		if(value >= 10) {
			//最大値が10以上だった場合
			maxValue = "00" + Integer.toString(value); 
		}else if(value >= 100) {
			//最大値が100以上だった場合
			maxValue = "0" + Integer.toString(value); 
		}else if(value >= 1000) {
			//最大値が1000以上だった場合
			maxValue = Integer.toString(value); 
		}else {
			//最大値が10未満だった場合
			maxValue = "000" + Integer.toString(value); 
		}
		//切り離した2文字と最大値を文字結合してIDを作る
		String usermaxId = start + maxValue;
		return usermaxId;
	}
	
	/** ユーザー登録 */
	@Override
	public void insertUserOne(MUser user) {
		userMapper.insertOne(user);
	}
	
	/** 会員を更新する */
	@Override
	public void updateUserOne(MUser user) {
		String userID = user.getUserID();
		String name = user.getUserName();
		String telNo = user.getUserTelno();
		String postcode = user.getUserPostcode();
		String address = user.getUserAddress();
		String password = user.getUserPassword();
		userMapper.updateOne(userID, name, telNo, postcode, address, password);
	}

	/**会員を削除する*/
	@Override
	public void deleteUserOne(String id) {
		userMapper.deleteOne(id);
	}

}
