package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Transfer;

@PreAuthorize("isAuthenticated()")
@Service
public class TransferSqlDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;

	public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Transfer createTransfer(Transfer transfer) {
		
		//Transfer transfer = new Transfer();
		
		String transferFunds = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) "
				+ "VALUES (2, 2, (SELECT account_id FROM accounts WHERE user_id = ?), (SELECT account_id FROM accounts WHERE user_id = ?), ?); "
				+ "UPDATE accounts SET balance = balance - ? WHERE account_id = ?; "
				+ "UPDATE accounts SET balance = balance + ? WHERE account_id = ?;";
				

		SqlRowSet result = jdbcTemplate.queryForRowSet(transferFunds, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount(), transfer.getAmount(), transfer.getAccountTo(), transfer.getAmount(), transfer.getAccountFrom());
		
		transfer = mapRowToTransfer(result);
		return transfer;
	}

	@Override
	public Transfer getTransferDetails(int transferId) {
		Transfer transfer = new Transfer();
		String sql = "SELECT * FROM transfers t JOIN accounts a ON t.account_from = a_account_id JOIN users u ON u.user_id = a.user_id WHERE transfer_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
		while(result.next()) {
			transfer = mapRowToTransfer(result);
		}
		return transfer;
	}

	@Override
	public Transfer getTransferFromDetails(int transferId) {
		Transfer transfer = new Transfer();
		String sql = "SELECT * FROM transfers t JOIN accounts a ON t.account_to = a_account_id JOIN users u ON u.user_id = a.user_id WHERE transfer_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
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
		while(result.next()) {
			transfers.add(mapRowToTransfer(result));
		}
		return transfers;
	}

	private Transfer mapRowToTransfer(SqlRowSet rs) {
		Transfer transfer = new Transfer();
		transfer.setTransferId(rs.getInt("transfer_id"));
		transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
		transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
		transfer.setAccountFrom(rs.getInt("account_from"));
		transfer.setAccountTo(rs.getInt("account_to"));
		transfer.setAmount(rs.getBigDecimal("amount"));
		return transfer;
	}


}
