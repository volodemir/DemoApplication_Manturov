package ru.manturov.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.manturov.exception.CustomException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryDao {
    private final DataSource dataSource;

    public List<ReportModel> getIncomeByPeriod(long userId, Date beginDate, Date endDate) {
        List<ReportModel> valueByCategoryList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select tc.category, sum(t.value_) as value_ " +
                    "FROM transaction_category as tc " +
                    "left join category_to_transaction as ctt on ctt.category_id = tc.id " +
                    "left join transaction_ as t on t.id = ctt.transaction_id " +
                    "left join account as a on t.to_account_id = a.id " +
                    "where (t.created_date between ? and ?) and tc.user_id = ? and tc.user_id = a.user_id and t.id is not null " +
                    "group by tc.category");

            ps.setTimestamp(1, new Timestamp(beginDate.getTime()));
            ps.setTimestamp(2, new Timestamp(endDate.getTime()));
            ps.setLong(3, userId);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                ReportModel reportModel = new ReportModel();
                reportModel.setCategoryName(resultSet.getString("category"));
                reportModel.setValue(resultSet.getBigDecimal("value_"));

                valueByCategoryList.add(reportModel);
            }

        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return valueByCategoryList;
    }

    public List<ReportModel> getExpenseByPeriod(long userId, Date beginDate, Date endDate) {
        List<ReportModel> valueByCategoryList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select tc.category, sum(t.value_) as value_ " +
                    "FROM transaction_category as tc " +
                    "left join category_to_transaction as ctt on ctt.category_id = tc.id " +
                    "left join transaction_ as t on t.id = ctt.transaction_id " +
                    "left join account as a on t.from_account_id = a.id " +
                    "where (t.created_date between ? and ?) and tc.user_id = ? and tc.user_id = a.user_id and t.id is not null " +
                    "group by tc.category");

            ps.setTimestamp(1, new Timestamp(beginDate.getTime()));
            ps.setTimestamp(2, new Timestamp(endDate.getTime()));
            ps.setLong(3, userId);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                ReportModel reportModel = new ReportModel();
                reportModel.setCategoryName(resultSet.getString("category"));
                reportModel.setValue(resultSet.getBigDecimal("value_"));

                valueByCategoryList.add(reportModel);
            }

        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return valueByCategoryList;
    }
}
