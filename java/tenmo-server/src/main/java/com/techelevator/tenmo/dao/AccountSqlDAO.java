package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountSqlDAO implements AccountDAO {

	private JdbcTemplate jdbcTemplate;

	public AccountSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public BigDecimal getAccountBalance(String userName) {
		String sql = "SELECT balance FROM users JOIN accounts ON users.user_id = accounts.user_id "
				+ "WHERE users.username = ?";
		return jdbcTemplate.queryForObject(sql, BigDecimal.class, userName);
	}

	@Override
	public long getAccountNumber(long userId) {
		String sql = "SELECT account_id FROM accounts WHERE user_id = ?";
		return jdbcTemplate.queryForObject(sql, Long.class, userId);
	}

}
