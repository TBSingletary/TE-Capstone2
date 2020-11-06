package com.techelevator.tenmo.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Transfer;

@Service
public class TransferSqlDAO implements TransferDAO {
	
	private JdbcTemplate jdbcTemplate;

	public TransferSqlDAO(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void createTransfer(Transfer transfer) {
		String accountLog = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) "
				+ "VALUES (3, 3, (SELECT user_id FROM users WHERE username = ?), (SELECT user_id FROM users WHERE username = ?), ?)";
		SqlRowSet result = jdbcTemplate.queryForRowSet(accountLog, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount()); 
	}
	
	@Override
	public void transferFunds(Transfer transfer)
	{
		String subtract = "UPDATE accounts SET balance = (balance - ?) "
				+ "WHERE user_id = (SELECT user_id FROM users WHERE username = ?)";
		SqlRowSet subtractResult = jdbcTemplate.queryForRowSet(subtract, transfer.getAmount(), transfer.getAccountFrom());
		
		String add = "UPDATE accounts SET balance = (balance + ?) "
				+ "WHERE user_id = (SELECT user_id FROM users WHERE username = ?)";
		SqlRowSet addResult = jdbcTemplate.queryForRowSet(subtract, transfer.getAmount(), transfer.getAccountTo());
	}

	@Override
	public double getUserBalance(String username)
	{
		double balance = 0;
		String sql = "SELECT balance FROM accounts JOIN users on users.user_id = accounts.user_id WHERE users.username = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
		if(result.next())
		{
			balance = result.getDouble("balance");
		}
		return balance;
	}

	@Override
	public String getTransferDetails(int id) {
		String sql = "SELECT * FROM transfers WHERE transfer_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
		
		return result.toString();
	}
	
	@Override
	public void updateTransferRequest(Transfer transfer, int id) {
		String sql = "UPDATE transfers SET transfer_status_id = ? WHERE transfer_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transfer.getTransferStatusId(), id);
		
	}

	@Override
	public String getTransferStatus(Transfer transfer) {
		String sql = "SELECT transfer_status_desc FROM transfer_statuses WHERE transfer_status_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		return result.toString();
	}

	@Override
	public List<Transfer> getAllTransfers(Transfer transfer) {
		List<Transfer> transfers = null;
		String sql = "SELECT * FROM tranfers";
				//+ "AND (account_to = (SELECT user_id FROM user WHERE username = ?) OR account_from = (SELECT user_id FROM user WHERE username = ?))";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		if(result.next())
		{
			transfers.add(mapRowToTransfer(result));
		}
		return transfers;
	}
	
	private Transfer mapRowToTransfer(SqlRowSet rs)
	{
		Transfer transfer = new Transfer();
		transfer.setTransferId(rs.getLong("transfer_id"));
		transfer.setTransferTypeId(rs.getLong("transfer_type_id"));
		transfer.setTransferStatusId(rs.getLong("transfer_status_id"));
		transfer.setAccountFrom(rs.getLong("account_from"));
		transfer.setAccountTo(rs.getLong("account_to"));
		transfer.setAmount(rs.getDouble("amount"));
		return transfer;
	}
}
