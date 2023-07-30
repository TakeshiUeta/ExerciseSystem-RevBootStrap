package com.example.service;

import java.util.List;

import com.example.domain.user.model.MUser;


public interface UserServise {
	/** ユーザー1件表示 */
	public MUser findUserOne(String userID);

	/** ユーザー全件表示 */
	public List<MUser> findUserAll();

	/** ユーザー登録 */
	public void insertUserOne(MUser user);

	/** 会員のMAXIDを検索する */
	public String findMaxIdUser();

	/** 会員を1件削除する */
	public void deleteUserOne(String userID);

	/** 会員を更新する */
	public void updateUserOne(MUser user);
}
