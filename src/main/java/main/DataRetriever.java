package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DataRetriever {
    public List<InvoiceTotal> findInvoiceTotals() {
    DBConnection dbConnection = new DBConnection();
    List<InvoiceTotal> invoiceTotals = new ArrayList<>();

    try (Connection connection = dbConnection.getConnection()) {

        String query = """
        SELECT i.id, i.customer_name, i.status, 
               SUM(il.quantity * il.unit_price) as total
        FROM invoice i
        LEFT JOIN invoice_line il ON i.id = il.invoice_id
        GROUP BY i.id, i.customer_name, i.status
        ORDER BY i.id
        """;


        PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            InvoiceTotal invoiceTotal = new InvoiceTotal();
            invoiceTotal.setId(resultSet.getInt("id"));
            invoiceTotal.setClientName(resultSet.getString("customer_name"));
            invoiceTotal.setStatus(StatusEnum.valueOf(resultSet.getString("status")));
            invoiceTotal.setTotal(resultSet.getDouble("total"));
            invoiceTotals.add(invoiceTotal);
        }

    } catch (Exception e) {
        throw new RuntimeException("Error fetching invoice totals", e);
    }

    return invoiceTotals;
}

    List<InvoiceTotal> findConfirmedAndPaidInvoiceTotals() {
    DBConnection dbConnection = new DBConnection();
    List<InvoiceTotal> invoiceTotals = new ArrayList<>();

    try(Connection connection = dbConnection.getConnection()) {
        String query = """
        select i.id, i.customer_name, i.status, SUM(il.quantity * il.unit_price) as totals
        from invoice i
        join invoice_line il on i.id = il.invoice_id
        where i.status = 'PAID' or i.status = 'CONFIRMED'
        group by i.id;

        """;

        PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery();

         while (resultSet.next()) {
             InvoiceTotal invoiceTotal = new InvoiceTotal();
             invoiceTotal.setId(resultSet.getInt("id"));
             invoiceTotal.setClientName(resultSet.getString("customer_name"));
             invoiceTotal.setStatus(StatusEnum.valueOf(resultSet.getString("status")));
             invoiceTotal.setTotal(resultSet.getDouble("totals"));
              invoiceTotals.add(invoiceTotal);
         }

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return invoiceTotals;
    }

    InvoiceStatusTotal computeStatusTotals() {
        DBConnection dbConnection = new DBConnection();
        InvoiceStatusTotal invoiceStatusTotal = new InvoiceStatusTotal();
        try(Connection connection = dbConnection.getConnection()) {
            String query = """
                        select i.status, SUM(il.quantity * il.unit_price) as total
                        from invoice i
                        join invoice_line il on i.id = il.invoice_id
                        group by i.status;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                invoiceStatusTotal.setStatus(StatusEnum.valueOf(resultSet.getString("status")));
                invoiceStatusTotal.setTotal(resultSet.getDouble("total"));
                return invoiceStatusTotal;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return invoiceStatusTotal;
    }
}
