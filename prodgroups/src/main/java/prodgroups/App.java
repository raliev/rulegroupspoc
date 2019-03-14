package prodgroups;

public class App
{
    public static void main( String[] args )
    {
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
        ruleProcessor.process("C00000003", "P00000004", "include");
        ruleProcessor.printProductGroupTable();
        ruleProcessor.printGroupDetailsForDebugPurposes();
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
    }
}
