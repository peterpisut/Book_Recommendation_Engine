import java.io.Serializable;
import java.util.ArrayList;
import javafx.util.Pair;
/**
 * The class Cart represents cart or basket for containig books to buy
 * 
 * Cart class implements Serializable for converting its
 * state to a byte stream. So, the byte stream can be reverted back
 * into a copy of the it.
 * 
 * Created by Pisut Suntronkiti  ID: 60070501037
 *            Wuttithat Krongyot ID: 60070501084
 */
public class Cart implements Serializable
{
    /** auto generated serialVersionUID is used for verifying the class in serialization and deserialization */
    private static final long serialVersionUID = 7695178204958042383L;

    /** refers to books and quantity that the user has selected */
    private static ArrayList<Pair<Book, Integer>> selectedBooks = new ArrayList<Pair<Book, Integer>>();

    /** the current net price of book */
    private float totalPrice = 0;

    /**
     * totalPrice getter method
     * @return the current net price of book
     */
    public float getTotalPrice()
    {
        return totalPrice;
    }

    /**
     * selectedBooks getter method
     * @return refers to books and quantity that the user has selected
     */
    public ArrayList<Pair<Book, Integer>> getSelectedBooks()
    {
        return selectedBooks;
    }

    /** 
     * calculate the total price
     */
    private void calculateTotalPrice()
    {
        float totalPrice = 0;
        for (Pair<Book,Integer> item : selectedBooks) 
        {
            totalPrice += item.getKey().getPrice() * item.getValue();
        }
        this.totalPrice = totalPrice;
    }

    /**
     * add a book to the cart
     * @param book refers to book that may be bought
     * @return true - if add book successfully
     */
    public boolean addBookToCart(Book book)
    {
    	int remaining = book.getRemaining();
		System.out.print("\nEnter number of book to buy(remaining " + remaining + ")");
		int bookCount = IOUtils.checkInputMenu(0, remaining);
    	
        try
        {
            selectedBooks.add(new Pair<>(book, bookCount));
            calculateTotalPrice();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeBookFromCart(Pair<Book, Integer> item)
    {
        try
        {
            if (!selectedBooks.remove(item))
                return false;
            calculateTotalPrice();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    public void showAllBooksInCart()
    {
        if (!selectedBooks.isEmpty())
        {
        	System.out.println("------------- Your cart -------------");
        	int index = 0;
            for (Pair<Book, Integer> pair : selectedBooks)
            {
            	index++;
                System.out.println("\t" + index + ". " + pair.getKey().getTitle() + "   " + pair.getValue() + " pcs.");
            }
            System.out.println("\n\tTotal price: " + totalPrice + " baht");
        }
        else
        {
            System.out.println("*** Your cart is empty! ***");
        }
    }

    public boolean purchase(Profile buyer)
    {
        if (BookCollection.isBookEnough(selectedBooks) && PaypalAdapter.pay())
        {
            BookCollection.decreaseRemainingBooks(selectedBooks);
            System.out.println("*** Fill delivery information ***");
            System.out.print("Receiver name> ");
            String receiver = IOUtils.getString();
            System.out.print("Address> ");
            String shippingAddress = IOUtils.getString();
            BillManager.createBill(buyer, selectedBooks, receiver, shippingAddress, totalPrice);
            selectedBooks.clear();
            return true;
        }
        return false;
    }
}