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
//        System.setProperty("java.security.policy", "src/main/dist/config/policy.all");
        System.out.println("Starting");

        System.out.println("set sec manager");
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
            }

            @Override
            public void checkPermission(Permission perm, Object context) {
            }

            @Override
            public void checkExit(int status) {
                String message = "System exit requested with error " + status;
                throw new SecurityException(message);
            }
        });
        try {
            System.setProperty("TestProperty", "value");
        } catch (AccessControlException ex) {
            System.out.println(ex);
        }
        System.out.println("set property");
        Assert.assertEquals(0,0);
    }

    /**
     * This unit test will fail
     * @throws Exception
     */
    @Test
    public void testSeqManagerNOTWorking() throws Exception {
        //        System.setProperty("java.security.policy", "src/main/dist/config/policy.all");
        System.out.println("Starting");
        System.setSecurityManager(new SecurityManager());

        System.out.println("set sec manager");
        try {
            System.setProperty("TestProperty", "value");
        } catch (AccessControlException ex) {
            System.out.println(ex);
        }
        System.out.println("set property");
        Assert.assertEquals(0,0);
    }
}

