import java.io.Serializable;
import java.util.ArrayList;

public class BillCollection implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = -8532049033326057555L;
    private ArrayList<Bill> billCollection = new ArrayList<Bill>();

    public ArrayList<Bill> getBillCollection()
    {
        return billCollection;
    }

    public boolean addBill(Bill bill)
    {
        try
        {
            this.billCollection.add(bill);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}