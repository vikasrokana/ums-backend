package com.Utility;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionUtilities {
    static Logger logger = Logger.getLogger(ConnectionUtilities.class);

    public static void closeConnectionResource(Connection con, Statement st, ResultSet rs){
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error(e);
            }
        }

        if (null != st) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (null != con) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
