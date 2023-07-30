package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.user.model.MUser;
@Mapper
public interface UserMapper {
	/** ユーザー1件表示 */
	public MUser findById(String userID);
	/** ユーザー全件表示 */
	public List<MUser> findAll();
	/** ユーザー登録 */
	public void insertOne(MUser user);
	/** 会員のMAXIDを検索する*/
	public MUser findMaxUser();
	/** 会員を1件削除する */
	public void deleteOne(String id);
	/** 会員を更新する */
	public void updateOne(@Param("userID") String userID, @Param("userName") String userName,
			@Param("userTelno") String userTelno, @Param("userPostcode") String userPostcode,
			@Param("userAddress") String userAddress, @Param("userPassword") String userPassword);
}
