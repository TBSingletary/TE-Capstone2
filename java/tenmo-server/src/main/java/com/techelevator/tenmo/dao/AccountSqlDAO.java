package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Account;

@Service
public class AccountSqlDAO implements AccountDAO {

	private JdbcTemplate jdbcTemplate;

	public AccountSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Account getAccountByUserId (Long userId) {
		Account account = null;
		String sql = "SELECT account_id, user_id, balance FROM accounts WHERE user_id = ?";
			
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		while(results.next()) {
			account = mapRowToAccount(results);
		}
		return account;
	}
	@Override 
	public void updateBalance(Account account) {
		String sql = "UPDATE accounts SET balance = ? Where account_id = ?";
		jdbcTemplate.update(sql, account.getBalance(), account.getAccountId());
		
	}

	
	private Account mapRowToAccount(SqlRowSet rs) {
	return new Account(rs.getLong("account_id"), rs.getLong("user_id"), rs.getBigDecimal("balance"));
	

	

	}

}
