package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.AccountBalance;

@Component
public class JDBCAccountDAO implements AccountDAO{
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCAccountDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public AccountBalance getBalance(String username) {
		AccountBalance accountBalance = new AccountBalance();
		String sql = "SELECT accounts.user_id, accounts.balance, accounts.account_id FROM accounts JOIN users ON accounts.user_id WHERE users.username = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
		if(result.next()) {
			accountBalance = mapRowToAccountBalance(result);
		}
		return accountBalance;
	}
	
	private AccountBalance mapRowToAccountBalance(SqlRowSet rowSet) {
		AccountBalance accountBalance = new AccountBalance();
		accountBalance.setAccountId(rowSet.getInt("account_id"));
		accountBalance.setUserId(rowSet.getInt("user_id"));
		accountBalance.setBalance(rowSet.getBigDecimal("balance"));
		return accountBalance;
			
	}

}
