import java.util.List;

public class HomePage
{
	private static Profile currentUser;

	public static void showMainMenu()
	{

		System.out.println("\n------------------ Main menu ------------------");
		System.out.println("\t1. Request recommendations.");
		System.out.println("\t2. See best seller.");
		System.out.println("\t3. Search for the books.");
		System.out.println("\t4. View your cart.");
		System.out.println("\t5. View buying history.");
		System.out.println("\t0. Back to welcome page.");

		int selMenu = IOUtils.checkInputMenu(0, 5);

		switch (selMenu)
		{
			case 0:
				Index.showIndex();
				break;
			case 1:
				requestRecommendation();
				break;
			case 2:
				seeBestSeller();
				break;
			case 3:
				searchBooks();
				break;
			case 4:
				viewCart();
				break;
			case 5:
				viewBuyingHistory();
				break;
			default:
				break;
		}

	}

	private static void requestRecommendation()
	{
		System.out.println(">>> Request recommendations...");
		System.out.println("\t1. Based on your interested genres.");
		System.out.println("\t2. Based on community.");
		System.out.println("\t0. Back to Main menu.");

		int selMenu = IOUtils.checkInputMenu(0, 2);

		switch (selMenu)
		{
			case 0:
				showMainMenu();
				break;
			case 1:
				currentUser.getCart().showAllBooksInCart();
				viewCart();
				break;
			case 2:
				break;
			case 3:
				currentUser.getCart().purchase(currentUser);
				break;
			default:
				break;
		}
	}

	private static void seeBestSeller()
	{
		System.out.println(">>> See best seller...");
	}

	private static void searchBooks()
	{
		System.out.println(">>> Search for the books...");
		System.out.print("Enter keywords> ");
		String keywords = IOUtils.getString();
		List<String> filteredBooks = BookCollection.searchBooks(keywords);

		if (filteredBooks != null)
		{
			System.out.print("\nSelect a book number to see the detail (or type '0' to back to main menu).");
			int selMenu = IOUtils.checkInputMenu(0, filteredBooks.size());

			if (selMenu == 0)
			{
				showMainMenu();
			}
			else
			{
				Book book = BookCollection.getBook(filteredBooks.get(selMenu - 1));
				System.out.println("\tTitle: " + book.getTitle());
				System.out.println("\tAuthor: " + book.getAuthor());
				System.out.println("\tGenre: " + book.getGenre());
				System.out.println("\tPage length: " + book.getLengthInPages());
				System.out.println("\tPrice (Baht): " + book.getPrice());
				System.out.println("\tAbstract: " + book.getAbtract());
				System.out.println("\tRamaining: " + book.getRemaining());
				System.out.println("\tISBN: " + book.getISBN());

				System.out.println("---------------------------------------");
				while (true)
				{
					System.out.print("Add this book to cart? (Y/N)> ");
					String ans = IOUtils.getString();
					if (ans.equals("Y"))
					{
						boolean bOk = currentUser.getCart().addBookToCart(book);
						currentUser.getCart().showAllBooksInCart();
						showMainMenu();
						break;

					}
					else if (ans.equals("N"))
					{
						showMainMenu();
						break;
					}
					else
					{
						continue;
					}
				}

			}
		}
		else
		{
			System.out.println("*** No item match your search ***\n");
			showMainMenu();
		}

	}

	private static void viewCart()
	{
		System.out.println(">>> View your cart...");
		System.out.println("\t1. Show all books in the cart.");
		System.out.println("\t2. Remove a book in the cart.");
		System.out.println("\t3. Make purchase.");
		System.out.println("\t0. Back to Main menu.");

		int selMenu = IOUtils.checkInputMenu(0, 3);

		switch (selMenu)
		{
			case 0:
				showMainMenu();
				break;
			case 1:
				currentUser.getCart().showAllBooksInCart();
				viewCart();
				break;
			case 2:
				break;
			case 3:
				currentUser.getCart().purchase(currentUser);
				break;
			default:
				break;
		}

	}

	private static void viewBuyingHistory()
	{
		System.out.println(">>> View buying history...");

		System.out.println("\tOrderNo.\tOrderDate\tTotalPrice");
		BillCollection bills = currentUser.getUserBillCollection();
		int index = 0;
//		for(ArrayList<Bill> itr: bills)
//		{
//			index++;
//			System.out.print("\t" + index + ")" + itr.getBillNo());
//			System.out.print("\t" + itr.getOrderDate());
//			System.out.print("\t" + itr.getTotalPrice() + "Baht");
//			System.out.print("\n");
//		}
	}

	public static void setCurrentUser(Profile user)
	{
		currentUser = user;
	}
}
