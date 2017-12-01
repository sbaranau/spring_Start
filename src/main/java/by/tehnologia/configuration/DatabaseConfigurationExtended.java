package by.tehnologia.configuration;

import org.apache.commons.configuration.DatabaseConfiguration;
import org.apache.commons.configuration.PropertyConverter;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Для поддержки упорядоченных ключей и Spring транзакций
 * @author Aleksandr Golovnyov
 */
public class DatabaseConfigurationExtended extends DatabaseConfiguration {

    private final DataSource datasource;
    private final String table;
    private final String nameColumn;
    private final String keyColumn;
    private final String valueColumn;
    private final String name;

    public DatabaseConfigurationExtended(DataSource datasource, String table, String keyColumn,
            String valueColumn) {
        super(datasource, table, keyColumn, valueColumn);
        this.datasource = datasource;
        this.table = table;
        this.nameColumn = null;
        this.keyColumn = keyColumn;
        this.valueColumn = valueColumn;
        this.name = null;
    }

    public DatabaseConfigurationExtended(DataSource datasource, String table, String keyColumn,
            String valueColumn, boolean commits) {
        super(datasource, table, keyColumn, valueColumn, commits);
        this.datasource = datasource;
        this.table = table;
        this.nameColumn = null;
        this.keyColumn = keyColumn;
        this.valueColumn = valueColumn;
        this.name = null;
    }

    public DatabaseConfigurationExtended(DataSource datasource, String table, String nameColumn,
            String keyColumn, String valueColumn, String name) {
        super(datasource, table, nameColumn, keyColumn, valueColumn, name);
        this.datasource = datasource;
        this.table = table;
        this.nameColumn = nameColumn;
        this.keyColumn = keyColumn;
        this.valueColumn = valueColumn;
        this.name = name;
    }

    public DatabaseConfigurationExtended(DataSource datasource, String table, String nameColumn,
            String keyColumn, String valueColumn, String name, boolean commits) {
        super(datasource, table, nameColumn, keyColumn, valueColumn, name, commits);
        this.datasource = datasource;
        this.table = table;
        this.nameColumn = nameColumn;
        this.keyColumn = keyColumn;
        this.valueColumn = valueColumn;
        this.name = name;
    }

    @Override
    public Object getProperty(String key) {
        Object result = null;

        // build the query
        StringBuilder query = new StringBuilder("SELECT * FROM ");
        query.append(table).append(" WHERE ");
        query.append(keyColumn).append("=?");
        if (nameColumn != null) {
            query.append(" AND ").append(nameColumn).append("=?");
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // поддержка текущей Spring транзакции
            conn = DataSourceUtils.getConnection(datasource);

            // bind the parameters
            pstmt = conn.prepareStatement(query.toString());
            pstmt.setString(1, key);
            if (nameColumn != null) {
                pstmt.setString(2, name);
            }

            rs = pstmt.executeQuery();

            List<Object> results = new ArrayList<>();
            while (rs.next()) {
                Object value = rs.getObject(valueColumn);
                if (isDelimiterParsingDisabled()) {
                    results.add(value);
                } else {
                    // Split value if it contains the list delimiter
                    Iterator<?> it = PropertyConverter.toIterator(value, getListDelimiter());
                    while (it.hasNext()) {
                        results.add(it.next());
                    }
                }
            }

            if (!results.isEmpty()) {
                result = (results.size() > 1) ? results : results.get(0);
            }
        } catch (SQLException e) {
            fireError(EVENT_READ_PROPERTY, key, null, e);
        } finally {
            if (DataSourceUtils.isConnectionTransactional(conn, datasource)) {
                close(pstmt, rs);
            } else {
                close(conn, pstmt, rs);
            }
        }

        return result;
    }

    protected void updateProperty(String key, Object obj) {
        // build the query
        String query = "UPDATE " + table + " SET ";
        if (nameColumn == null) {
            query += valueColumn + " = ? ";
            query += "WHERE " + keyColumn + " = ?";
        } else {
            query += valueColumn + " = ? ";
            query += "WHERE " + nameColumn + " = ? AND " + keyColumn + " = ?";
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // поддержка текущей Spring транзакции
            conn = DataSourceUtils.getConnection(datasource);

            // bind the parameters
            pstmt = conn.prepareStatement(query);
            int index = 1;
            pstmt.setString(index++, String.valueOf(obj));
            if (nameColumn != null) {
                pstmt.setString(index++, name);
            }
            pstmt.setString(index++, key);

            pstmt.executeUpdate();
            commitIfRequired(conn);
        } catch (SQLException e) {
            fireError(EVENT_ADD_PROPERTY, key, obj, e);
        } finally {
            if (DataSourceUtils.isConnectionTransactional(conn, datasource)) {
                close(pstmt, null);
            } else {
                close(conn, pstmt, null);
            }
        }
    }

    @Override
    public void setProperty(String key, Object value) {
        fireEvent(EVENT_SET_PROPERTY, key, value, true);
        setDetailEvents(false);

        try {
            updateProperty(key, value);
        } finally {
            setDetailEvents(true);
        }

        fireEvent(EVENT_SET_PROPERTY, key, value, false);
    }

    private void commitIfRequired(Connection conn) throws SQLException {
        if (isDoCommits()) {
            conn.commit();
        }
    }

    public Iterator<String> getKeys() {
        Collection<String> keys = new ArrayList<>();

        // build the query
        String query = "SELECT DISTINCT id, " + keyColumn + " FROM " + table;
        if (nameColumn != null) {
            query += " WHERE " + nameColumn + "=?";
        }

        query += " ORDER BY id";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = datasource.getConnection();

            // bind the parameters
            pstmt = conn.prepareStatement(query);
            if (nameColumn != null) {
                pstmt.setString(1, name);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                keys.add(rs.getString(2));
            }
        } catch (SQLException e) {
            fireError(EVENT_READ_PROPERTY, null, null, e);
        } finally {
            // clean up
            close(conn, pstmt, rs);
        }

        return keys.iterator();
    }

    private void close(Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            getLogger().error("An error occurred on closing the result set", e);
        }

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            getLogger().error("An error occured on closing the statement", e);
        }
    }

    private void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            getLogger().error("An error occurred on closing the result set", e);
        }

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            getLogger().error("An error occured on closing the statement", e);
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            getLogger().error("An error occured on closing the connection", e);
        }
    }

}
