import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Tester {

    private Class myTestedClass;
    private Method beforeSuiteMethod;
    private Method afterSuiteMethod;
    private Map<Integer, List<Method>> myMethods = new HashMap();

    public static void main(String[] args) {
        new Tester().start(Experimental.class);
    }

    public void start(Class testClass) {
        myTestedClass = testClass;
        giveAndSortMethods();
        invokeMethods();
    }

    private void giveAndSortMethods() {
        for (Method declaredMethod : myTestedClass.getDeclaredMethods()) {
            declaredMethod.setAccessible(true);
            if (declaredMethod.getAnnotation(BeforeSuite.class) != null) {
                if(beforeSuiteMethod != null){
                    throw new RuntimeException("Method has more 1 \"beforesuite\" methods.");
                }
                beforeSuiteMethod = declaredMethod;
                continue;
            }
            if (declaredMethod.getAnnotation(AfterSuite.class) != null) {
                if( afterSuiteMethod != null) {
                    throw new RuntimeException("Method has more 1 \"aftersuite\" methods.");
                }
                afterSuiteMethod = declaredMethod;
                continue;
            }
            if (declaredMethod.getAnnotation(Test.class) != null) {
                List<Method> frequency = myMethods.getOrDefault(declaredMethod.getAnnotation(Test.class).priority(),null);
                if (frequency != null) {
                    List<Method> temp = new ArrayList<>(frequency);
                    temp.add(declaredMethod);
                    myMethods.put(declaredMethod.getAnnotation(Test.class).priority(), temp);
                }else {
                    myMethods.put(declaredMethod.getAnnotation(Test.class).priority(), Arrays.asList(declaredMethod));
                }
            } else {
                System.out.println("Class have methods not marked annotations Test - " + declaredMethod.getName());
            }
        }
    }

    private void invokeMethods(){
        try {
            Experimental experimental = new Experimental();
            beforeSuiteMethod.invoke(experimental);
            for (Integer priority : myMethods.keySet()) {
                for (Method method : myMethods.get(priority)) {
                    //System.out.println(Arrays.toString(method.getParameterTypes()));
                    method.invoke(experimental);
                }
            }
            afterSuiteMethod.invoke(experimental);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


    }



}
