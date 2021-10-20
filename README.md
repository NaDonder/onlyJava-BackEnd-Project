# onlyJava-BackEnd-Project
backend coupon project using only JAVA

Information description:
A Coupon System gives ability for Companies to create coupons
The system has Customer as well, customers can purchase coupons.
The coupons are limited by Amount and End Date and every customer can buy 1 coupon of the same type.
The system store the coupon bought by every customer
The income of the system is by customer buying coupons and from the creation of the coupons by the company.
The afforities divide to 3 types of clients:
1. Administrator - Managing the Company and Customer List.
2. Company - Managing the Coupon List that belong to the company.
3. Customer - purchase of coupons.

The System uses Threads, JDBC, DAO, Java Beans, OOP: and was built in stages:
First Stage:
Creating connection pool(SingleTon) with max num of connections and 4 methods for using the Connection pool.
Creating runQuery for connection and SQL statement.
Creating Data base manager for creating the DB + tables.
Second Stage:
Creating the Beans(seeds) that describe the info in the DB
Third Stage:
Creating DAO which allows us to use basic CRUD(Create, Read, Update, Delete) method to the DB.
Forth Stage:
Building the logic thinking of each Client Type.
Fifth stage:
Building a LoginManager(singleton) for each client type login.
Thread stage:
Building a daily job for deleting coupon out of date

And a Test Section for showing everything works
