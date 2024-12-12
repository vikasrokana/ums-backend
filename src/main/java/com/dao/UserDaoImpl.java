package com.dao;

import com.Utility.ConnectionUtilities;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class UserDaoImpl implements UserDao{
    static Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
    @Override
    public Integer logout(Long userId) {
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;
        Integer isLoggedin = 0;
        String sql = "update user set is_loggedin =? where id =?";
        try {
            con=dataSource.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setBoolean(1,false);
            ps.setLong(2,userId);
            logger.info(sql);
            isLoggedin = ps.executeUpdate();
            logger.info("data updated");
        }catch (SQLException e){
            e.printStackTrace();
            logger.error("Error in SQL: " +sql);
            return isLoggedin;
        }finally {
            ConnectionUtilities.closeConnectionResource(con, st, rs);

        }
        return isLoggedin;
    }
}
