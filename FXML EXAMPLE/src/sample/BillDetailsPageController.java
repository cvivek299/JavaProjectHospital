package sample;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.SQLException;

//get ur bill ,and update bill table using your booking id
//no checks for two bills with same booking id,program will go down,databse will be consistent
public class BillDetailsPageController extends CommonActions {

    @FXML
    private TextField bookingId;
    //to display error input,and for now bill amount
    @FXML
    private Label alertMessage;

    //the employee who is printing the bill
    private int employeeId;

    //passing employee id from prev page
    public void set(int employeeId)
    {
        super.employeeId=employeeId;
        this.employeeId=employeeId;
    }

    //see if there is any such booking id
    private boolean checkValid(int bookingId) throws SQLException {
        String sql = "select count(*) from booking where booking_id="+bookingId+";";
        return SqlOperations.checkIfAnyPresentAlready(sql);
    }

    private void insertInBillTable(int employeeId,int bookingId,float cost) throws SQLException
    {
        String sql="insert into bill(booking_id,employee_id,total_cost) " +
                "values ("+bookingId+","+employeeId+","+cost+");";
        SqlOperations.insert(sql);
    }

    //for now only room price
    private float findTotalCost(int bookingId) throws SQLException {
        String sql="select price from room natural join room_price where room_no=" +
                "(select room_no from booking where booking_id="+bookingId+");";
        return SqlOperations.floatSelect(sql);
    }


    //when button clicked,insertis into db,and prints amount
    @FXML
    public void createBill() throws SQLException
    {
        if(bookingId.getText().length()==0) {
            alertMessage.setText("Please enter the Booking Id.");
            return;
        }
        int bookingIdInInt=Integer.parseInt(bookingId.getText());
        boolean isValid=checkValid(bookingIdInInt);
        if(!isValid)
        {
            alertMessage.setText("Please enter a valid room number");
            return;
        }
        float cost=findTotalCost(bookingIdInInt);
        insertInBillTable(employeeId,bookingIdInInt,cost);
        alertMessage.setText("This is your bill amount to be paid "+cost);
    }


}
