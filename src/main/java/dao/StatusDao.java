package dao;

import model.Order;
import model.Status;
import util.DbUtil;

import java.sql.*;

public class StatusDao {

    private static final String CREATE_STATUS_QUERY =
            "INSERT INTO statuses(accepted, approvedRepairCosts, inRepair, readyForPickup, resignation, orderId) VALUES (?,?,?,?,?,?)";
    private static final String READ_STATUS_QUERY =
            "SELECT * FROM statuses WHERE orderId = ?";
    private static final String UPDATE_STATUS_QUERY =
            "UPDATE statuses SET accepted=?, approvedRepairCosts=?, inRepair=?, readyForPickup=?, resignation=? WHERE orderId = ?";
    private static final String DELETE_STATUS_QUERY =
            "DELETE FROM statuses WHERE orderId = ?";

    protected Status create (Status status){

        try(Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(CREATE_STATUS_QUERY);

            OrderDao orderDao = new OrderDao();

            Order order = orderDao.read(status.getOrder().getId());

            statement.setInt(6, order.getId());
            statement.setBoolean(1, status.isAccepted());
            statement.setBoolean(2, status.isApprovedRepairCosts());
            statement.setBoolean(3, status.isInRepair());
            statement.setBoolean(4, status.isReadyForPickup());
            statement.setBoolean(5, status.isResignation());

            statement.executeUpdate();

            return status;
        }

        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Status read (int statusId){

        try(Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(READ_STATUS_QUERY);

            statement.setInt(1, statusId);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){

                Status status = new Status();

                OrderDao orderDao = new OrderDao();

                status.setOrder(orderDao.read(resultSet.getInt("orderId")));
                status.setAccepted(resultSet.getBoolean("accepted"));
                status.setApprovedRepairCosts(resultSet.getBoolean("approvedRepairCosts"));
                status.setInRepair(resultSet.getBoolean("inRepair"));
                status.setReadyForPickup(resultSet.getBoolean("readyForPickup"));
                status.setResignation(resultSet.getBoolean("resignation"));

                return status;
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public Status update(Status status){

        try(Connection connection = DbUtil.getConn())
        {

            PreparedStatement statement =
                    connection.prepareStatement(UPDATE_STATUS_QUERY);

            statement.setBoolean(1, status.isAccepted());
            statement.setBoolean(2, status.isApprovedRepairCosts());
            statement.setBoolean(3, status.isInRepair());
            statement.setBoolean(4, status.isReadyForPickup());
            statement.setBoolean(5, status.isResignation());
            statement.setInt(6, status.getOrder().getId());

            statement.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    protected void delete (int statusId){

        try(Connection connection = DbUtil.getConn())
        {
            PreparedStatement statement =
                    connection.prepareStatement(DELETE_STATUS_QUERY);

            statement.setInt(1, statusId);

            statement.executeUpdate();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

}
