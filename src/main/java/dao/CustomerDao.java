package dao;

import model.Customer;
import util.DbUtil;

import java.sql.*;

public class CustomerDao {

    private static final String CREATE_CUSTOMER_QUERY =
            "INSERT INTO customers(firstName, lastName, birthDate) VALUES (?,?,?)";
    private static final String READ_CUSTOMER_QUERY =
            "SELECT * FROM customers WHERE id = ?";
    private static final String UPDATE_CUSTOMER_QUERY =
            "UPDATE customers SET firstName = ?, lastName = ?, birthDate = ? WHERE id = ?";
    private static final String DELETE_CUSTOMER_QUERY =
            "DELETE FROM customers WHERE id = ?";

    public Customer create (Customer customer){
        try(Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(CREATE_CUSTOMER_QUERY, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setDate(3, customer.getBirthDate());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.next())
            {
                customer.setId(resultSet.getInt(1));
            }

            return customer;
        }
        catch (SQLException e)

        {
            e.printStackTrace();
            return null;
        }
    }

    public Customer read(int customerId){

        try(Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(READ_CUSTOMER_QUERY);

            statement.setInt(1, customerId);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){

                Customer customer = new Customer();

                customer.setId(resultSet.getInt("id"));
                customer.setFirstName(resultSet.getString("firstName"));
                customer.setLastName(resultSet.getString("lastName"));
                customer.setBirthDate(resultSet.getDate("birthDate"));

                return customer;
            }

        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public Customer update (Customer customer){

        try(Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(UPDATE_CUSTOMER_QUERY);

            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setDate(3, customer.getBirthDate());
            statement.setInt(4, customer.getId());

            statement.executeUpdate();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void delete(int customerId){

        try(Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(DELETE_CUSTOMER_QUERY);

            statement.setInt(1, customerId);

            statement.executeUpdate();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


}
