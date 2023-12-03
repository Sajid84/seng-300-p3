package managers.interfaces;

import com.jjjwelectronics.screen.ITouchScreen;

import javax.swing.*;

public interface IScreen extends ISystemManagerNotify {

    /**
     * This simply returns the root panel of the form.
     *
     * @return the object's root panel
     */
    JPanel getPanel();
    
    
    /**
     * This simply returns the root frame of the form.
     *
     * @return the object's root frame
     */
    JFrame getFrame();

    /**
     * This configures any implementor to take their internal root {@link JPanel}
     * and put attach that into the parent {@link JFrame} of the Touch Screen.
     *
     * @param touchScreen the touch screen to attach panels to
     */
    void configure(ITouchScreen touchScreen);

}
