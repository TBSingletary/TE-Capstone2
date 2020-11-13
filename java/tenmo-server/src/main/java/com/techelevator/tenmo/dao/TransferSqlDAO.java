package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Transfer;

@Service
public class TransferSqlDAO implements TransferDAO {

	private AccountDAO accountDAO;
	private JdbcTemplate jdbcTemplate;

	public TransferSqlDAO(JdbcTemplate jdbcTemplate, AccountDAO accountDAO) {
		this.jdbcTemplate = jdbcTemplate;
		this.accountDAO = accountDAO;
	}

	@Override
	public Transfer createTransfer(Transfer transfer) {
		String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) "
				+ "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";
		long fromAcctNumber = accountDAO.getAccountNumber(transfer.getFromUserId());
		long toAcctNumber = accountDAO.getAccountNumber(transfer.getToUserId());
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transfer.getTypeOfTransferId(),
				transfer.getStatusOfTransferId(), fromAcctNumber, toAcctNumber, transfer.getTransferAmount());
		if (results.next()) {
			transfer.setTransferId(results.getLong("transfer_id"));
		}
		return transfer;
	}

	public void subtractFromBalance(BigDecimal amount, long userId) {
		String sql = "UPDATE accounts set balance = ((SELECT balance FROM accounts "
				+ "WHERE user_id = ?) - ?) WHERE user_id = ?";
		jdbcTemplate.update(sql, userId, amount, userId);
	}

	public void addToBalance(BigDecimal amount, long userId) {
		String sql = "UPDATE accounts set balance = ((SELECT balance FROM accounts WHERE user_id = ?) + ?) "
				+ "WHERE user_id = ?";
		jdbcTemplate.update(sql, userId, amount, userId);
	}

	public List<Transfer> getTransferHistory(long userId) {
		List<Transfer> transferList = new ArrayList<>();
		String sql = "SELECT distinct transfers.transfer_id, transfers.account_from as account_from_id, transfers.account_to as account_to_id, a.username as from_user, b.username as to_user, "
				+ "transfer_types.transfer_type_desc as type, transfer_statuses.transfer_status_desc as status, transfers.amount "
				+ " FROM transfers " + " JOIN users a ON transfers.account_from = a.user_id "
				+ " JOIN users b ON transfers.account_to = b.user_id "
				+ " JOIN transfer_statuses ON transfers.transfer_status_id = transfer_statuses.transfer_status_id "
				+ " JOIN transfer_types ON transfers.transfer_type_id = transfer_types.transfer_type_id "
				+ " WHERE a.user_id = ? OR b.user_id = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
		while (results.next()) {
			Transfer newTransfer = mapRowToTransfer(results, userId);
			transferList.add(newTransfer);
		}
		return transferList;
	}

	private Transfer mapRowToTransfer(SqlRowSet results, long userId) {
		Transfer newTransfer = new Transfer();
		newTransfer.setTransferId(results.getLong("transfer_id"));
		newTransfer.setFromUserId(results.getLong("account_from_id"));
		newTransfer.setToUserId(results.getLong("account_to_id"));
		newTransfer.setFromUsername(results.getString("from_user"));
		newTransfer.setToUserName(results.getString("to_user"));
		newTransfer.setTypeOfTransfer(results.getString("type"));
		newTransfer.setStatusOfTransfer(results.getString("status"));
		newTransfer.setTransferAmount(results.getBigDecimal("amount"));
		return newTransfer;
	}
}
