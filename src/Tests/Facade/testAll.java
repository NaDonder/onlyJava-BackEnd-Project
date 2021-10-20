package Tests.Facade;

public class testAll {
    public static void main() {

        try {
            AdminFacadeTest.main();

            CompanyFacadeTest.main();

            CustomerFacadeTest.main();

        } catch (Exception error) {
            System.out.println("Something even more terrible just happened");
        }
    }
}
