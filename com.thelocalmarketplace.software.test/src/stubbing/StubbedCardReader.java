package stubbing;

import com.jjjwelectronics.card.AbstractCardReader;

public class StubbedCardReader extends AbstractCardReader {
    public StubbedCardReader() {
        // setting the probability to -1 because Walker's if-statement could randomly fail because 0 is not greater than 0
        probabilityOfTapFailure = -1;
        probabilityOfInsertFailure = -1;
        probabilityOfSwipeFailure = -1;

        // plugging in and turning on
        plugIn(StubbedGrid.instance());
        turnOn();
    }
}
