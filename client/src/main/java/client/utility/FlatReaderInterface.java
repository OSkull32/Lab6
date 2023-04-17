package client.utility;

import common.data.*;

public interface FlatReaderInterface {
    String readName();
    Coordinates readCoordinates();
    int readArea();
    long readNumberOfRooms();
    long readNumberOfBathrooms();
    Furnish readFurnish();
    View readView();
    House readHouse();

}
