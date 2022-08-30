package repository.jdbc;

import model.Skill;
import repository.SkillRepository;
import utils.JdbcUtils;
import utils.ResultSetConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JDBCSkillRepositoryImpl implements SkillRepository {
    private static final String tableName = "skills";

    @Override
    public List<Skill> getAll() {
        String SQL = "SELECT * FROM " + tableName;
        try (ResultSet resultSet = JdbcUtils.getPreparedStatement(SQL).executeQuery()) {
            return ResultSetConverter.convertToSkillsList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Skill create(Skill skill) {
        insertSkill(skill);
        return getByName(skill.getName());
    }

    private void insertSkill(Skill skill) {
        try (PreparedStatement preparedStatement =
                     JdbcUtils.getPreparedStatement(String.format("INSERT INTO %s (name) VALUES(?)", tableName))) {
            preparedStatement.setString(1, skill.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Skill getByName(String name) {
        try (PreparedStatement preparedStatement =
                     JdbcUtils.getPreparedStatement(String.format("SELECT * FROM %s WHERE name=?", tableName))) {
            preparedStatement.setString(1, name);
            return ResultSetConverter.convertToSkill(preparedStatement.executeQuery());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Skill getById(Long id) {
        try (PreparedStatement preparedStatement =
                     JdbcUtils.getPreparedStatement(String.format("SELECT * FROM %s WHERE id=?", tableName))) {
            preparedStatement.setLong(1, id);
            return ResultSetConverter.convertToSkill(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Skill update(Skill skill) {
        try (PreparedStatement preparedStatement =
                     JdbcUtils.getPreparedStatement(String.format("UPDATE %s SET name=? WHERE id=?", tableName))) {
            preparedStatement.setString(1, skill.getName());
            preparedStatement.setLong(2, skill.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getByName(skill.getName());
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