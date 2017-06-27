package com.sap.cloud.samples.ariba.discovery.rfx.daos;

import java.text.MessageFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.samples.ariba.discovery.rfx.persistency.EntityManagerProvider;

/**
 * Basic DAO for CRUD operations.
 * 
 * @param <T>
 *            - type of the DAO
 */
public class BaseDao<T> {

	private static final String ERROR_NAMED_QUERY_NOT_FOUND_OR_INVALID = "Named query not found or invalid [Name: {0}, Type: {1}].";
	private static final String ERROR_CANNOT_CREATE_NAMED_QUERY_WITH_NULL_NAME = "Cannot create named query with null name [Type: {0}].";
	private static final String ERROR_CANNOT_DELETE_ENTITY_WITH_NULL_ID_TYPE = "Cannot delete entity with null ID [Type: {0}].";
	private static final String ERROR_CANNOT_READ_ENTITY_WITH_NULL_ID = "Cannot read entity with null ID [Type: {0}].";
	private static final String ERROR_CANNOT_PERSIST_NULL_OBJECTS = "Cannot persist null objects [Type: {0}].";
	private static final String ERROR_CANNOT_MERGE_NULL_OBJECTS = "Cannot merge null objects [Type: {0}].";

	private static final String DEBUG_NAMED_QUERY_CREATED = "Named Query created [Query: {}, Type: {}].";
	private static final String DEBUG_ENTITY_DELETED = "Entity deleted [ID: {}, Type: {}]";
	private static final String DEBUG_ALL_ENTITIES_DELETED = "All entities deleted [Size: {}, Type: {}].";
	private static final String DEBUG_THERE_IS_NO_ENTITIES_IN_THE_DATABASE = "There is no entities in the database to be deleted [Type: []].";
	private static final String DEBUG_ENITY_FOUND_ID = "Enity found [ID: {}, Type: {}]";
	private static final String DEBUG_READ_ALL_ENTITIES_COUNT = "Read all entities [Size: {}, Type: {}].";
	private static final String DEBUG_ENTITY_PERSISTED = "Entity persisted [Entity: {}, Type: {}].";
	private static final String DEBUG_ENTITY_UPDATED = "Entity updated [Entity: {}, Type: {}].";
	private static final String DEBUG_ENTITY_NOT_FOUND_ID = "Entity not found [ID: {}, Type: {}].";

	protected EntityManager entityManager;
	private Class<T> type;

	private final static Logger logger = LoggerFactory.getLogger(BaseDao.class);

	/**
	 * @param type
	 *            type of the DAO
	 */
	protected BaseDao(final Class<T> type) {
		this.entityManager = EntityManagerProvider.createEntityManager();
		this.type = type;
	}

	/**
	 * Updates the given entity in the database.
	 * 
	 * @param entity
	 *            the entity to be merged in the database
	 * @return the merged entity.
	 */
	public T update(final T entity) {
		if (entity == null) {
			String errorMessage = MessageFormat.format(ERROR_CANNOT_MERGE_NULL_OBJECTS, type);
			logger.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}
		entityManager.getTransaction().begin();
		T updated = entityManager.merge(entity);
		entityManager.getTransaction().commit();
		logger.debug(DEBUG_ENTITY_UPDATED, entity, type);
		return updated;
	}

	/**
	 * Persists the given entity in the database.
	 * 
	 * @param entity
	 *            the entity to be persisted in the database
	 * @return the persisted entity.
	 */
	public T create(final T entity) {
		if (entity == null) {
			String errorMessage = MessageFormat.format(ERROR_CANNOT_PERSIST_NULL_OBJECTS, type);
			logger.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
		logger.debug(DEBUG_ENTITY_PERSISTED, entity, type);
		return entity;
	}

	/**
	 * Returns all entities persisted in the database.
	 * 
	 * @return all entities persisted in the database.
	 */
	public List<T> readAll() {
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
		criteriaQuery.select(criteriaQuery.from(type));
		final TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
		List<T> entities = query.getResultList();
		logger.debug(DEBUG_READ_ALL_ENTITIES_COUNT, entities.size(), type);
		return entities;
	}

	/**
	 * Returns the entity with the given ID.
	 * 
	 * @param id
	 *            the id of the entity
	 * @return the entity with the given id.
	 */
	public T read(final String id) {
		if (id == null) {
			String errorMessage = MessageFormat.format(ERROR_CANNOT_READ_ENTITY_WITH_NULL_ID, type);
			logger.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}
		T entity = entityManager.find(type, id);
		if (entity == null) {
			logger.debug(DEBUG_ENTITY_NOT_FOUND_ID, id, type);
		} else {
			logger.debug(DEBUG_ENITY_FOUND_ID, id, type);

		}
		return entity;
	}

	/**
	 * Returns whether an entity with the given id exists.
	 * 
	 * @param id
	 *            the id of the entity
	 * @return whether an entity with the given id exists.
	 */
	public boolean doesExist(String id) {
		T entity = read(id);
		if (entity == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Deletes all entities persisted in the database.
	 */
	public List<T> deleteAll() {
		List<T> entities = readAll();
		if (entities.size() > 0) {
			entityManager.getTransaction().begin();
			for (T entity : entities) {
				entityManager.remove(entity);
			}
			entityManager.getTransaction().commit();
			logger.debug(DEBUG_ALL_ENTITIES_DELETED, entities.size(), type);
		} else {
			logger.debug(DEBUG_THERE_IS_NO_ENTITIES_IN_THE_DATABASE, type);
		}
		return entities;
	}

	/**
	 * Deletes the Entity with the given ID.
	 * 
	 * @param id
	 *            the id of the entity
	 */
	public T delete(final String id) {
		if (id == null) {
			String errorMessage = MessageFormat.format(ERROR_CANNOT_DELETE_ENTITY_WITH_NULL_ID_TYPE, type);
			logger.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}
		T entity = read(id);
		if (entity == null) {
			logger.error(DEBUG_ENTITY_NOT_FOUND_ID, id, type);
		} else {
			entityManager.getTransaction().begin();
			entityManager.remove(entity);
			entityManager.getTransaction().commit();
			logger.debug(DEBUG_ENTITY_DELETED, id, type);
		}
		return entity;
	}

	/**
	 * Returns the query by given name.
	 * 
	 * @param name
	 *            the name of the query
	 * @return the typed query.
	 */
	public TypedQuery<T> createNamedQuery(String name) {
		if (name == null) {
			String errorMessage = MessageFormat.format(ERROR_CANNOT_CREATE_NAMED_QUERY_WITH_NULL_NAME, type);
			logger.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
		criteriaQuery.select(criteriaQuery.from(type));
		TypedQuery<T> typedQuery = null;
		try {
			typedQuery = (TypedQuery<T>) entityManager.createNamedQuery(name, type);
		} catch (IllegalArgumentException e) {
			String errorMessage = MessageFormat.format(ERROR_NAMED_QUERY_NOT_FOUND_OR_INVALID, name, type);
			logger.error(errorMessage, e);
			throw new IllegalArgumentException(errorMessage, e);
		}
		logger.debug(DEBUG_NAMED_QUERY_CREATED, typedQuery, type);
		return typedQuery;
	}

}
