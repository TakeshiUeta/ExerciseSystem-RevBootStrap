package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.domain.user.model.MUser;
import com.example.form.User;
import com.example.service.impl.UserServiceImpl;

@Controller
public class UserController {
	@Autowired
	private UserServiceImpl userServise;
	@Autowired
	private ModelMapper modelMapper;

	// ユーザー画面に遷移する
	@GetMapping("/User")
	public String getMapUser(Model model, @ModelAttribute User user) {
		return "User";
	}

	// ユーザー1件表示画面に遷移する
	@PostMapping("/test/db")
	public String postMapUser(Model model, @ModelAttribute User user) {
		MUser mUser = new MUser();	
		try {
			String userID = user.getUserID();
			String password = user.getUserPassword();
			// DB側のユーザー情報を取得する
			mUser = userServise.findUserOne(userID);
			// DB側のパスワードを取得する
			String userPassword = mUser.getUserPassword();
			// IDとパスワードに相違がある場合メッセージを表示させてユーザー画面に遷移する
			if (!password.equals(userPassword)) {
				String msg = "会員IDまたはパスワードが違います";
				model.addAttribute("msg", msg);
				return "User";
			}
		} catch (Exception e) {
			// TODO: handle exception
			String msg = "会員IDまたはパスワードが違います";
			model.addAttribute("msg", msg);
			return "User";
		}
		user = modelMapper.map(mUser, User.class);
		model.addAttribute("user", user);
		return "test/db";
	}

	// ユーザー一覧画面に遷移する
	@GetMapping("/test/UserList")
	public String getMapUserList(Model model) {
		List<MUser> mUserList = userServise.findUserAll();
		List<User> userList =new ArrayList<>();
		for(MUser mUser: mUserList) {
			User user = modelMapper.map(mUser, User.class);
			userList.add(user);
		}
		model.addAttribute("userList", userList);
		return "test/UserList";
	}

	// ユーザー登録画面に遷移する
	@PostMapping("/test/UserUpDate")
	public String postMapUserUpDate(Model model, @ModelAttribute User user) {
		return "test/UserUpDate";
	}

	// ユーザーを登録する
	@GetMapping("/test/UserUpDate")
	public String getMapUserUpDate(Model model, @ModelAttribute User user, BindingResult bindingResult) {
		String name = user.getUserName();
		String telNo = user.getUserTelno();
		String postcode = user.getUserPostcode();
		String adress = user.getUserAddress();
		String password = user.getUserPassword();
		if (bindingResult.hasErrors()) {
			// ユーザー登録画面に戻ります
			return "test/UserUpDate";
		}

		String userId = userServise.findMaxIdUser();
		user = new User(userId, name, telNo, postcode, adress, password);
		MUser mUser = modelMapper.map(user, MUser.class);
		userServise.insertUserOne(mUser);
		model.addAttribute("user",user);
		return "test/UserUpDate";
	}

	// ユーザー削除画面に遷移する
	@PostMapping("/test/UserDelete")
	public String postMapDeleteUser(Model model, @ModelAttribute User user) {
		return "test/UserDelete";
	}

	// ユーザーを削除する
	@GetMapping("/test/UserDelete")
	public String getMapDeleteUser(Model model, @ModelAttribute User user) {
		String userID = "";
		MUser mUser = new MUser();
		try {
			// 入力されたIDをゲットする
			userID = user.getUserID();
			// 入力されたパスワードをゲットする
			String password = user.getUserPassword();
			// 検索
			mUser = userServise.findUserOne(userID);
			// DBに格納されたパスワードをゲットする
			String userPassword = mUser.getUserPassword();
			// 入力されたパスワードと相違ないか確認
			if (!password.equals(userPassword)) {
				String msg = "会員IDまたはパスワードが違います";
				model.addAttribute("msg", msg);
				return "test/UserDelete";
			}
		} catch (Exception e) {
			// TODO: handle exception
			String msg = "会員IDまたはパスワードが違います";
			model.addAttribute("msg", msg);
			return "test/UserDelete";
		}
		// ユーザーを削除する
		userServise.deleteUserOne(userID);
		//MUserをUserに変換
		user = modelMapper.map(mUser, User.class);
		String msg = "以下のユーザーを削除しました。";
		model.addAttribute("msg2", msg);
		model.addAttribute("user", user);
		return "test/UserDelete";
	}

	@PostMapping("/test/UserChange")
	public String postUserChange(Model model, @ModelAttribute User user) {
		MUser mUser = new MUser();
		try {
			String userID = user.getUserID();
			String password = user.getUserPassword();
			mUser = userServise.findUserOne(userID);

			String userPassword = mUser.getUserPassword();
			// 入力されたIDとパスワードが相違する場合はユーザー画面に遷移する
			if (!password.equals(userPassword)) {
				String msg = "会員IDまたはパスワードが違います";
				model.addAttribute("msg", msg);
				return "User";
			}
		} catch (Exception e) {
			// TODO: handle exception
			String msg = "会員IDまたはパスワードが違います";
			model.addAttribute("msg", msg);
			return "User";
		}
		//MUserをUserに変換
		user = modelMapper.map(mUser, User.class);
		model.addAttribute("user", user);
		return "test/UserChange";
	}

	@GetMapping("/test/UserChange")
	public String gettUserChange(Model model, @ModelAttribute @Validated User user, BindingResult bindingResult) {
		// 入力チェック
		if (bindingResult.hasErrors()) {
			// NG:ユーザー更新画面に戻ります
			model.addAttribute("user", user);
			return "test/UserChange";
		}
		//UserをMUserに変換する
		MUser mUser = modelMapper.map(user, MUser.class);
		//userIdを取得する
		String userId = mUser.getUserID();
		// ユーザーを更新する
		userServise.updateUserOne(mUser);
		// 更新されたユーザーをゲットする
		mUser = userServise.findUserOne(userId);
		//更新されたMUserをUserに変換する
		user = modelMapper.map(mUser, User.class);
		// メッセージ作成
		String msg2 = "以下のように変更されました";
		// 更新情報とメッセージをmodelに入れる
		model.addAttribute("user", user);
		model.addAttribute("msg2", msg2);
		return "test/UserChange";
	}
}
