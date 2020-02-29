package dao;

import model.Employee;
import model.Order;
import model.Status;
import model.Vehicle;
import util.DbUtil;

import java.sql.*;

public class OrderDao {

    private static final String CREATE_ORDER_QUERY =
            "INSERT INTO orders(acceptanceForRepair, plannedStartOfRepair, startingRepair, assignedForRepairId, " +
                    "problemDescription, repairDescription, actualStatusId, " +
                    "repairedVehicleId, costOfUsedParts, manHour, numberOfManHour) " +
                    "VALUE(?,?,?,?,?,?,?,?,?,?,?)";
    private static final String READ_ORDER_QUERY =
            "SELECT * FROM orders WHERE id=?";
    private static final String UPDATE_ORDER_QUERY =
            "UPDATE orders SET acceptanceForRepair=?, plannedStartOfRepair=?, startingRepair=?, assignedForRepairId=?, " +
                    "problemDescription=?, repairDescription=?, actualStatusId=?, " +
                    "repairedVehicleId=?, costOfUsedParts=?, manHour=?, numberOfManHour=? WHERE id = ?";
    private static final String DELETE_ORDER_QUERY =
            "DELETE FROM orders WHERE id = ?";

    public Order create (Order order){

        try(Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(CREATE_ORDER_QUERY, Statement.RETURN_GENERATED_KEYS);

            statement.setTimestamp(1, order.getAcceptanceForRepair());
            statement.setTimestamp(2, order.getPlannedStartOfRepair());
            statement.setTimestamp(3, order.getStartingRepair());
            statement.setInt(4, order.getAssignedForRepair().getId());
            statement.setString(5, order.getProblemDescription());
            statement.setString(6, order.getRepairDescription());
            statement.setInt(7, order.getActualStatus().getId());
            statement.setInt(8, order.getRepairedVehicle().getId());
            statement.setFloat(9, (float) order.getCostOfUsedParts());
            statement.setFloat(10, (float) order.getAssignedForRepair().getPerHour());
            statement.setFloat(11, (float) order.getNumberOfManHour());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.next()){

                order.setId(resultSet.getInt(1));

            }

            return order;
        }

        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public Order create (int orderId){

        try(Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(READ_ORDER_QUERY);

            statement.setInt(1, orderId);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){

                Order order = new Order();

                order.setId(resultSet.getInt("id"));
                order.setAcceptanceForRepair(resultSet.getTimestamp("acceptanceForRepair"));
                order.setPlannedStartOfRepair(resultSet.getTimestamp("plannedStartOfRepair"));
                order.setStartingRepair(resultSet.getTimestamp("startingRepair"));

                EmployeeDao employeeDao = new EmployeeDao();
                Employee assignedForRepair = employeeDao.read(resultSet.getInt("assignedForRepair"));
                order.setAssignedForRepair(assignedForRepair);

                order.setProblemDescription(resultSet.getString("problemDescription"));
                order.setRepairDescription(resultSet.getString("repairDescription"));

                StatusDao statusDao = new StatusDao();
                Status actualStatus = statusDao.read(resultSet.getInt("actualStatusId"));
                order.setActualStatus(actualStatus);

                VehicleDao vehicleDao = new VehicleDao();
                Vehicle repairedVehicle = vehicleDao.read(resultSet.getInt("repairedVehicleId"));
                order.setRepairedVehicle(repairedVehicle);

                order.setCostOfUsedParts(resultSet.getFloat("costOfUsedParts"));
                order.setManHour();
                order.setNumberOfManHour(resultSet.getFloat("numberOfManHour"));

                return order;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Order update(Order order){

        try(Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(UPDATE_ORDER_QUERY);

            statement.setTimestamp(1, order.getAcceptanceForRepair());
            statement.setTimestamp(2, order.getPlannedStartOfRepair());
            statement.setTimestamp(3, order.getStartingRepair());
            statement.setInt(4, order.getAssignedForRepair().getId());
            statement.setString(5, order.getProblemDescription());
            statement.setString(6, order.getRepairDescription());
            statement.setInt(7, order.getActualStatus().getId());
            statement.setInt(8, order.getRepairedVehicle().getId());
            statement.setFloat(9, (float) order.getCostOfUsedParts());
            statement.setFloat(10, (float) order.getAssignedForRepair().getPerHour());
            statement.setFloat(11, (float) order.getNumberOfManHour());
            statement.setInt(12, order.getId());

            statement.executeUpdate();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int orderId){

        try(Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(DELETE_ORDER_QUERY);

            statement.setInt(1, orderId);

            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

}
