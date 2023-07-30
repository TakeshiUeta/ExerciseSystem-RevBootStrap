package com.example.domain.user.model;

import lombok.Data;

@Data
public class MUser {
	/** 会員ID */
	private String userID;

	/** 会員名 */
	private String userName;

	/** 電話番号 */
	private String userTelno;

	/** 郵便番号 */
	private String userPostcode;

	/** 住所 */
	private String userAddress;

	/** パスワード */
	private String userPassword;

}
