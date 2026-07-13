// dao/IDAO.java
package hdkmanagement.dao;

import java.util.List;

public interface IDAO<T> {
    // Thêm mới
    boolean insert(T entity);
    
    // Cập nhật
    boolean update(T entity);
    
    // Xóa (xóa cứng hoặc xóa mềm)
    boolean delete(int id);
    
    // Lấy theo ID
    T getById(int id);
    
    // Lấy tất cả
    List<T> getAll();
    
    // Tìm kiếm
    List<T> search(String keyword);
}