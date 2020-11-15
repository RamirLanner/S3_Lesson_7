public class Experimental {

    private static final int ID = 6574837;

    public Experimental() {
    }

    @BeforeSuite
    private void beforeM(){
        System.out.println("Before block!");
    }

    @AfterSuite
    private void afterM(){
        System.out.println("After block!");
    }
/*срабатывает RuntimeException если более 1 метода имеют анотации AfterSuite, BeforeSuite*/
//    @AfterSuite
//    private void afterMethodCopy(){
//        System.out.println("After block!");
//    }

    @Test()
    private void printData(){
        System.out.println("Метод");
    }

    @Test(priority = 1)
    private void printData1(){
        System.out.println("Метод 1");
    }

    @Test(priority = 2)
    private void printData2(){
        System.out.println("Метод 2");
    }

    @Test(priority = 3)
    private void printData3(){
        System.out.println("Метод 3");
    }

    @Test(priority = 4)
    private void printData4(){
        System.out.println("Метод 4");
    }

    @Test(priority = 2)
    private void printData10(){
        System.out.println("Метод 10");
    }

    @Override
    public String toString() {
        return "Experimental class ID = "+ID;
    }
}
