package prodgroups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App
{
    static String filename = "testrules2.txt";
    public static void main( String[] args )
    {
 //       test1();
 //       test2();
        try {
            test3();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void test3() throws IOException {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);

        File file = new File(filename);

        BufferedReader br = new BufferedReader(new FileReader(s+"/prodgroups/"+file));

        String st;
        RuleProcessor ruleProcessor = new RuleProcessor();
        while ((st = br.readLine()) != null)
            ruleProcessor.parseAndProcess(st);
            System.out.println(st);

    }

    private static void test2() {
        List<CustomerGroup> customerGroups = new ArrayList(
                Arrays.asList(
                        new CustomerGroup("1", Arrays.asList(1,3,5,7,9,11), ""),
                        new CustomerGroup("2", Arrays.asList(3,7,9,11), ""),
                        new CustomerGroup("3", Arrays.asList(3,5,7,11), ""),
                        new CustomerGroup("4", Arrays.asList(1,5,9), ""),
                        new CustomerGroup("5", Arrays.asList(1,9,11), ""),
                        new CustomerGroup("6", Arrays.asList(1,3,7,9), ""),
                        new CustomerGroup("7", Arrays.asList(1,7,11), "")
                        /*new CustomerGroup("7", Arrays.asList(11), ""),
                        new CustomerGroup("8", Arrays.asList(3,11), ""),
                        new CustomerGroup("9", Arrays.asList(7,11), ""),
                        new CustomerGroup("10", Arrays.asList(9,11), ""),
                        new CustomerGroup("11", Arrays.asList(5,11), "")*/
                        ) );
        GroupProcessor groupProcessor = new GroupProcessor();
        groupProcessor.setGroups(customerGroups);
        groupProcessor.optimize();
        groupProcessor.printGroupsForDebug();
    }

    private static void test1() {
        RuleProcessor ruleProcessor = new RuleProcessor();
        ruleProcessor.process("C00000003", "P00000001", "include");
        ruleProcessor.process("C00000001", "P00000002", "include");
        ruleProcessor.process("C00000002", "P00000003", "include");
        ruleProcessor.process("C00000005", "P00000003", "include");
        ruleProcessor.process("C00000002", "P00000004", "include");
        ruleProcessor.process("C00000002", "P00000005", "include");
        ruleProcessor.printProductGroupTable();
        ruleProcessor.printGroupDetailsForDebugPurposes();
        ruleProcessor.printCustomerProductMatrix();
        ruleProcessor.process("C00000004", "P00000001", "include");
        ruleProcessor.printProductGroupTable();
        ruleProcessor.printGroupDetailsForDebugPurposes();
        ruleProcessor.printCustomerProductMatrix();
        ruleProcessor.process("C00000003", "P00000004", "include");
        ruleProcessor.printProductGroupTable();
        ruleProcessor.printGroupDetailsForDebugPurposes();
        ruleProcessor.printCustomerProductMatrix();
        ruleProcessor.process("C00000004", "P00000002", "include");
        ruleProcessor.printProductGroupTable();
        ruleProcessor.printGroupDetailsForDebugPurposes();
        ruleProcessor.process("C00000002", "P00000004", "exclude");
        ruleProcessor.printProductGroupTable();
        ruleProcessor.printGroupDetailsForDebugPurposes();
        ruleProcessor.process("C00000002", "P00000005", "exclude");
        ruleProcessor.printProductGroupTable();
        ruleProcessor.printGroupDetailsForDebugPurposes();
        ruleProcessor.printCustomerProductMatrix();
        ruleProcessor.process("C00000002", "P00000001", "exclude");
        ruleProcessor.printProductGroupTable();
        ruleProcessor.printGroupDetailsForDebugPurposes();
        ruleProcessor.process("C00000003", "P00000001", "exclude");
        ruleProcessor.printProductGroupTable();
        ruleProcessor.printGroupDetailsForDebugPurposes();
        ruleProcessor.process("C00000005", "P00000001", "include");
        ruleProcessor.printProductGroupTable();
        ruleProcessor.printGroupDetailsForDebugPurposes();
        ruleProcessor.process("C00000004", "P00000001", "exclude");
        ruleProcessor.printProductGroupTable();
        ruleProcessor.printGroupDetailsForDebugPurposes();
        ruleProcessor.printCustomerProductMatrix();
        ruleProcessor.process("C00000002", "P00000004", "include");
        ruleProcessor.printProductGroupTable();
        ruleProcessor.printGroupDetailsForDebugPurposes();
        ruleProcessor.printCustomerProductMatrix();
        ruleProcessor.process("C00000005", "P00000004", "include");
        ruleProcessor.printProductGroupTable();
        ruleProcessor.printGroupDetailsForDebugPurposes();
        ruleProcessor.printCustomerProductMatrix();
        ruleProcessor.process("C00000001", "P00000005", "include");
        ruleProcessor.printProductGroupTable();
        ruleProcessor.printGroupDetailsForDebugPurposes();
        ruleProcessor.printCustomerProductMatrix();
        ruleProcessor.process("C00000004", "P00000005", "include");
        ruleProcessor.printProductGroupTable();
        ruleProcessor.printGroupDetailsForDebugPurposes();
        ruleProcessor.printCustomerProductMatrix();
        ruleProcessor.process("C00000002", "P00000001", "include");
        ruleProcessor.printProductGroupTable();
        ruleProcessor.printGroupDetailsForDebugPurposes();
        ruleProcessor.printCustomerProductMatrix();
        ruleProcessor.process("C00000005", "P00000001", "include");
        ruleProcessor.printProductGroupTable();
        ruleProcessor.printGroupDetailsForDebugPurposes();
        ruleProcessor.printCustomerProductMatrix();
        ruleProcessor.process("C00000001", "P00000001", "include");
        ruleProcessor.printProductGroupTable();
        ruleProcessor.printGroupDetailsForDebugPurposes();
        ruleProcessor.printCustomerProductMatrix();
    }
}
