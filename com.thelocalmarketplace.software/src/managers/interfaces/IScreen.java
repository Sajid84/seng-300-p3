package managers.interfaces;

import com.jjjwelectronics.screen.ITouchScreen;

import javax.swing.*;

public interface IScreen extends IScreenUpdate {

    /**
     * This simply returns the root panel of the form.
     *
     * @return the object's root panel
     */
    JPanel getPanel();

    /**
     * This configures any implementor to take their internal root {@link JPanel}
     * and put attach that into the parent {@link JFrame} of the Touch Screen.
     *
     * @param touchScreen the touch screen to attach panels to
     */
    void configure(ITouchScreen touchScreen);

}
