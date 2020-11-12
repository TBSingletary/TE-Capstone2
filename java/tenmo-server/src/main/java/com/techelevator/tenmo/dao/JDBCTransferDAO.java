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
public class JDBCTransferDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCTransferDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<User> getAllUsers() {
		String sql = "SELECT username, user_id FROM users";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
		List<User> users = new ArrayList<>();
		while (rowSet.next()) {
			User user = mapRowToUser(rowSet);
			users.add(user);
		}
		return users;
	}

	@Override
	public void addFunds(BigDecimal amount, int userToId) {
		String sql = "UPDATE accounts SET balance = balance + ? WHERE user_id - ?";
		jdbcTemplate.update(sql, amount, userToId);
	}

	@Override
	public void withdrawFunds(BigDecimal amount, int userFromId) {
		addFunds(amount, userFromId);
	}

	@Override
	public Transfer addTransfer(Transfer transfer, BigDecimal amount, int userToId, int userFromId) {
		String sql = "INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (DEFAULT, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, 2, 2, userFromId, userToId, amount);
		return transfer;
	}

	@Override
	public List<Transfer> getTransfers(int accountId) {
		String sql = "SELECT transfer_id, 'From: ' AS from_or_to, username, amount "
				+ "FROM transfers "
				+ "JOIN accounts ON transfers.account_from = accounts.account_id "
				+ "JOIN users ON accounts.user_id = users.user_id "
				+ "WHERE account_to = ? "
				+ "AND transfer_type_id = 2 "
				+ "UNION ALL "
				+ "SELECT transfer_id, 'To: ' AS from_or_to, username, amount "
				+ "FROM transfers "
				+ "JOIN accounts ON transfers.account_to = accounts.account_id "
				+ "JOIN users ON accounts.user_id = users.user_id "
				+ "WHERE account_from = ? "
				+ "AND transfer_type_id = 2";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
		List<Transfer> transfers = new ArrayList<Transfer>();
		while(result.next()) {
			Transfer transfer = mapRowToTransferTo(result);
			transfers.add(transfer);
		}
		return transfers;
	}

	@Override
	public List<Transfer> getTransferDetailsByTransferId(int transferId) {
		String sql = "SELECT transfers.transfer_id, transfers.account_from, u.username AS user_from, users.username AS user_to, transfer_types.transfer_type_desc, transfer_statuses.transfer_status_desc, transfers.amount "
				+ "FROM transfers "
				+ "JOIN accounts ON transfers.account_to = accounts.account_id "
				+ "JOIN users ON accounts.user_id = uers.user_id "
				+ "JOIN transfer_types ON transfers.transfer_type_id = transfer_types.transfer_type_id "
				+ "JOIN transfer_statuses ON transfers.transfer_status_id = transfer_statuses.transfer_status_id "
				+ "JOIN users AS u ON transfers.account_from = u.user_id "
				+ "WHERE transfers.transfer_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
		List<Transfer> transfers = new ArrayList<Transfer>();
		while (result.next()) {
			Transfer transfer2 = mapRowToTransferFrom(result);
			transfers.add(transfer2);
		}
		return transfers;		
	}

	private Transfer mapRowToTransferFrom(SqlRowSet rowSet) {
		Transfer transfer = new Transfer();
		transfer.setTransferId(rowSet.getInt("transfer_id"));
		transfer.setAccountFrom(rowSet.getInt("account_from"));
		transfer.setUserNameFrom(rowSet.getString("from"));
		transfer.setUserNameTo(rowSet.getString("to"));
		transfer.setTypeDescription(rowSet.getString("transfer_type_desc"));
		transfer.setStatusDescription(rowSet.getString("transfer_status_desc"));
		transfer.setAmount(rowSet.getBigDecimal("amount"));
		return transfer;
	}

	private Transfer mapRowToTransferTo(SqlRowSet rs) {
		Transfer transfer = new Transfer();
		transfer.setTransferId(rs.getInt("transfer_id"));
		transfer.setUsername("username");
		transfer.setFromOrTo(rs.getString("from_or_to"));
		transfer.setAmount(rs.getBigDecimal("amount"));
		return transfer;
	}

	private User mapRowToUser(SqlRowSet rowSet) {
		User user = new User();
		user.setId(rowSet.getInt("user_id"));
		user.setUsername(rowSet.getString("username"));
		return user;
	}

}
