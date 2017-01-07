package co.srsp.hibernate;

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import co.srsp.hibernate.orm.EmployeeToSkillsetRatings;

/**
 * Home object for domain model class EmployeeToSkillsetRatings.
 * @see .EmployeeToSkillsetRatings
 * @author Hibernate Tools
 */
public class EmployeeToSkillsetRatingsHome {

	private static final Log log = LogFactory.getLog(EmployeeToSkillsetRatingsHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(EmployeeToSkillsetRatings transientInstance) {
		log.debug("persisting EmployeeToSkillsetRatings instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(EmployeeToSkillsetRatings instance) {
		log.debug("attaching dirty EmployeeToSkillsetRatings instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(EmployeeToSkillsetRatings instance) {
		log.debug("attaching clean EmployeeToSkillsetRatings instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(EmployeeToSkillsetRatings persistentInstance) {
		log.debug("deleting EmployeeToSkillsetRatings instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public EmployeeToSkillsetRatings merge(EmployeeToSkillsetRatings detachedInstance) {
		log.debug("merging EmployeeToSkillsetRatings instance");
		try {
			EmployeeToSkillsetRatings result = (EmployeeToSkillsetRatings) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public EmployeeToSkillsetRatings findById(int id) {
		log.debug("getting EmployeeToSkillsetRatings instance with id: " + id);
		try {
			EmployeeToSkillsetRatings instance = (EmployeeToSkillsetRatings) sessionFactory.getCurrentSession()
					.get("EmployeeToSkillsetRatings", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(EmployeeToSkillsetRatings instance) {
		log.debug("finding EmployeeToSkillsetRatings instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("EmployeeToSkillsetRatings")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
