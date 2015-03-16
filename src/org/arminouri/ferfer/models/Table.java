package org.arminouri.ferfer.models;

/**
 * Created by arminouri on 3/11/15.
 */
import static org.arminouri.ferfer.models.Constants.*;
import org.arminouri.ferfer.io.Config;
public abstract class Table {
    public abstract String get_table_name();
    protected static final Config config = new Config();
    public final String table_path = config.HOME + get_table_name();
    abstract public String toString();
}
