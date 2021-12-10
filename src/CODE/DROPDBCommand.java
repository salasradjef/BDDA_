package CODE;

import java.io.File;
import java.io.IOException;

public class DROPDBCommand {


    public void Execute() throws IOException {
        DiskManager disk = DiskManager.getInstance();
        BufferManager BM = BufferManager.getInstance();
        Catalog catalog = Catalog.getInstance();
        BM.FlushAll();
        catalog.reset();
        disk.clean_all();
        System.out.println("DROPDB effectué");

    }
}

