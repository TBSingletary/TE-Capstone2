package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;

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
	public double getBalance()
	{
		double balance = 0.0;
		return balance;
	}
}
