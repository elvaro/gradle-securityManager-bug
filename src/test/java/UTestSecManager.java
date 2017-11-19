import org.junit.*;

import java.security.AccessControlException;
import java.security.Permission;

/**
 * Test class for testing Gradle Java Security Manager bug
 */

public class UTestSecManager {

    /**
     * This unit test will succeed
     * @throws Exception
     */
    @Test
    public void testSeqManagerWorking() throws Exception {
        //there is no need for this policy file since the new security manager will be override the neccessary
        //check permission methods.
        System.setProperty("java.security.policy", "src/main/dist/policy.all");

        //This creates a new SecurityManager that will basically allow anything which of course is very insecure
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
            }

            @Override
            public void checkPermission(Permission perm, Object context) {
            }
        });
        try {
            System.setProperty("TestProperty", "value");
        } catch (AccessControlException ex) {
            System.out.println(ex);
        }
        System.setProperty("AnotherProperty", "value");
        Assert.assertEquals(0,0);
    }

    /**
     * This unit test will fail if run from Gradle
     * @throws Exception
     */
    @Test
    public void testSeqManagerNOTWorking() throws Exception {
        System.setProperty("java.security.policy", "src/main/dist/policy.all");
        System.setSecurityManager(new SecurityManager());

        try {
            //This will print out a AccessControlException if run from Gradle but will run fine in IntelliJ
            System.setProperty("TestProperty", "value");
        } catch (AccessControlException ex) {
            System.out.println(ex);
        }
        //the following line will cause the unit tast to hang if run from Gradle otherwise it will run fine
        System.setProperty("AnotherProperty", "value");
        Assert.assertEquals(0,0);
    }
}

