package dao;

import model.Employee;
import model.Order;
import model.Status;
import model.Vehicle;
import util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    private static final String CREATE_ORDER_QUERY =
            "INSERT INTO orders(acceptanceForRepair, plannedStartOfRepair, startingRepair, assignedForRepairId, " +
                    "problemDescription, repairDescription, statusId, " +
                    "repairedVehicleId, customerRepairCost,costOfUsedParts, manHour, numberOfManHour) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String READ_ORDER_QUERY =
            "SELECT * FROM orders WHERE id=?";
    private static final String UPDATE_ORDER_QUERY =
            "UPDATE orders SET acceptanceForRepair=?, plannedStartOfRepair=?, startingRepair=?, assignedForRepairId=?, " +
                    "problemDescription=?, repairDescription=?, statusId=?, " +
                    "repairedVehicleId=?,customerRepairCost=?, costOfUsedParts=?, manHour=?, numberOfManHour=? WHERE id = ?";
    private static final String DELETE_ORDER_QUERY =
            "DELETE FROM orders WHERE id = ?";
    private static final String FIND_ALL_ORDERS_QUERY =
            "SELECT * FROM orders";

    public Order create (Order order){

        try(Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(CREATE_ORDER_QUERY, Statement.RETURN_GENERATED_KEYS);

            //when order is created, its status is created automatically
            StatusDao statusDao = new StatusDao();
            Status status = new Status();
            statusDao.create(status);

            statement.setTimestamp(1, order.getAcceptanceForRepair());
            statement.setTimestamp(2, order.getPlannedStartOfRepair());
            statement.setTimestamp(3, order.getStartingRepair());
            statement.setInt(4, order.getAssignedForRepair().getId());
            statement.setString(5, order.getProblemDescription());
            statement.setString(6, order.getRepairDescription());
            statement.setInt(7, status.getId());
            statement.setInt(8, order.getRepairedVehicle().getId());
            statement.setFloat(9, (float) order.getCustomerRepairCost());
            statement.setFloat(10, (float) order.getCostOfUsedParts());
            statement.setFloat(11, (float) order.getAssignedForRepair().getPerHour());
            statement.setFloat(12, (float) order.getNumberOfManHour());

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

    public Order read (int orderId){

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
                Employee assignedForRepair = employeeDao.read(resultSet.getInt("assignedForRepairId"));
                order.setAssignedForRepair(assignedForRepair);

                order.setProblemDescription(resultSet.getString("problemDescription"));
                order.setRepairDescription(resultSet.getString("repairDescription"));

                StatusDao statusDao = new StatusDao();
                Status actualStatus = statusDao.read(resultSet.getInt("statusId"));
                order.setStatus(actualStatus);

                VehicleDao vehicleDao = new VehicleDao();
                Vehicle repairedVehicle = vehicleDao.read(resultSet.getInt("repairedVehicleId"));
                order.setRepairedVehicle(repairedVehicle);

                order.setCustomerRepairCost(resultSet.getFloat("customerRepairCost"));
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
            statement.setInt(7, order.getStatus().getId());
            statement.setInt(8, order.getRepairedVehicle().getId());
            statement.setFloat(9, (float) order.getCustomerRepairCost());
            statement.setFloat(10, (float) order.getCostOfUsedParts());
            statement.setFloat(11, (float) order.getAssignedForRepair().getPerHour());
            statement.setFloat(12, (float) order.getNumberOfManHour());
            statement.setInt(13, order.getId());

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

    public List<Order> findAll(){

        try(Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(FIND_ALL_ORDERS_QUERY);

            List<Order> orders = new ArrayList<>();

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){

                Order order = new Order();

                order.setId(resultSet.getInt("id"));
                order.setAcceptanceForRepair(resultSet.getTimestamp("acceptanceForRepair"));
                order.setPlannedStartOfRepair(resultSet.getTimestamp("plannedStartOfRepair"));
                order.setStartingRepair(resultSet.getTimestamp("startingRepair"));

                EmployeeDao employeeDao = new EmployeeDao();
                Employee assignedForRepair = employeeDao.read(resultSet.getInt("assignedForRepairId"));
                order.setAssignedForRepair(assignedForRepair);

                order.setProblemDescription(resultSet.getString("problemDescription"));
                order.setRepairDescription(resultSet.getString("repairDescription"));

                StatusDao statusDao = new StatusDao();
                Status actualStatus = statusDao.read(resultSet.getInt("statusId"));
                order.setStatus(actualStatus);

                VehicleDao vehicleDao = new VehicleDao();
                Vehicle repairedVehicle = vehicleDao.read(resultSet.getInt("repairedVehicleId"));
                order.setRepairedVehicle(repairedVehicle);

                order.setCustomerRepairCost(resultSet.getFloat("customerRepairCost"));
                order.setCostOfUsedParts(resultSet.getFloat("costOfUsedParts"));
                order.setManHour();
                order.setNumberOfManHour(resultSet.getFloat("numberOfManHour"));

                orders.add(order);
            }

            return orders;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
