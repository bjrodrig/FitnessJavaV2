/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.resource.spi.work.Work;

/**
 *
 * @author barodriguez
 */
public class PersistenceHelper {
    private static final EntityManager entityManager;
    @Resource(mappedName="jdbc/fitness") DataSource dataSource;
    static {
        entityManager = Persistence.createEntityManagerFactory("FitnessPU").
            createEntityManager();
    }
    public static EntityManager getEntityManager() { 
        return entityManager; 
    };
    
    public static void clearDatabase() throws SQLException {
    Connection c = entityManager.unwrap(java.sql.Connection.class);
    Statement s = c.createStatement();
    s.execute("SET DATABASE REFERENTIAL INTEGRITY FALSE");
    Set<String> tables = new HashSet<String>();
    ResultSet rs = s.executeQuery("select table_name " +
        "from INFORMATION_SCHEMA.system_tables " +
        "where table_type='TABLE' and table_schem='PUBLIC'");
    while (rs.next()) {
        if (!rs.getString(1).startsWith("DUAL_")) {
            tables.add(rs.getString(1));
        }
    }
    rs.close();
    for (String table : tables) {
        s.executeUpdate("DELETE FROM " + table);
    }
    s.execute("SET DATABASE REFERENTIAL INTEGRITY TRUE");
    s.close();
    }

}
