package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@PreAuthorize("isAuthenticated()")
@Service
public class TransferSqlDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;

	public TransferSqlDAO(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Transfer createTransfer(Transfer transfer) 
	{
		String transferFunds = "BEGIN TRANSACTION;"
				+ "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (2, 2, (SELECT user_id FROM users WHERE username = ?), (SELECT user_id FROM users WHERE username = ?), ?); "
				+ "UPDATE accounts SET balance = balance - (SELECT amount FROM transfers WHERE transfer_id = ?) WHERE user_id = (SELECT user_id FROM users WHERE username = ?); "
				+ "UPDATE accounts SET balance = balance + (SELECT amount FROM transfers WHERE transfer_id = ?) WHERE user_id = (SELECT user_id FROM users WHERE username = ?); "
				+ "COMMIT";
				
		SqlRowSet result = jdbcTemplate.queryForRowSet(transferFunds);
		
		transfer = mapRowToTransfer(result);
		return transfer;
	}

//	@Override
//	public void transferFunds(Transfer transfer)
//	{
//		String subtract = "UPDATE accounts SET balance = balance - (SELECT amount FROM transfers WHERE transfer_id = ?) "
//				+ "WHERE user_id = (SELECT user_id FROM users WHERE username = ?)";
//		SqlRowSet subtractResult = jdbcTemplate.queryForRowSet(subtract, transfer.getAmount(), transfer.getAccountFrom());
//
//		String add = "UPDATE accounts SET balance = balance + (SELECT amount FROM transfers WHERE transfer_id = ?) "
//				+ "WHERE user_id = (SELECT user_id FROM users WHERE username = ?)";
//		SqlRowSet addResult = jdbcTemplate.queryForRowSet(add, transfer.getAmount(), transfer.getAccountTo());		 
//		
//	}

	@Override
	public Transfer getTransferDetails(int id) {
		Transfer transfer = new Transfer();
		String sql = "SELECT * FROM transfers WHERE transfer_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
		while(result.next()) {
			transfer = mapRowToTransfer(result);
		}
		return transfer;
	}

	@Override
	public void updateTransferRequest(Transfer transfer, int id) {
		String sql = "UPDATE transfers SET transfer_status_id = ? WHERE transfer_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transfer.getTransferStatusId(), id);

	}

	@Override
	public String getTransferStatus(Transfer transfer, int id) {
		String sql = "SELECT transfer_status_desc FROM transfer_statuses WHERE transfer_status_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
		return result.getNString("transfer_status_desc");
	}

	@Override
	public List<Transfer> getAllTransfers(int id) {
		List<Transfer> transfers = new ArrayList<Transfer>();
		String sql = "SELECT * FROM transfers WHERE account_from = ? OR account_to = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id, id);
		while(result.next())
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
