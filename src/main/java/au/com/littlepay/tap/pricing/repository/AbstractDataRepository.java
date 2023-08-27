package au.com.littlepay.tap.pricing.repository;

import java.util.List;

public interface AbstractDataRepository<T> {

    void save(T data);

    List<T> findAll();

    T findById(String id);

}
