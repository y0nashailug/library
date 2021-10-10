The following extra credit usecases have been included in the design of the Library system. 

For Admin
	- Add New Book
	- Edit Member
	- Delete Member
	
For Librarian
	- Print Checkout Record
	- Determine Overdue
	
We have modularized the Library System Project and introduced our own business controllers for convenience. These business controllers are also shown in our sequence diagram. The controllers introduced in our project are 
	- BookController
	- CheckoutController
	- MemberController

The SystemController makes requests to the controllers of the models and gets a response and send it back to
the UIController and the UIController updates the panels.

Exception has been added for each the controllers mentioned above
 - BookException
 - CheckoutRecordException
 - LibrarySystemException
 - LogoutException

Rule sets has been added for each UI panels

Custom JButton, JTextField and JPasswordField has been implemented to have a good look to the UI
and to have control for all the elements in one place.

	