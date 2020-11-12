package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Transfer;

@Service
public class TransferSqlDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;

	public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private BigDecimal BigDecimal(String sqlBalanceString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transfer initiateTransfer(Transfer transfer) {
		String sqlBalanceString = "SELECT balance FROM accounts WHERE account_id = ?;";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sqlBalanceString, transfer.getAccount_from());
		BigDecimal amountToTransfer = transfer.getAmount();
		BigDecimal stringInt = null;
		if (rs.next()) {
			stringInt = new BigDecimal(rs.getString("balance"));
		}
		if (amountToTransfer.compareTo(stringInt) == -1) {
			// initiateTransfer(transfer);
			// }

			String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from,account_to, amount)"
					+ "VALUES (2,1,?,?,?) RETURNING transfer_id;";
			SqlRowSet rs1 = jdbcTemplate.queryForRowSet(sql, transfer.getAccount_from(), transfer.getAccount_to(),
					transfer.getAmount());
			if (rs1.next()) {
				transfer.setTransfer_id(rs1.getInt("transfer_id"));
			}
			return transfer;
		} else {
			System.out.println("Not enough funds. Rejected : " + transfer.getTransfer_status_id());

			return null;
		}

	}

	@Override
	public boolean updateBalances(Transfer transfer) {
		boolean result = false;
		String sql = "BEGIN TRANSACTION; UPDATE accounts "
				+ "SET balance = balance + (SELECT amount FROM transfers WHERE transfer_id = ? AND transfer_status_id = 1) "
				+ "WHERE account_id = (SELECT account_to FROM transfers WHERE transfer_id = ? AND transfer_status_id = 1); "
				+ "UPDATE accounts "
				+ "SET balance = balance - (SELECT amount FROM transfers WHERE transfer_id = ? AND transfer_status_id = 1) "
				+ "WHERE account_id = (SELECT account_from FROM transfers WHERE transfer_id = ? AND transfer_status_id = 1); "
				+ "UPDATE transfers " + "SET transfer_status_id = 2" + " WHERE transfer_id = ?;" + " COMMIT;";
		int updatedCount = jdbcTemplate.update(sql, transfer.getTransfer_id(), transfer.getTransfer_id(),
				transfer.getTransfer_id(), transfer.getTransfer_id(), transfer.getTransfer_id());
		if (updatedCount == 3) {
			result = true;
		}
		return result;
	}

	private Transfer mapRowToTransfer(SqlRowSet rs) {
		Transfer transfer = new Transfer();
		transfer.setTransfer_id(rs.getInt("transfer_id"));
		transfer.setTransfer_type_id(rs.getInt("transfer_type_id"));
		transfer.setTransfer_status_id(rs.getInt("transfer_status_id"));
		transfer.setAccount_from(rs.getInt("account_from"));
		transfer.setAccount_to(rs.getInt("account_to"));
		transfer.setAmount(rs.getBigDecimal("amount"));
		return transfer;
	}

}
