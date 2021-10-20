package Beans;

import Enum.CouponType;

import java.sql.Date;

public class Coupon {

    /**
     * Fields of the Coupon Bean Class
     */
    private int id;
    private int companyID;
    private int category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;

    /**
     * Constructor without the ID
     * @param companyID Company ID cascaded with Category
     * @param category Category (Enum) for the category of the coupon
     * @param description description of the coupon
     * @param title The title of the coupon (there can be only one) https://www.youtube.com/watch?v=sqcLjcSloXs
     * @param startDate The start date of the coupon
     * @param endDate The end date of the coupon
     * @param amount amount left of the coupon
     * @param price the $$$ baby
     * @param image Image of the coupon
     */
    public Coupon(int companyID, CouponType category, String description, String title, Date startDate, Date endDate, int amount, double price, String image) {
        this.companyID = companyID;
        this.category = category.ordinal();
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    /**
     * Constructor without the CompanyID, category and the title
     * @param description description of the coupon
     * @param title The title of the coupon (there can be only one) https://www.youtube.com/watch?v=sqcLjcSloXs
     * @param startDate The start date of the coupon
     * @param endDate The end date of the coupon
     * @param amount amount left of the coupon
     * @param price the $$$ baby
     * @param image Image of the coupon
     */
    public Coupon(String description, String title, Date startDate, Date endDate, int amount, double price, String image){
        this.description = description;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    /**
     * Constructor with only id start date and end date for the CouponExpirationDailyJob Thread
     * @param id id of the coupon by the SQL server
     * @param startDate the start date
     * @param endDate the end date
     */
    public Coupon(int id, Date startDate, Date endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * String builder toString() method because he in not immutable (doesn't stay in memory)
     * we don't want to BOMB the memory!
     * @return the text written in the StringBuilder
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Coupon details: " + "\n");
        sb.append("Company ID: " + companyID + " (of coupon)" + "\n");
        sb.append("Title: " + title + "\n");
        sb.append("Description: " + description + "\n");
        sb.append("Start Date: " + startDate + "\n");
        sb.append("End Date: " + endDate + "\n");
        sb.append("Amount of the coupon: " + amount + " pcs." + "\n");
        sb.append("Price: " + price + "$" + "\n");
        sb.append(image + "\n");

        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
