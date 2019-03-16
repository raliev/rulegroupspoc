package prodgroups;

import java.util.List;
import java.util.stream.Collectors;

public class CommonUtils {
    public static String toCommaSep(List<String> allCustomerNames) {
        return allCustomerNames.stream()
                .collect( Collectors.joining( "," ) );
    }
}
