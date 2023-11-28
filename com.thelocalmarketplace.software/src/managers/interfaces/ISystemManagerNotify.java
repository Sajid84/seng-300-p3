package managers.interfaces;

import com.jjjwelectronics.Item;
import managers.enums.SessionStatus;

public interface ISystemManagerNotify {

    void notifyItemAdded(Item item);

    void notifyItemRemoved(Item item);

    void notifyStateChange(SessionStatus state);

}
