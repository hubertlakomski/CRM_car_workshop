package dao;

import model.Employee;
import util.DbUtil;

import java.sql.*;

public class EmployeeDao {

    private static final String CREATE_EMPLOYEE_QUERY =
            "INSERT INTO employees (firstName, lastName, address, telephoneNumber, note, manHour) VALUES (?,?,?,?,?,?)";
    private static final String READ_EMPLOYEE_QUERY =
            "SELECT * FROM employees WHERE id=?";
    private static final String UPDATE_EMPLOYEE_QUERY =
            "UPDATE employees SET firstName=?, lastName=?, address=?, telephoneNumber=?, note=?, manHour=? WHERE id=?";
    private static final String DELETE_EMPLOYEE_QUERY =
            "DELETE FROM employees WHERE id = ?";

    public Employee create (Employee employee){

        try(Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(CREATE_EMPLOYEE_QUERY, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getAddress());
            statement.setString(4, employee.getTelephoneNumber());
            statement.setString(5, employee.getNote());
            statement.setDouble(6, employee.getPerHour());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.next())
            {
                employee.setId(resultSet.getInt(1));
            }

            return employee;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Employee read (int employeeId){

        try (Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(READ_EMPLOYEE_QUERY);

            statement.setInt(1, employeeId);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){

                Employee employee = new Employee();

                employee.setId(resultSet.getInt("id"));
                employee.setFirstName(resultSet.getString("firstName"));
                employee.setLastName(resultSet.getString("lastName"));
                employee.setAddress(resultSet.getString("address"));
                employee.setTelephoneNumber(resultSet.getString("telephoneNumber"));
                employee.setPerHour(resultSet.getDouble("manHour"));

                return employee;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Employee update (Employee employee){

        try(Connection connection = DbUtil.getConn()){

            PreparedStatement statement =
                    connection.prepareStatement(UPDATE_EMPLOYEE_QUERY);

            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getAddress());
            statement.setString(4, employee.getTelephoneNumber());
            statement.setString(5, employee.getNote());
            statement.setDouble(6, employee.getPerHour());
            statement.setInt(7, employee.getId());

            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void delete(int employeeId){

        try(Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(DELETE_EMPLOYEE_QUERY);

            statement.setInt(1, employeeId);

            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

}
