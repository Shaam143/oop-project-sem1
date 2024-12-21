public class Medicine {
    //Attributes
    private String name;
    private int shelfAge;
    private String expiryDate;

    //Constructor
    public Medicine (String name, int shelfAge, String expirtyDate) {
        this.name = name;
        this.shelfAge = shelfAge;
        this.expiryDate = expirtyDate;
    }
    
    //Method to return medicine details
    public String getInfo () {
        return  "Medicine Name : " +name + "\n" +
                "Age : " +shelfAge + " years \n" +
                "Expiry Date : " +expiryDate;
    }

    //Main method
    public static void main(String[] args) {
        //Example
        Medicine med = new Medicine ("Paracetamol", 2, "31-12-2021");

        //Display
    System.out.println(med.getInfo());
    }

    
}

