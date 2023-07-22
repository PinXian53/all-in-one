package com.pino.db.dao;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@RequiredArgsConstructor
public class BaseDAO<S, ID> {

    private final JpaRepository<S, ID> jpaRepository;

    public void save(S entity) {
        jpaRepository.save(entity);
    }

    public Iterable<S> saveAll(Iterable<S> entities) {
        return jpaRepository.saveAll(entities);
    }

    public Optional<S> findById(ID id) {
        return jpaRepository.findById(id);
    }

    public boolean existsById(ID id) {
        return jpaRepository.existsById(id);
    }

    public List<S> findAll() {
        return IterableUtils.toList(jpaRepository.findAll());
    }

    public List<S> findAll(Sort sort) {
        return IterableUtils.toList(jpaRepository.findAll(sort));
    }

    public Page<S> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable);
    }

    public Iterable<S> findAllById(Iterable<ID> ids) {
        return jpaRepository.findAllById(ids);
    }

    public long count() {
        return jpaRepository.count();
    }

    public void deleteById(ID id) {
        jpaRepository.deleteById(id);
    }

    public void delete(S entity) {
        jpaRepository.delete(entity);
    }

    public void deleteAllById(Iterable<? extends ID> ids) {
        jpaRepository.deleteAllById(ids);
    }

    public void deleteAll(Iterable<? extends S> entities) {
        jpaRepository.deleteAll(entities);
    }

    public void deleteAll() {
        jpaRepository.deleteAll();
    }
}
