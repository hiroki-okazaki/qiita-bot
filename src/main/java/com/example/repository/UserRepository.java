package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

@Repository
public class UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * Userオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setUserId(rs.getString("user_id"));
		user.setRegistrationUrl(rs.getString("registration_url"));

		return user;
	};
	
	/**
	 * ユーザーIDからユーザー情報を取得します.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報 存在しない場合はnullを返します
	 */
	public User findByUserId(String userId) {
		String sql = "select id,user_id,registration_url from users where user_id = :userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}
	
	public void insert(User user) {
		String sql = "INSERT INTO users(user_id, registration_url) VALUES(:userId, :registrationUrl)";
	    SqlParameterSource param = new MapSqlParameterSource().addValue("userId", user.getUserId()).addValue("registrationUrl", user.getRegistrationUrl());
		
		template.update(sql, param);
		System.out.println("777777777777");
	}
	
	public List<User> findAll(){
	    String sql = "select id,user_id,registration_url from users ";
	    List<User> userList = template.query(sql,USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList;
	}
}
