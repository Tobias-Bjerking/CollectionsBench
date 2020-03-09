package se.su.dsv.online_concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OperationGenerator {

    public static List<Integer> generateOperationSequence(int lookupPercentage, int insertPercentage, int forEachPercentage, int removePercentage) {
        List<Integer> operationSequence = new ArrayList<>();

        for (int i = 0; i < lookupPercentage; i++)
            operationSequence.add(1);

        for (int i = 0; i < insertPercentage; i++)
            operationSequence.add(2);

        for(int i = 0; i< forEachPercentage; i++)
            operationSequence.add(3);

        for (int i = 0; i < removePercentage; i++)
            operationSequence.add(4);

        Collections.shuffle(operationSequence);
        return operationSequence;
    }

}
