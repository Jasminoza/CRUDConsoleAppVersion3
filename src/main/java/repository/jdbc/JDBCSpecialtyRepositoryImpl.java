package repository.jdbc;

import model.Specialty;
import repository.SpecialtyRepository;
import utils.JdbcUtils;
import utils.ResultSetConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JDBCSpecialtyRepositoryImpl implements SpecialtyRepository {
    private static final String tableName = "specialties";

    @Override
    public List<Specialty> getAll() {
        String SQL = "SELECT * FROM " + tableName;
        try (ResultSet resultSet = JdbcUtils.getPreparedStatement(SQL).executeQuery()) {
            return ResultSetConverter.convertToSpecialtiesList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Specialty create(Specialty specialty) {
        insertSpecialty(specialty);
        return getByName(specialty.getName());
    }

    private static void insertSpecialty(Specialty specialty) {
        try (PreparedStatement preparedStatement =
                     JdbcUtils.getPreparedStatement(String.format("INSERT INTO %s (name) VALUES(?)", tableName))) {
            preparedStatement.setString(1, specialty.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Specialty getByName(String name) {
        try (PreparedStatement preparedStatement =
                     JdbcUtils.getPreparedStatement(String.format("SELECT * FROM %s WHERE name=?", tableName))) {
            preparedStatement.setString(1, name);
            return ResultSetConverter.convertToSpecialty(preparedStatement.executeQuery());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    @Override
    public Specialty getById(Long id) {
        try (PreparedStatement preparedStatement =
                     JdbcUtils.getPreparedStatement(String.format("SELECT * FROM %s WHERE id=?", tableName))) {
            preparedStatement.setLong(1, id);
            return ResultSetConverter.convertToSpecialty(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Specialty update(Specialty specialty) {
        try (PreparedStatement preparedStatement =
                     JdbcUtils.getPreparedStatement(String.format("UPDATE %s SET name=? WHERE id=?", tableName))) {
            preparedStatement.setString(1, specialty.getName());
            preparedStatement.setLong(2, specialty.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getByName(specialty.getName());
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement preparedStatement =
                     JdbcUtils.getPreparedStatement(String.format("DELETE FROM %s WHERE id=?", tableName))) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}