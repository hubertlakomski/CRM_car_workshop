package dao;
import model.Customer;
import model.Vehicle;
import util.DbUtil;

import java.sql.*;

public class VehicleDao {

    private static final String CREATE_VEHICLE_QUERY =
            "INSERT INTO vehicles(brand, model, productionYear, registrationNumber, nextReview, ownerId) VALUES (?,?,?,?,?,?)";
    private static final String READ_VEHICLE_QUERY =
            "SELECT * FROM vehicles WHERE id = ?";
    private static final String UPDATE_VEHICLE_QUERY =
            "UPDATE vehicles SET brand=?, model=?, productionYear=?, registrationNumber=?, nextReview=?, ownerId=? WHERE id=?";
    private static final String DELETE_VEHICLE_QUERY =
            "DELETE FROM vehicles WHERE id=?";


    public Vehicle create (Vehicle vehicle){

        try(Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);

            vehicleSetStatement(vehicle, statement);

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.next()){

                vehicle.setId(resultSet.getInt(1));

            }

            return vehicle;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Vehicle read (int vehicleId) {

        try (Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(READ_VEHICLE_QUERY);

            statement.setInt(1, vehicleId);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                Vehicle vehicle = new Vehicle();

                vehicle.setId(resultSet.getInt("id"));
                vehicle.setBrand(resultSet.getString("brand"));
                vehicle.setModel(resultSet.getString("model"));
                vehicle.setProductionYear(resultSet.getInt("productionYear"));
                vehicle.setRegistrationNumber(resultSet.getString("registrationNumber"));
                vehicle.setNextReview(resultSet.getTimestamp("nextReview"));

                CustomerDao customerDao = new CustomerDao();
                Customer vehicleOwner = customerDao.read(resultSet.getInt("customerId"));

                vehicle.setOwner(vehicleOwner);

                return vehicle;
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Vehicle update(Vehicle vehicle){

        try(Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(UPDATE_VEHICLE_QUERY);

            vehicleSetStatement(vehicle, statement);

            statement.setInt(7, vehicle.getId());

            statement.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int vehicleId){

        try(Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(DELETE_VEHICLE_QUERY);

            statement.setInt(1, vehicleId);

            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    private void vehicleSetStatement(Vehicle vehicle, PreparedStatement statement) throws SQLException {
        statement.setString(1, vehicle.getBrand());
        statement.setString(2, vehicle.getModel());
        statement.setInt(3, vehicle.getProductionYear());
        statement.setString(4, vehicle.getRegistrationNumber());
        statement.setTimestamp(5, vehicle.getNextReview());
        statement.setInt(6, vehicle.getOwner().getId());
    }

}
