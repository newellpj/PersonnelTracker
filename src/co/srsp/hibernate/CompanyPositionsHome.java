
package co.srsp.hibernate;

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import co.srsp.hibernate.orm.CompanyPositions;

/**
 * Home object for domain model class CompanyPositions.
 * @see .CompanyPositions
 * @author Hibernate Tools
 */
public class CompanyPositionsHome {

	private static final Log log = LogFactory.getLog(CompanyPositionsHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(CompanyPositions transientInstance) {
		log.debug("persisting CompanyPositions instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(CompanyPositions instance) {
		log.debug("attaching dirty CompanyPositions instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CompanyPositions instance) {
		log.debug("attaching clean CompanyPositions instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(CompanyPositions persistentInstance) {
		log.debug("deleting CompanyPositions instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CompanyPositions merge(CompanyPositions detachedInstance) {
		log.debug("merging CompanyPositions instance");
		try {
			CompanyPositions result = (CompanyPositions) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public CompanyPositions findById(java.lang.Integer id) {
		log.debug("getting CompanyPositions instance with id: " + id);
		try {
			CompanyPositions instance = (CompanyPositions) sessionFactory.getCurrentSession().get("CompanyPositions",
					id);
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

	public List findByExample(CompanyPositions instance) {
		log.debug("finding CompanyPositions instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("CompanyPositions")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
