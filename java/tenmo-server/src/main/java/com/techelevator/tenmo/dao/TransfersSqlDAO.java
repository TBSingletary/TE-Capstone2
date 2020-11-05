package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class TransfersSqlDAO implements TransfersDAO {
	private JdbcTemplate jdbcTemplate;
	
	public TransfersSqlDAO(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void transferFunds()
	{
		
	}
	
	@Override
	public double getBalance(String username)
	{
		double balance = 0.0;
		String sql = "SELECT balance FROM accounts JOIN users on users.user_id = accounts.user_id WHERE users.username = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
		if(result.next())
		{
			balance = result.getDouble("balance");
		}
		return balance;
	}
}
