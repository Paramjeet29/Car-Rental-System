	package entity;
	
	public class Customer {
	    private int customerID;
	    private String name;
	    private String contactInfo;
	    private String firstName;
	    private String lastName;
	    private String email;
	    private String phone;
	
	    public Customer(int customerID, String firstName, String lastName, String email, String phone) {
	        this.customerID = customerID;
	        this.firstName = firstName;
	        this.lastName = lastName;
	        this.email = email;
	        this.phone = phone;
	        this.name = firstName + " " + lastName;  // Optionally set name from first and last name
	        this.contactInfo = email + ", " + phone;  // Optionally set contactInfo from email and phone
	    }
	
	    // Getters and Setters
	    public int getCustomerID() {
	        return customerID;
	    }
	
	    public void setCustomerID(int customerID) {
	        this.customerID = customerID;
	    }
	
	    public String getName() {
	        return name;
	    }
	
	    public void setName(String name) {
	        this.name = name;
	    }
	
	    public String getContactInfo() {
	        return contactInfo;
	    }
	
	    public void setContactInfo(String contactInfo) {
	        this.contactInfo = contactInfo;
	    }
	
	    public String getFirstName() {
	        return firstName;
	    }
	
	    public void setFirstName(String firstName) {
	        this.firstName = firstName;
	        this.name = firstName + " " + this.lastName; // Update name when first name changes
	    }
	
	    public String getLastName() {
	        return lastName;
	    }
	
	    public void setLastName(String lastName) {
	        this.lastName = lastName;
	        this.name = this.firstName + " " + lastName; // Update name when last name changes
	    }
	
	    public String getEmail() {
	        return email;
	    }
	
	    public void setEmail(String email) {
	        this.email = email;
	        updateContactInfo(); // Update contact info when email changes
	    }
	
	    public String getPhone() {
	        return phone;
	    }
	
	    public void setPhone(String phone) {
	        this.phone = phone;
	        updateContactInfo(); // Update contact info when phone changes
	    }
	
	    // Method to update contact info based on email and phone
	    private void updateContactInfo() {
	        this.contactInfo = this.email + ", " + this.phone;
	    }
	
	    @Override
	    public String toString() {
	        return "Customer [customerID=" + customerID + ", name=" + name + ", contactInfo=" + contactInfo + "]";
	    }
	}
